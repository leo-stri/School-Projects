import requests
from helper import create_connection, execute_query

# Download the dataset from the internet
url = 'https://raw.githubusercontent.com/Hipo/university-domains-list/master/world_universities_and_domains.json'
response = requests.get(url)
data = response.json()

# Extract the university names from the dataset
with create_connection("../PSAH.sqlite") as conn:
    cursor = conn.cursor()
    for item in data:
        if item['country'] in ["United Kingdom", "United States"]:
            execute_query(cursor, "INSERT INTO schools (SchoolName, WebPage, Country) VALUES (?, ?, ?)", item['name'], item['web_pages'][0], item['country'])

