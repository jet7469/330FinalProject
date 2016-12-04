import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;

//GUI class

public class View extends JFrame {

   //global variables
   public JTabbedPane jtp;
   public JPanel facultyTab;
   public JPanel researchTab;
   public JPanel postTab;
   public JPanel searchTab;
   DLUser dl;

   //constructor
   public View(DLUser dl) {
   
      this.dl = dl;
   
      //tabbed pane
      jtp = new JTabbedPane();
      
      //faculty viewing tab -- only viewable if admin or faculty
      if (dl.getAccess() == "Faculty") {
         facultyTab = new FacultyView();
         jtp.add("My Research", facultyTab);
      }
      
      //research viewing tab
      researchTab = new ResearchView();
      jtp.add("Research", researchTab);
      
      //add research tab -- only viewable if admin or faculty
      if (dl.getAccess() == "Faculty") {
         postTab = new PostView();
         jtp.add("Post", postTab);
      }
      
      //advanced search tab
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