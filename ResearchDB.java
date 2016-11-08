import java.sql.*;
import java.util.*;

public class ResearchDB {

   //connection variables
   String uri = "jdbc:mysql://localhost/researchDb?autoReconnect=true&useSSL=false";
   String driver = "com.mysql.jdbc.Driver";
   String user = "root";
   String password = "student";
   Connection conn = null;
    
   //constructor 
   public void ResearchDB() {
       //load the driver
      try {
         Class.forName(driver);
         System.out.println("Driver loaded.");      
      } //end try
      catch(ClassNotFoundException cnfe) {
         System.out.println("Cannot find or load Driver: "+driver); 
      } //end catch
   }
   
   //connect to the database and return true or false
   public boolean connect() {
       try {
         conn = DriverManager.getConnection(uri, user, password);
         System.out.println("Database open");
         return true;
      }
      catch(SQLException sqle) {
         System.out.println("Cound not connect to db: "+uri);
         return false;
      }        
   } //end method connect

   //close the connection and return true or false
   public boolean close() {
      try {
         conn.close();
         System.out.println("Database closed");
         return true;
      }
      catch(SQLException sqle) {
         System.out.println("Could not close database");
         return false;
      }
   }

} //end class ResearchDB