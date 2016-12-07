/**Java Database Connectivity Final Project
*Course Title: Java Data Connectivity and Access 
*Course Number: ISTE-330
*Instructor: Professor Floeser
*@author Jenna Tillotson, Louis Trapani, Rosalee Hacker, Steven Ricci
*@version 1.0, 12/7/2016
*
*Description: Post View Class
*"This program conatins methods the creation of a JPanel for the 
*Research tab in our GUI. This class requires a connection to the Database
*and use of 2d ArrayLists for data."
*/

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.util.ArrayList;

//JPanel for ResearchTab

public class ResearchView extends JPanel {

   //global variables
   private JTable table;
   private JTextField jtfSearch;

   private String [] columnNames = {"Title", "Abstract", "Citation", "Author"}; 
   private Object [][] data;                              
   private Database db;
   
   /**
   *Constructor
   */
   public ResearchView() {
   
      db = new Database();
      try {
         db.connect();
         
         String sql = "SELECT p.title, p.abstract, p.citation, CONCAT(f.fname, ' ', f.lname) FROM papers p " +
                      "INNER JOIN authorship a ON a.paperId = p.id " +
                      "INNER JOIN faculty f ON a.facultyId = f.id";
         
         ArrayList<ArrayList<String>> myData = db.getData(sql, true);
         data = db.convert(myData);
          
         //center/south of panel
         table = new JTable(new CustomTableModel(columnNames, data));
         table.setRowHeight(50);
      
         JScrollPane jsp = new JScrollPane(table);
         jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
         add(jsp);
      
      }
      catch(DLException dle) {
         System.out.println("DLE");
      }

   } //end constructor
   
} //end class ResearchView
