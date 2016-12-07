import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;



public class CollabView extends JPanel implements ActionListener {

   //global variables
   private JPanel jpCenter;
   private JTextField jtfTitle;
   private JTextField jtfFacName;
   private JTextField jtfMessage;
   private JButton jbSearch;
   private DLUser dl;
   
   //global variables
   private JTable table;
   private JTextField jtfDisplayCollab;
   
   //THESE ARE PLACEHOLDERS, NEED TO BE CHANGED
   private String [] columnNames = {"username", "title", "message"}; 
   private Object [][] data;
                                 
   private Database db;   

   //constructor
   public CollabView(DLUser _dl) {
   
      dl = _dl;
      
      setLayout(new BorderLayout());
      
      if (dl.getAccess().equals("Student")) {
         //north of panel
         add(new JLabel("Search By-", JLabel.CENTER), BorderLayout.NORTH);
         
         //center of panel
         jpCenter = new JPanel(new GridLayout(0,2));
         
         jpCenter.add(new JLabel("Paper Title: ", JLabel.RIGHT));
         jtfTitle = new JTextField();
         jpCenter.add(jtfTitle);
         
         jpCenter.add(new JLabel("Faculty Name: ", JLabel.RIGHT));
         jtfFacName = new JTextField();
         jpCenter.add(jtfFacName);
         
         jpCenter.add(new JLabel("Message: ", JLabel.RIGHT));
         jtfMessage = new JTextField();
         jpCenter.add(jtfMessage);
         
         add(jpCenter, BorderLayout.CENTER);
         
         //south of panel
         jbSearch = new JButton("Submit");
         jbSearch.addActionListener(this);
         jbSearch.setPreferredSize(new Dimension(20, 40));
         add(jbSearch, BorderLayout.SOUTH);
         
      } else if (dl.getAccess().equals("Faculty")) {
         System.out.println("nothing yet");
         
         
         
         
      //layout of Panel
      setLayout(new BorderLayout());
      
      //north of panel
      jtfDisplayCollab = new JTextField();
      add(jtfDisplayCollab, BorderLayout.NORTH);
      
      db = new Database();
      try {
         db.connect();
         
         String sql = "SELECT paperId, username, title, message from collaborations JOIN papers ON (papers.id = collaborations.paperId)";
         
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
       
      }
   } //end constructor
   
   public void actionPerformed(ActionEvent ae) {
      String title = jtfTitle.getText();
      String facName = jtfFacName.getText();
      String message = jtfMessage.getText();
      int paperId;
      Collaboration newCollab = new Collaboration();
      DLUser user = new DLUser();
      Faculty f = new Faculty();
      
      if (facName.equals("Steve Zilora")) {
         f.setId(1);
         //dl.setId(facId);
         
      } else if (facName.equals("Dan Bogaard")) {
         f.setId(2);
         //dl.setId(facId);
      }
      
//       dl.fetch();
      
      try {
         paperId = dl.createCollaboration(title);      
         newCollab.post(paperId, dl.getUsername(), message);
         jtfTitle.setText("");
         jtfFacName.setText("");
         jtfMessage.setText("");
      }
      catch(Exception e) {
         JOptionPane.showMessageDialog(this, "Error creating account.");
         System.out.println("Exception Occured");
      }       
   }//end actionPerformed

   
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