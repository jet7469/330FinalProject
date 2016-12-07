import java.util.*;
import java.sql.*;

public class Collaboration {
   private int paperId;
   private String username;
   private String message;
    
   Database mysql = new Database();
    

	public Collaboration() {} 
    public Collaboration(int _paperId, String _username, String _message) {
         paperId = _paperId;
         username = _username;
         message = _message;
         
    }
    
    public int getPaperId() {
    	return this.paperId;
    }
    
    public void setPaperId(int _paperId) {
    	this.paperId = _paperId;
    }
    
    public String getUsername() {
    	return this.username;
    }
    
    public void setUsername(String _username) {
    	this.username = _username;
    }
    
    public String getMessage() {
      return this.message;
    }
    
    public void setMessage(String _message) {
      this.message = _message;
    }
    
    
   /** 
   *  fetch method calls getData with a mySQL SELECT
   * statement to retrieve an entry from the DB.
   * Once entry is retrieved it will check if there
   * is data available. If so, it will set the object
   * attributes with that information and return true.
   * If data is not avialable it will return false
   *
   * @param mysql - the database to use
   * @return a boolean
   */
   
    public boolean fetch() throws DLException {
      ArrayList<ArrayList<String>> result;
      ArrayList<String> inner = new ArrayList<>();
      ArrayList<String> strVals = new ArrayList<>(); 
       
      try {
        mysql.connect();
      } catch(DLException dle) {
        throw new DLException(dle, "fetch:46", "Can't Connect");
      }
      
      String select = "SELECT * FROM " + "collaborations WHERE paperId = ?" + " AND username = ?";
      strVals.add(Integer.toString(this.getPaperId()));
      strVals.add(this.getUsername());
        try {                                        
         result = mysql.getData(select, strVals);
         if(result == null || result.size() == 0) {
            return false;
         } else {
            for (int i = 0; i < result.size(); i++) {
             inner = result.get(i);
            }
         }
         this.setPaperId(Integer.parseInt(inner.get(0)));
         this.setUsername(inner.get(1));
         this.setMessage(inner.get(2));         
        } catch(DLException dle) {
           throw new DLException(dle, "fetch:55", "SELECT Statement Error");
        }      
        
      mysql.close();
      return true;
   }//end fetch by paperId
   
   
   /** 
   * put method takes object attributes and updates existing 
   * database entry using the mySQL UPDATE statement for the 
   * Authorship object that calls it.
   *
   * @param mysql - the database to use
   * @return a boolean
   */
   
   //put method when searching by facultyId
   public boolean put(int _paperId, String _username, String _message, int _key) throws DLException {  
   ArrayList<String> strVals = new ArrayList<String>();
   
      try {
         mysql.connect();                 
      } catch(DLException dle) {
         throw new DLException(dle,"An Error Has Occurred", "put:128");
      }                                         
      
      strVals.add(Integer.toString(_paperId));       
      strVals.add(_username);
      strVals.add(_message);
      strVals.add(Integer.toString(_key));       
                       
      try {
         String update = "UPDATE collaborations SET paperId = ?" + 
                         ", username = ?" + ", message = ?" + 
                         " WHERE paperId = ?" + " AND username = ?";                                             
         mysql.setData(update, strVals);
      } catch(DLException dle) {
         throw new DLException(dle, "put:145", "UPDATE Statement Error");
      }        
      
      mysql.close();
      return true;  
   }
   
   
   /** 
   * post method takes attributes from Authorship object
   * and adds as NEW entry into database 
   * using a mySQL CREATE statement for the Authorship
   * object that calls it.
   *
   * @param mysql - the database to use
   * @return a boolean
   */
   public boolean post(int _paperId,  String _username, String _message) throws DLException{
      ArrayList<String> strVals = new ArrayList<String>();

      strVals.add(Integer.toString(_paperId));      
      strVals.add(_username); 
      strVals.add(_message);
         
      try {
         mysql.connect();                 
      }catch(DLException dle) {
         throw new DLException(dle,"An Error Has Occurred", "post:127");
      }
      
      try {   
         String insert = "INSERT INTO collaborations VALUES(?" + ", ?" + ", ?" + ");";  
         mysql.setData(insert, strVals);
      }catch(DLException dle) {
         throw new DLException(dle, "post:134", "INSERT Statement Error");
      }       
        
      mysql.close();
      return true;
   }//end post()
   
   
   
   /** 
   * delete method deletes a db entry/row
   * uses the setData() method from the DB class
   * and enters a mySQL DELETE statement for the  
   * Authorship object that cals it.
   *
   * @param mysql - the database to use
   * @return a boolean
   */
   
   //delete method by facultyId
   public boolean delete() throws DLException {
      ArrayList<String> strVals = new ArrayList<String>();

      strVals.add(Integer.toString(this.getPaperId()));    
      strVals.add(this.getUsername());
             
      try {
         mysql.connect();                 
      } catch(DLException dle) {
         throw new DLException(dle,"An Error Has Occurred", "delete:163");
      }   
      
      try {  
         String deleteCommand = ("DELETE FROM collaborations WHERE paperId = ?" + " AND username = ?"); 
         mysql.setData(deleteCommand, strVals);
        } catch(DLException dle) {
           throw new DLException(dle, "delete:170", "DELETE Statement Error");
        }        
      mysql.close();
      return true;
   }
} 