import os

from cs50 import SQL
from flask import Flask, flash, redirect, render_template, request, session
from flask_session import Session
from tempfile import mkdtemp
from werkzeug.security import check_password_hash, generate_password_hash
from datetime import datetime, timezone, timedelta

from helpers import apology, login_required, lookup, usd

# Configure application
app = Flask(__name__)

# Ensure templates are auto-reloaded
app.config["TEMPLATES_AUTO_RELOAD"] = True

# Custom filter
app.jinja_env.filters["usd"] = usd
app.jinja_env.filters["abs"] = abs

# Configure session to use filesystem (instead of signed cookies)
app.config["SESSION_PERMANENT"] = False
app.config["SESSION_TYPE"] = "filesystem"
Session(app)

# Configure CS50 Library to use SQLite database
db = SQL("sqlite:///finance.db")

mytz = timezone(timedelta(hours=8))

# Make sure API key is set
if not os.environ.get("API_KEY"):
    raise RuntimeError("API_KEY not set")


@app.after_request
def after_request(response):
    """Ensure responses aren't cached"""
    response.headers["Cache-Control"] = "no-cache, no-store, must-revalidate"
    response.headers["Expires"] = 0
    response.headers["Pragma"] = "no-cache"
    return response


@app.route("/")
@login_required
def index():
    """Show portfolio of stocks"""
    user = db.execute("SELECT * FROM users WHERE id = ?", session["user_id"])[0]
    stocks = db.execute("SELECT SUM(netIn) as shares, stock as symbol FROM transactions WHERE buyer_id = ? GROUP BY stock HAVING shares > 0", session["user_id"])
    stockTotal = 0

    for stock in stocks:
        info = lookup(stock["symbol"])
        stock["price"] = info["price"]
        total = stock["price"] * stock["shares"]
        stock["total"] = total
        stockTotal += total

    user["stockTotal"] = stockTotal
    user["grandValue"] = stockTotal + user["cash"]

    return render_template("index.html", user=user, stocks=stocks)


@app.route("/buy", methods=["GET", "POST"])
@login_required
def buy():
    """Buy shares of stock"""
    if request.method == 'GET':
        return render_template("buy.html")
    else:
        symbol = request.form.get("symbol")
        shares = request.form.get("shares")
        info   = lookup(symbol)
        if not info:
            return apology("Company not found")
        if not shares or not shares.isdigit() or int(shares) <= 0:
            return apology("Shares must be a positive integer")
        shares = int(shares)
        price  = float(info["price"])
        cost   = price * shares
        id     = session['user_id']
        bal    = (db.execute("SELECT * FROM users WHERE id = ?", id))[0]["cash"]
        print(type(bal))
        if cost > bal:
            return apology("Not sufficient balance")

        # No error. Proceed the purchase
        date = datetime.now(mytz).strftime('%Y-%m-%d %H:%M:%S')
        db.execute("INSERT INTO transactions (buyer_id, stock, price, netIn, time, state) VALUES (?, ?, ?, ?, ?, 'buy')", id, symbol, price, shares, date)
        newBal = bal - cost
        db.execute("UPDATE users SET cash = ? WHERE id = ?", newBal, id)
        flash("bought")
        return redirect("/")


@app.route("/history")
@login_required
def history():
    """Show history of transactions"""
    transactions = db.execute("SELECT * FROM transactions WHERE buyer_id = ? ORDER BY time DESC", session["user_id"])
    return render_template("history.html", transactions = transactions)


@app.route("/login", methods=["GET", "POST"])
def login():
    """Log user in"""
    # Forget any user_id
    session.clear()

    # User reached route via POST (as by submitting a form via POST)
    if request.method == "POST":
        name = request.form.get("username")
        # Ensure username was submitted
        if not request.form.get("username"):
            return apology("must provide username", 403)

        # Ensure password was submitted
        elif not request.form.get("password"):
            return apology("must provide password", 403)

        # Query database for username
        rows = db.execute("SELECT * FROM users WHERE username = ?", request.form.get("username"))

        # Ensure username exists and password is correct
        if len(rows) != 1 or not check_password_hash(rows[0]["hash"], request.form.get("password")):
            return apology("invalid username and/or password", 403)

        # Remember which user has logged in
        session["user_id"] = rows[0]["id"]
        session["user_name"] = rows[0]["username"]

        # Redirect user to home page
        return redirect("/")

    # User reached route via GET (as by clicking a link or via redirect)
    else:
        return render_template("login.html")


@app.route("/logout")
def logout():
    """Log user out"""

    # Forget any user_id
    session.clear()
    # Redirect user to login form
    return redirect("/")


@app.route("/quote", methods=["GET", "POST"])
@login_required
def quote():
    """Get stock quote."""
    if request.method == "GET":
        return render_template("quote.html")
    else:
        symbol = request.form.get("symbol")
        info = lookup(symbol)
        if not info:
            return apology("stock not found")
        return render_template("quoted.html", info=info)


@app.route("/register", methods=["GET", "POST"])
def register():
    """Register user"""
    if request.method == 'GET':
        return render_template("register.html")
    else:
        username = request.form.get("username").strip()
        password = request.form.get("password")
        confirm = request.form.get("confirmation")

    def search(username):
        data = db.execute("SELECT * FROM users WHERE username = ?", username)
        return (len(data) > 0)

    # Validate username
    if not(username):
        return apology("Username cannot be blank")
    elif search(username):
        return apology("Username already exists")

    # Validate password
    if not(password):
        return apology("Password cannot be blank")
    elif not(password == confirm):
        return apology("Confirmation does not match password")

    db.execute("INSERT INTO users (username, hash) VALUES (?, ?)", username, generate_password_hash(password))
    flash("registered")
    return redirect("/login")


@app.route("/sell", methods=["GET", "POST"])
@login_required
def sell():
    """Sell shares of stock"""
    if request.method == "POST":
        symbol = request.form.get("symbol")
        shares = request.form.get("shares")
        if not symbol:
            return apology("Must choose a stock")
        info   = lookup(symbol)
        if not info:
            return apology("Company" + symbol + "doesn't exit")
        stock = db.execute("SELECT SUM(netIn) as quantity FROM transactions WHERE buyer_id = ? AND stock = ? GROUP BY stock", session["user_id"], symbol)
        if stock[0]["quantity"] <= 0:
            return apology("You don't own any shares of" + symbol)
        if not shares.isdigit() or int(shares) <= 0:
            return apology("Shares must be a positive integer")
        shares = int(shares)
        if shares > stock[0]["quantity"]:
            return apology("Insufficient shares")

        #proceed selling
        time = datetime.now(mytz).strftime("%Y-%m-%d %H:%M:%S")
        db.execute("INSERT INTO transactions (buyer_id, stock, price, netIn, time, state) VALUES (?, ?, ?, ?, ?, 'sell')", session["user_id"], symbol, info["price"], -shares, time)
        user = db.execute("SELECT * FROM users WHERE id = ?", session["user_id"])
        gain = info["price"] * shares
        bal = gain + user[0]["cash"]
        db.execute("UPDATE users SET cash = ? WHERE id = ?", bal, session["user_id"])
        flash("sold")
        return redirect("/")
    else:
        symbols = db.execute("SELECT stock, SUM(netIn) as shares FROM transactions WHERE buyer_id = ? GROUP BY stock HAVING SUM(netIn) > 0", session["user_id"])
        return render_template("sell.html", symbols=symbols)

@app.route("/add", methods=["POST"])
@login_required
def add():
    if request.method == "POST":
        amount = request.form.get("amount")
        error = ""
        try:
            amount = float(amount)
        except ValueError:
            error = "Amount must be a number"

        if not error and amount <= 0:
            error = "Amount must be positive"

        if error:
            return apology(error)

        #proceed adding cash
        cash = db.execute("SELECT * FROM users WHERE id = ?", session["user_id"])[0]["cash"]
        newBal = cash + amount
        db.execute("UPDATE users SET cash = ? WHERE id = ?", newBal, session["user_id"])
        flash("Cash added: " + usd(amount))
        return redirect("/")