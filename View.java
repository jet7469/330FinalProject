import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//GUI class

public class View extends JFrame {

   //global variables
   public JTabbedPane jtp;
   public JPanel facultyTab;
   public JPanel researchTab;
   public JPanel postTab;
   public JPanel searchTab;
   

   //constructor
   public View() {
   
      //plan to make a JOptionPane on opening of the program that allows to login or create acccount
      
      //tabbed pane
      jtp = new JTabbedPane();
      facultyTab = new FacultyView();
      jtp.add("Faculty", facultyTab);
      researchTab = new ResearchView();
      jtp.add("Research", researchTab);
      postTab = new PostView();
      jtp.add("Post", postTab);
      searchTab = new SearchView();
      jtp.add("Advanced Search", searchTab);
      add(jtp);
      
      
      //GUI settings
      setTitle("Faculty Research");
      setVisible(true);
      setLocationRelativeTo(null);
      setSize(600,500);
      setDefaultCloseOperation(EXIT_ON_CLOSE);

   } //end constructor

} //end class View