from datetime import datetime, timezone, timedelta
from tempfile import mkdtemp

from flask import Flask, flash, redirect, render_template, request, session, url_for, jsonify, make_response
from flask_session import Session

import matplotlib.pyplot as plt
from matplotlib.ticker import MaxNLocator
import numpy as np
import io
import joblib

from helper import create_connection, execute_query, execute_read_query, login_required, admin_required, new_record, msg


# Configure application
app = Flask(__name__)

# Ensure templates are auto-reloaded
app.config["TEMPLATES_AUTO_RELOAD"] = True

# Configure session to use filesystem (instead of signed cookies)
app.config["SESSION_PERMANENT"] = False
app.config["SESSION_TYPE"] = "filesystem"
Session(app)

# Use SQLite database
PATH = "PSAH.sqlite"
conn = create_connection(PATH)
cursor = conn.cursor()

mytz = timezone(timedelta(hours=8))

#Configure admin user accounts
admin_usernames = {"mainadmin": "12345"}

encoder = joblib.load("static/model_encoder.joblib")
scaler = joblib.load("static/model_scaler.joblib")
model = joblib.load("static/random_forest_regression_model.joblib")
def predict(input):

    # input format:
    # applied subject, year until now, GPA,current subject, is honor college, GMAT, GRE, english test exempted, TOFEL, IELTS, internship
    fields = ['applied subject', 'year until now', 'GPA', 'current subject', 'is honor college', 'GMAT', 'GRE', 'english test exempted', 'TOFEL', 'IELTS', 'internship']

    if not (len(input) == len(fields)):
        raise ValueError("input size doesn't fit field size")
    else:
        input = [{fields[i]: input[i] for i in range(len(input))}]

    x = encoder.transform(input)
    cols_to_scale = ["GPA", "GMAT", "GRE", "TOFEL", "IELTS"]
    x_scaled = x.copy()
    x_scaled[cols_to_scale] = scaler.transform(x_scaled[cols_to_scale])
    predicted = model.predict(x_scaled)
    return predicted[0]

@app.after_request
def after_request(response):
    """Ensure responses aren't cached"""
    response.headers["Cache-Control"] = "no-cache, no-store, must-revalidate"
    response.headers["Expires"] = 0
    response.headers["Pragma"] = "no-cache"
    return response

@app.route("/", methods=["GET"])
def index():
    """Homepage"""
    if session.get("type") in ["Mainadmin", "Subadmin"]:
        return redirect("/admin")

    return render_template('Homepage/index.html')

@app.route("/help", methods=["GET"])
def help():
    """help"""
    return render_template('Homepage/help.html')

@app.route("/signup", methods=["GET", "POST"])
def register():
    """Register accounts"""
    with create_connection(PATH) as conn:
        cursor = conn.cursor()
        if request.method == 'GET':
            return render_template("Homepage/signup.html")
        else:
            username = request.form.get("username").strip()
            password = request.form.get("password")
            confirm = request.form.get("confirm_password")
            student_id = request.form.get("Number")
            error1 = ''
            error2 = ''
            error3 = ''

            # Username already exists
            rows_name = execute_read_query(cursor, "SELECT * FROM users WHERE username = ?", username)
            rows_stid = execute_read_query(cursor, "SELECT * FROM users WHERE student_id = ?", student_id)
            if len(rows_name) >= 1:
                error1 = "Username already exists"
            if confirm != password:
                error2 = "Your confirm doesn't patch original password"
            if len(rows_stid) >= 1:
                error3 = "Student id already exists"

            if error1 != '' or error2 != '' or error3 != '':
                return render_template("Homepage/signup.html", error1=error1, error2=error2, error3=error3)

            execute_query(cursor, "INSERT INTO users (username, password, student_id, banned) VALUES (?, ?, ?, ?)", username, password, student_id, 0)
            id = execute_read_query(cursor, "SELECT id FROM users WHERE username = ?", username)[0][0]
            session['student_id'] = student_id

    session["type"] = "UM Student"
    session["user_id"] = id
    session["user_name"] = username

    return redirect("/")

@app.route("/login", methods=["GET", "POST"])
def login():
    """Log user in"""
    # Forget any user_id
    session.clear()

    # User reached route via POST (as by submitting a form via POST)
    if request.method == "POST":
        type = request.form.get("type")
        with create_connection(PATH) as conn:
            cursor = conn.cursor()
            # Query database for username
            if type == "user":
                rows = execute_read_query(cursor, "SELECT id, password, student_id, username, banned FROM users WHERE username = ?", request.form.get("username"))
                if (len(rows) == 1) and (request.form.get("password") == rows[0][1]):
                # Log student in
                    if rows[0][4] != 1:
                        session["type"] = "UM Student"
                        session["student_id"] = rows[0][2]
                        session["user_id"] = rows[0][0]
                        session["user_name"] = rows[0][3]
                        return redirect("/")
                    else:
                        return render_template("Homepage/login.html", error="Your account is banned")
                else:
                    return render_template("Homepage/login.html", error="Incorrect username or password", user=1)

            elif type == "admin":
                rows = execute_read_query(cursor, "SELECT id, password, username FROM subadmin WHERE username = ?", request.form.get("username"))
                if request.form.get("username") in admin_usernames and request.form.get("password") == admin_usernames[request.form.get("username")]:
                    #Log main admin in
                    session["type"] = "Mainadmin"
                    session["user_name"] = "main admin"
                    return redirect("/admin")
                elif (len(rows) == 1) and (request.form.get("password") == rows[0][1]):
                    #Log sub admin in
                    session["type"] = "Subadmin"
                    session["id"] = rows[0][0]
                    session["user_name"] = rows[0][2]
                    return redirect("/admin")
                else:
                    return render_template("Homepage/login.html", error="Incorrect username or password", admin=1)

    # User reached route via GET (as by clicking a link or via redirect)
    else:
        return render_template("Homepage/login.html")

@app.route("/logout", methods=["GET"])
def logout():
    session.clear()
    return redirect('/')

@app.route("/forget", methods=["GET", "POST"])
def forget():
    with create_connection(PATH) as conn:
        if request.method == "GET":
            return render_template("Homepage/forget_username.html")

        elif request.method == "POST":
            cursor = conn.cursor()
            student_id = request.form.get("student_id")
            cursor.execute("SELECT username FROM users WHERE student_id = ?", (student_id,))
            row = cursor.fetchone()
            return jsonify(row=row)

@app.route("/feedback", methods=["GET", "POST"])
@login_required
@admin_required
def feedback():
    with create_connection(PATH) as conn:
        cursor = conn.cursor()
        if request.method == "GET":
            return render_template("feedback.html")
        else:
            description = request.form.get("description")
            date = datetime.now(mytz).strftime('%Y-%m-%d %H:%M:%S')
            username = session["user_name"]
            execute_query(conn, "INSERT INTO feedback (username, description, date) VALUES (?, ?, ?);", username, description, date)
            return msg("Your feedback has been received.", 200)

@app.route("/admin", methods=["GET"])
@login_required
@admin_required
def admin():
    if session["type"] == "Subadmin":
        name = "admin " + session["user_name"]
    else:
        name = session["user_name"]

    return render_template("Homepage/admin.html", name=name)

@app.route("/view_feedback", methods=["GET", "POST"])
@login_required
@admin_required
def view_feedback():
    conn = db.engine.connect()
    if request.method == "GET":
        lines = execute_read_query(conn, "SELECT id, username, date FROM feedback ORDER BY date DESC")
        return render_template("view_feedback.html", lines=lines)
    else:
        id = request.form.get("id")
        rows = execute_read_query(conn, "SELECT * FROM feedback WHERE id = ?", id)
        username = rows[0]["username"]
        description = rows[0]["description"]
        date = rows[0]["date"]
        return render_template("each_feedback.html", username=username, description=description, date=date)

@app.route("/admin/data", methods=["GET"])
@login_required
@admin_required
def manage_data():
    with create_connection(PATH) as conn:
        cursor = conn.cursor()

        NUM_PER_PAGE = 10
        page = request.args.get('page', 1, type=int)
        action = request.args.get('action', '')

        if action in ['', 'Apply']:
            script = """SELECT schools.SchoolName, records.Result, records.AppliedProgram, records.CurrentMajor, records.GPA,
                                records.GMAT, records.GRE, records.TOFEL, records.IELTS, records.IsHonorCollege,
                                records.EnglishTestExempted, records.HasInternship, records.Year, records.id
                        FROM records
                        JOIN schools ON records.SchoolId = schools.id
                        WHERE Result LIKE ? AND schools.SchoolName LIKE ? AND CurrentMajor LIKE ? AND AppliedProgram LIKE ?
                                AND Year BETWEEN ? AND ? AND GPA BETWEEN ? AND ? AND GMAT BETWEEN ? AND ?
                                AND GRE BETWEEN ? AND ? AND TOFEL BETWEEN ? AND ? AND IELTS BETWEEN ? AND ?
                                AND IsHonorCollege LIKE ? AND HasInternship LIKE ?
                        LIMIT ? OFFSET ?"""

            script_full = """SELECT records.id
                        FROM records
                        JOIN schools ON records.SchoolId = schools.id
                        WHERE Result LIKE ? AND schools.SchoolName LIKE ? AND CurrentMajor LIKE ? AND AppliedProgram LIKE ?
                                AND Year BETWEEN ? AND ? AND GPA BETWEEN ? AND ? AND GMAT BETWEEN ? AND ?
                                AND GRE BETWEEN ? AND ? AND TOFEL BETWEEN ? AND ? AND IELTS BETWEEN ? AND ?
                                AND IsHonorCollege LIKE ? AND HasInternship LIKE ?
                        """

            subjects = execute_read_query(cursor, "SELECT subject_name FROM subjects")

            school = request.args.get("school", "")
            result = request.args.get("result", "")
            current_subject = request.args.get("currentSubject", "")
            target_subject = request.args.get("targetSubject", "")
            id_current = 0
            id_target = 0

            if current_subject:
                id_current = execute_read_query(cursor, "SELECT id FROM subjects WHERE subject_name = ?", current_subject)[0][0]
            if target_subject:
                id_target = execute_read_query(cursor, "SELECT id FROM subjects WHERE subject_name = ?", target_subject)[0][0]

            year_start = request.args.get("yearStart", 0, type=int)
            year_end = request.args.get("yearEnd", 2023, type=int)
            gpa_start = request.args.get("gpaStart", 0.0, type=float)
            gpa_end = request.args.get("gpaEnd", 4.0, type=float)
            gmat_start = request.args.get("GMATStart", 0, type=int)
            gmat_end = request.args.get("GMATEnd", 800, type=int)
            gre_start = request.args.get("GREStart", 0, type=int)
            gre_end = request.args.get("GREEnd", 340, type=int)
            tofel_start = request.args.get("TOFELStart", 0, type=int)
            tofel_end = request.args.get("TOFELEnd", 120, type=int)
            ielts_start = request.args.get("IELTSStart", 0, type=float)
            ielts_end = request.args.get("IELTSEnd", 9, type=float)

            is_honor_college = request.args.get("IsHonorCollege", "")
            has_internship = request.args.get("HasInternship", "")

            rows = execute_read_query(cursor, script, '%'+ result +'%', '%' + school + '%', '%'+ current_subject +'%', '%'+ target_subject +'%', year_start, year_end, gpa_start, gpa_end, gmat_start, gmat_end,
                                    gre_start, gre_end, tofel_start, tofel_end, ielts_start, ielts_end, '%'+ is_honor_college +'%', '%'+ has_internship +'%'
                                    , NUM_PER_PAGE, (page - 1) * NUM_PER_PAGE)

            full_rows = execute_read_query(cursor, script_full, '%'+ result +'%', '%' + school + '%', '%'+ current_subject +'%', '%'+ target_subject +'%', year_start, year_end, gpa_start, gpa_end, gmat_start, gmat_end,
                                    gre_start, gre_end, tofel_start, tofel_end, ielts_start, ielts_end, '%'+ is_honor_college +'%', '%'+ has_internship +'%'
                                    )
            ids = [row[0] for row in full_rows]

            return render_template("admin/manage_data.html", rows=rows, subjects=subjects, page=page, len=len, result=result, school=school,
                                    currentSubject=current_subject, targetSubject=target_subject, yearStart=year_start, yearEnd=year_end, gpaStart=gpa_start, gpaEnd=gpa_end,
                                    currentIndex=id_current, targetIndex=id_target, gmat_start=gmat_start, gmat_end=gmat_end, gre_start=gre_start, gre_end=gre_end,
                                    tofel_start=tofel_start, tofel_end=tofel_end, ielts_start=ielts_start, ielts_end=ielts_end, is_honor_college=is_honor_college, has_internship=has_internship, ids=ids)

@app.route("/admin/delete", methods=["POST"])
@login_required
@admin_required
def delete_data():
    with create_connection(PATH) as conn:
        cursor = conn.cursor()
        ids = request.get_json().get('ids')
        print(type(ids))
        print(ids)

        if isinstance(ids, list):
            for id in ids:
                execute_query(cursor, "DELETE FROM records WHERE id = ?", id)
        else:
            execute_query(cursor, "DELETE FROM records WHERE id = ?", ids)


        return '', 200

@app.route("/admin/edit/<int:id>", methods=["GET", "POST"])
@login_required
@admin_required
def edit_data(id):
    with create_connection(PATH) as conn:
        cursor = conn.cursor()
        subjects = execute_read_query(cursor, "SELECT subject_name FROM subjects")
        info = execute_read_query(cursor, """SELECT schools.SchoolName, Result, AppliedProgram, CurrentMajor, GPA, GMAT, GRE,
                                    TOFEL, IELTS, IsHonorCollege, EnglishTestExempted, HasInternship, Year
                                    FROM records
                                    JOIN schools on records.SchoolId = schools.id
                                    WHERE records.id = ?""", id)[0]
        if request.method == "POST":
            school = request.form.get("school", info[0])
            result = request.form.get("result", info[1], type=int)
            target = request.form.get("target", info[2])
            current = request.form.get("current", info[3])
            gpa = request.form.get("gpa", info[4], type=float)
            gmat = request.form.get("gmat", info[5], type=float)
            gre = request.form.get("gre", info[6], type=float)
            tofel = request.form.get("tofel", info[7], type=float)
            ielts = request.form.get("ielts", info[8], type=float)
            honor = 1 if request.form.get("honor") else 0
            exempted = 1 if request.form.get("exempted") else 0
            intern = 1 if request.form.get("intern") else 0
            year = request.form.get("year", info[12], type=int)

            rows = execute_read_query(cursor, "SELECT id FROM schools WHERE SchoolName = ?", school)
            if len(rows) != 1:
                error1 = "Invalid school"
                return render_template("admin/edit_data.html", error1=error1, info=info, subjects=subjects)
            else:
                school_id = rows[0][0]
                execute_query(cursor, """UPDATE records SET SchoolId = ?, Result = ?, AppliedProgram = ?, CurrentMajor = ?, GPA = ?, GMAT = ?, GRE = ?,
                                    TOFEL = ?, IELTS = ?, IsHonorCollege = ?, EnglishTestExempted = ?, HasInternship = ?, Year = ? WHERE id = ?""",
                                    school_id, result, target, current, gpa, gmat, gre, tofel, ielts, honor, exempted, intern, year, id)
                return redirect("/admin/data")

        else:

            return render_template("admin/edit_data.html", info=info, subjects=subjects)



@app.route("/admin/ban", methods=["GET", "POST"])
@login_required
@admin_required
def ban_user():
    with create_connection("PSAH.sqlite") as conn:
        cursor = conn.cursor()
        if request.method == "GET":
            action = request.args.get("action")

            if action == "search name":
                searched = request.args.get("search", "")
                lines = execute_read_query(cursor, "SELECT id, username, banned FROM users WHERE username LIKE ?", '%'+searched+'%')

                return render_template("admin/ban.html", lines=lines, name_searched=searched)

            elif action == "search id":
                if searched := request.args.get("search", type=int):
                    lines = execute_read_query(cursor, "SELECT id, username, banned FROM users WHERE id = ?", searched)
                else:
                    lines = execute_read_query(cursor, "SELECT id, username, banned FROM users")

                return render_template("admin/ban.html", lines=lines, id_searched=searched)

            else:
                lines = execute_read_query(cursor, "SELECT id, username, banned FROM users")
                return render_template("admin/ban.html", lines=lines)

        else:
            id = int(request.json['id'])
            action = request.json['action']

            if action == "Ban":
                execute_query(cursor, "UPDATE users SET banned = 1 WHERE id = ?", id)
            elif action == "Unban":
                execute_query(cursor, "UPDATE users SET banned = 0 WHERE id = ?", id)

            return '', 200

@app.route("/admin/subadmin", methods=["GET"])
@login_required
@admin_required
def manage_subadmin():
    if session["type"] != "Mainadmin":
        return msg("You're not authorized to access this page")

    with create_connection(PATH) as conn:
        cursor = conn.cursor()
        lines = execute_read_query(cursor, "SELECT id, username FROM subadmin")
        return render_template("admin/manage_subadmin.html", lines=lines)

@app.route("/admin/subadmin/create", methods=["GET", "POST"])
@login_required
@admin_required
def create_subadmin():
    if session["type"] != "Mainadmin":
        return msg("You're not authorized to access this page")

    with create_connection(PATH) as conn:
        cursor = conn.cursor()

        if request.method == "GET":
            return render_template("admin/create_subadmin.html")

        else:
            username = request.form.get("username")
            password = request.form.get("password")

            error1 = ''
            rows = execute_read_query(cursor, "SELECT * FROM subadmin WHERE username = ?", username)
            if (len(rows) >= 1) or username in admin_usernames:
                error1 = "username already exists"
                return render_template("admin/create_subadmin.html", error1=error1)
            else:
                execute_query(cursor, "INSERT INTO subadmin (username, password) VALUES (?, ?)", username, password)
                return redirect("/admin/subadmin")

@app.route("/admin/subadmin/edit/<int:id>", methods=["GET", "POST"])
@login_required
@admin_required
def modify_subadmin(id):
    with create_connection(PATH) as conn:
        cursor = conn.cursor()
        if session["type"] != "Mainadmin":
            return msg("You're not authorized to access this page")

        if request.method == "GET":
            row = execute_read_query(cursor, "SELECT username, password FROM subadmin WHERE id = ?", id)[0]
            username, password = row[0], row[1]
            return render_template("admin/edit_subadmin.html", username=username, password=password)
        else:
            row = execute_read_query(cursor, "SELECT username, password FROM subadmin WHERE id = ?", id)[0]
            username, password = row[0], row[1]
            new_username = request.form.get("new_username")
            new_password = request.form.get("new_password")

            error1 = ''
            rows = execute_read_query(cursor, "SELECT id FROM subadmin WHERE username = ?", new_username)
            if len(rows) >= 1 and rows[0][0] != id:
                error1 = "username already exists"
                return render_template("admin/edit_subadmin.html", error1=error1, username=username, password=password)
            else:
                execute_query(cursor, "UPDATE subadmin SET username = ?, password = ? WHERE id = ?", new_username, new_password, id)
                return redirect("/admin/subadmin")

@app.route("/admin/subadmin/delete", methods=["POST"])
@login_required
@admin_required
def delete_subadmin():
    with create_connection(PATH) as conn:
        cursor = conn.cursor()
        if session["type"] != "Mainadmin":
            return msg("You're not authorized to access this page")

        id = request.form.get("id")
        execute_query(cursor, "DELETE FROM subadmin WHERE id = ?", id)
        return redirect("/admin/subadmin")



@app.route("/upload", methods=["GET", "POST"])
@login_required
def upload():
    with create_connection(PATH) as conn:
        cursor = conn.cursor()
        subjects = execute_read_query(cursor, "SELECT subject_name FROM subjects")
        if request.method == "GET":
            return render_template("user/upload.html", subjects=subjects)
        else:
            error1, error2, error3 = '', '', ''
            name = request.form.get("school")
            rows = execute_read_query(cursor, "SELECT id FROM schools WHERE SchoolName = ?", name)
            if len(rows) != 1:
                error1 = "Invalid school"
            else:
                id = rows[0][0]


            subject = request.form.get("subject")
            rows = execute_read_query(cursor, "SELECT id FROM subjects WHERE subject_name = ?", subject)
            if len(rows) != 1:
                error2 = "Invalid subject"

            result = 1 if request.form.get("result") == "Accepted" else 0
            year = request.form.get("year", type=int)
            GPA = request.form.get("gpa", 0, type=float)

            current_subject = request.form.get("current_subject")
            rows = execute_read_query(cursor, "SELECT id FROM subjects WHERE subject_name = ?", current_subject)
            if len(rows) != 1:
                error3 = "Invalid subject"

            honor_college = 1 if request.form.get("honors") else 0
            GMAT = request.form.get("gmat", 0, type=float)
            GRE = request.form.get("gre", 0, type=float)
            TOFEL = request.form.get("tofel", 0, type=float)
            IELTS = request.form.get("ielts", 0, type=float)
            exempted = 1 if request.form.get("exempted") else 0
            internship = 1 if request.form.get("internship") else 0

            if error1 != '' or error2 != '' or error3 != '':
                return render_template("user/upload.html", error1=error1, error2=error2, error3=error3, subjects=subjects)

            new_record(id, subject, result, year, GPA, current_subject, honor_college, GMAT, GRE, TOFEL, IELTS, exempted, internship)

            return msg("Your record has been uploaded!", 200)

@app.route("/search_school", methods=["GET"])
def search_school():
    search = request.args.get('search', '')
    with create_connection(PATH) as conn:
        cursor = conn.cursor()
        rows = execute_read_query(cursor, "SELECT id, SchoolName FROM schools WHERE SchoolName LIKE ?", '%' + search + '%')
        filtered_data = [{'id': row[0], 'name': row[1]} for row in rows]
        return jsonify(filtered_data)

@app.route("/university", methods=["GET", "POST"])
@login_required
def university():
    NUM_PER_PAGE = 10
    if request.method == "GET":
        with create_connection(PATH) as conn:
            cursor = conn.cursor()
            q = request.args.get('q', '')
            page = request.args.get('page', 1, type=int)
            rows = execute_read_query(cursor, "SELECT id, SchoolName, records_num, WebPage from schools WHERE SchoolName like ? ORDER BY records_num DESC LIMIT ? OFFSET ?;", ('%' + q + '%'), NUM_PER_PAGE, (page - 1) * NUM_PER_PAGE)
            return render_template("user/university.html", result=rows, q=q, page=page, len=len)
    else:
        if action := request.form.get("action"):
            id = request.form.get("id")
            if action == "View":
                return redirect(url_for('university_view', id=id))
            elif action == "Comment":
                return redirect(url_for('university_comment', id=id))

@app.route("/university/view/<int:id>", methods=["GET"])
@login_required
def university_view(id):
    with create_connection(PATH) as conn:
        cursor = conn.cursor()
        NUM_PER_PAGE = 10

        page = request.args.get('page', 1, type=int)
        # Filters
        current_subject = request.args.get("currentSubject", "")
        target_subject = request.args.get("targetSubject", "")
        id_current = 0
        id_target = 0

        if current_subject:
            id_current = execute_read_query(cursor, "SELECT id FROM subjects WHERE subject_name = ?", current_subject)[0][0]
        if target_subject:
            id_target = execute_read_query(cursor, "SELECT id FROM subjects WHERE subject_name = ?", target_subject)[0][0]

        year_start = request.args.get("yearStart", "0", type=int)
        year_end = request.args.get("yearEnd", "2023", type=int)
        gpa_start = request.args.get("gpaStart", "0.0", type=float)
        gpa_end = request.args.get("gpaEnd", "4.0", type=float)
        gmat_start = request.args.get("GMATStart", "0", type=int)
        gmat_end = request.args.get("GMATEnd", "800", type=int)
        gre_start = request.args.get("GREStart", "0", type=int)
        gre_end = request.args.get("GREEnd", "340", type=int)
        tofel_start = request.args.get("TOFELStart", "0", type=int)
        tofel_end = request.args.get("TOFELEnd", "120", type=int)
        ielts_start = request.args.get("IELTSStart", "0", type=float)
        ielts_end = request.args.get("IELTSEnd", "9", type=float)

        is_honor_college = request.args.get("IsHonorCollege", "both")
        has_internship = request.args.get("HasInternship", "both")

        def convert(to_convert):
            if to_convert == "both":
                return "%"
            elif to_convert == "yes":
                return "1"
            elif to_convert == "no":
                return "0"

        is_honor_college_code, has_internship_code = convert(is_honor_college), convert(has_internship)

        subjects = execute_read_query(cursor, "SELECT subject_name FROM subjects")
        school_name = execute_read_query(cursor, "SELECT SchoolName FROM schools where id = ?", id)[0][0]
        rows = execute_read_query(cursor, """SELECT id, GPA, AppliedProgram, CurrentMajor, IsHonorCollege, GMAT,
                                GRE, TOFEL, IELTS, EnglishTestExempted, HasInternship, Year FROM records WHERE Result = 1 AND SchoolId = ?
                                AND CurrentMajor LIKE ? AND AppliedProgram LIKE ? AND Year BETWEEN ? AND ? AND GPA BETWEEN
                                ? AND ? AND GMAT BETWEEN ? AND ? AND GRE BETWEEN ? AND ? AND TOFEL BETWEEN ? AND ? AND IELTS BETWEEN ? AND ?
                                AND IsHonorCollege LIKE ? AND HasInternship LIKE ? ORDER BY Year DESC LIMIT ? OFFSET ?;""",
                                id, '%'+ current_subject +'%', '%'+ target_subject +'%', year_start, year_end, gpa_start, gpa_end, gmat_start, gmat_end,
                                gre_start, gre_end, tofel_start, tofel_end, ielts_start, ielts_end, is_honor_college_code, has_internship_code
                                , NUM_PER_PAGE, (page - 1) * NUM_PER_PAGE)

        return render_template("user/univerisity_view.html", id=id, school_name=school_name, rows=rows, subjects=subjects, page=page, len=len,
                                currentSubject=current_subject, targetSubject=target_subject, yearStart=year_start, yearEnd=year_end, gpaStart=gpa_start, gpaEnd=gpa_end,
                                currentIndex=id_current, targetIndex=id_target, gmat_start=gmat_start, gmat_end=gmat_end, gre_start=gre_start, gre_end=gre_end,
                                tofel_start=tofel_start, tofel_end=tofel_end, ielts_start=ielts_start, ielts_end=ielts_end, is_honor_college=is_honor_college, has_internship=has_internship)

@app.route("/university/analysis/<int:id>", methods=["GET"])
@login_required
def university_analysis(id):
    with create_connection(PATH) as conn:
        cursor = conn.cursor()
        school_name = execute_read_query(cursor, "SELECT SchoolName FROM schools WHERE id = ?", id)[0][0]

        if action := request.args.get("action", ""):
            rows = execute_read_query(cursor, f"SELECT {action} FROM records WHERE SchoolId = ? AND Result = 1", id)

            if action in ["GPA", "GMAT", "GRE", "TOFEL", "IELTS"]:
                data = [row[0] for row in rows if row[0] != 0]
                mean = round(np.mean(data), 2)

                return render_template("user/analyze.html", mean=mean, school_name=school_name, id=id, action=action)
            else:
                data = [row[0] for row in rows]
                honor_college_rate = None
                intern_rate = None

                if action == "IsHonorCollege":
                    honor_college_rate = round(np.mean(data), 2)
                elif action == "HasInternship":
                    intern_rate = round(np.mean(data), 2)

                return render_template("user/analyze.html", honor_college_rate=honor_college_rate, intern_rate=intern_rate, school_name=school_name, id=id, action=action)
        else:
            return render_template("user/analyze.html", school_name=school_name, id=id)

@app.route("/university/analysis_image/<int:id>", methods=["GET"])
@login_required
def analysis_image(id):
    with create_connection(PATH) as conn:
        cursor = conn.cursor()
        if action := request.args.get("action"):
            if action in ["GPA", "GMAT", "GRE", "TOFEL", "IELTS"]:
                rows = execute_read_query(cursor, f"SELECT {action} FROM records WHERE SchoolId = ? AND Result = 1", id)
                data = [row[0] for row in rows if row[0] != 0]
                fig, ax = plt.subplots()
                ax.yaxis.set_major_locator(MaxNLocator(integer=True))
                ax.hist(data, bins=20)

                png_output = io.BytesIO()
                fig.savefig(png_output, format='png')
                png_output.seek(0)
                response = make_response(png_output.getvalue())
                response.headers['Content-Type'] = 'image/png'

                return response
            else:
                rows = execute_read_query(cursor, f"SELECT {action} FROM records WHERE SchoolId = ? AND Result = 1", id)
                data = [row[0] for row in rows]

                yes = 0
                no = 0
                for item in data:
                    if item == 1:
                        yes += 1
                    else:
                        no += 1

                fig, ax = plt.subplots()
                ax.pie([yes, no], labels=['yes', 'no'], colors=['green', 'darkred'], startangle=90, autopct='%1.1f%%')

                png_output = io.BytesIO()
                fig.savefig(png_output, format='png')
                png_output.seek(0)
                response = make_response(png_output.getvalue())
                response.headers['Content-Type'] = 'image/png'

                return response

@app.route("/university/predict/<int:id>", methods=["GET", "POST"])
@login_required
def university_predict(id):
    with create_connection(PATH) as conn:
        cursor = conn.cursor()
        school_name = execute_read_query(cursor, "SELECT SchoolName from schools WHERE id = ?", id)[0][0]
        subjects = [row[0] for row in execute_read_query(cursor, "SELECT subject_name FROM subjects")]

        if request.method == "GET":
            return render_template("user/predict.html", school_name=school_name, subjects=subjects, id=id)

        else:
            applied_subject = request.form.get("applied_subject")
            gpa = request.form.get("gpa", type=float)
            current_major = request.form.get("current_major")
            is_honor_college = 1 if request.form.get("is_honor_college") == 'Yes' else 0
            gmat = request.form.get("gmat", 0, type=float)
            gre = request.form.get("gre", 0, type=float)
            toefl = request.form.get("toefl", 0, type=float)
            ielts = request.form.get("ielts", 0, type=float)
            exempted = 1 if request.form.get("exempted") == 'Yes' else 0
            intern = 1 if request.form.get("intern") == 'Yes' else 0

            # input format:
            # applied subject, year until now, GPA,current subject, is honor college, GMAT, GRE, english test exempted, TOFEL, IELTS, internship
            input = [applied_subject, 0, gpa, current_major, is_honor_college, gmat, gre, exempted, toefl, ielts, intern]
            output = "{:.0%}".format(predict(input))


            return jsonify(output=output, subject=applied_subject, school=school_name)

if __name__ == '__main__':
    app.run(debug=True)
    # Load predict models

