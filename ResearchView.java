import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

//JPanel for ResearchTab

public class ResearchView extends JPanel {

   //global variables
   private JTable table;
   private JTextField jtfSearch;
   
   //THESE ARE PLACEHOLDERS, NEED TO BE CHANGED
   private String [] columnNames = {"Title", "Abstract", "Citation", "Author"}; 
   private Object [][] data;
                                 
   private Database db;
   
   //constructor
   public ResearchView() {
   
      //layout of Panel
      setLayout(new BorderLayout());
      
      //north of panel
      jtfSearch = new JTextField();
      add(jtfSearch, BorderLayout.NORTH);
      
      db = new Database();
      try {
         db.connect();
         
         String sql = "SELECT p.title, p.abstract, p.citation, CONCAT(f.fname, ' ', f.lname) FROM papers p " +
                      "INNER JOIN authorship a ON a.paperId = p.id " +
                      "INNER JOIN faculty f ON a.facultyId = f.id";
         
         ArrayList<ArrayList<String>> myData = db.getData(sql, true);
         data = convert(myData);
         
        
      
      //center/south of panel
      table = new JTable(new CustomTableModel(columnNames, data));
      table.setRowHeight(50);
      
      JScrollPane jsp = new JScrollPane(table);
      jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      add(jsp,BorderLayout.CENTER);
      
      }
      catch(DLException dle) {
         System.out.println("DLE");
      }

   
   } //end constructor
   
   //convert 2D arrayList result set to 2d array for JTable
   public Object[][] convert(ArrayList<ArrayList<String>>dataList) {
      
      int numRows = dataList.size()-1; //total rows -1 bc the first row is column names
      
      Object [][] newData = new Object [numRows][5];
      
      for (int i = 1; i < dataList.size(); i++) {
         for (int j = 1; j < dataList.get(i).size(); j++) {
            newData[i-1][j-1] = (Object) dataList.get(i).get(j);
         }
      }
      return newData;
   }


} //end class ResearchView