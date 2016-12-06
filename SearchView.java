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
   private DLUser dl;

   //constructor
   public SearchView(DLUser _dl) {
   
      dl = _dl;
      
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
      jbSearch.addActionListener(this);
      jbSearch.setPreferredSize(new Dimension(20, 40));
      add(jbSearch, BorderLayout.SOUTH);
   
   } //end constructor
   
   public void actionPerformed(ActionEvent ae) {
      String keyword = jtfKeyword.getText();
      String facName = jtfFaculty.getText();
      String topic = jtfTopic.getText();
      
      if (!keyword.isEmpty()) {
      
         String keywordString = "";
         ArrayList<ArrayList<String>> results;
         
         try {
            results = dl.searchKeyword("%" + keyword + "%");
            
            for (int i = 0; i < results.size(); i++) {
               for (int j = 0; j < results.get(i).size(); j++) {
                  keywordString = keywordString.concat(results.get(i).get(j)) + "\n";
               }
            }
            JOptionPane.showMessageDialog(this, "<html>Search results of keyword input:<br>" + keywordString);
         } catch (Exception e) {
            System.out.println("Error retreiving keyword search");
         }   
      }
      
      if (!facName.isEmpty()) {
         
         String facultyString = "";
         ArrayList<ArrayList<String>> results;
         
         try {
            results = dl.searchFac("%" + facName + "%");
            
            for (int i = 0; i < results.size(); i++) {
               for (int j = 0; j < results.get(i).size(); j++) {
                  facultyString = facultyString.concat(results.get(i).get(j)) + "\n";
               }
            }
            JOptionPane.showMessageDialog(this, "<html>Search results of faculty input:<br>" + facultyString);
         } catch (Exception e) {
            System.out.println("Error retreiving faculty search");
         }   
      }
      
      if (!topic.isEmpty()) {
         String topicString = "";
         ArrayList<ArrayList<String>> results;
         
         try {
            results = dl.searchTopics("%" + topic + "%");
            
            for (int i = 0; i < results.size(); i++) {
               for (int j = 0; j < results.get(i).size(); j++) {
                  topicString = topicString.concat(results.get(i).get(j)) + "\n";
               }
            }
            JOptionPane.showMessageDialog(this, "<html>Search results of faculty input:<br>" + topicString);
         } catch (Exception e) {
            System.out.println("Error retreiving topic search");
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

