/**Java Database Connectivity Final Project
*Course Title: Java Data Connectivity and Access 
*Course Number: ISTE-330
*Instructor: Professor Floeser
*@author Jenna Tillotson, Louis Trapani, Rosalee Hacker, Steven Ricci
*@version 1.0, 12/7/2016
*
*Description: Table Model Class
*"This program conatins methods to support creation of an  Custom Table Model
*for the tables in the Database using the Abstact Table Model Class."
*/

import javax.swing.table.AbstractTableModel;

/**
*TableModel for the Table
*/

class CustomTableModel extends AbstractTableModel {
      
      String [] columnNames;
      Object [][] data;
      
      /**Constructor gets an array of column names and 2d array of data
      *@param column names
      *@param data
      */
      public CustomTableModel(String[] _columnNames, Object [][] _data) {
         columnNames = _columnNames;
         data = _data;
      }
   
     //accessor/mutators
      /**Get column count
      *@return length of column name int
      */
      public int getColumnCount() {
         return columnNames.length;
      }
      /**Get row count
      *@return length of data int
      */
      public int getRowCount() {
         return data.length;
      }
      /**Get column name
      *@param column int
      *@return column name int From column array
      */
      public String getColumnName(int column) {
         return columnNames[column];
      }
      /**Get data
      *@param row int
      *@param column int
      *@return data from 2d array
      */
      public Object getValueAt(int row, int column) {
         return data[row][column];
      }
      /**Cell editing
      *@param row int
      *@param column int
      *@return boolean
      */
      public boolean isCellEditable(int row, int column) {
         return false;
      }

   }
