/**Java Database Connectivity Final Project
*Course Title: Java Data Connectivity and Access 
*Course Number: ISTE-330
*Instructor: Professor Floeser
*@author Jenna Tillotson, Louis Trapani, Rosalee Hacker, Steven Ricci
*@version 1.0, 12/7/2016
*
*Description: Database Class
*"This program conatins methods to support connection to a database,
*getting data from the database, setting data in the database, executing statements
*and queries, describing tables and our class uses transactions"
*follows google standard.
*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;

public class Database {

  // global vars
  private String uri;
  private String driver;
  private String username;
  private String password;
  private Connection conn = null;
      
  /**
  *Constructor.
  */
  public Database() {
    uri = "jdbc:mysql://127.0.0.1/FacResearchDB?useSSL=false";
    driver = "com.mysql.jdbc.Driver";
    username = "root";
    password = "flower11"; // enter your mysql password
      
    System.out.println(uri + driver + username + password);
      
  }
   
  /**
  * connect method loads mysql driver
  * connects to database
  *
  * @return boolean true if connection successful, false for failed connection.
  */
  public boolean connect() throws DLException {
    System.out.println("SPOT 2: " + uri + driver + username + password);

    // connect to the database
    try {
      
      System.out.println("SPOT 3: " + uri + driver + username + password);

      conn = DriverManager.getConnection(uri, username, password);
      System.out.println("Connected to DB");
      return true;
    } catch (SQLException sqle) {
      throw new DLException(sqle, "An Error Has Occurred", "connect:48", "Could Not Connect to " 
          + "Database Using URI " + uri + " and/or incorrect Username and Password");
    }
  }
   
  /**
  * close method closes connection between
  * java object and mysql database. Returns 
  * true if successful.
  *
  * @return boolean true for successful close, return boolean false for failed close
  */
  public boolean close() throws DLException {
    // close the database
    try {
      if (conn != null) {
        conn.close();
      }
      System.out.println("DB Closed");
      return true;         
    } catch (SQLException sqle) {
      throw new DLException(sqle, "An Error Has Occurred", "close:67", "Could Not " 
          + "close the Database ");
    }
  }
   
  /**
   * getData method takes in an sql statement 
   * and sets a ResultSet equal to the results 
   * of that mysql SELECT statement. This data is placed 
   * into an ArrayList one field at a time.   
   * 
   * @param sql-a string that contains mySQL select statement
   * @return -lst ArrayList<ArrayList<String>> 
   */
  public ArrayList<ArrayList<String>> getData(String sqlQuery) throws DLException {
    ArrayList<ArrayList<String>> lst = new ArrayList<ArrayList<String>>();
   
    try {
      Statement stmnt = conn.createStatement();
      ResultSet rs = stmnt.executeQuery(sqlQuery);
      ResultSetMetaData rsmd = rs.getMetaData();
      int numFields = rsmd.getColumnCount();
         
      while (rs.next()) {
        ArrayList<String> inner = new ArrayList<String>();
        for (int i = 1; i < numFields + 1 ; i++) {
          inner.add(rs.getString(i));
        }
        lst.add(inner);
      }     
    } catch (SQLException sqle) {
      throw new DLException(sqle, "An Error Has Occurred", "getData:92", "Could Not Retrieve "
          + "Data", "arg1 - String sql: " + sqlQuery);
    }
    return lst;
  }

   /**
   * getData method takes in an sql statement and a boolean
   * and sets a ResultSet equal to the results 
   * of that mysql SELECT statement. This data is placed 
   * into an ArrayList one field at a time.
   * If getData is called with the false boolean
   * it will call the original method. This displays the 
   * DRY principle of software engineering 
   * by not repeating the same steps in both methods.   
   *
   * @param sql - a string that contains mySQL select statement
   * @param b - boolean that determines whether column titles are used
   * @return - ArrayList<ArrayList<String>> 
   */   
   public ArrayList<ArrayList<String>> getData(String sqlQuery, Boolean b) throws DLException {
    ArrayList<ArrayList<String>> lst = new ArrayList<ArrayList<String>>();
      
    try {
      Statement stmnt = conn.createStatement();
      ResultSet rs = stmnt.executeQuery(sqlQuery);
      ResultSetMetaData rsmd = rs.getMetaData();
      int numFields = rsmd.getColumnCount();
         
      if (b) {
        ArrayList<String> colHeadings = new ArrayList<String>();
        for (int i = 1; i <= numFields; i++) {
          colHeadings.add(rsmd.getColumnName(i));          
        }         
        lst.add(colHeadings);
        int counter = 1;
        while (rs.next()) {
          ArrayList<String> inner = new ArrayList<String>();
          for (int i = 1; i < numFields + 1 ; i++) {
            inner.add(rs.getString(i));
          }
          lst.add(inner);
          counter++;
        }         
      } else {  
        return getData(sqlQuery);
      }
    } catch (SQLException sqle) {
      throw new DLException(sqle, "An Error Has Occurred", "getData:129", "Could Not "
      + "Retrieve Data", "arg1 - String sql: " + sqlQuery);
    }       
    return lst;
  }
   
   
  /**
  * setData method takes in an sql statement and calls
  * executeUpdate() on the mySQL statement to execute the query.
  * if succesful it will return true.
  *
  * @param sql - a string of mySQL statement (create, update, delete)
  * @return - boolean true for sucessfull update
  * 
  */
  public Boolean setData(String sqlQuery) throws DLException {
    try {
      Statement stmnt = conn.createStatement();
      stmnt.executeUpdate(sqlQuery);
      return true;
    } catch (SQLException sqle) {
      throw new DLException(sqle, "An Error Has Occurred", "setData:169", "Could Not Make Change "
      + "TO Database", "arg1 - String sql: " + sqlQuery);
    }
  }   
   
  /**
  * descTable method use metadata to print out how many
  * fields were retrieved from query and then prints a two column
  * list with column names and types. Finally, prints out a table
  * similar to that in mySQL
  *
  * @param sql - a string that contains an sql query
  */
  public void descTable(String sqlQuery, ArrayList<String> strVals) throws DLException {
   
    try {   
      //Statement stmnt = conn.createStatement();
      PreparedStatement pstmt = prepare(sqlQuery, strVals);
      ResultSet rs = pstmt.executeQuery();
      ResultSetMetaData rsmd = rs.getMetaData();         
      int numFields = rsmd.getColumnCount();
      System.out.println(numFields + " fields retrieved.\n");
      System.out.printf("%-20s  %-20s\n", "Field Name:", "Column Type:");         
         
      for (int i = 1; i <= numFields; i++) {
        System.out.printf("%-20s  %-20s\n", rsmd.getColumnName(i), rsmd.getColumnTypeName(i));       
      }
      System.out.printf("\n");
      // print formatted top border for titles
      for (int i = 1; i <= numFields; i++) {
        System.out.printf("+-");
        for (int j = 1; j <= Math.max(Math.min(rsmd.getPrecision(i), 25), rsmd.getColumnName(i).length()); j++) {
          System.out.printf("-");
        }
        System.out.printf("-");
      }
      System.out.printf("+\n");
      // print column names with proper spacing
      for (int i = 1; i <= numFields; i++) {
        int column_width = Math.max(Math.min(rsmd.getPrecision(i), 25), rsmd.getColumnName(i).length());         
        System.out.printf("| %-" + column_width + "s ", rsmd.getColumnName(i));
      }
      System.out.printf("\n");
      // print formatted bottom border for titles
      for (int i = 1; i <= numFields; i++) {
        System.out.printf("+-");
        for (int j = 1; j <= Math.max(Math.min(rsmd.getPrecision(i), 25), rsmd.getColumnName(i).length()); j++) {
          System.out.printf("-");
        }
        System.out.printf("-");
      }
      System.out.printf("+\n");         
      // printing the data from mySQL query
      while (rs.next()) {
        for (int i = 1; i <= numFields; i++) {
          int column_width = Math.max(Math.min(rsmd.getPrecision(i), 25), rsmd.getColumnName(i).length());
          System.out.printf("| %-" + column_width + "s ", rs.getString(i));
        }
        System.out.printf("\n");
      }       
      // print formatted bottom border for table
      for (int i = 1; i <= numFields; i++) {
        System.out.printf("+-");
        for (int j = 1; j <= Math.max(Math.min(rsmd.getPrecision(i), 25), rsmd.getColumnName(i).length()); j++) {
          System.out.printf("-");
        }
        System.out.printf("-");
      }
      System.out.printf("+\n");
      System.out.printf("\n\n\n");  
    } catch (SQLException sqle) {
      throw new DLException(sqle, "An Error Has Occurred", "descTable:189", "Describe Table error", "arg1 - String sql: " + sqlQuery + strVals);
    }         
  }
   
  /**
  * prepare method  creates a preparedStatement using the 
  * sql String that is passed in and uses setString to place values 
  * from passed in arrayList to fill in the statement.
  *
  * @param sqlQuery - an sql statement
  * @param strVals - an arrayList of String values
  * @return - a PreparedStatement 
  */
  public PreparedStatement prepare(String sqlQuery, ArrayList<String> strVals) throws DLException {
   
    try {
      PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
      for (int i = 1; i <= strVals.size(); i++) {
        pstmt.setString(i, strVals.get(i - 1));
      }
      return pstmt;
    } catch (SQLException sqle) {
      throw new DLException(sqle, "An Error Has Occurred", "prepare:260", "Prepared Statement " 
      + "error", "arg1 - String sqlQuery: " + sqlQuery + "arg2 - ArrayList<String>: " + strVals);
    }
  }
   
   
  /**
  * getData method calls prepare(), executes the statement 
  * with executeQuery() method and returns a 2D array. 
  * 
  * @param sqlQuery - an sql statement
  * @param strVals - an arrayList of String values
  * @return - lst ArrayList<ArrayList<String>>
  */   
  public ArrayList<ArrayList<String>> getData(String sqlQuery, ArrayList<String> strVals) throws DLException {
    ArrayList<ArrayList<String>> lst = new ArrayList<ArrayList<String>>();
      
    try {
      PreparedStatement pstmt = prepare(sqlQuery, strVals);
      ResultSet rs = pstmt.executeQuery();
      ResultSetMetaData rsmd = rs.getMetaData();
      int numFields = rsmd.getColumnCount();
         
      while (rs.next()) {
        ArrayList<String> inner = new ArrayList<String>();
        for (int i = 1; i < numFields + 1 ; i++) {
          inner.add(rs.getString(i));
        }
        lst.add(inner);
      }
    } catch (SQLException sqle) {
      throw new DLException(sqle, "An Error Has Occurred", "getData:284", "Could Not Retrieve Data", "arg1 "
          + "- String sqlQuery: " + sqlQuery + "arg2 - ArrayList<String>: " + strVals);
    }  
      
    this.descTable(sqlQuery, strVals);
    return lst;      
  }
   
  /**
  * setData method calls prepare() and then seecuteStmt()
  * to set the data. returns true if successful.
  *
  * @param sqlQuery - an sql statement
  * @param strVals - an arrayList of String values
  * @return - a boolean true for successful set data
  * @return - a boolean false for failed set data
  */
  public boolean setData(String sqlQuery, ArrayList<String> strVals) throws DLException {
    ArrayList<ArrayList<String>> lst = new ArrayList<ArrayList<String>>();
    PreparedStatement pstmt = prepare(sqlQuery, strVals);
    int result = executeStmt(sqlQuery, strVals);
      
    if (result >= 0) {
      return true;
    } else {
      return false;
    }
  } 
   
  /**
  * executeStmt method calls prepare() and
  * uses that statement with executeUpdate() returns
  * a single int value.
  *
  * @param sqlQuery - an sql statement
  * @param strVals - an arrayList of String values
  * @return - an int result
  */   
  public int executeStmt(String sqlQuery, ArrayList<String> strVals) throws DLException {
    try {
      PreparedStatement pstmt = prepare(sqlQuery, strVals);
      int result = pstmt.executeUpdate(); 
      return result;
    } catch (SQLException sqle) {
      throw new DLException(sqle, "An Error Has Occurred", "executeStmt:333", "Could Not Execute Prepared Statement", "arg1 - String sqlQuery: " + sqlQuery + "arg2 - ArrayList<String>: " + strVals);
    }     
  }  
   
   
  /**
  * starTrans method starts an mySQL transaction.
  */
  public void startTrans() throws DLException {
    try {
      if (conn == null || conn.getAutoCommit()) {
        this.connect();
      }
      conn.setAutoCommit(false);
    } catch (SQLException sqle) {
      throw new DLException(sqle, "An Error Has Occurred", "startTrans:349", "Could Not Start Transaction");
    }
  }
   
  /**
  * endTrans method ends a mySQL transaction.
  */   
  public void endTrans() throws DLException {
    try {
      conn.setAutoCommit(true);
      if (conn != null && conn.getAutoCommit()) {
        this.close();
      }
      conn = null;
    } catch (SQLException sqle) {
      throw new DLException(sqle, "An Error Has Occurred", "end:362", "Could Not End Transaction");
    }   
  }
   

  /**
  * rollbackTrans method performs a rollback
  * on a mySQL transaction.
  */   
  public void rollbackTrans() throws DLException {
    try {
      conn.rollback();
    } catch (SQLException sqle) {
      throw new DLException(sqle, "An Error Has Occurred", "rollbackTrans:379", "Could Not Rollback Transaction");
    }
  }
   
  /**
  * commitTrans method performs a rollback
  * on a mySQL transaction.
  */     
  public void commitTrans() throws DLException {
    try {
      conn.commit();
    } catch (SQLException sqle) {
      throw new DLException(sqle, "An Error Has Occurred", "commitTrans:391", "Could Not Commit Transaction");
    }
  }
   
  /**Convert 2D arrayList result set to 2d array for JTable.
  *@param ArrayList datalist
  *@return newData
  */
  public Object[][] convert(ArrayList<ArrayList<String>> dataList) {
      
    int numRows = dataList.size() - 1; //total rows -1 bc the first row is column names
    Object [][] newData = new Object [numRows][4]; //4 bc there are 4 columns of data
      
    //start at 1 because dataList.get(0) is the column names 
    for (int i = 1; i < dataList.size(); i++) {  
      //loop through the arraylist within arraylist
      for (int j = 0; j < dataList.get(i).size(); j++) {
        newData[i - 1][j] = (Object) dataList.get(i).get(j);
      }
    }
    return newData;
  }    
}
