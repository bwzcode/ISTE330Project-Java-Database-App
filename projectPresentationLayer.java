/*
*  ISTE-330-02
*  Group Project
*  Presentation Layer
*  Group 6: Ruby Tchou, Jason Zhao, Brian Zhu
*/
import java.sql.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.GridLayout;

public class projectPresentationLayer extends JFrame{

   public static Font myFontForOutput = new Font("Courier", Font.PLAIN, 14);
   
   projectDataLayer ms = new projectDataLayer();
    
   public projectPresentationLayer(){
      super("projectPresentationLayer");
      
      //Jpanel for Database login
      JPanel InputBox = new JPanel(new GridLayout(4,1));
      JLabel lbDb = new JLabel("Database:");
      JLabel lbUsername = new JLabel("Username:");
      JLabel lbPassword = new JLabel("Password (Default is Student):");
      JTextField txtDb = new JTextField("projectdb");
      JTextField txtUsername = new JTextField("root");
      JPasswordField txtPassword = new JPasswordField("");
      JLabel filler          = new JLabel(" ");
      JCheckBox pwordCheckbox = new JCheckBox("Show Password"); 
      pwordCheckbox.addActionListener(
         new ActionListener(){ 
            public void actionPerformed(ActionEvent e){ 
               if(pwordCheckbox.isSelected()){ 
                  txtPassword.setEchoChar((char)0);
               }
               else{
                  txtPassword.setEchoChar('*');
               }
            }
         });
      
      
      //Setting Font for Database Logins
      lbDb.setFont(myFontForOutput);
      txtDb.setFont(myFontForOutput);
      lbUsername.setFont(myFontForOutput);
      txtUsername.setFont(myFontForOutput);
      lbPassword.setFont(myFontForOutput);
      txtPassword.setFont(myFontForOutput);
      
      //Adding Database inputs into JPanel           
      InputBox.add(lbDb);
      InputBox.add(txtDb);
      InputBox.add(lbUsername);
      InputBox.add(txtUsername);
      InputBox.add(lbPassword);
      InputBox.add(txtPassword);
      InputBox.add(filler);
      InputBox.add(pwordCheckbox); 
      JOptionPane.showMessageDialog(null, InputBox, "Connection", JOptionPane.INFORMATION_MESSAGE);         
      // int exit = JOptionPane.showMessageDialog(null, InputBox, "Connection", JOptionPane.INFORMATION_MESSAGE);
   //       if (exit == JOptionPane.YES_OPTION)
   //       {
   //          System.exit(0);
   //       }
      
      String database = txtDb.getText();
      String username = txtUsername.getText();
      String password = txtPassword.getText();
      
      ms.driverLoad();
       
      //Call the connect method
      String msg = ms.connect(username, password, database);
      
      setSize(630,300);
      setLocation(585,350);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
   
      JFrame jframe = new JFrame();
      JPanel LoginInput = new JPanel(new GridLayout(3,1));
   
      //User Login Inputs
      JLabel lblUser             = new JLabel("Username: ");
      JTextField tfUser          = new JTextField(); 
      JLabel lblPassword         = new JLabel("Password: ");
      JPasswordField tfPassword  = new JPasswordField();
      JLabel lblUserStar         = new JLabel("  *  ");
      JLabel lblPasswordStar     = new JLabel("  *  ");
      JLabel filler1          = new JLabel(" ");
      JCheckBox passCheckbox = new JCheckBox("Show Password"); 
      passCheckbox.addActionListener(
         new ActionListener(){ 
            public void actionPerformed(ActionEvent e){ 
               if(passCheckbox.isSelected()){ 
                  tfPassword.setEchoChar((char)0);
               }
               else{
                  tfPassword.setEchoChar('*');
               }
            }
         });
      
      //Setting Font for User Logins
      lblUser.setFont(myFontForOutput);
      tfUser.setFont(myFontForOutput);
      lblPassword.setFont(myFontForOutput);
      txtUsername.setFont(myFontForOutput);
      tfPassword.setFont(myFontForOutput);
      lblUserStar.setFont(myFontForOutput);
      lblPasswordStar.setFont(myFontForOutput);
      
      //Set red color for star
      lblUserStar.setForeground(Color.RED);
      lblPasswordStar.setForeground(Color.RED);
      
      //Add the Login Inputs into Jpanel
      LoginInput.add(lblUser);
      LoginInput.add(tfUser);
      LoginInput.add(lblUserStar);
      LoginInput.add(lblPassword);
      LoginInput.add(tfPassword);
      LoginInput.add(lblPasswordStar);
      LoginInput.add(filler1);
      LoginInput.add(passCheckbox); 
      add(LoginInput);         
      
      JOptionPane.showMessageDialog(null, LoginInput, "Login", JOptionPane.INFORMATION_MESSAGE);
      
      JPanel jpCenter = new JPanel();
      jpCenter.setLayout(new GridLayout(7,2));
      
      JLabel creds = new JLabel("Group 6: Ruby Tchou, Jason Zhao, Brian Zhu");
      
      //Search Button
      JButton jSearchButton = new JButton("Search"); 
      jSearchButton.addActionListener(
         new ActionListener(){
            public void actionPerformed(ActionEvent ae){
               //Output Box
               JPanel outBox = new JPanel(new GridLayout(0,1));
               JTextArea jtaOutText = new JTextArea(10,50);
               jtaOutText.setFont(myFontForOutput);
               outBox.add(jtaOutText);
               
               //Search Box
               //Get Input
               JPanel searchBox = new JPanel(new GridLayout(0,2));
               JLabel lbHeader = new JLabel("List Keywords in the Textfield");
               JLabel lbHeader2 = new JLabel("Please Seaparate by comma(',')");
               JLabel lbSearch = new JLabel("Search For: ");
               JLabel lbBlank = new JLabel("");
               JTextField txtSearch = new JTextField("");
               JButton jSearchButton = new JButton("Search");
               jSearchButton.addActionListener(
                     new ActionListener(){
                        public void actionPerformed(ActionEvent ae){
                           String dispMsg = new String();
                           //Reset JTextArea to be Blank
                           jtaOutText.setText("");
                           //First Parse Data from Text Field
                           String strInput = txtSearch.getText();
                           //Check for Blanks
                           if(strInput.equals("") || strInput.equals(null)){
                              JOptionPane.showMessageDialog(null,"Error: Empty Search","ERROR", JOptionPane.WARNING_MESSAGE);
                           }//if
                           else{
                              //Split By Comma
                              String [] strArray = strInput.split(",");
                              int result = 0;
                              for(int i = 0; i < strArray.length; i++){
                                 jtaOutText.append(ms.findMatching(strArray[i]));
                              }//for
                              JOptionPane.showMessageDialog(null,outBox,"Information", JOptionPane.INFORMATION_MESSAGE);
                           }//else
                        }
                     });//List Button
               
               lbHeader.setFont(myFontForOutput);
               lbHeader2.setFont(myFontForOutput);
               lbSearch.setFont(myFontForOutput);
               txtSearch.setFont(myFontForOutput);
               
               searchBox.add(lbHeader);
               searchBox.add(lbHeader2);
               searchBox.add(lbSearch);
               searchBox.add(txtSearch);
               searchBox.add(lbBlank);
               searchBox.add(jSearchButton);
               
               JOptionPane.showOptionDialog(null, searchBox, "Search for Students & Faculty", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);               
            }
         });//Search Button  
      
      //List Users Button
      JButton jListButton = new JButton("List All users"); 
      jListButton.addActionListener(
         new ActionListener(){
            public void actionPerformed(ActionEvent ae){
               JOptionPane.showMessageDialog(null,ms.getAllUsers(),"Information", JOptionPane.INFORMATION_MESSAGE);
            }
         });//End of List Button
      //List Abstracts Button
      //List Button
      JButton lisAbsButton = new JButton("List Abstracts");
      lisAbsButton.addActionListener(
         new ActionListener(){
            public void actionPerformed(ActionEvent ae){
               JOptionPane.showOptionDialog(null, ms.getAbstracts(), "Abstract Configuration - View Function", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
               }
      });//End of List Abstract Button 
      //Abstract Button   
      JButton jAbsButton = new JButton("Abstract Configuration"); 
      jAbsButton.addActionListener(
         new ActionListener(){
            public void actionPerformed(ActionEvent ae){
               //Abstract Configuration
               JPanel absBox = new JPanel(new GridLayout(0,1));
               //Add Button
               JButton addAbs = new JButton("Add Abstract");
               addAbs.addActionListener(
                     new ActionListener(){
                        public void actionPerformed(ActionEvent ae){
                           
                           JPanel addAbsBox = new JPanel(new GridLayout(4,2));
                           addAbsBox.setSize(500,500);
                           
                           JLabel lbAbsUser = new JLabel("Enter Username: ");
                           JLabel lbTitle = new JLabel("Abstract Title: ");
                           JLabel lbContent = new JLabel("Abstract Content: ");
                           JLabel lbBlank = new JLabel("");
                           JTextField tfAbsUser = new JTextField("");
                           JTextField tfTitle = new JTextField("");
                           JTextArea jtaContent = new JTextArea(3,40);
                           JButton addBtn = new JButton("Add");
                           addBtn.addActionListener(
                                 new ActionListener(){
                                    public void actionPerformed(ActionEvent ae){
                                       String inUser = tfAbsUser.getText();
                                       String inTitle = tfTitle.getText();
                                       String inContent = jtaContent.getText();
                                       //Check for Blanks
                                       if(inUser.equals("") || inUser.equals(null)){
                                          JOptionPane.showMessageDialog(null,"Error: Empty User","ERROR", JOptionPane.WARNING_MESSAGE);
                                       }//if
                                       else if(inTitle.equals("") || inTitle.equals(null)){
                                          JOptionPane.showMessageDialog(null,"Error: Empty Title","ERROR", JOptionPane.WARNING_MESSAGE);
                                       }//else if
                                       else if(inContent.equals("") || inContent.equals(null)){
                                          JOptionPane.showMessageDialog(null,"Error: Empty Content","ERROR", JOptionPane.WARNING_MESSAGE);
                                       }//else if
                                       else{
                                          int result = ms.addFacultyAbstract(inUser,inTitle,inContent);
                                          JOptionPane.showMessageDialog(null,"Abstract was added successfully!","Success", JOptionPane.INFORMATION_MESSAGE);
                                       }//else
                                    }//actionPerformed
                                 }//actionListener
                              );//addActionListener
                           lbAbsUser.setFont(myFontForOutput);
                           lbTitle.setFont(myFontForOutput);
                           lbContent.setFont(myFontForOutput);
                           tfAbsUser.setFont(myFontForOutput);
                           tfTitle.setFont(myFontForOutput);
                           jtaContent.setFont(myFontForOutput);
                           addBtn.setFont(myFontForOutput);
                           
                           addAbsBox.add(lbAbsUser);
                           addAbsBox.add(tfAbsUser);
                           addAbsBox.add(lbTitle);
                           addAbsBox.add(tfTitle);
                           addAbsBox.add(lbContent);
                           addAbsBox.add(jtaContent);
                           addAbsBox.add(lbBlank);
                           addAbsBox.add(addBtn);
                           
                           JOptionPane.showOptionDialog(null, addAbsBox, "Abstract Configuration - Add Function", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
                        
                        }//actionPerformed
                     });//ActionListener
               //Delete Button
               JButton delAbs = new JButton("Delete Abstract");
               delAbs.addActionListener(
                     new ActionListener(){
                        public void actionPerformed(ActionEvent ae){
                           
                           JPanel delAbsBox = new JPanel(new GridLayout(2,2));
                           delAbsBox.setSize(500,500);                   
                           JLabel lbAbsID = new JLabel("Enter Abstract ID to be deleted:");
                           JLabel lbBlank = new JLabel("");
                           JTextField tfAbsID = new JTextField();
                           JButton delBtn = new JButton("Delete");
                           delBtn.addActionListener(
                                 new ActionListener(){
                                    public void actionPerformed(ActionEvent ae){
                                       String inAbsID = tfAbsID.getText();
                                       //Check for Blanks
                                       if(inAbsID.equals("") || inAbsID.equals(null)){
                                          JOptionPane.showMessageDialog(null,"Error: Empty Abstract ID","ERROR", JOptionPane.WARNING_MESSAGE);
                                       }//if
                                       else{
                                          int delAbsID = Integer.parseInt(inAbsID);
                                          JOptionPane.showMessageDialog(null, ms.deleteAbstract(delAbsID), "Abstract Configuration", JOptionPane.INFORMATION_MESSAGE);
                                       }//else
                                       
                                    }//actionPerformed
                                 }//ActionListener
                              );//addActionListener
                           
                           
                           lbAbsID.setFont(myFontForOutput);
                           tfAbsID.setFont(myFontForOutput);
                           delBtn.setFont(myFontForOutput);
                           
                           delAbsBox.add(lbAbsID);
                           delAbsBox.add(tfAbsID);
                           delAbsBox.add(lbBlank);
                           delAbsBox.add(delBtn);
                           
                           JOptionPane.showOptionDialog(null, delAbsBox, "Abstract Configuration - Delete Function", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
                        }//actionPerformed
                     }//actionListener
                  );//addActionListener
               
               JButton updAbs = new JButton("Update Abstract");
               updAbs.addActionListener(
                     new ActionListener(){
                        public void actionPerformed(ActionEvent ae){
                           
                           JPanel updAbsBox = new JPanel(new GridLayout(4,2));
                           updAbsBox.setSize(500,500);                   
                           JLabel lbAbsID = new JLabel("Enter Abstract ID to be Updated:");
                           JLabel lbTitle = new JLabel("Abstract Title: ");
                           JLabel lbContent = new JLabel("Abstract Content: ");
                           JLabel lbBlank = new JLabel("");
                           JTextField tfAbsID = new JTextField();
                           JTextField tfTitle = new JTextField("");
                           JTextArea jtaContent = new JTextArea(3,40);
                           JButton updBtn = new JButton("Update");
                           updBtn.addActionListener(
                                 new ActionListener(){
                                    public void actionPerformed(ActionEvent ae){
                                       String inAbsID = tfAbsID.getText();
                                       String inTitle = tfTitle.getText();
                                       String inContent = jtaContent.getText();
                                       //Check for Blanks
                                       if(inAbsID.equals("") || inAbsID.equals(null)){
                                          JOptionPane.showMessageDialog(null,"Error: Empty Abstract ID","ERROR", JOptionPane.WARNING_MESSAGE);
                                       }//if
                                       else if(inTitle.equals("") || inTitle.equals(null)){
                                          JOptionPane.showMessageDialog(null,"Error: Empty Abstract Title","ERROR", JOptionPane.WARNING_MESSAGE);
                                       }//else if
                                       else if(inContent.equals("") || inContent.equals(null)){
                                          JOptionPane.showMessageDialog(null,"Error: Empty Content","ERROR", JOptionPane.WARNING_MESSAGE);
                                       }//else if
                                       else{
                                          int updAbsID = Integer.parseInt(inAbsID);
                                          JOptionPane.showMessageDialog(null, ms.updateAbstract(updAbsID,inTitle,inContent), "Abstract Configuration", JOptionPane.INFORMATION_MESSAGE);
                                       }//else
                                       
                                    }//actionPerformed
                                 }//ActionListener
                              );//addActionListener
                           
                           
                           lbAbsID.setFont(myFontForOutput);
                           lbTitle.setFont(myFontForOutput);
                           lbContent.setFont(myFontForOutput);
                           tfAbsID.setFont(myFontForOutput);
                           tfTitle.setFont(myFontForOutput);
                           jtaContent.setFont(myFontForOutput);
                           updBtn.setFont(myFontForOutput);
                           
                           updAbsBox.add(lbAbsID);
                           updAbsBox.add(tfAbsID);
                           updAbsBox.add(lbTitle);
                           updAbsBox.add(tfTitle);
                           updAbsBox.add(lbContent);
                           updAbsBox.add(jtaContent);
                           updAbsBox.add(lbBlank);
                           updAbsBox.add(updBtn);
                           
                           JOptionPane.showOptionDialog(null, updAbsBox, "Abstract Configuration - Delete Function", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
                        }//actionPerformed
                     }//actionListener
                  );//addActionListener
               
               
               
               addAbs.setFont(myFontForOutput);
               delAbs.setFont(myFontForOutput);
               updAbs.setFont(myFontForOutput);

               absBox.add(addAbs);
               absBox.add(delAbs);
               absBox.add(updAbs);
               
               JOptionPane.showOptionDialog(null, absBox, "Abstract Configuration - Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
               
            }
         });//End of Abstract Button
      
      //Add New User
      JButton jAddButton = new JButton("Add New User"); 
      jAddButton.addActionListener(
         new ActionListener(){
            public void actionPerformed(ActionEvent ae){
               //JPanel for Add Person
               JPanel PersonInputBox = new JPanel(new GridLayout(2,0));
               JLabel lbSFG = new JLabel("Enter User Type S/F/G:");
               JTextField txtSFG = new JTextField("");
               JLabel lbContactType = new JLabel("Contact type:");
               JTextField txtContactType = new JTextField("");
               
               //Setting Font for Add Person 
               lbSFG.setFont(myFontForOutput);
               txtSFG.setFont(myFontForOutput);
               lbContactType.setFont(myFontForOutput);
               txtContactType.setFont(myFontForOutput);
               
               //Add the Person InputBox into Jpanel
               PersonInputBox.add(lbSFG);
               PersonInputBox.add(txtSFG);
               PersonInputBox.add(lbContactType);
               PersonInputBox.add(txtContactType);
               
               JOptionPane.showMessageDialog(null, PersonInputBox, "Add Person", JOptionPane.INFORMATION_MESSAGE);
               
               //JPanel for New User Information
               JPanel NewUserInfo = new JPanel(new GridLayout(12,2));
               
               JLabel business = new JLabel("Business:");
               JTextField txtbusiness = new JTextField("");
               
               JLabel deptID = new JLabel("Department ID:");
               JTextField txtdeptID = new JTextField("");
               
               JLabel fName = new JLabel("First Name:");
               JTextField txtfName = new JTextField("");
               
               JLabel lName = new JLabel("Last Name:");
               JTextField txtlName = new JTextField("");
               
               JLabel uName = new JLabel("User Name:");
               JTextField txtuName = new JTextField("");
               
               JLabel pword = new JLabel("Password:");
               JPasswordField txtpword = new JPasswordField("");
               
               JLabel filler2          = new JLabel(" ");
               JLabel lbBlank = new JLabel("");

               JCheckBox pwCheckbox = new JCheckBox("Show Password"); 
               pwCheckbox.addActionListener(
                  new ActionListener(){ 
                     public void actionPerformed(ActionEvent e){ 
                        if(pwCheckbox.isSelected()){ 
                           txtpword.setEchoChar((char)0);
                        }
                        else{
                           txtpword.setEchoChar('*');
                        }
                     }
                  });
               JButton usrBtn = new JButton("Add User");
               //Show Departments
               JButton showDept = new JButton("Show Departments");
               showDept.addActionListener(
               new ActionListener(){
                  public void actionPerformed(ActionEvent ae){
                     JOptionPane.showMessageDialog(null,ms.getDepts(),"Information", JOptionPane.INFORMATION_MESSAGE);
                  }
               });//End of show depts button
               //Show Interests
               JButton showInterests = new JButton("Show Interests");
               showInterests.addActionListener(
               new ActionListener(){
                  public void actionPerformed(ActionEvent ae){
                     JOptionPane.showMessageDialog(null,ms.getInterests(),"Information", JOptionPane.INFORMATION_MESSAGE);
                  }
               });//End of show interests button 
               //Show Keyword
               JButton showKeywords = new JButton("Show Keywords");
               showKeywords.addActionListener(
               new ActionListener(){
               public void actionPerformed(ActionEvent ae){
                  JOptionPane.showMessageDialog(null,ms.getKeywords(),"Information", JOptionPane.INFORMATION_MESSAGE);
               }
               });//End of show keywords button
               //Add Custom Keywords
               JButton jAddKeyword = new JButton("Add Custom Keyword");
               jAddKeyword.addActionListener(
                  new ActionListener(){
                     public void actionPerformed(ActionEvent ae){
                        // JLabel Keyword = new JLabel("Keyword:");
                           //JTextField txtKeyword = new JTextField("");
                           //String keyword = txtKeyword.getText();
                           String Keyword = new String();
                           Keyword = JOptionPane.showInputDialog(null, "Keyword: ", "Keyword", JOptionPane.INFORMATION_MESSAGE);
                           ms.addNewKeyword(Keyword);

                           }
                     });
               //Add New Keywords to Faculty
               JButton addKey = new JButton("Add Keywords To Faculty");
               addKey.addActionListener(
                  new ActionListener(){
                     public void actionPerformed(ActionEvent ae){
                     
                     JPanel addKeybox = new JPanel(new GridLayout(3,2));
                     addKeybox.setSize(500,500);
                     JLabel lbUsr = new JLabel("Enter Username:");
                     JTextField txtUsr = new JTextField();
                     JLabel lbKey = new JLabel("Enter Keyword ID (Separate by Commas ',')");
                     JTextField txtKey = new JTextField();
                     JLabel lbBlank = new JLabel("");
                     JButton addBtn = new JButton("Add");
                     addBtn.addActionListener(
                        new ActionListener(){
                           public void actionPerformed(ActionEvent ae){
                              String inKey = txtKey.getText();
                              String inUser = txtUsr.getText();
                              System.out.println(inKey);
                              if(inKey.equals("") || inKey.equals(null)){
                                    JOptionPane.showMessageDialog(null,"Error: Empty Keyword ID","ERROR", JOptionPane.WARNING_MESSAGE);
                              }//if
                              else{
                                 String [] keyArray = inKey.split(",");
                                 String dispMsg = new String();
                                 int result = 0;
                                    for(int i = 0; i < keyArray.length; i++){
                                       String keyID = keyArray[i];
                                       if(keyID.matches("[0-9]+")){
                                          int keyInt = Integer.parseInt(keyID);
                                          result = ms.addFacKeywords(inUser,keyInt);
                                          if(result > 0){
                                             JOptionPane.showMessageDialog(null,"Successfully Added Keyword to Faculty","Success", JOptionPane.INFORMATION_MESSAGE);
                                          }//if
                                          else{
                                             JOptionPane.showMessageDialog(null,"Error: Keyword Not Added to Faculty","ERROR", JOptionPane.WARNING_MESSAGE);
                                          }//else
                                       }//if
                                    }//for
                              }//else
                           }//ActionEvent
                        }//ActionListener
                     );//
                     
                     lbUsr.setFont(myFontForOutput);
                     txtUsr.setFont(myFontForOutput);
                     lbKey.setFont(myFontForOutput);
                     txtKey.setFont(myFontForOutput);
                     addBtn.setFont(myFontForOutput);
                     
                     addKeybox.add(lbUsr);
                     addKeybox.add(txtUsr);
                     addKeybox.add(lbKey);
                     addKeybox.add(txtKey);
                     addKeybox.add(lbBlank);
                     addKeybox.add(addBtn);
                     
                     JOptionPane.showOptionDialog(null, addKeybox, "Add Keywords", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
                     
                     }//actionPerformed
               });//addActionListener

               //Setting Font for New User Info
               business.setFont(myFontForOutput);
               txtbusiness.setFont(myFontForOutput);
               deptID.setFont(myFontForOutput);
               txtdeptID.setFont(myFontForOutput);
               fName.setFont(myFontForOutput);
               txtfName.setFont(myFontForOutput);
               lName.setFont(myFontForOutput);
               txtlName.setFont(myFontForOutput);
               uName.setFont(myFontForOutput);
               txtuName.setFont(myFontForOutput);
               pword.setFont(myFontForOutput);
               txtpword.setFont(myFontForOutput);
               usrBtn.setFont(myFontForOutput);
               showInterests.setFont(myFontForOutput);
               showKeywords.setFont(myFontForOutput);
               showDept.setFont(myFontForOutput);
               addKey.setFont(myFontForOutput);
               jAddKeyword.setFont(myFontForOutput);
               //Add the New User Info input box into Jpanel
               NewUserInfo.add(business);
               NewUserInfo.add(txtbusiness);
               NewUserInfo.add(deptID);
               NewUserInfo.add(txtdeptID);
               NewUserInfo.add(fName);
               NewUserInfo.add(txtfName);
               NewUserInfo.add(lName);
               NewUserInfo.add(txtlName);
               NewUserInfo.add(uName);
               NewUserInfo.add(txtuName);
               NewUserInfo.add(pword);
               NewUserInfo.add(txtpword);
               NewUserInfo.add(filler2);
               NewUserInfo.add(pwCheckbox);
               NewUserInfo.add(showInterests);
               NewUserInfo.add(usrBtn);
               NewUserInfo.add(showDept);
               NewUserInfo.add(jAddKeyword);
               NewUserInfo.add(showKeywords);
               NewUserInfo.add(addKey);
               
               
               String UserAcc = txtSFG.getText();
               String contact = txtContactType.getText();
               ms.addPerson(UserAcc,contact);
               
               if(UserAcc.toLowerCase().equals("s")){
                  txtdeptID.setEnabled(false);
                  txtbusiness.setEnabled(false);
                  addKey.setEnabled(false);
                  jAddKeyword.setEnabled(false);
                  usrBtn.addActionListener(
                     new ActionListener(){
                        public void actionPerformed(ActionEvent ae){
                           int id = ms.getLastInsert();
                           String firstName = txtfName.getText();
                           String lastName = txtlName.getText();
                           String userName = txtuName.getText();
                           String password = txtpword.getText();
                           ms.addStudent(id, firstName, lastName, userName, password);
                     }//actionPerformed
                  
                  }//ActionListener
               
               );//accActionListener      
               }
               else if(UserAcc.toLowerCase().equals("f")){
                  txtbusiness.setEnabled(false);
                  usrBtn.addActionListener(
                     new ActionListener(){
                        public void actionPerformed(ActionEvent ae){
                           int id = ms.getLastInsert();
                           int deptid = Integer.parseInt(txtdeptID.getText());
                           String firstName = txtfName.getText();
                           String lastName = txtlName.getText();
                           String userName = txtuName.getText();
                           String password = txtpword.getText();
                           ms.addFaculty(id, deptid, firstName, lastName, userName, password);
                     }//actionPerformed
                  
                  }//ActionListener
               
               );//accActionListener
               }
               else if(UserAcc.toLowerCase().equals("g")){
                  txtdeptID.setEnabled(false);
                  addKey.setEnabled(false);
                  jAddKeyword.setEnabled(false);
                  usrBtn.addActionListener(
                     new ActionListener(){
                        public void actionPerformed(ActionEvent ae){
                           int id = ms.getLastInsert();
                           String business = txtbusiness.getText();
                           String firstName = txtfName.getText();
                           String lastName = txtlName.getText();
                           String userName = txtuName.getText();
                           String password = txtpword.getText();
                           ms.addGuest(id, business, firstName, lastName, userName, password);
                     }//actionPerformed
                  
                  }//ActionListener
               
               );//accActionListener
               }
               

               JOptionPane.showOptionDialog(null, NewUserInfo, "Insert Person", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
            }
         });//End of Add New User button
         
      //Exit Button
      JButton jExitButton = new JButton("EXIT"); 
      jExitButton.addActionListener(
         new ActionListener(){
            public void actionPerformed(ActionEvent ae){
               ms.close();
               java.util.Date today = new java.util.Date();
               System.out.println("Program terminated @ "+today);
               JOptionPane.showMessageDialog(null,"Program terminated @"+today,"Plain", JOptionPane.PLAIN_MESSAGE);
               System.exit(0);
            }
         });//End of Exit button
      jpCenter.add(creds);
      jpCenter.add(jSearchButton);
      jpCenter.add(jListButton);
      jpCenter.add(lisAbsButton);
      jpCenter.add(jAbsButton);
      jpCenter.add(jAddButton);
      jpCenter.add(jExitButton);
      add(jpCenter);
                    
      creds.setFont(myFontForOutput);         
      jSearchButton.setFont(myFontForOutput);
      jListButton.setFont(myFontForOutput);
      lisAbsButton.setFont(myFontForOutput);
      jAbsButton.setFont(myFontForOutput);
      jAddButton.setFont(myFontForOutput);
      jExitButton.setFont(myFontForOutput);
      
      setVisible(true);
      
      String user = tfUser.getText();
      String pass = tfPassword.getText();
      String encryptedPass = new String();
      boolean secure = false;
               
      //Student Account
      String Acc = ms.getAccType(user);
      if(Acc.toLowerCase().equals("s")){
         System.out.println("\nStudent Account Type Found!");
         JOptionPane.showMessageDialog(null,"Student Login Successful - Entering READ ONLY Mode","Information", JOptionPane.INFORMATION_MESSAGE);
         jAddButton.setEnabled(false);
         jAbsButton.setEnabled(false);
      }//if
      
      //Guest Account
      else if(Acc.toLowerCase().equals("g")){
         System.out.println("\nGuest Account Type Found!");
         JOptionPane.showMessageDialog(null,"Guest Login Successful - Entering READ ONLY Mode","Information", JOptionPane.INFORMATION_MESSAGE);
         jAddButton.setEnabled(false);
         jAbsButton.setEnabled(false);
      }//else if
      
      //Faculty Account
      else if(Acc.toLowerCase().equals("f")){
         System.out.println("\nFaculty Account Type Found!");
         //Begin Faculty Login Process
         //Encrypt Plain Text Password
         encryptedPass = ms.encrypt(pass);
         //Verify Login
         secure = ms.verifyFacPassword(user,encryptedPass);
         if(secure == true){
            JOptionPane.showMessageDialog(null,"Faculty Login Successful - Entering READ / WRITE Mode","Information", JOptionPane.INFORMATION_MESSAGE);
         
         }//if
         else{
            JOptionPane.showMessageDialog(null,"Faculty Login Failed - Entering READ ONLY Mode","Warning", JOptionPane.WARNING_MESSAGE);
         
         }//else
      }//else if
      
      else{
         System.out.println("\nUser Account Doesn't Exist - Quitting Program");
      
         System.exit(0);
      }//else
   
   }//projectLoginGUI Constructor
   
   public static void main(String [] args){
      new projectPresentationLayer();
   }//main
}//projectPresentationLayer