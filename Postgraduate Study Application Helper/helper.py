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
        if session.get("type") is None:
            return redirect("/login")
        
        if session.get("type") == "UM Student":
            with create_connection("PSAH.sqlite") as conn:
                cursor = conn.cursor()
                banned = execute_read_query(cursor, "SELECT banned FROM users WHERE id = ?", session["user_id"])[0][0]
                if banned == 1:
                    session.clear()
                    return msg("Your account is banned")
                
        return f(*args, **kwargs)
    return decorated_function

def admin_required(f):
    """
    Decorate routes to require admin.
    """
    @wraps(f)
    def decorated_function(*args, **kwargs):
        if session.get("type") not in ["Mainadmin", "Subadmin"]:
            return msg("You're not authorized to access this page")
        return f(*args, **kwargs)
    return decorated_function


def msg(message, code=400):
    """Render message as a thank you to user."""
    return render_template("thank_you.html", message=message), code

def new_record(SchoolId, AppliedSubject, result, year, GPA, current_subject, honor_college, GMAT, GRE, TOFEL, IELTS, exempted, internship):
    with create_connection("PSAH.sqlite") as conn:
        cursor = conn.cursor()
        execute_query(cursor, """INSERT INTO records (SchoolId, AppliedProgram, Result, Year, GPA, CurrentMajor, IsHonorCollege, GMAT, GRE, TOFEL, IELTS, EnglishTestExempted, HasInternship)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)""",
                    SchoolId, AppliedSubject, result, year, GPA, current_subject, honor_college, GMAT, GRE, TOFEL, IELTS, exempted, internship)
        
        if result == 1:
            execute_query(cursor, 'UPDATE schools SET records_num = records_num + 1 WHERE id = ?', SchoolId)

