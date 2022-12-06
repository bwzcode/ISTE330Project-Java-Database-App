/*
*   ISTE-330-02
*   Final Project
*   Group 6: Ruby Tchou, Jason Zhao, Brian Zhu 
*   Database File
*/

/*
*   Database Creation
*/
-- DROP DATABASE IF EXISTS
DROP DATABASE IF EXISTS projectdb;
-- CREATE & USE DATABASE
CREATE DATABASE projectdb;
USE projectdb;
-- Credentials
SELECT "ISTE-330-02" AS "Course","Group Project" AS "Assignment", "Group 6: Ruby Tchou, Jason Zhao, Brian Zhu" AS "Group_Members", CURDATE() AS "Todays_Date";
-- Person Table
CREATE TABLE person(
    personID INT NOT NULL AUTO_INCREMENT,
    typeID ENUM('F','G','S') NOT NULL,
    contactType VARCHAR(50),
    CONSTRAINT person_pk PRIMARY KEY (personID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Faculty Section
-- Faculty Contact Table
CREATE TABLE faculty_contact(
    UID INT NOT NULL,
    phoneNum VARCHAR(25),
    email VARCHAR(50),
    CONSTRAINT faculty_contact_uid_pk PRIMARY KEY (UID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- Department Table
CREATE TABLE department(
    departmentID INT NOT NULL AUTO_INCREMENT,
    department VARCHAR(50),
    CONSTRAINT department_pk PRIMARY KEY (departmentID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- Faculty Table
CREATE TABLE faculty(
    UID INT NOT NULL,
    departmentID INT NOT NULL,
    firstName VARCHAR(50),
    lastName VARCHAR(50),
    username VARCHAR(50),
    password VARCHAR(50),
    CONSTRAINT faculty_pk PRIMARY KEY (UID),
    CONSTRAINT faculty_id_fk FOREIGN KEY (UID) REFERENCES person(personID),
    CONSTRAINT faculty_department_fk FOREIGN KEY (departmentID) REFERENCES department(departmentID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- Faculty Location Table
CREATE TABLE faculty_location(
    UID INT NOT NULL,
    buildingNum VARCHAR(50),
    officeNum VARCHAR(50),
    CONSTRAINT faculty_location_pk PRIMARY KEY (UID),
    CONSTRAINT faculty_location_uid_fk FOREIGN KEY (UID) REFERENCES faculty(UID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- Abstract Table
CREATE TABLE abstract(
    abstractID INT NOT NULL AUTO_INCREMENT,
    abstractTitle VARCHAR(100),
    abstractContent MEDIUMTEXT,
    CONSTRAINT abstract_pk PRIMARY KEY (abstractID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT = 1;
-- Faculty Abstract Table
CREATE TABLE faculty_abstract(
    UID INT NOT NULL,
    abstractID INT NOT NULL,
    CONSTRAINT faculty_abstract_pk PRIMARY KEY (UID,abstractID),
    CONSTRAINT faculty_abstract_uid_fk FOREIGN KEY (UID) REFERENCES faculty(UID),
    CONSTRAINT faculty_abstract_abstractid_fk FOREIGN KEY (abstractID) REFERENCES abstract(abstractID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- Keywords Table
CREATE TABLE keywords(
    keywordID INT NOT NULL AUTO_INCREMENT,
    keyword VARCHAR(50),
    CONSTRAINT keywords_pk PRIMARY KEY (keywordID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT = 1;
-- Faculty Keywords Table
CREATE TABLE faculty_keywords(
    UID INT NOT NULL,
    keywordID INT NOT NULL,
    CONSTRAINT faculty_abstract_pk PRIMARY KEY (UID, keywordID),
    CONSTRAINT faculty_keywords_uid_fk FOREIGN KEY (UID) REFERENCES faculty(UID),
    CONSTRAINT faculty_keywords_keywordid_fk FOREIGN KEY (keywordID) REFERENCES keywords(keywordID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Sudent Section
-- Student Table
CREATE TABLE student(
    studentID INT NOT NULL,
    firstName VARCHAR(50),
    lastName VARCHAR(50),
    username VARCHAR(50),
    password VARCHAR(50),
    CONSTRAINT student_pk PRIMARY KEY (studentID),
    CONSTRAINT student_id_fk FOREIGN KEY (studentID) REFERENCES person(personID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- Student Contact Table
CREATE TABLE student_contact(
    studentID INT NOT NULL,
    email VARCHAR(50),
    CONSTRAINT student_contact_pk PRIMARY KEY (studentID),
    CONSTRAINT student_contact_id_fk FOREIGN KEY (studentID) REFERENCES student(studentID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- Interests Table
CREATE TABLE interests(
    interestID INT NOT NULL AUTO_INCREMENT,
    interest VARCHAR(50),
    CONSTRAINT interests_pk PRIMARY KEY (interestID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT = 1;
-- Student Interests Table
CREATE TABLE student_interests(
    studentID INT NOT NULL,
    interestID INT NOT NULL,
    CONSTRAINT student_interests_pk PRIMARY KEY (studentID, interestID),
    CONSTRAINT student_interests_id_fk FOREIGN KEY (studentID) REFERENCES student(studentID),
    CONSTRAINT student_interests_interestid_fk FOREIGN KEY (interestID) REFERENCES interests(interestID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- Student Department Table
CREATE TABLE student_department(
    studentID INT NOT NULL,
    departmentID INT NOT NULL,
    CONSTRAINT student_department_pk PRIMARY KEY (studentID, departmentID),
    CONSTRAINT student_department_studentid_fk FOREIGN KEY (studentID) REFERENCES student(studentID),
    CONSTRAINT student_deparment_deptid_fk FOREIGN KEY (departmentID) REFERENCES department(departmentID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Guest Section
-- Guest Contact Table
CREATE TABLE guest_contact(
    guestID INT,
    contactInfo VARCHAR(50),
    CONSTRAINT guest_contact_pk PRIMARY KEY (guestID)
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- Guest Table
CREATE TABLE guest(
    guestID INT NOT NULL,
    business VARCHAR(50),
    firstName VARCHAR(50),
    lastName VARCHAR(50),
    username VARCHAR(50),
    password VARCHAR(50),
    CONSTRAINT guest_pk PRIMARY KEY (guestID),
    CONSTRAINT guest_id_fk FOREIGN KEY (guestID) REFERENCES person(personID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*
*   Stored Procedures
*/
-- Get Student Info
DROP PROCEDURE IF EXISTS get_student_info;
DELIMITER //
CREATE PROCEDURE get_student_info(IN p_stuID INT)
BEGIN
	SELECT studentID AS "Student ID", 
    concat(firstName," ", lastName) AS "Student Name",
    department AS "Student Department",
    email AS "Email Address"
    FROM student JOIN student_contact USING(studentID)
    JOIN student_department USING(studentID)
    JOIN department USING(departmentID)
    WHERE studentID = p_stuID;
END //
DELIMITER ;

-- Get Faculty Info
DROP PROCEDURE IF EXISTS get_faculty_info;
DELIMITER //
CREATE PROCEDURE get_faculty_info(IN p_UID INT)
BEGIN
	SELECT UID AS "Faculty ID", 
    concat(firstName," ", lastName) AS "Faculty Member",
    department AS "Department",
    email AS "Email Address",
    phoneNum AS "Phone Number",
    buildingNum AS "Building Number",
    officeNum AS "Office Number"
    FROM faculty JOIN faculty_contact USING(UID)
    JOIN faculty_location USING(UID)
    JOIN department USING(departmentID)
    WHERE UID = p_UID;
END //
DELIMITER ;

-- Get Student Interests
DROP PROCEDURE IF EXISTS get_student_interests;
DELIMITER //
CREATE PROCEDURE get_student_interests(IN p_stuID INT)
BEGIN
	SELECT studentID AS "Student ID", 
    concat(firstName," ", lastName) AS "Student Name",
    group_concat(interests.interest SEPARATOR' , ') AS "Interests"
    FROM student JOIN student_interests USING(studentID)
    JOIN interests USING(interestID)
    WHERE studentID = p_stuID
    GROUP BY studentID;
END //
DELIMITER ;
-- Get Faculty Keywords
DROP PROCEDURE IF EXISTS get_faculty_keywords;
DELIMITER //
CREATE PROCEDURE get_faculty_keywords(IN p_UID INT)
BEGIN
	SELECT UID AS "Faculty ID", 
    concat(firstName," ", lastName) AS "Faculty Member",
    group_concat(keywords.keyword SEPARATOR ' , ') AS "Keywords"
    FROM faculty JOIN faculty_keywords USING(UID)
    JOIN keywords USING(keywordID)
    WHERE UID = p_UID
    GROUP BY UID;
END //
DELIMITER ;

-- Get Faculty Department
DROP PROCEDURE IF EXISTS get_faculty_dept;
DELIMITER //
CREATE PROCEDURE get_faculty_dept(IN p_UID INT)
BEGIN
	SELECT UID AS "Faculty ID", 
    concat(firstName," ", lastName) AS "Faculty Member",
    departmentID AS "Department ID",
    department.department AS "Department Name"
    FROM faculty JOIN department USING(departmentID)
    WHERE UID = p_UID;
END //
DELIMITER ;

-- Get Faculty Abstracts
DROP PROCEDURE IF EXISTS get_faculty_abs;
DELIMITER //
CREATE PROCEDURE get_faculty_abs(IN p_UID INT)
BEGIN
	SELECT UID AS "Faculty ID", 
    concat(firstName," ", lastName) AS "Faculty Member",
    abstractID AS "Abstract ID",
    abstractTitle AS "Abstract Title"
    FROM faculty JOIN faculty_abstract USING(UID)
    JOIN abstract USING(abstractID)
    WHERE UID = p_UID;
END //
DELIMITER ;

-- Student Procedures
-- Drop Procedure if Exists
DROP PROCEDURE IF EXISTS insert_student;

-- Create Procedure insert_student
DELIMITER //
CREATE PROCEDURE insert_student(IN p_ID INT, IN p_typeID CHAR, IN p_contactType VARCHAR(50),IN p_fName VARCHAR(50), IN p_lName VARCHAR(50), IN p_username VARCHAR(50), IN p_password VARCHAR(50),IN p_email VARCHAR(50),IN p_deptID INT)
    BEGIN
        -- Add to Person Table
        INSERT INTO person VALUES(p_ID,p_typeID,p_contactType);
        -- Add to Student Table
        INSERT INTO student VALUES (p_ID,p_fName,p_lName,p_username,SHA1(p_password));
        -- Add to Student_Contact Table
        INSERT INTO student_contact VALUES (p_ID,p_email);
        -- Add to Student_Department Table
        INSERT INTO student_department VALUES (p_ID,p_deptID);
    END //
DELIMITER ;

-- Faculty Procedures
-- Drop Procedure if Exists
DROP PROCEDURE IF EXISTS insert_faculty;
-- Create Procedure insert_faculty
DELIMITER //
CREATE PROCEDURE insert_faculty(IN p_ID INT, IN p_typeID CHAR, IN p_contactType VARCHAR(50), IN p_deptID INT, IN p_fName VARCHAR(50), IN p_lName VARCHAR(50), IN p_username VARCHAR(50), IN p_password VARCHAR(50),IN p_email VARCHAR(50), IN p_phoneNumber VARCHAR(25),IN p_buildingNum VARCHAR(50), IN p_officeNum VARCHAR(50))
    BEGIN
        -- Add to Person Table
        INSERT INTO person VALUES(p_ID,p_typeID,p_contactType);
        -- Add to Student Table
        INSERT INTO faculty VALUES(p_ID,p_deptID,p_fName,p_lName,p_username,SHA1(p_password));
        -- Add to Student_Contact Table
        INSERT INTO faculty_contact VALUES(p_ID,p_phoneNumber,p_email);
        -- Add to Faculty Location Table
        INSERT INTO faculty_location VALUES(p_ID,p_buildingNum,p_officeNum);
    END //
DELIMITER ;

-- Drop Procedure if Exists
DROP PROCEDURE IF EXISTS insert_faculty_abstract;
-- Create Procedure insert_faculty_abstract
DELIMITER //
CREATE PROCEDURE insert_faculty_abstract(IN p_ID INT, IN p_abstractTitle VARCHAR(100), IN p_abstractContent MEDIUMTEXT)
    BEGIN
        DECLARE abstractInsertID INT DEFAULT 0;
        -- Add to Abstract Table
        INSERT INTO abstract(abstractTitle,abstractContent) VALUES(p_abstractTitle, p_abstractContent);
        -- Get ID of the Abstract Inserted
        SELECT LAST_INSERT_ID() INTO abstractInsertID;
        -- Add to Faculty Abstract Table
        INSERT INTO faculty_abstract VALUES(p_ID,abstractInsertID);
    END //
DELIMITER ;

-- Guest Procedures
-- Drop Procedure if Exists
DROP PROCEDURE IF EXISTS insert_guest;
-- Create Procedure insert_faculty_abstract
DELIMITER //
CREATE PROCEDURE insert_guest(IN p_ID INT, IN p_typeID CHAR, IN p_contactType VARCHAR(50),p_business VARCHAR(50),IN p_fName VARCHAR(50), IN p_lName VARCHAR(50), IN p_username VARCHAR(50), IN p_password VARCHAR(50), IN p_contactInfo VARCHAR(50))
    BEGIN
        -- Add to Person Table
        INSERT INTO person VALUES(p_ID,p_typeID,p_contactType);
        -- Add to Guest Table
        INSERT INTO guest VALUES(p_ID,p_business,p_fName,p_lName,p_username,SHA1(p_password));
        -- Add to Guest Contact Table
        INSERT INTO guest_contact VALUES(p_ID,p_contactInfo);
    END //
DELIMITER ;

/*
*   Table Data
*/

-- Populate Interests Table
INSERT INTO interests VALUES (1, 'Pascal');
INSERT INTO interests VALUES (2, 'Java');
INSERT INTO interests VALUES (3, 'JDBC');
INSERT INTO interests VALUES (4, 'MySQL');
INSERT INTO interests VALUES (5, 'Python');
INSERT INTO interests VALUES (6, 'COBOL');
INSERT INTO interests VALUES (7, 'C');
INSERT INTO interests VALUES (8, 'CyberSecurity');
INSERT INTO interests VALUES (9, 'C++');
INSERT INTO interests VALUES (10, 'C#');
INSERT INTO interests VALUES (11, 'JavaScript');
INSERT INTO interests VALUES (12, 'PHP');
INSERT INTO interests VALUES (13, 'Ada');
INSERT INTO interests VALUES (14, "Ruby/Ruby on Rails");

-- Populate Department Table
INSERT INTO department VALUES(1,'Computing and Information Technologies');
INSERT INTO department VALUES(2,'Computer Science');
INSERT INTO department VALUES(3,'Computing Security');
INSERT INTO department VALUES(4,'Game Design & Development');
INSERT INTO department VALUES(5,'Human Centered Computing');
INSERT INTO department VALUES(6,'New Media Interactive Development');
INSERT INTO department VALUES(7,'Software Engineering');
INSERT INTO department VALUES(8,'Web and Mobile Computing');
INSERT INTO department VALUES(9,'Artifical Intelligence');
INSERT INTO department VALUES(10,'Computing Exploration');

-- Populate Keywords Table
INSERT INTO keywords VALUES (1, 'Pascal');
INSERT INTO keywords VALUES (2, 'Java');
INSERT INTO keywords VALUES (3, 'JDBC');
INSERT INTO keywords VALUES (4, 'MySQL');
INSERT INTO keywords VALUES (5, 'Python');
INSERT INTO keywords VALUES (6, 'COBOL');
INSERT INTO keywords VALUES (7, 'C');
INSERT INTO keywords VALUES (8, 'CyberSecurity');
INSERT INTO keywords VALUES (9, 'C++');
INSERT INTO keywords VALUES (10, 'C#');
INSERT INTO keywords VALUES (11, 'JavaScript');
INSERT INTO keywords VALUES (12, 'PHP');
INSERT INTO keywords VALUES (13, 'Ada');
INSERT INTO keywords VALUES (14, "Ruby/Ruby on Rails");


/*
*   Sample Users / Data
*/
-- Student: Brian Zhu
CALL insert_student(1,'S','Email','Brian','Zhu','bwz5597','brianpassword','bwz5597@rit.edu',1);
INSERT INTO student_interests VALUES(1,2);
INSERT INTO student_interests VALUES(1,3);
INSERT INTO student_interests VALUES(1,4);
INSERT INTO student_interests VALUES(1,5);

-- Student: Ruby Tchou
CALL insert_student(2,'S','Email','Ruby','Tchou','rt7452','rubypassword','rt7452@rit.edu',1);
INSERT INTO student_interests VALUES(2,2);
INSERT INTO student_interests VALUES(2,4);
INSERT INTO student_interests VALUES(2,5);
INSERT INTO student_interests VALUES(2,12);

-- Student: Jason Zhao
CALL insert_student(3,'S','Email','Jason','Zhao','jz1205','jasonpassword','jz1205@rit.edu',1);
INSERT INTO student_interests VALUES(3,2);
INSERT INTO student_interests VALUES(3,4);
INSERT INTO student_interests VALUES(3,5);

-- Guest: Will Bates
CALL insert_guest(4,'G','Email','Macrosoft','Will','Bates','wbates','totallysecure','wbates@macrosoft.net');

-- Faculty: Jim Habermas
CALL insert_faculty(5,'F','Email',1,'Jim','Habermas', 'jrhicsa', 'student','Jim.Habermas@rit.edu','585-746-933','70','2673');
CALL insert_faculty_abstract(5,"Learn C and C++ by Samples",
"This book, Learn C and C++ by Samples written by James R.
Habermas, is a companion to A First Book Ansi C++ by Gary
Bronson. It is the author's firm belief that one can never have
too many samples. If a textbook is to be useful, it needs
primary support through an instructor and/or more samples.
This textbook contains a wealth of useful C & C++ samples that
are fashioned to further demonstrate the topics outlined in the
text.");
INSERT INTO faculty_keywords VALUES(5,2);
INSERT INTO faculty_keywords VALUES(5,3);
INSERT INTO faculty_keywords VALUES(5,4);