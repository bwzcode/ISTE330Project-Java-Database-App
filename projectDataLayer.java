/*
*  ISTE-330-02
*  Group Project
*  Data Layer
*  Group 6: Ruby Tchou, Jason Zhao, Brian Zhu
*/
//Add Libraries
import java.sql.*;
import java.util.*;
import java.io.*;
import java.math.*;
import java.security.*;

public class projectDataLayer{
   //Variables for SQL Connection
   private Connection conn;
   private Statement stmt;
   private ResultSet rs;
   private String sqlQuery;
   
   //User Login Info
   private String user;
   private String password;
   
   //Database Connection Info
   private String dbName;
   
   //Database Variables
   private String table;
   
   //Variables for File IO
   File file;
   BufferedReader buffReader;
   
   //SQL Drivers
   final String DEFAULT_DRIVER = "com.mysql.cj.jdbc.Driver";
   //Database URL
   private String url = "jdbc:mysql://localhost/";
   
   // Database Methods
   
   /**
   *  driverLoad Method
   *  Confirms the Java-MySQL Driver is loaded
   */
   public void driverLoad(){
      try {
         Class.forName(DEFAULT_DRIVER);
         System.out.println("Driver: "+DEFAULT_DRIVER+" loaded successfully.");
      }//try
      catch(ClassNotFoundException cnfe) {
         System.out.println("Unable to load driver class: " + DEFAULT_DRIVER);
      }//catch
   }//driverLoad Method
   /**
   *  connect Method
   *  Connects to the MySQL Database
   */
   public String connect(String inUser, String inPassword, String inDbName){
      String dispMsg = new String();
      boolean connected = false;
      //Set Values
      user = inUser;
      password = inPassword;
      dbName = inDbName;
      //Append DB Name     
      url = url + dbName;
      url = url + "?serverTimezone=UTC"; // Mac Compatibility
      //Set Default Password if Blank
      if(password.equalsIgnoreCase("")){
         password = "student";
      }//if
      else{
         password = password;
      }//else
      
      //Connect to the Database
      try {
         conn = DriverManager.getConnection(url, user, password); //Add username & password for DB connection
         connected = true;
      }//try
      catch (SQLException se){
         se.printStackTrace();
         connected = false;
      }//catch
      if(connected){
         dispMsg = "Connection Successful - You are connected  \nAuthor's name ->  ISTE 330 - Group 6" + "\n";
         dispMsg += "Connected to: " + url;
      }//if
      else{
         dispMsg = "Connection Failed - Unable to connect to data source: " +url; 
      }//else
      return(dispMsg);
   }//connect Method
   
   /**
   *  close Method
   *  Closes the connection to MySQL Database  
   */
   public void close(){
      try{
         if(rs != null) rs.close();
         if(stmt != null) stmt.close();
         if(conn != null) conn.close();
         System.out.println("------------------------------------------------------");
         System.out.println("Connection Closed Successfully - You are disconnected");
         System.out.println("Good Bye");
      }//try
      catch(SQLException se){
         se.printStackTrace();
      }//catch
   }//close Method
   
   /*
   *  countRows Method
   *  Gets the amount of Rows in a specified Table
   */
   public int countRows(String inTable){
      table = inTable;
      int rowCount = 0;
      try{
         //Create a Statement
         stmt = conn.createStatement();
         //Create ResultSet
         sqlQuery = "SELECT COUNT(*) FROM "+ table;
         //Execute SQL Table Update
         rs = stmt.executeQuery(sqlQuery);
         //Next row in RS
         rs.next();
         //Get Row Count
         rowCount = rs.getInt(1);
         System.out.println("COUNT successful");
      }//try
      catch(SQLException sqle){
         rowCount = 0;
         System.out.println("COUNT Failed");
         sqle.printStackTrace();
      }//catch
      return(rowCount);
   }//countRows Method
   
   // Add methods
   
   public String addPerson(String sfg, String contactType){ 
      String returnVal = new String();
      int result = 0;
      int idNum = 0;
      System.out.println("------------INSERT Person Table STARTED-------------");
      
      try{ 
         PreparedStatement pstmt; 
         
         pstmt = conn.prepareStatement("INSERT INTO person(typeID,contactType) VALUES(?,?)"); 
          
         pstmt.setString(1,String.valueOf(sfg));
         pstmt.setString(2,contactType); 
         System.out.println("\nCommand about to be executed: " + pstmt); 
         result = pstmt.executeUpdate();
         System.out.println("------------INSERT Person Table FINISHED-------------");
      }// end of try
      catch(SQLException sqle){ 
         System.out.println("SQL ERROR"); 
         System.out.println("INSERT IN startPerson FAILED :("); 
         System.out.println("ERROR MESSAGE is ---> " + sqle); 
         sqle.printStackTrace(); 
      }// end sqle catch
      if(sfg.toLowerCase().equals("s")){ 
         returnVal = "s";
      }// end of "S"
      else if(sfg.toLowerCase().equals("f")){ 
         returnVal = "f";
      }// end of "F"
      else if(sfg.toLowerCase().equals("g")){ 
         returnVal = "g";
      }// end of "g"
      return(returnVal);
   }// end addPerson 
   
   public int addFaculty(int idNum, int departmentID, String firstName, String lastName, String username, String password){ 
      
      int result = 0; 
      System.out.println("------------INSERT FACULTY STARTED-------------");
      
      try{ 
         PreparedStatement pstmt; 
         
         pstmt = conn.prepareStatement("INSERT INTO faculty VALUES (?,?,?,?,?,?)"); 
         
         pstmt.setInt(1,idNum);
         pstmt.setInt(2,departmentID); 
         pstmt.setString(3,firstName); 
         pstmt.setString(4,lastName); 
         pstmt.setString(5,username); 
         pstmt.setString(6,password);
         
         //print out the auto incremented id
         
         System.out.println("\nCommand about to be executed: " + pstmt); 
         result = pstmt.executeUpdate(); 
         System.out.println("------------INSERT FACULTY FINISHED-------------");
         
      }// end of try
      catch(SQLException sqle){ 
         System.out.println("SQL ERROR"); 
         System.out.println("INSERT IN FACULTY FAILED :("); 
         System.out.println("ERROR MESSAGE is ---> " + sqle); 
         sqle.printStackTrace(); 
         return(0);
      }// end sqle catch
      return(result); 
   }// end of addFaculty   
   
   public int addStudent(int idNum, String firstName, String lastName, String username, String password){ 
      
      int result = 0; 
      System.out.println("------------INSERT STUDENT STARTED-------------");
      
      try{ 
         PreparedStatement pstmt; 
         
         pstmt = conn.prepareStatement("INSERT INTO student VALUES (?,?,?,?,?)"); 
         
         pstmt.setInt(1,idNum);
         pstmt.setString(2,firstName); 
         pstmt.setString(3,lastName); 
         pstmt.setString(4,username); 
         pstmt.setString(5,password);
         
         System.out.println("\nCommand about to be executed: " + pstmt); 
         result = pstmt.executeUpdate(); 
         System.out.println("------------INSERT STUDENT FINISHED-------------");
         
      }// end of try
      catch(SQLException sqle){ 
         System.out.println("SQL ERROR"); 
         System.out.println("INSERT IN STUDENT FAILED :("); 
         System.out.println("ERROR MESSAGE is ---> " + sqle); 
         sqle.printStackTrace(); 
         return(0);
      }// end sqle catch
      return(result); 
   }// end of AddStudent
   
   public int addGuest(int idNum, String business, String firstName, String lastName, String username, String password){ 
      
      int result = 0; 
      System.out.println("------------INSERT GUEST STARTED-------------");
      
      try{ 
         PreparedStatement pstmt; 
         
         pstmt = conn.prepareStatement("INSERT INTO guest VALUES (?,?,?,?,?,?)"); 
         
         pstmt.setInt(1,idNum); 
         pstmt.setString(2,business); 
         pstmt.setString(3,firstName); 
         pstmt.setString(4,lastName); 
         pstmt.setString(5,username); 
         pstmt.setString(6,password);
         
         System.out.println("\nCommand about to be executed: " + pstmt); 
         result = pstmt.executeUpdate(); 
         System.out.println("------------INSERT GUEST FINISHED-------------");
         
      }// end of try
      catch(SQLException sqle){ 
         System.out.println("SQL ERROR"); 
         System.out.println("INSERT IN GUEST FAILED :("); 
         System.out.println("ERROR MESSAGE is ---> " + sqle); 
         sqle.printStackTrace(); 
         return(0);
      }// end sqle catch
      return(result); 
   }// end of addGuest
   
   public int addFacultyAbstract(String inUser,String inTitle, String inContent){
      int result = 0;
      int UID = 0;
      String enteredFaculty = inUser;
      String abstractTitle = inTitle;
      String abstractContent = inContent;
      try{
         //First get FacultyID
         PreparedStatement pstmt;
         pstmt = conn.prepareStatement("SELECT UID FROM faculty WHERE username = ?;");
         pstmt.setString(1,enteredFaculty);
         rs = pstmt.executeQuery(); 
         while(rs.next()){
            UID = rs.getInt(1);
         }//while
         //Run Stored Procedure
         sqlQuery = "{CALL insert_faculty_abstract(?,?,?)}";
         CallableStatement cstmt = conn.prepareCall(sqlQuery);
         cstmt.setInt(1,UID);
         cstmt.setString(2,abstractTitle);
         cstmt.setString(3,abstractContent);
         ResultSet rs2 = cstmt.executeQuery();
      }//try
      catch(SQLException sqle){
         System.out.println("addFacultyAbstract Failed!"); 
         sqle.printStackTrace(); 
      }//catch
      return (result);
   }//addFacultyAbstract Method
   
   /**
   *  addNewInterest
   *  Adds an Interest to the Interests Table
   */
   public void addNewInterest(String inText){
      int result =0;
      String enteredText = inText;
      try{
         PreparedStatement pstmt;
         pstmt = conn.prepareStatement("INSERT INTO interests(interest) VALUES(?)");
         pstmt.setString(1,enteredText);
         result = pstmt.executeUpdate();
      }//try
      catch(SQLException sqle){
         System.out.println("addNewInterest Failed!");
         sqle.printStackTrace();
      }//catch 
   }//addNewInterest
   
   /**
   *  addNewKeyword
   *  Adds an Keyword to the Keywords Table
   */
   public void addNewKeyword(String inText){
      int result = 0;
      String enteredText = inText;
      try{
         PreparedStatement pstmt;
         pstmt = conn.prepareStatement("INSERT INTO keywords(keyword) VALUES(?)");
         pstmt.setString(1,enteredText);
         result = pstmt.executeUpdate();
      }//try
      catch(SQLException sqle){
         System.out.println("addNewKeyword Failed!");
         sqle.printStackTrace();
      }//catch
   }//addNewKeyword
   
   /**
   *  addStuInterests
   *  Associates Interests with Students
   */
   public int addStuInterests(String inUser, int inInterestID){
      int result = 0;
      int stuID = 0;
      int interestID = inInterestID;
      String enteredStudent = inUser;
      try{
         //Translate Username to StudentID
         PreparedStatement pstmt;
         pstmt = conn.prepareStatement("SELECT studentID FROM student WHERE username = ?;");
         pstmt.setString(1,enteredStudent);
         rs = pstmt.executeQuery(); 
         while(rs.next()){
            stuID = rs.getInt(1);
         }//while
         //Now Add Interests
         pstmt = conn.prepareStatement("INSERT IGNORE INTO student_interests(studentID,interestID) VALUES(?,?);");//Insert Ignore to override duplicates
         //Assign Values
         pstmt.setInt(1,stuID);
         pstmt.setInt(2,interestID);
         //Execute
         result = pstmt.executeUpdate();
      }//try
      catch(SQLException sqle){
         System.out.println("addStuInterests Method Failed!");
         sqle.printStackTrace();
      }//catch
      return(result);
   }//addStuInterests
       
   /**
   *  addFacKeywords
   *  Associates Keywords with Faculty 
   */
   public int addFacKeywords(String inUser, int inKeywordID){
      int result = 0;
      int UID = 0;
      int keywordID = inKeywordID;
      String enteredFaculty = inUser;
      try{
         //Translate Username to Faculty UID
         PreparedStatement pstmt;
         pstmt = conn.prepareStatement("SELECT UID FROM faculty WHERE username = ?;");
         pstmt.setString(1,enteredFaculty);
         rs = pstmt.executeQuery(); 
         while(rs.next()){
            UID = rs.getInt(1);
         }//while
         //Now Add Keywords
         pstmt = conn.prepareStatement("INSERT IGNORE INTO faculty_keywords(UID,keywordID) VALUES(?,?);");//Insert Ignore to override duplicates
         //Assign Values
         pstmt.setInt(1,UID);
         pstmt.setInt(2,keywordID);
         //Execute
         result = pstmt.executeUpdate();
      }//try
      catch(SQLException sqle){
         System.out.println("addFacKeywords Method Failed!");
         sqle.printStackTrace();
      }//catch
      return(result);
   }//addFacKeywords
          
   /**
   *  addFacLocation
   *  Add Data to faculty_location table based on Username
   */
   public int addFacLocation(String inUser, String inBldg, String inRoom){
      int result = 0;
      int UID = 0;
      String enteredFac = inUser;
      String enteredBldg = inBldg;
      String enteredRoom = inRoom;
      try{
         //Translate Username to Faculty UID
         PreparedStatement pstmt;
         pstmt = conn.prepareStatement("SELECT UID FROM faculty WHERE username = ?;");
         pstmt.setString(1,enteredFac);
         rs = pstmt.executeQuery(); 
         while(rs.next()){
            UID = rs.getInt(1);
         }//while
         //Now Add Location Data
         pstmt = conn.prepareStatement("INSERT INTO faculty_location VALUES(?,?,?);");
         //Assign Values
         pstmt.setInt(1,UID);
         pstmt.setString(2,enteredBldg);
         pstmt.setString(3,enteredRoom);
         //Execute
         result = pstmt.executeUpdate();
      }//try
      catch(SQLException sqle){
         System.out.println("addFacLocation Method Failed!");
         sqle.printStackTrace();
      }//catch
   
      return(result);
   }//addFacLocation
   
   
   /**
   *  addFacContact
   *  Add Data to faculty_contact table
   */
   public int addFacContact(String inUser, String inPhone, String inEmail){
      int result = 0;
      int UID = 0;
      String enteredFac = inUser;
      String enteredPhone = inPhone;
      String enteredEmail = inEmail;
      try{
         //Translate Username to Faculty UID
         PreparedStatement pstmt;
         pstmt = conn.prepareStatement("SELECT UID FROM faculty WHERE username = ?;");
         pstmt.setString(1,enteredFac);
         rs = pstmt.executeQuery(); 
         while(rs.next()){
            UID = rs.getInt(1);
         }//while
         //Now Add Location Data
         pstmt = conn.prepareStatement("INSERT INTO faculty_contact VALUES(?,?,?);");
         //Assign Values
         pstmt.setInt(1,UID);
         pstmt.setString(2,enteredPhone);
         pstmt.setString(3,enteredEmail);
         //Execute
         result = pstmt.executeUpdate();
      }//try
      catch(SQLException sqle){
         System.out.println("addFacContact Method Failed!");
         sqle.printStackTrace();
      }//catch
      return(result);
   }//addFacContact
   
   //Delete Methods
   
   /**
   *  deleteAbstract 
   *  Delete's the Abstract based on the abstractID
   */
   public String deleteAbstract(int inAbstractID){
      int result = 0;
      int absID = inAbstractID;
      String dispMsg = new String();
      try{
         PreparedStatement pstmt;
         //First Delete from Associative Table
         pstmt = conn.prepareStatement("DELETE FROM faculty_abstract WHERE abstractID = ?");
         pstmt.setInt(1,absID);
         result += pstmt.executeUpdate();
         //Now Delete from Abstract Table
         pstmt = conn.prepareStatement("DELETE FROM abstract WHERE abstractID = ?");
         pstmt.setInt(1,absID);
         result += pstmt.executeUpdate();
         if(result > 0){
            dispMsg = "Abstract Deleted Successfully";
         }//if
         else{
            dispMsg = "Invalid ID - Abstract was NOT Deleted";
         }//else
      }//try
      catch(SQLException sqle){
         System.out.println("deleteAbstract Failed");
         sqle.printStackTrace();
      }//catch
      return(dispMsg);
   }//deleteAbstract
   
   //Update Methods
   
   /**
   *  updateAbstract
   *  Update's the Abstract based on the abstractID, passes through new Title & Content
   */
   public String updateAbstract(int inAbstractID, String inTitle, String inContent){
      int result = 0;
      int absID = inAbstractID;
      String dispMsg = new String();
      String newTitle = inTitle;
      String newContent = inContent;
      try{
         PreparedStatement pstmt;
         //
         pstmt = conn.prepareStatement("UPDATE abstract SET abstractTitle = ? , abstractContent = ? WHERE abstractID = ?");
         pstmt.setString(1,newTitle);
         pstmt.setString(2,newContent);
         pstmt.setInt(3,absID);
         result += pstmt.executeUpdate();
         if(result > 0){
            dispMsg = "Abstract Updated Successfully";
         }//if
         else{
            dispMsg = "Abstract was NOT Updated";
         }//else
      }//try
      catch(SQLException sqle){
         System.out.println("updateAbstract Failed");
         sqle.printStackTrace();
      }//catch
      return(dispMsg);
   }//deleteAbstract
   
   // Get Methods
   
   /**
   *  getAllUsers
   *  Shows All Users, Account Type,ID Number, and their names
   */
   public String getAllUsers(){
      String dispMsg = new String();
      String formatMsg = new String();
      try{
         PreparedStatement pstmt;
         //Faculty
         pstmt = conn.prepareStatement("SELECT * FROM faculty");
         rs = pstmt.executeQuery();
         while(rs.next()){
            int UID = rs.getInt(1);
            String fName = rs.getString(3);
            String lName = rs.getString(4);
            String user = rs.getString(5);
            formatMsg += "Account Type: Faculty | Faculty UID: "+ UID + " | Username: " + user + " | Name: " + fName + " " + lName+ "\n";
         }//while
         //Student
         pstmt = conn.prepareStatement("SELECT * FROM student");
         rs = pstmt.executeQuery();
         while(rs.next()){
            int stuID = rs.getInt(1);
            String fName = rs.getString(2);
            String lName = rs.getString(3);
            String user = rs.getString(4);
            formatMsg += "Account Type: Student | Student ID: "+ stuID + " | Username: " + user + " | Name: " + fName + " " + lName + "\n";
         }//while
         //Guest
         pstmt = conn.prepareStatement("SELECT * FROM guest");
         rs = pstmt.executeQuery();
         while(rs.next()){
            int guestID = rs.getInt(1);
            String fName = rs.getString(3);
            String lName = rs.getString(4);
            String user = rs.getString(5);
            formatMsg += "Account Type: Guest   | Guest ID: "+ guestID + " | Username: " + user + " | Name: " + fName + " " + lName + "\n";
         }//while
         dispMsg += formatMsg;                         
      }//try
      catch(SQLException sqle){
         System.out.println("getAllUsers Failed!");
         sqle.printStackTrace();
      }//catch
      return (dispMsg);
   }//getAllusers
   
   /**
   *  getDepts Method
   *  Lists all Departments & Their ID
   */
   public String getDepts(){
      String dispMsg = new String();
      try{
         //Create a Statement
         PreparedStatement pstmt;
         pstmt = conn.prepareStatement("SELECT * FROM department ORDER BY departmentID");
         //Execute SQL Query
         rs = pstmt.executeQuery();
         //Get Output
         while(rs.next()) {
            int deptID = rs.getInt(1);
            String deptName = rs.getString(2);
            dispMsg += deptID + " - " + deptName + "\n";
         }//while rs
      }//try
      catch(SQLException sqle){
         System.out.println("getDepts Failed!");
         sqle.printStackTrace();
      }//catch
      return (dispMsg);
   }//getInterests Method
   
   /**
   *  getInterests Method
   *  Lists all Interests & Their ID
   */
   public String getInterests(){
      String dispMsg = new String();
      try{
         //Create a Statement
         PreparedStatement pstmt;
         pstmt = conn.prepareStatement("SELECT * FROM interests ORDER BY interestID");
         //Execute SQL Query
         rs = pstmt.executeQuery();
         //Get Output
         while(rs.next()) {
            String interestID = rs.getString(1);
            String interestName = rs.getString(2);
            dispMsg += interestID + " - " + interestName + "\n";
         }//while rs
      }//try
      catch(SQLException sqle){
         System.out.println("getInterests Method Failed!");
         sqle.printStackTrace();
      }//catch
      return (dispMsg);
   }//getInterests Method
   
   /**
   *  getKeywords Method
   *  Lists all Keywords & Their ID
   */
   public String getKeywords(){
      String dispMsg = new String();
      try{
         //Create a Statement
         PreparedStatement pstmt;
         pstmt = conn.prepareStatement("SELECT * FROM keywords ORDER BY keywordID");
         //Execute SQL Query
         rs = pstmt.executeQuery();
         //Get Output
         while(rs.next()) {
            String keywordID = rs.getString(1);
            String keywordName = rs.getString(2);
            dispMsg += keywordID + " - " + keywordName + "\n";
         }//while rs
      }//try
      catch(SQLException sqle){
         System.out.println("getInterests Method Failed!");
         sqle.printStackTrace();
      }//catch
      return (dispMsg);
   }//getKeywords Method
   
   /**
   *  getAbstracts Method
   *  Lists all Abstract Names & Their ID
   */
   public String getAbstracts(){
      String dispMsg = new String();
      try{
         //Create a Statement
         PreparedStatement pstmt;
         pstmt = conn.prepareStatement("SELECT * FROM abstract ORDER BY abstractID");
         //Execute SQL Query
         rs = pstmt.executeQuery();
         //Get Output
         while(rs.next()) {
            String absID = rs.getString(1);
            String absTitle = rs.getString(2);
            String absContent = rs.getString(3);
            dispMsg += "--------------------------------------------------------------------------------------" + "\n";
            dispMsg += "Abstract ID: "+absID + " | Title: " + absTitle + "\nDescription: " + absContent + "\n";
         }//while rs
      }//try
      catch(SQLException sqle){
         System.out.println("getInterests Method Failed!");
         sqle.printStackTrace();
      }//catch
      return (dispMsg);
   }//getAbstracts Method   
   
   /**
   *  getStudentInterests
   *  Uses a Stored Procedure to get a list of Interests from the Student
   */
   public String getStudentInterests(String inUser){
      int stuID = 0;
      String stuName = new String();
      String interests = new String();
      String returnData = new String();
      String enteredStudent = inUser;
      try{
            //First get StudentID
         PreparedStatement pstmt;
         pstmt = conn.prepareStatement("SELECT studentID FROM student WHERE username = ?;");
         pstmt.setString(1,enteredStudent);
         rs = pstmt.executeQuery(); 
         while(rs.next()){
            stuID = rs.getInt(1);
         }//while
            //Run Stored Procedure
         sqlQuery = "{CALL get_student_interests(?)}";
         CallableStatement cstmt = conn.prepareCall(sqlQuery);
         cstmt.setInt(1,stuID);
         ResultSet rs2 = cstmt.executeQuery();
         while (rs2.next()){
            stuID = rs2.getInt(1);
            stuName = rs2.getString(2);
            interests = rs2.getString(3);
            returnData = stuName + "'s Interests: " + interests;
         }//While
      }//try
      catch(SQLException sqle){
         System.out.println("getStudentSkills Failed!"); 
         sqle.printStackTrace(); 
      }//catch
      return (returnData);
   }//getStudentInterests
   
   /**
   *  getFacultyDept Method
   *  Returns Faculty & Their Department
   */
   public String getFacultyDept(String inUser){
      int UID = 0;
      int deptID = 0;
      String facName = new String();
      String deptName = new String();
      String returnData = new String();
      String enteredFaculty = inUser;
      try{
         //First get FacultyID
         PreparedStatement pstmt;
         pstmt = conn.prepareStatement("SELECT UID FROM faculty WHERE username = ?;");
         pstmt.setString(1,enteredFaculty);
         rs = pstmt.executeQuery(); 
         while(rs.next()){
            UID = rs.getInt(1);
         }//while
         //Run Stored Procedure
         sqlQuery = "{CALL get_faculty_dept(?)}";
         CallableStatement cstmt = conn.prepareCall(sqlQuery);
         cstmt.setInt(1,UID);
         ResultSet rs2 = cstmt.executeQuery();
         while(rs2.next()){
            UID = rs2.getInt(1);
            facName = rs2.getString(2);
            deptID = rs2.getInt(3);
            deptName = rs2.getString(4);
            returnData = "Faculty ID: "+ UID +" | Faculty Member: " + facName + "\nDepartment ID: "+ deptID + " | Department Name: "+deptName;
         }//while
      }//try
      catch(SQLException sqle){
         System.out.println("getFacultyDept Failed!"); 
         sqle.printStackTrace(); 
      }///catch
      return (returnData);
   }//getFacultyDept Method
   
   /**
   *  getFacultyKeywords Method
   *  Uses a Stored Procedure to get a list of Keywords from the faculty
   */
   public String getFacultyKeywords(String inUser){
      int UID = 0;
      String facName = new String();
      String keywords = new String();
      String returnData = new String();
      String enteredFaculty = inUser;
      try{
            //First get FacultyID
         PreparedStatement pstmt;
         pstmt = conn.prepareStatement("SELECT UID FROM faculty WHERE username = ?;");
         pstmt.setString(1,enteredFaculty);
         rs = pstmt.executeQuery(); 
         while(rs.next()){
            UID = rs.getInt(1);
         }//while
            //Run Stored Procedure
         sqlQuery = "{CALL get_faculty_keywords(?)}";
         CallableStatement cstmt = conn.prepareCall(sqlQuery);
         cstmt.setInt(1,UID);
         ResultSet rs2 = cstmt.executeQuery();
         while (rs2.next()){
            UID = rs2.getInt(1);
            facName = rs2.getString(2);
            keywords = rs2.getString(3);
            returnData = facName + "'s Keywords: " + keywords;
         }//While
      }//try
      catch(SQLException sqle){
         System.out.println("getFacultyKeywords Failed!"); 
         sqle.printStackTrace(); 
      }//catch
      return (returnData);
   }//getFacultyKeywords Method
   
   /**
   *  getFacultyAbstracts
   *  Gets a list of Abstracts based on the username
   */
   public String getFacultyAbstracts(String inUser){
      int UID = 0;
      int absID;
      String facName = new String();
      String absTitle = new String();
      String formatMsg = new String();
      String returnData = new String();
      String enteredFaculty = inUser;
      try{
         //First get FacultyID
         PreparedStatement pstmt;
         pstmt = conn.prepareStatement("SELECT UID FROM faculty WHERE username = ?;");
         pstmt.setString(1,enteredFaculty);
         rs = pstmt.executeQuery(); 
         while(rs.next()){
            UID = rs.getInt(1);
         }//while
         //Run Stored Procedure
         sqlQuery = "{CALL get_faculty_abs(?)}";
         CallableStatement cstmt = conn.prepareCall(sqlQuery);
         cstmt.setInt(1,UID);
         ResultSet rs2 = cstmt.executeQuery();
         while (rs2.next()){
            UID = rs2.getInt(1);
            facName = rs2.getString(2);
            absID = rs2.getInt(3);
            absTitle = rs2.getString(4);
            formatMsg += "Faculty: "+ facName + " | Abstract ID: " + absID + " | Abstract Name: " +absTitle;
         }//While
         returnData += formatMsg;
      }//try
      catch(SQLException sqle){
         System.out.println("getFacultyAbstracts Failed!");
         sqle.printStackTrace();
      }//catch
      return(returnData);
   }//getFacultyAbstracts
   
   /**
   *  getLastInsert Method
   *  Returns the ID of the last Record Inserted
   */
   public int getLastInsert(){
      int idNum = 0;
      try{
         //Get id of Last Insert
         stmt = conn.createStatement();
         sqlQuery = "SELECT LAST_INSERT_ID();";//Get ID Number of Last Insert
         rs = stmt.executeQuery(sqlQuery);
         while(rs.next()){
            idNum = rs.getInt(1);
         }//while
         System.out.println("ID of inserted record is: "+ idNum);
      }//try
      catch(SQLException sqle){ 
         System.out.println("getLastInsert Failed!"); 
         sqle.printStackTrace(); 
         return(0);
      }//catch
      return(idNum);
   }//getLastInsert
   
   /**
   *  getAccType
   *  Gets the Account Type based on Username
   */
   public String getAccType(String inUser){
      int returnNum = 0;
      String enteredUser = inUser;
      String returnData = new String();
      //Array to Contain All Usernames
      ArrayList<String> users = new ArrayList<String>();
      try{
         PreparedStatement pstmt;
         //Get Users from Faculty Table
         pstmt = conn.prepareStatement("SELECT username FROM faculty");
         rs = pstmt.executeQuery();
         while(rs.next()){
            users.add(rs.getString(1));
         }//while
         
         //Get Users from Student Table
         pstmt = conn.prepareStatement("SELECT username FROM student");
         rs = pstmt.executeQuery();
         while(rs.next()){
            users.add(rs.getString(1));
         }//while
         
         //Get Users from Guest Table
         pstmt = conn.prepareStatement("SELECT username FROM guest");
         rs = pstmt.executeQuery();
         while(rs.next()){
            users.add(rs.getString(1));
         }//while
      //Check if the Account Exists from Accounts Array & the Account Type
         if(users.contains(enteredUser) == true){
         
         //Get the Account Type
         //Use Select Exists to determine if it exists in the Table
         
         //Check Faculty Table
            pstmt = conn.prepareStatement("SELECT EXISTS(SELECT * FROM faculty WHERE username = ?)");
            pstmt.setString(1,enteredUser);
            rs = pstmt.executeQuery();
            while(rs.next()){
               returnNum = rs.getInt(1);
            }//while
            if(returnNum > 0){
               returnData = "f";
            }//if
         
         //Check Student Table
            pstmt = conn.prepareStatement("SELECT EXISTS(SELECT * FROM student WHERE username = ?)");
            pstmt.setString(1,enteredUser);
            rs = pstmt.executeQuery();
            while(rs.next()){
               returnNum = rs.getInt(1);
            }//while
            if(returnNum > 0){
               returnData = "s";
            }//if
         
         //Check Guest Table
            pstmt = conn.prepareStatement("SELECT EXISTS(SELECT * FROM guest WHERE username = ?)");
            pstmt.setString(1,enteredUser);
            rs = pstmt.executeQuery();
            while(rs.next()){
               returnNum = rs.getInt(1);
            }//while
            if(returnNum > 0){
               returnData = "g";
            }//if
         }//if
         else{
            System.out.println("\nUser Account Doesn't Exist - Quitting Program");
            System.exit(0);
         }//else
      }//try
      catch(SQLException sqle){
         System.out.println("getAccType Failed!");
         sqle.printStackTrace();
      }//catch
      return (returnData);
   }//getAccType

   // Verify Methods
   
   /**
   *  verifyFacPassword Method
   *  verifies that the password entered matches the one in the database for Faculty Table
   */
   public boolean verifyFacPassword(String inUser, String inPass){
      String storedPass = new String();
      String enteredPass = inPass;
      String enteredUser = inUser;
      
      try{
         PreparedStatement pstmt;
         pstmt = conn.prepareStatement("SELECT password FROM faculty WHERE username = ?;");
         pstmt.setString(1,enteredUser);
         rs = pstmt.executeQuery();
         while(rs.next()){
            storedPass = rs.getString(1);
         }//while
      }//try
      catch(SQLException sqle){
         System.out.println("verifyPassword Failed!"); 
         sqle.printStackTrace();
      }//catch
      if(storedPass.equals(enteredPass)){
         System.out.println("Password does match!");
         return true;
      }//if
      else{
         System.out.println("Error: Username & Password mismatch");
         return false; 
      }//else
   }//verifyFacPassword Method
   //Other Methods
   
   /**
   *  findMatching
   *  Finds all Users with similar interests & keywords based on a specified word
   */
   public String findMatching(String inWord){
      int resCount = 0;
      int userID = 0;
      String tmpMsg = new String();
      String dispMsg = new String();
      String dbInfoMsg = new String();
      String enteredWord = inWord;
      String enteredWordLower = inWord.toLowerCase();
      String userData = new String();
      String name = new String();
      
      ArrayList<Integer> personIDList = new ArrayList<Integer>();
      ArrayList<Integer> userDataIDList = new ArrayList<Integer>();
      ArrayList<String> userDataList = new ArrayList<String>();
      ArrayList<String> userDataListLower = new ArrayList<String>();
      //First Import Data from the Database         
      try{
         PreparedStatement pstmt;
         //Get all IDs
         pstmt = conn.prepareStatement("SELECT * FROM person");
         rs = pstmt.executeQuery();
         while(rs.next()){
            personIDList.add(rs.getInt(1));
         }//while
         //Get the Data of Students & Faculty Only & Add into Array List
         for(int i = 0; i < personIDList.size(); i++){
            //Faculty First
            String facQuery = "{CALL get_faculty_keywords(?)}";
            CallableStatement cstmt = conn.prepareCall(facQuery);
            cstmt.setInt(1,personIDList.get(i));
            ResultSet collectRS = cstmt.executeQuery();
            while(collectRS.next()){
               userID = collectRS.getInt(1);
               name = collectRS.getString(2);
               String keywords = collectRS.getString(3);
               String returnData = "Keywords: " + keywords + "\n";
               String returnDataLower = returnData.toLowerCase();
               //Add to List of User ID's
               userDataIDList.add(userID);
               //Add to List of Data
               userDataList.add(returnData);
               userDataListLower.add(returnDataLower);
            }//while
         }//for
         for(int j = 0; j < personIDList.size(); j++){
            //Now Students
            String stuQuery = "{CALL get_student_interests(?)}";
            CallableStatement cstmt = conn.prepareCall(stuQuery);
            cstmt.setInt(1,personIDList.get(j));
            ResultSet collectRS = cstmt.executeQuery();
            while(collectRS.next()){
               userID = collectRS.getInt(1);
               name = collectRS.getString(2);
               String interests = collectRS.getString(3);
               String returnData = "Interests: " + interests + "\n";
               String returnDataLower = returnData.toLowerCase();
               //Add to List of User ID's
               userDataIDList.add(userID);
               //Add to List of Data
               userDataList.add(returnData);
               userDataListLower.add(returnDataLower);
            }//while
         }//for
      }//try
      catch(SQLException sqle){
         System.out.println("findMatching (Data Collection Portion) Failed!");
         sqle.printStackTrace();
      }//catch
      //Now Search ArrayList to Find the String
      //Compare the Entered String to List
      for(int x = 0; x < userDataList.size(); x++){
         if(userDataListLower.get(x).contains(enteredWord.toLowerCase())){
            //Now Pull Data for the Found Users from the Database
            try{
               //Once again, Faculty First
               String facQuery2 = "{CALL get_faculty_info(?)}";
               CallableStatement cstmtFac = conn.prepareCall(facQuery2);
               cstmtFac.setInt(1,userDataIDList.get(x));
               ResultSet findFacRS = cstmtFac.executeQuery();
               while(findFacRS.next()){
                  String facName = findFacRS.getString(2);     
                  String facDept = findFacRS.getString(3);
                  String facEmail = findFacRS.getString(4);
                  String facPhone = findFacRS.getString(5);
                  String facBldg = findFacRS.getString(6);
                  String facRm = findFacRS.getString(7);
                  dbInfoMsg += "------------------------------------------------------" + "\n";
                  dbInfoMsg += "User Type: Faculty \nName: " + facName + "\nDepartment: " + facDept + "\nEmail: " + facEmail + "\nPhone Number: " + facPhone + "\nOffice Location: \nBuilding: " + facBldg + "\nRoom: " + facRm + "\n";
                  dbInfoMsg += userDataList.get(x);
                  resCount++;
               }//while
               //Now the Students
               String stuQuery2 = "{CALL get_student_info(?)}";
               CallableStatement cstmtStu = conn.prepareCall(stuQuery2);
               cstmtStu.setInt(1,userDataIDList.get(x));
               ResultSet findStuRS = cstmtStu.executeQuery();
               while(findStuRS.next()){
                  String stuName = findStuRS.getString(2);   
                  String stuDept = findStuRS.getString(3);
                  String stuEmail = findStuRS.getString(4);
                  dbInfoMsg += "------------------------------------------------------" + "\n";
                  dbInfoMsg += "User Type: Student \nName: " + stuName + "\nDepartment: " + stuDept + "\nEmail: " + stuEmail + "\n";
                  dbInfoMsg += userDataList.get(x);
                  resCount++;
               }//while
            }//try
            catch(SQLException sqle){
               System.out.println("findMatching (Searching Portion) Failed!");
               sqle.printStackTrace();
            }//catch
         }//if
      }//for
         //Check Result Count
         if(resCount > 0){
            tmpMsg = "";
            //Response for Matches found
            dispMsg += "------------------------------------------------------" + "\n";
            dispMsg += "Matches were found for: "+ enteredWord + "\n";
            dispMsg += "------------------------------------------------------" + "\n";
            dispMsg += "Listing matching users..." + "\n";
         }//if   
         else{
            //Response for No Matches
            tmpMsg += "------------------------------------------------------" + "\n";
            tmpMsg += "No Matches Found for: " + enteredWord + "\n";
            tmpMsg += "------------------------------------------------------" + "\n";
         }//else
      dispMsg = tmpMsg + dispMsg + dbInfoMsg;
      return (dispMsg);
   }//findMatching
   
   /**
   *  encrypt Method
   *  encyrpts a String using SHA1 Encryption
   */
   public String encrypt(String inString){
      String sha1 = new String();
      String passedVal = inString;
      try{
         MessageDigest digest = MessageDigest.getInstance("SHA-1");
         digest.update(passedVal.getBytes("utf8"));
         sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
      }//try
      catch(Exception e){
         System.out.println("encrypt Failed!");
         e.printStackTrace();
      }//catch
      System.out.println("Encrypted Password: "+sha1);
      return (sha1);
   }//encrypt Method
}//projectDataLayer Class