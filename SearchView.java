import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;


//JPanel for SearchTab

public class SearchView extends JPanel implements ActionListener {

   //global variables
   private JPanel jpCenter;
   private JTextField jtfKeyword;
   private JTextField jtfFaculty;
   private JTextField jtfTopic;
   private JButton jbSearch;

   //constructor
   public SearchView() {
      setLayout(new BorderLayout());
      
      //north of panel
      add(new JLabel("Search By-", JLabel.CENTER), BorderLayout.NORTH);
      
      //center of panel
      jpCenter = new JPanel(new GridLayout(0,2));
      
      jpCenter.add(new JLabel("Keyword: ", JLabel.RIGHT));
      jtfKeyword = new JTextField();
      jpCenter.add(jtfKeyword);
      
      jpCenter.add(new JLabel("Faculty: ", JLabel.RIGHT));
      jtfFaculty = new JTextField();
      jpCenter.add(jtfFaculty);
      
      jpCenter.add(new JLabel("Topic: ", JLabel.RIGHT));
      jtfTopic = new JTextField();
      jpCenter.add(jtfTopic);
      
      add(jpCenter, BorderLayout.CENTER);
      
      //south of panel
      jbSearch = new JButton("Submit");
      jbSearch.setPreferredSize(new Dimension(20, 40));
      add(jbSearch, BorderLayout.SOUTH);
   
   } //end constructor
   
   public void actionPerformed(ActionEvent ae) {
      String keyword = jtfKeyword.getText();
      String facName = jtfFaculty.getText();
      String topic = jtfTopic.getText();
      
      if (keyword != "") {
      //select statement
      }
      if (facName != "") {
      //select statement
      }
      if (topic != "") {
      //select statement
      }

   }

} //end class SearchView
