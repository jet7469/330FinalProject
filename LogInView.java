import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

//GUI class

public class LogInView extends JFrame {

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
   JTextField existingEmail;
   JTextField existingPassword;
   JButton login;
   //variables for creating a new account
   JLabel newAccount;
   JRadioButton facultyUser;
   JRadioButton studentUser;
   JRadioButton publicUser;
   JTextField newEmail;
   JTextField newPassword;
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
      west.setBorder(new EmptyBorder(100, 100, 100, 100));
      existingAccount = new JLabel("Have an account?");
      existingEmail = new JTextField();
      existingEmail.setToolTipText("Email");
      existingPassword = new JTextField();
      existingPassword.setToolTipText("Password");
      login = new JButton("Submit");
      west.add(existingAccount);
      west.add(existingEmail);
      west.add(existingPassword);
      west.add(login, BorderLayout.SOUTH);
      
      
      //east panel for creating a new account
      east = new JPanel(new GridLayout(0,1));
      east.setBorder(new EmptyBorder(100, 100, 100, 100));
      newAccount = new JLabel("First time here?");
      facultyUser = new JRadioButton("Faculty");
      studentUser = new JRadioButton("Student");
      publicUser = new JRadioButton("Public");
      newEmail = new JTextField();
      newEmail.setToolTipText("Email");
      newPassword = new JTextField();
      newPassword.setToolTipText("Password");
      newAccountSubmit = new JButton("Submit");
      east.add(newAccount);
      east.add(facultyUser);
      east.add(studentUser);
      east.add(publicUser);
      east.add(newEmail);
      east.add(newPassword);
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

} //end class LogInView
