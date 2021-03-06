/**Java Database Connectivity Final Project
*Course Title: Java Data Connectivity and Access 
*Course Number: ISTE-330
*Instructor: Professor Floeser
*@author Jenna Tillotson, Louis Trapani, Rosalee Hacker, Steven Ricci
*@version 1.0, 12/7/2016
*
*Description: LoginView Class
*"This program conatins the methods and classes required to
*create a GUI for a Login Page for those accessing our program.
*There are multiple options for logging in depending on access level
*and we implemented Java events for the various scenrios."
*/
import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;

//GUI class

public class LogInView extends JFrame implements ActionListener {

   //global variables
   //panels
   JPanel north;
   JPanel east;
   JPanel west;
   //titles of screen
   JLabel title;
   JLabel subtitle;
   //variables for logging in with exisiting account
   JLabel existingAccount;
   JTextField existingUsername;
   JPasswordField existingPassword;
   JButton login;
   //variables for creating a new account
   JLabel newAccount;
   JTextField newUsername;
   JPasswordField newPassword;
   JTextField newName;
   JButton newAccountSubmit;


   //constructor
   public LogInView() {
      
      
      //sets JLabel for title and subtitle
      title = new JLabel("<html><center>Faculty Research</center><br>Login to access the RIT Faculty Research Database", JLabel.CENTER);
      title.setFont(title.getFont().deriveFont(18.0f));
   
      //establishes northern jpanel
      //adds jlabel to northern panel
      north = new JPanel(new BorderLayout());
      north.add(title);
      
      //west panel for loggin in with an exisiting account
      west = new JPanel(new GridLayout(0,1));
      west.setBorder(new EmptyBorder(70, 100, 100, 00));
      existingAccount = new JLabel("Have an account?");
      existingUsername = new JTextField();
      existingPassword = new JPasswordField();
      login = new JButton("Submit");
      login.addActionListener(this);
      west.add(existingAccount);
      west.add(new JLabel("Username:",JLabel.LEFT));
      west.add(existingUsername);
      west.add(new JLabel("Password:",JLabel.LEFT));
      west.add(existingPassword);
      west.add(login, BorderLayout.SOUTH);
      
      
      //east panel for creating a new account
      east = new JPanel(new GridLayout(0,1));
      east.setBorder(new EmptyBorder(70, 100, 100, 100));
      newAccount = new JLabel("First time here as a student?");
      newUsername = new JTextField();
      newPassword = new JPasswordField();
      newName = new JTextField();
      newAccountSubmit = new JButton("Submit");
      newAccountSubmit.addActionListener(this);
      east.add(newAccount);
      east.add(new JLabel("Username:",JLabel.LEFT));
      east.add(newUsername);
      east.add(new JLabel("Password:",JLabel.LEFT));
      east.add(newPassword);
      east.add(new JLabel("Full name:",JLabel.LEFT));
      east.add(newName);
      east.add(newAccountSubmit, BorderLayout.SOUTH);
      
      //adds panels to frame
      add(north, BorderLayout.NORTH);
      add(west, BorderLayout.WEST);
      add(east, BorderLayout.EAST);
      
      //GUI settings
      setTitle("LogIn");
      setVisible(true);
      setLocationRelativeTo(null);
      setSize(600,500);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
   } //end constructor
   
   /**Implementation of an Action Event
   *for login attemps and their outcomes
   *@param ActionEvent id
   */
   public void actionPerformed(ActionEvent ae) {
      
      //find which button was clicked
      Object choice = ae.getSource();
      
      if (choice == login) {
         String username = existingUsername.getText();
         String pass = existingPassword.getText();
         
         DLUser dl = new DLUser(username);
         try {
            dl.login(username,pass);
            System.out.println("LOGIN " + dl.getAccess());
            new MainView(dl);  
         }
         
         catch (DLException dle) {
            System.out.println("Exception occured");
            JOptionPane.showMessageDialog(this, "<html>Error loggin in. Check the following:" +
               "<br>1. You have filled out all the fields<br>2. Username is correct" +
               "<br>3. Password is correct<br>4. Your account exists ");
         }
         catch (Exception e) {
            System.out.println("Generic exception");
            e.printStackTrace();
         } 
      }
      
      else if (choice == newAccountSubmit) {
         
         String newUser = newUsername.getText();
         String newPass = newPassword.getText();
         String newFullName = newName.getText();
         
         DLUser newDL = new DLUser();
         try {
            boolean newEntry = newDL.post(newUser, newPass, newFullName, "Student");
         
            if (newEntry) {
               newDL.login(newUser, newPass);
            }
         }
         catch(Exception e) {
            JOptionPane.showMessageDialog(this, "Error creating account.");
            System.out.println("Exception Occured");
         }            
      }   
   }
} //end class LogInView
