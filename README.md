ISTE-330-02
Final Project
Group 6: Ruby Tchou, Jason Zhao, Brian Zhu 
Directions.txt File

Initial Sign-In: 
To connect to the Database, use the following login.
Username should be root (this will change in further updates as we add more features).
Password is defaulted to student. 
Database is projectdb. 


Java Data Layer Directions

To make access to the database easier, a Java program was developed. We’re starting off with the Data Layer first, and developing the Presentation Layer GUI afterward. For the time being, we’ve added a main method to the Data Layer to test our Insert Methods. Once the GUI is developed, the prompts will be removed.

First, a login prompt is presented to the User to enter their Username, Password (default is student), and Database name. Afterward, the main method will ask the user questions and create a basic profile in the database based on the details provided.

Java Presentation Layer Directions
Application Sign In:
Once the connection to the Database is established, you can then login as a specific user.
Logins will be with a Username & Password, which is encrypted in the database using SHA1.

Below, we have some sample users already added to the Database with encrypted passwords.

Additionally, the users have plain text passwords listed below. Along with their permissions in the Database / Application.
Faculty

Username: jrhicsa
Password: student

Permissions
Search & View Users based on Interests and Keywords
Update & Add Keywords to their own profile
Add Abstracts to their own profile

Students

Username: bwz5597
Password: brianpassword

Username: rt7452
Password: rubypassword

Username: jz1205
Password: jasonpassword

Permissions
Search & View Users based on Interests and Keywords
Update & Add Interests to their own profile

Guests

Username: wbates
Password:  totallysecure

Permissions
Search & View Users based on Interests and Keywords

Button Mechanics: 

Faculty: 

Search 

Search for students and faculty with the same interests / keywords as those inputted. 

List All Users

Lists all users in the database. This includes all faculty, students, and guests. The user’s account type, ID (int), username, and name is presented. 

List Abstracts

This allows any user to look at all the abstract names and authors in the database. 
 
Add New Users 

This button will allow the faculty to add a new user to the database. They will follow a prompt that will require them to input if the new user is a student (S), faculty (F), or guest (G), their contact type, department ID (int), first name, last name, and password. 

Additionally there will be buttons to show lists of Interests, keywords, and departments.
There will also be buttons to add Custom Keywords and to associate keyword IDs with Faculty Usernames.

Abstract Configuration

Abstract Configuration brings the user to another GUI that allows them to add, delete, or update the abstracts. 

The “Add Abstract” button allows the faculty to add a new abstract to their account. They will input the title and content here. 

The “Delete Abstract” button will delete the abstract according to the Abstract ID (int). 

The “Update Abstract” button will take in the Abstract ID (int) of the abstract that will be modified and will overwrite the old information with the new title and content. 


Student: 

Search 

Search for students and faculty with the same interests as those inputted. 

List All Users

Lists all users in the database. This includes all faculty, students, and guests. The user’s account type, ID (int), username, and name is presented. 
 
List Abstracts

This allows any user to look at all the abstract names and authors in the database. 

Abstract Configuration

This button will not be usable to the students.

Add New Users 

This button will not be usable to the students. 


Guest: 

Search 

Search for students and faculty with the same interests as those inputted. 

List All Users

Lists all users in the database. This includes all faculty, students, and guests. The user’s account type, ID (int), username, and name is presented. 


List Abstracts

This allows any user to look at all the abstract names and authors in the database. 

Abstract Configuration

This button will not be usable to the guests.
 
Add New Users 

This button will not be usable to the guests. 



Stored Procedures Directions

There are numerous Stored Procedures within the database to insert users & data into the database easier from the MySQL command line. There are also 3 Stored Procedures to output data from the Database.

Output Stored Procedures

get_student_interests

Returns the name of the student & the name of the interests associated with them

get_faculty_keywords

Returns the name of the Faculty & the name of the keywords associated with them

get_faculty_dept

Returns the faculty’s UID, name, departmentID, and department name

Variables used in the Insert Stored Procedures:

personID (studentID, UID, guestID) - INT
typeID - CHAR ((either ‘F’,’S’,’G’) to differentiate between Faculty, Student, and Guest)
departmentID - INT
abstractID - INT
contactType - VARCHAR(50)
firstName - VARCHAR(50)
lastName - VARCHAR(50)
username - VARCHAR(50)
password - VARCHAR(50)
email - VARCHAR(50)
phoneNumber - VARCHAR(25)
contactInfo - VARCHAR(50)
buildingNumber - VARCHAR(50)
officeNumber - VARCHAR(50)
abstractTitle - VARCHAR(50)
abstractContent - MEDIUMTEXT (for large amounts of text / characters)

insert_student
Requires: personID, typeID, contactType, firstName, lastName, username, password, email, departmentID

Passes the variables specified by the user and will assign the following:

personID, typeID, contactType added to ‘person’ table

personID (as studentID), firstNane, lastName, username, password added to ‘student’ table
password is encrypted using the SHA1() function in MySQL

personID (as studentID), email added to ‘student_contact’ table

personID (as studentID), departmentID added to ‘student_department’ table

insert_faculty

Requires: personID, typeID, contactType, departmentID, firstName, lastName, username, password, email, phoneNumber, buildingNumber, officeNumber

Passes the variables specified by the user and will assign the following:

personID, typeID, contactType added to ‘person’ table


personID (as UID), departmentID, firstName, lastName, username, password added to ‘faculty’ table
password is encrypted using the SHA1() function in MySQL

personID (as UID), phoneNumber, email added to ‘faculty_contact’ table

personID (as UID), buildingNumber, officeNumber added to ‘faculty_location’ table


Insert_faculty_abstract


Requires: UID, abstractTitle, abstractContent

Passes the variables specified by the user and will assign the following:

abstractTile, abstractContent added to ‘abstract’ table

abstractID is parsed from the last insert

UID, abstractID added to ‘faculty_abstract’ table


insert_guest
Requires: personID, typeID, contactType, business, firstName, lastName, username, password, contactInfo

Passes the variables specified by the user and will assign the following:

personID, typeID, contactType added to ‘person’ table

personID (as guestID), business, firstName, lastName, username, password added to ‘guest’ table
password is encrypted using the SHA1() function in MySQL

personID (as guestID), contactInfo added to ‘guest_contact’ table
