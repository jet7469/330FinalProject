import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;



public class keywordsView extends JPanel implements ActionListener {

   //global variables
   private JPanel jpCenter;
   
   private JTable table;
   private JTextField jtfId;
   private JTextField jtfKeyword;
   private JTextField jtfTitle;
   private JButton jbSubmit;


   
   //THESE ARE PLACEHOLDERS, NEED TO BE CHANGED
   private String [] columnNames = {"Keyword", "Title"}; 
   private Object [][] data;
                                 
   private Database db;
   
   //constructor
   public keywordsView() {
   
      //layout of Panel
      setLayout(new BorderLayout());
      
      //north of panel
     //  jtfSearch = new JTextField();
//       add(jtfSearch, BorderLayout.NORTH);
      
      db = new Database();
      try {
         db.connect();
         
         String sql = "select id, keyword, title from paper_keywords JOIN papers USING (id)";
         
         ArrayList<ArrayList<String>> myData = db.getData(sql, true);
         data = convert(myData);
         
         jpCenter = new JPanel(new GridLayout(0,2));
         
      jpCenter.add(new JLabel("Id: ", JLabel.RIGHT));
      jtfId = new JTextField();
      jpCenter.add(jtfId);
      
      jpCenter.add(new JLabel("Keyword: ", JLabel.RIGHT));
      jtfKeyword = new JTextField();
      jpCenter.add(jtfKeyword); 
      
      jbSubmit = new JButton("Submit");
      jbSubmit.addActionListener(this);      
      jpCenter.add(jbSubmit, JLabel.RIGHT);
      jbSubmit.setPreferredSize(new Dimension(10, 10));      

      
      //center/south of panel
      table = new JTable(new CustomTableModel(columnNames, data));
      table.setRowHeight(20);
      
      JScrollPane jsp = new JScrollPane(table);
      jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      add(jsp,BorderLayout.CENTER);
      
      add(jpCenter, BorderLayout.SOUTH);
}
      catch(DLException dle) {
         System.out.println("DLE");
      }
      
      
   
   } //end constructor
   
   
   public void actionPerformed(ActionEvent ae) {
         int newId = Integer.parseInt(jtfId.getText());
         String newKeyword = jtfKeyword.getText();
         
         Paper_keywords newPK = new Paper_keywords();
         
         try {
            newPK.post(newId, newKeyword);
            jtfId.setText("");
            jtfKeyword.setText("");
         }
         catch(Exception e) {
            JOptionPane.showMessageDialog(this, "Error creating account.");
            System.out.println("Exception Occured");
         }        
      }

   
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