import java.util.*;
import java.sql.*;

public class Collaboration {
   private int paperId;
   private int userId;
    
   Database mysql = new Database();
    

	public Collaboration() {} 
    public Collaboration(int _paperId, int _userId) {
         paperId = _paperId;
         userId = _userId;
    }
    
    public int getPaperId() {
    	return this.paperId;
    }
    
    public void setPaperId(int _paperId) {
    	this.paperId = _paperId;
    }
    
    public int getUserId() {
    	return this.userId;
    }
    
    public void setUserId(int _userId) {
    	this.userId = _userId;
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
      
      String select = "SELECT * FROM " + "collaborations WHERE paperId = ?" + " AND userId = ?";
      strVals.add(Integer.toString(this.getPaperId()));
      strVals.add(Integer.toString(this.getUserId()));
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
         this.setUserId(Integer.parseInt(inner.get(1)));         
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
   public boolean put(int facultyId, int paperId, int key) throws DLException {  
   ArrayList<String> strVals = new ArrayList<String>();
   
      try {
         mysql.connect();                 
      } catch(DLException dle) {
         throw new DLException(dle,"An Error Has Occurred", "put:128");
      }                                         
      
      strVals.add(Integer.toString(facultyId));       
      strVals.add(Integer.toString(paperId));
      strVals.add(Integer.toString(key));       
                       
      try {
         String update = "UPDATE Authorship SET facultyId = ?" + 
                         ", paperId = ?"                       + 
                         " WHERE facultyId = ?" + " AND paperId = ?";                                             
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
   public boolean post(int paperId,  int userId) throws DLException{
      ArrayList<String> strVals = new ArrayList<String>();

      strVals.add(Integer.toString(paperId));      
      strVals.add(Integer.toString(userId)); 
         
      try {
         mysql.connect();                 
      }catch(DLException dle) {
         throw new DLException(dle,"An Error Has Occurred", "post:127");
      }
      
      try {   
         String insert = "INSERT INTO Authorship VALUES(?" + ", ?" + ");";  
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
      strVals.add(Integer.toString(this.getUserId()));
             
      try {
         mysql.connect();                 
      } catch(DLException dle) {
         throw new DLException(dle,"An Error Has Occurred", "delete:163");
      }   
      
      try {  
         String deleteCommand = ("DELETE FROM Authorship WHERE paperId = ?" + " AND userId = ?"); 
         mysql.setData(deleteCommand, strVals);
        } catch(DLException dle) {
           throw new DLException(dle, "delete:170", "DELETE Statement Error");
        }        
      mysql.close();
      return true;
   }
} 