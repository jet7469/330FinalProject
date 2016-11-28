import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//JPanel for ResearchTab

public class ResearchView extends JPanel {

   //global variables
   private JTable table;
   private JTextField jtfSearch;
   
   //THESE ARE PLACEHOLDERS, NEED TO BE CHANGED
   private String [] columnNames = {"Research", "Name", "Type", "Date"}; 
   private Object [][] data = {{"Research #1", "Professor", "Java", "1/11/2016"},
                                 {"Research #2", "Doctor", "PHP", "3/10/2016"}};

   //constructor
   public ResearchView() {
   
      //layout of Panel
      setLayout(new BorderLayout());
      
      //north of panel
      jtfSearch = new JTextField();
      add(jtfSearch, BorderLayout.NORTH);
      
      //center/south of panel
      table = new JTable(new CustomTableModel(columnNames, data));
      
      JScrollPane jsp = new JScrollPane(table);
      jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      add(jsp,BorderLayout.CENTER);

   
   } //end constructor

} //end class ResearchView