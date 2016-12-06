/**Java Database Connectivity Final Project
*Course Title: Java Data Connectivity and Access 
*Course Number: ISTE-330
*Instructor: Professor Floeser
*@author Jenna Tillotson, Louis Trapani, Rosalee Hacker, Steven Ricci
*@version 1.0, 12/7/2016
*
*Description: Post View Class
*"This program conatins methods to support the creation of a JPanel for
*the Post tab in the GUI. Action Listener is implemented for events."
*/

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import javax.swing.JOptionPane;


//JPanel for PostTab

public class AddView extends JPanel implements ActionListener {

   //global variables
   private JPanel jpCenter;
   private JTextField jtfUserName;
   private JTextField jtfPassword;
   private JTextField jtfName;
   private JButton jbSubmit;

   /**
   *Constructor
   */
   public AddView() {
      setLayout(new BorderLayout());
      
      //north of panel
      add(new JLabel("Add New Faculty User", JLabel.CENTER), BorderLayout.NORTH);
      
      //center of panel
      jpCenter = new JPanel(new GridLayout(0,2));
      
      jpCenter.add(new JLabel("Username: ", JLabel.RIGHT));
      jtfUserName = new JTextField();
      jpCenter.add(jtfUserName);
      
      jpCenter.add(new JLabel("Password: ", JLabel.RIGHT));
      jtfPassword = new JTextField();
      jpCenter.add(jtfPassword);
      
      jpCenter.add(new JLabel("Name: ", JLabel.RIGHT));
      jtfName = new JTextField();
      jpCenter.add(jtfName);
      
      add(jpCenter, BorderLayout.CENTER);
      
      //south of panel
      jbSubmit = new JButton("Submit");
      jbSubmit.addActionListener(this);
      add(jbSubmit, BorderLayout.SOUTH);
      jbSubmit.setPreferredSize(new Dimension(20, 40));

   } //end constructor
      /**Implementing actionn event using actionPerformed method
      *@param ActionEvent attribute
      */
      public void actionPerformed(ActionEvent ae) {
         String newUser = jtfUserName.getText();
         String newPass = jtfPassword.getText();
         String newFullName = jtfName.getText();
         
         DLUser newDL = new DLUser();
         try {
            newDL.post(newUser, newPass, newFullName, "Faculty");
            jtfUserName.setText("");
            jtfPassword.setText("");
            jtfName.setText("");
         }
         catch(Exception e) {
            JOptionPane.showMessageDialog(this, "Error creating account.");
            System.out.println("Exception Occured");
         }        
      }
   
   

} //end class PostView
