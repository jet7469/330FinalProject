import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.util.ArrayList;


//JPanel for SearchTab

public class SearchView extends JPanel implements ActionListener {

   //global variables
   private JPanel jpCenter;
   private JTextField jtfKeyword;
   private JTextField jtfFaculty;
   private JTextField jtfTopic;
   private JButton jbSearch;

   //constructor
   public SearchView() {
      setLayout(new BorderLayout());
      
      //north of panel
      add(new JLabel("Search By-", JLabel.CENTER), BorderLayout.NORTH);
      
      //center of panel
      jpCenter = new JPanel(new GridLayout(0,2));
      
      jpCenter.add(new JLabel("Keyword: ", JLabel.RIGHT));
      jtfKeyword = new JTextField();
      jpCenter.add(jtfKeyword);
      
      jpCenter.add(new JLabel("Faculty: ", JLabel.RIGHT));
      jtfFaculty = new JTextField();
      jpCenter.add(jtfFaculty);
      
      jpCenter.add(new JLabel("Topic: ", JLabel.RIGHT));
      jtfTopic = new JTextField();
      jpCenter.add(jtfTopic);
      
      add(jpCenter, BorderLayout.CENTER);
      
      //south of panel
      jbSearch = new JButton("Submit");
      jbSearch.setPreferredSize(new Dimension(20, 40));
      add(jbSearch, BorderLayout.SOUTH);
   
   } //end constructor
   
   public void actionPerformed(ActionEvent ae) {
      String keyword = jtfKeyword.getText();
      String facName = jtfFaculty.getText();
      String topic = jtfTopic.getText();
      
      if (keyword != "") {
         String keywordSelectQuery = "SELECT title FROM papers WHERE abstract LIKE '%?%';";
         PreparedStatement stmt = conn.prepareStatement(keywordSelectQuery);
         
         stmt.setString(1, keyword);
         ResultSet rs = stmt.executeQuery();
         ResultSetMetaData rsmd = rs.getMetaData();
         
         int columnsNumber = rsmd.getColumnCount();
         
         while (rs.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
               String columnValue = rs.getString(i);
               JOptionPane.showMessageDialog(this, "<html>Search results of keyword:" +
               "<br>" + columnValue );
            }
         }  
      }
      if (facName != "") {
      
      
         /*String facultySelectQuery = "SELECT lName FROM faculty WHERE lName LIKE '%?%';";
         PreparedStatement stmt = conn.prepareStatement(facultySelectQuery);
         
         stmt.setString(1, facName);
         ResultSet rs = stmt.executeQuery();
         ResultSetMetaData rsmd = rs.getMetaData();
         
         int columnsNumber = rsmd.getColumnCount();
         
         while (rs.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
               String columnValue = rs.getString(i);
               JOptionPane.showMessageDialog(this, "<html>Search results of faculty:" +
               "<br>" + columnValue );
            }
         }*/ 
      }
      if (topic != "") {
         String topicSelectQuery = "SELECT title FROM papers JOIN paper_keywords ON papers.id = paper_keywords.id WHERE keyword LIKE '%?%';";
         PreparedStatement stmt = conn.prepareStatement(topicSelectQuery);
         
         stmt.setString(1, topic);
         ResultSet rs = stmt.executeQuery();
         ResultSetMetaData rsmd = rs.getMetaData();
         
         int columnsNumber = rsmd.getColumnCount();
         
         while (rs.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
               String columnValue = rs.getString(i);
               JOptionPane.showMessageDialog(this, "<html>Search results of keyword:" +
               "<br>" + columnValue );
            }
         } 
      }
   }//end actionPerformed
   
   //convert 2D arrayList result set to 2d array for JTable
   public Object[][] convert(ArrayList<ArrayList<String>>dataList) {
      
      int numRows = dataList.size();
      
      Object [][] newData = new Object [numRows][4];
      
      for (int i = 0; i < dataList.size(); i++) {
         for (int j = 0; j < dataList.get(i).size(); j++) {
            newData[i][j] = (Object) dataList.get(i).get(j);
         }
      }
      return newData;
   }
   
} //end class SearchView

