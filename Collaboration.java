/**Java Database Connectivity Final Project
*Course Title: Java Data Connectivity and Access 
*Course Number: ISTE-330
*Instructor: Professor Floeser
*@author Jenna Tillotson, Louis Trapani, Rosalee Hacker, Steven Ricci
*@version 1.0, 12/7/2016
*
*Description: Collaboration
*"This program conatins methods to support the collaboration table
*in the database. This includes getting data from the database, setting data in the database, 
*executing statementsand queries, describing tables."
*
*/

import java.util.*;
import java.sql.*;

public class Collaboration {
   private int paperId;
   private int userId;
    
   Database mysql = new Database();
    
	/**
	*Default Constructor
	*/
	public Collaboration() {} 
    /**Constructor gets gets paperId and userId
    *@param int- paperId
    *@param int- userId
    */
    public Collaboration(int _paperId, int _userId) {
         paperId = _paperId;
         userId = _userId;
    }
    //accessor/mutators
    /**Get int- paperId
    *@return int- paperId 
    */
    public int getPaperId() {
    	return this.paperId;
    }
    /**Set paperId
    *@param int- paperId 
    */
    public void setPaperId(int _paperId) {
    	this.paperId = _paperId;
    }
    /**Get userId
    *@return int- userId 
    */
    public int getUserId() {
    	return this.userId;
    }
    /**Set userId
    *@param int- userId
    */
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
   * @return boolean true- for successful fetch
   * @return boolean false- for unsuccessful fetch
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
   * @param int- facultyId
   * @param int- paperId
   * @param int- key
   * @return boolean true- successful update
   * @return boolean false- unsuccessful update
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
   * @param int- paperId
   * @param int- userId
   * @return boolean true- for successful post
   * @return boolean false- for unsuccessful post
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
   * @return boolean true- for successsful delete
   * @return boolean false- for unsuccessful delete
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
