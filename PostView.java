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

public class PostView extends JPanel implements ActionListener {

   //global variables
   private JPanel jpCenter;
   private JTextField jtfTitle;
   private JTextField jtfAbstract;
   private JTextField jtfCitation;
      
   private JButton jbSubmit;

   //constructor
   public PostView() {
      setLayout(new BorderLayout());
      
      //north of panel
      add(new JLabel("Add New Research", JLabel.CENTER), BorderLayout.NORTH);
      
      //center of panel
      jpCenter = new JPanel(new GridLayout(0,2));
           
      jpCenter.add(new JLabel("Title:", JLabel.RIGHT));
      jtfTitle = new JTextField();
      jpCenter.add(jtfTitle);
      
      jpCenter.add(new JLabel("Abstract:", JLabel.RIGHT));
      jtfAbstract = new JTextField();
      jpCenter.add(jtfAbstract);
      
      jpCenter.add(new JLabel("Citation:", JLabel.RIGHT));
      jtfCitation = new JTextField();
      jpCenter.add(jtfCitation);
      
      add(jpCenter, BorderLayout.CENTER);
      
      //south of panel
      jbSubmit = new JButton("Submit"); //ACTIONLISTENER FOR BUTTON NEEDED HERE***
      jbSubmit.addActionListener(this);      
      add(jbSubmit, BorderLayout.SOUTH);
      jbSubmit.setPreferredSize(new Dimension(20, 40));
   } //end constructor
   
      public void actionPerformed(ActionEvent ae) {
         String newTitle = jtfTitle.getText();
         String newAbstract = jtfAbstract.getText();
         String newCitation = jtfCitation.getText();
         
         
         Papers newPaper = new Papers();
         try {
            newPaper.post(0, newTitle, newAbstract, newCitation);
            jtfTitle.setText("");
            jtfAbstract.setText("");
            jtfCitation.setText("");
         }
         catch(Exception e) {
            JOptionPane.showMessageDialog(this, "Error creating account.");
            System.out.println("Exception Occured");
         }        
      }      

      

} //end class PostView
