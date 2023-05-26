from helper import create_connection, execute_query

subjects = ['Accounting and Finance', 'Agriculture and Forestry', 
 'Anatomy and Physiology', 'Anthropology', 
 'Archaeology', 'Architecture and Built Environment', 
 'Art History', 'Art and Design', 
 'Arts and Humanities', 'Biological Sciences', 
 'Business and Management Studies', 'Chemistry', 
 'Classics and Ancient History', 'Communication and Media Studies', 
 'Computer Science and Information Systems', 'Data Science', 
 'Dentistry', 'Development Studies', 
 'Earth and Marine Sciences', 'Economics and Econometrics', 
 'Education and Training', 'Engineering - Chemical', 
 'Engineering - Civil and Structural', 'Engineering - Electrical and Electronic', 
 'Engineering - Mechanical', 'Engineering - Mineral and Mining', 
 'Engineering - Petroleum', 'Engineering and Technology', 
 'English Language and Literature', 'Environmental Sciences', 
 'Geography', 'Geology', 
 'Geophysics', 'History', 
 'Hospitality and Leisure Management', 'Law and Legal Studies', 
 'Library and Information Management', 'Life Sciences and Medicine', 
 'Linguistics', 'Marketing', 
 'Materials Sciences', 'Mathematics', 
 'Medicine', 'Modern Languages', 
 'Natural Sciences', 'Nursing', 
 'Performing Arts', 'Pharmacy and Pharmacology', 
 'Philosophy', 'Physics and Astronomy', 
 'Politics', 'Psychology', 
 'Social Policy and Administration', 'Social Sciences and Management', 
 'Sociology', 'Sports-Related Subjects', 
 'Statistics and Operational Research', 'Theology, Divinity and Religious Studies', 
 'Veterinary Science']

with create_connection("../PSAH.sqlite") as conn:
    cursor = conn.cursor()
    for subject in subjects:
        execute_query(cursor, "INSERT INTO subjects (subject_name) VALUES (?)", subject)
