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
   public JPanel addTab;
   public DLUser dl = null;

   //constructor
   public View(DLUser _dl) {
   
      dl = _dl;
   
      //tabbed pane
      jtp = new JTabbedPane();
      
      
      //faculty viewing tab -- only viewable if admin or faculty
      if (dl.getAccess().equals("Faculty") || dl.getAccess().equals("Admin")) {
         facultyTab = new FacultyView();
         jtp.add("My Research", facultyTab);
      }
      
      //research viewing tab
      researchTab = new ResearchView();
      jtp.add("Research", researchTab);
      
      //add research tab -- only viewable if admin or faculty
      if (dl.getAccess().equals("Faculty") || dl.getAccess().equals("Admin")) {
         postTab = new PostView();
         jtp.add("Post", postTab);
      }
      
      if (dl.getAccess().equals("Admin")) {
         postTab = new PostView();
         jtp.add("Add Faculty", postTab);
      }
      
      //advanced search tab
      addTab = new addView(); // DOES NOT EXIST YET
      jtp.add("Advanced Search", addTab);
      add(jtp);
      
      
      //GUI settings
      setTitle("Faculty Research");
      setVisible(true);
      setLocationRelativeTo(null);
      setSize(600,500);
      setDefaultCloseOperation(EXIT_ON_CLOSE);

   } //end constructor

} //end class View