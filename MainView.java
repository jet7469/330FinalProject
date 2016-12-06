/**Java Database Connectivity Final Project
*Course Title: Java Data Connectivity and Access 
*Course Number: ISTE-330
*Instructor: Professor Floeser
*@author Jenna Tillotson, Louis Trapani, Rosalee Hacker, Steven Ricci
*@version 1.0, 12/7/2016
*
*Description: Main View Class
*"This class creates GUI using JFrame for our
*programs main page that contains tabs for different section
*and also verifies access for sections only viewable for 
*members with different access levels"
*/
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
   public DLUser dl = null;

   //constructor
   /**MainView class constructor
   *@param Data layer user
   */
   public MainView(DLUser _dl) {
   
      dl = _dl;
   
      //tabbed pane
      jtp = new JTabbedPane();
      
      
      //faculty viewing tab -- only viewable if faculty
      try 
      {
         if (dl.getAccess().equals("Faculty")) {
            facultyTab = new FacultyView();
            jtp.add("My Research", facultyTab);
         }
      
      
      //research viewing tab
      researchTab = new ResearchView();
      jtp.add("All Research", researchTab);
      
      //add research tab -- only viewable if admin or faculty
      if (dl.getAccess().equals("Faculty") || dl.getAccess().equals("Admin")) {
         postTab = new PostView();
         jtp.add("Post", postTab);
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
