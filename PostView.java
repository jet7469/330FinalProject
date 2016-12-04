import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

//JPanel for PostTab

public class PostView extends JPanel {

   //global variables
   private JPanel jpCenter;
   private JTextField jtfTitle;
   private JTextField jtfTopic;
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
      
      jpCenter.add(new JLabel("Topic:", JLabel.RIGHT));
      jtfTopic = new JTextField();
      jpCenter.add(jtfTopic);
      
      jpCenter.add(new JLabel("Abstract:", JLabel.RIGHT));
      jtfAbstract = new JTextField();
      jpCenter.add(jtfAbstract);
      
      jpCenter.add(new JLabel("Citation:", JLabel.RIGHT));
      jtfCitation = new JTextField();
      jpCenter.add(jtfCitation);
      
      add(jpCenter, BorderLayout.CENTER);
      
      //south of panel
      jbSubmit = new JButton("Submit"); //ACTIONLISTENER FOR BUTTON NEEDED HERE***
      add(jbSubmit, BorderLayout.SOUTH);
      jbSubmit.setPreferredSize(new Dimension(20, 40));
      
      
   
   
   } //end constructor

} //end class PostView
