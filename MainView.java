import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

//GUI class

public class MainView extends JFrame {

   //global variables
   public JTabbedPane jtp;
   public JPanel facultyTab;
   public JPanel researchTab;
   public JPanel postTab;
   public JPanel searchTab;
   public JPanel addTab;
   public JPanel keywordsTab;
   public JPanel collabTab;
   public DLUser dl = null;

   //constructor
   public MainView(DLUser _dl) {
   
      dl = _dl;
   
      //tabbed pane
      jtp = new JTabbedPane();
      
      
      //faculty viewing tab -- only viewable if faculty
      try {
         if (dl.getAccess().equals("Faculty")) {
         facultyTab = new FacultyView(dl);
         jtp.add("My Research", facultyTab);
         }
      
         //research viewing tab
         researchTab = new ResearchView();
         jtp.add("All Research", researchTab);

         //add research tab -- only viewable if admin or faculty
         if (dl.getAccess().equals("Faculty") || dl.getAccess().equals("Admin")) {
            postTab = new PostView(dl);
            jtp.add("Post", postTab);
         }
      }
      } catch (NullPointerException npe) {
          System.out.println("Exception occured");
          JOptionPane.showMessageDialog(this, "<html>Error loggin in. Check the following:" +
                "<br>1. You have filled out all the fields<br>2. Username is correct" +
                "<br>3. Password is correct<br>4. Your account exists ");
       }
      if (dl.getAccess().equals("Admin")) {
         addTab = new AddView();
         jtp.add("Add Faculty", addTab);
         keywordsTab = new KeywordsView();
         jtp.add("Edit Keywords", keywordsTab);
      }
      
      if (dl.getAccess().equals("Student") || dl.getAccess().equals("Faculty")) {
          collabTab = new CollabView(dl);
          jtp.add("Collaborations", collabTab);
       }
      
      //advanced search tab
       searchTab = new SearchView(dl);
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
