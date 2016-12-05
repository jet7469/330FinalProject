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

   //constructor
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
      jbSubmit = new JButton("Submit"); //ACTIONLISTENER FOR BUTTON NEEDED HERE***
      jbSubmit.addActionListener(this);
      add(jbSubmit, BorderLayout.SOUTH);
      jbSubmit.setPreferredSize(new Dimension(20, 40));

   } //end constructor

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