import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

//JPanel for SearchTab

public class SearchView extends JPanel {

   //global variables
   private JPanel jpCenter;
   private JTextField jtfKeyword;
   private JTextField jtfFaculty;
   private JTextField jtfEmail;
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
      
      jpCenter.add(new JLabel("Email: ", JLabel.RIGHT));
      jtfEmail = new JTextField();
      jpCenter.add(jtfEmail);
      
      add(jpCenter, BorderLayout.CENTER);
      
      //south of panel
      jbSearch = new JButton("Submit");
      jbSearch.setPreferredSize(new Dimension(20, 40));
      add(jbSearch, BorderLayout.SOUTH);
   
   } //end constructor

} //end class SearchView
