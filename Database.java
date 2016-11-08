import java.sql.*;
import java.util.*;


public class Database {

   // global vars
   private String uri;
   private String driver;
   private String username;
   private String password;
   private Connection conn = null;
   
   
   // constructors
   public Database() {
      String uri = "jdbc:mysql://127.0.0.1/FacResearchDB?useSSL=false";
      String driver = "com.mysql.jdbc.Driver";
      String username = "root";
      String password = "student";
   }

   public Database(String _uri, String _driver, String _username, String _password) throws DLException {
      uri = _uri;
      driver = _driver;
      username = _username;
      password = _password;
      
      try {
         Class.forName(driver); // how to load the driver
      } catch(ClassNotFoundException cnfe) {
         throw new DLException(cnfe, "An Error Has Occured", "Database:30", "Cannot find or load driver: " + driver);
      }
   }
   
   
   /**
   * connect method loads mysql driver
   * connects to database and returns true if 
   * successful.
   *
   * @return boolean
   */
   public boolean connect() throws DLException {

      // connect to the database
      try {
         conn = DriverManager.getConnection(uri, username, password);
         System.out.println("Connected to DB");
         return true;
      } catch(SQLException sqle) {
         throw new DLException(sqle, "An Error Has Occurred", "connect:48", "Could Not Connect to Database Using URI " + uri + " and/or incorrect Username and Password");
      }
   }
   
   /**
   * close method closes connection between
   * java object and mysql database. Returns 
   * true if successful.
   *
   * @return boolean
   */
   public boolean close() throws DLException {
      // close the database
      try {
         if(conn != null) {
            conn.close();
         }
         System.out.println("DB Closed");
         return true;         
      } catch(SQLException sqle) {
         throw new DLException(sqle, "An Error Has Occurred", "close:67", "Could Not Close the Database ");
      }

   }
   
   
   /**
   * getData method takes in an sql statement 
   * and sets a ResultSet equal to the results 
   * of that mysql SELECT statement. This data is placed 
   * into an ArrayList one field at a time.   
   * 
   * @param sql - a string that contains mySQL select statement
   * @return - ArrayList<ArrayList<String>> 
   */
   public ArrayList<ArrayList<String>> getData(String sqlQuery) throws DLException {
      ArrayList<ArrayList<String>> lst = new ArrayList<ArrayList<String>>();
   
      try{
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
      }catch (SQLException sqle) {
        throw new DLException(sqle, "An Error Has Occurred", "getData:92", "Could Not Retrieve Data", "arg1 - String sql: " + sqlQuery);
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
      
      try{
         Statement stmnt = conn.createStatement();
         ResultSet rs = stmnt.executeQuery(sqlQuery);
         ResultSetMetaData rsmd = rs.getMetaData();
         int numFields = rsmd.getColumnCount();
         
         if(b) {
            ArrayList<String> colHeadings = new ArrayList<String>();
            for(int i = 1; i<=numFields; i++) {
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
         }else{  
           return getData(sqlQuery);
         }
      }catch (SQLException sqle) {
         throw new DLException(sqle, "An Error Has Occurred", "getData:129", "Could Not Retrieve Data", "arg1 - String sql: " + sqlQuery);
      }       
      return lst;
   }
   
   
   /**
   * setData method takes in an sql statement and calls
   * executeUpdate() on the mySQL statement to execute the query.
   * if succesful it will return true.
   *
   * @param sql - a string of mySQL statement (create, update, delete)
   * @return - boolean 
   */
   public Boolean setData(String sqlQuery) throws DLException {
      try {
         Statement stmnt = conn.createStatement();
         stmnt.executeUpdate(sqlQuery);
         return true;
      } catch (SQLException sqle) {
         throw new DLException(sqle, "An Error Has Occurred", "setData:169", "Could Not Make Change TO Database", "arg1 - String sql: " + sqlQuery);
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
   public void descTable(String sqlQuery) throws DLException {
   
      try{   
         Statement stmnt = conn.createStatement();
         ResultSet rs = stmnt.executeQuery(sqlQuery);
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
         while(rs.next()) {
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
         throw new DLException(sqle, "An Error Has Occurred", "descTable:189", "Describe Table error", "arg1 - String sql: " + sqlQuery);
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
          for(int i = 1; i <= strVals.size(); i++) {
             pstmt.setString(i, strVals.get(i-1));
         }
         return pstmt;
      }catch(SQLException sqle) {
         throw new DLException(sqle, "An Error Has Occurred", "prepare:260", "Prepared Statement error", "arg1 - String sqlQuery: " + sqlQuery + "arg2 - ArrayList<String>: " + strVals);
      }
   }
   
   
   /**
   * getData method calls prepare(), executes the statement 
   * with executeQuery() method and returns a 2D array. 
   * 
   * @param sqlQuery - an sql statement
   * @param strVals - an arrayList of String values
   * @return - ArrayList<ArrayList<String>>
   */   
   public ArrayList<ArrayList<String>> getData(String sqlQuery, ArrayList<String> strVals) throws DLException {
      ArrayList<ArrayList<String>> lst = new ArrayList<ArrayList<String>>();
      
      try{
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
      }catch (SQLException sqle) {
         throw new DLException(sqle, "An Error Has Occurred", "getData:284", "Could Not Retrieve Data", "arg1 - String sqlQuery: " + sqlQuery + "arg2 - ArrayList<String>: " + strVals);
      }  
      return lst;      
   }
   
   /**
   * setData method calls prepare() and then seecuteStmt()
   * to set the data. returns true if successful.
   *
   * @param sqlQuery - an sql statement
   * @param strVals - an arrayList of String values
   * @return - a boolean
   */
   public boolean setData(String sqlQuery, ArrayList<String> strVals) throws DLException {
      ArrayList<ArrayList<String>> lst = new ArrayList<ArrayList<String>>();
      PreparedStatement pstmt = prepare(sqlQuery, strVals);
      int result = executeStmt(sqlQuery, strVals);
      
      if(result >= 0) {
         return true;
      }else {
         return false;
      }
   } 
   
   /**
   * executeStmt method calls prepare() and
   * uses that statement with executeUpdate() returns
   * a single int value
   *
   * @param sqlQuery - an sql statement
   * @param strVals - an arrayList of String values
   * @return - an int
   */   
   public int executeStmt(String sqlQuery, ArrayList<String> strVals) throws DLException {
      try{
         PreparedStatement pstmt = prepare(sqlQuery, strVals);
         int result = pstmt.executeUpdate(); 
         return result;
      }catch(SQLException sqle) {
         throw new DLException(sqle, "An Error Has Occurred", "executeStmt:333", "Could Not Execute Prepared Statement", "arg1 - String sqlQuery: " + sqlQuery + "arg2 - ArrayList<String>: " + strVals);
      }     
   }  
   
   
   /*
   * starTrans method starts an mySQL transaction
   */
   public void startTrans() throws DLException{
      try {
         if (conn==null || conn.getAutoCommit()) {
            this.connect();
         }
         conn.setAutoCommit(false);
      } catch(SQLException sqle) {
         throw new DLException(sqle, "An Error Has Occurred", "startTrans:349", "Could Not Start Transaction");
      }
   }
   
   
   /*
   * endTrans method ends a mySQL transaction
   *
   */   
   public void endTrans() throws DLException{
      try {
         conn.setAutoCommit(true);
         if(conn != null && conn.getAutoCommit()) {
            this.close();
         }
         conn = null;
      } catch(SQLException sqle) {
         throw new DLException(sqle, "An Error Has Occurred", "end:362", "Could Not End Transaction");
      }   
   }
   

   /*
   * rollbackTrans method performs a rollback
   * on a mySQL transaction
   */   
   public void rollbackTrans() throws DLException{
      try {
         conn.rollback();
      } catch(SQLException sqle) {
         throw new DLException(sqle, "An Error Has Occurred", "rollbackTrans:379", "Could Not Rollback Transaction");
      }
   }
   
   /*
   * commitTrans method performs a rollback
   * on a mySQL transaction
   */     
   public void commitTrans() throws DLException {
      try {
         conn.commit();
      } catch(SQLException sqle) {
         throw new DLException(sqle, "An Error Has Occurred", "commitTrans:391", "Could Not Commit Transaction");
      }
   }     
}