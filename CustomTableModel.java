import java.util.*;
import javax.swing.table.AbstractTableModel;

/**
*TableModel for the TableView class
*/

class CustomTableModel extends AbstractTableModel {
      
      String [] columnNames;
      Object [][] data;

      public CustomTableModel(String[] _columnNames, Object [][] _data) {
         columnNames = _columnNames;
         data = _data;
      }
   
     
      public int getColumnCount() {
         return columnNames.length;
      }
      
      public int getRowCount() {
         return data.length;
      }
      
      public String getColumnName(int column) {
         return columnNames[column];
      }
      
      public Object getValueAt(int row, int column) {
         return data[row][column];
      }
      
      public boolean isCellEditable(int row, int column) {
         return false;
      }

   }
