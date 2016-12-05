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
   private String [] columnNames = {"Title", "Abstract", "Citation"}; 
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
         
         String sql = "SELECT * from papers";
         
         ArrayList<ArrayList<String>> myData = db.getData(sql, true);
         data = convert(myData);
         
        
      
      //center/south of panel
      table = new JTable(new CustomTableModel(columnNames, data));
      
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
      
      int numRows = dataList.size();
      
      Object [][] newData = new Object [numRows][3];
      
      for (int i = 1; i < dataList.size(); i++) {
         for (int j = 1; j < dataList.get(i).size(); j++) {
            newData[i][j-1] = (Object) dataList.get(i).get(j);
         }
      }
      return newData;
   }


} //end class ResearchView