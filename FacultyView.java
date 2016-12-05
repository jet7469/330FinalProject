import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTable;

import java.awt.BorderLayout;

//JPanel for FacultyTab
//Takes the faculty member that is logged in and loads all of their research

public class FacultyView extends JPanel {

   //global variables
   private String [] columnNames = {"Title", "Abstract", "Citation"};
   Object [][] data = { {"title1", "abstract1", "cite1"}, {"title2", "abstract2", "citation2"}};
   private JButton jbDelete;
   private JTable table;


   //constructor
   public FacultyView() {
      setLayout(new BorderLayout());
      
      //title at top
      add(new JLabel("My Papers", JLabel.CENTER), BorderLayout.NORTH);
      
      //table in center
      table = new JTable(new CustomTableModel(columnNames, data));
      table.setRowHeight(50);
      table.setShowGrid(false);
      table.setShowHorizontalLines(true);
      add(table, BorderLayout.CENTER);
      
      //delete button on south
      jbDelete = new JButton("Delete");
      add(jbDelete, BorderLayout.SOUTH);
   
   
   } //end constructor

} //end class facultyView