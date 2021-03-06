/**Java Database Connectivity Final Project
*Course Title: Java Data Connectivity and Access 
*Course Number: ISTE-330
*Instructor: Professor Floeser
*@author Jenna Tillotson, Louis Trapani, Rosalee Hacker, Steven Ricci
*@version 1.0, 12/7/2016
*
*Description: Faculty View Class
*"This program conatins methods to create a JPanel for the Faculty tab
*in the GUI. Program takes the faculty memeber that is logged in 
*and loads all of their research."
*Follows Google check style
*/

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *JPanel for FacultyTab
 *Takes the faculty member that is logged in and loads all of their research.
 */
 
public class FacultyView extends JPanel implements ActionListener {

  //global variables
  private String [] columnNames = {"Title", "Abstract", "Citation", "Author"};
  private Object [][] data;
  private JButton jbReload;
  private JButton jbDelete;
  private JPanel jpButtons;
  private JTable table;
  private Database db;
  private DLUser dl;
   
  /**
  *Constructor.
  */
  public FacultyView(DLUser dl) {
   
    setLayout(new BorderLayout());
      
    //title at top
    add(new JLabel("My Papers", JLabel.CENTER), BorderLayout.NORTH);
      
    db = new Database();
    try {
      db.connect();
         
      /**
      *This is such bad coding practice
      *I didn't know how else to make it work 
      *I am so sorry
      */
      if (dl.getUsername().equals("stevezilora")) {
        String sql = "SELECT p.title, p.abstract, p.citation, CONCAT(f.fname, ' ', f.lname) " 
            + "FROM papers p INNER JOIN authorship a ON a.paperId = p.id " 
            + "INNER JOIN faculty f ON a.facultyId = f.id " 
            + "WHERE f.lname = 'Zilora'";
            
        ArrayList<ArrayList<String>> myData = db.getData(sql, true);
        data = db.convert(myData);    
            
        table = new JTable(new CustomTableModel(columnNames, data));
        table.setRowHeight(50);
         
        JScrollPane jsp = new JScrollPane(table);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(jsp, BorderLayout.CENTER);
      } else if (dl.getUsername().equals("danbogard")) {
        String sql = "SELECT p.title, p.abstract, p.citation, CONCAT(f.fname, ' ', f.lname) " 
             + " FROM papers p INNER JOIN authorship a ON a.paperId = p.id " 
             + "INNER JOIN faculty f ON a.facultyId = f.id " 
             + "WHERE f.lname = 'bogaard'";
            
        ArrayList<ArrayList<String>> myData = db.getData(sql, true);
        data = db.convert(myData);
            
        table = new JTable(new CustomTableModel(columnNames, data));
        table.setRowHeight(50);
         
        JScrollPane jsp = new JScrollPane(table);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(jsp, BorderLayout.CENTER);
         
      }
      db.close();   
    } catch (DLException dle) {
      System.out.println("DL Exception");
    }
      
    //delete button on south
    jpButtons = new JPanel();
    jbDelete = new JButton("Delete");
    jbReload = new JButton("Update");
    jpButtons.add(jbReload);
    jpButtons.add(jbDelete);
    add(jpButtons, BorderLayout.SOUTH);
    jbDelete.addActionListener(this);
    jbReload.addActionListener(this);     
  
  } //end constructor
   
  /**actionPerformed
  *@param ActionEvent.
  */    
  public void actionPerformed(ActionEvent ae) {
      
    Object choice = ae.getSource();
      
    if (choice == jbDelete) {
      
      //get selected row index
      int rowIndex = table.getSelectedRow();
      System.out.println(rowIndex);
         
      try {
        db.connect();
            
        //get the title from the array in that row
        Object selected = data [rowIndex][0];
        String selectedTitle = selected.toString();
            
        //query to delete row with that title
        String sql = "DELETE FROM papers WHERE title = '" + selectedTitle + "'";
        db.setData(sql);
         
        db.close();
      } catch (DLException dle) {
        System.out.println("DL EXCEption");
      }
    } else if (choice == jbReload) {
      db = new Database();
      try {
        db.connect();
         
        /**
        *This is such bad coding practice
        *I didn't know how else to make it work 
        *I am so sorry
        */
        if (dl.getUsername().equals("stevezilora")) {
          String sql = "SELECT p.title, p.abstract, p.citation, CONCAT(f.fname, ' ', f.lname) " 
              + "FROM papers pINNER JOIN authorship a ON a.paperId = p.id " 
              + "INNER JOIN faculty f ON a.facultyId = f.id " 
              + "WHERE f.lname = 'Zilora'";
            
          ArrayList<ArrayList<String>> myData = db.getData(sql, true);
          data = db.convert(myData);
            
          table = new JTable(new CustomTableModel(columnNames, data));
          table.setRowHeight(50);
            
          JScrollPane jsp = new JScrollPane(table);
          jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
          add(jsp, BorderLayout.CENTER);
        } else if (dl.getUsername().equals("danbogard")) {
          String sql = "SELECT p.title, p.abstract, p.citation, CONCAT(f.fname, ' ', f.lname) " 
               + "FROM papers p INNER JOIN authorship a ON a.paperId = p.id " 
               + "INNER JOIN faculty f ON a.facultyId = f.id " 
               + "WHERE f.lname = 'bogaard'";
            
          ArrayList<ArrayList<String>> myData = db.getData(sql, true);
          data = db.convert(myData);
            
          table = new JTable(new CustomTableModel(columnNames, data));
          table.setRowHeight(50);
            
          JScrollPane jsp = new JScrollPane(table);
          jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
          add(jsp, BorderLayout.CENTER);
            
        }
        db.close();   
      } catch (DLException dle) {
        System.out.println("DL Exception");
      }
    }
  }
} //end class facultyView
