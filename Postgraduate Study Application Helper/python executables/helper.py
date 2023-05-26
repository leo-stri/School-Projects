import sqlite3
from sqlite3 import Error
from flask import redirect, render_template, request, session
from functools import wraps

def create_connection(path):
    connection = None
    try:
        connection = sqlite3.connect(path)
    except Error as e:
        print(f"Error occurred trying to connect to database {path}: {e}")
    return connection

def execute_query(cursor, query, *args):
    cursor.execute(query, args)
    cursor.connection.commit()
    

def execute_read_query(cursor, query, *args):
    result = None
    cursor.execute(query, args)
    result = cursor.fetchall()
    return result

def login_required(f):
    """
    Decorate routes to require login.
    """
    @wraps(f)
    def decorated_function(*args, **kwargs):
        if session.get("user_id") is None:
            return redirect("/login")
        return f(*args, **kwargs)
    return decorated_function

def admin_required(f):
    """
    Decorate routes to require admin.
    """
    @wraps(f)
    def decorated_function(*args, **kwargs):
        if session.get("type") not in ["Mainadmin", "Subadmin"]:
            return no_permit("You're not authorized to access this page")
        return f(*args, **kwargs)
    return decorated_function

def no_permit(message, code=400):
    """Render message as an error to user."""
    def escape(s):
        """
        Escape special characters.

        https://github.com/jacebrowning/memegen#special-characters
        """
        for old, new in [("-", "--"), (" ", "-"), ("_", "__"), ("?", "~q"),
                         ("%", "~p"), ("#", "~h"), ("/", "~s"), ("\"", "''")]:
            s = s.replace(old, new)
        return s
    return render_template("no_permit.html", top=code, bottom=escape(message)), code

def thank_you(message):
    """Render message as a thank you to user."""
    return render_template("thank_you.html", message=message)

def new_record(SchoolId, AppliedSubject, result, year, GPA, current_subject, honor_college, GMAT, GRE, TOFEL, IELTS, exempted, internship):
    with create_connection("PSAH.sqlite") as conn:
        cursor = conn.cursor()
        execute_query(cursor, """INSERT INTO records (SchoolId, AppliedProgram, Result, Year, GPA, CurrentMajor, IsHonorCollege, GMAT, GRE, TOFEL, IELTS, EnglishTestExempted, HasInternship)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)""",
                    SchoolId, AppliedSubject, result, year, GPA, current_subject, honor_college, GMAT, GRE, TOFEL, IELTS, exempted, internship)
        
        if result == 1:
            execute_query(cursor, 'UPDATE schools SET records_num = records_num + 1 WHERE id = ?', SchoolId)

def predict(input):
    return 0.50