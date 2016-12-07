//import java.sql.*;
import java.util.*;

public class Authorship {

   Database mysql = new Database();

   // 2a. attributes
   private int facultyId;
   private int paperId;
   
   
   
   //default constructor
   public Authorship() {
   }
   
   
   //constructor for both if needed
   public Authorship(int _facultyId, int _paperId) {
      facultyId = _facultyId;
      paperId = _paperId;
   }
   
   
   //Accessors and Mutator methods
   public int getFacultyId() {
      return this.facultyId;
   }
   
   public void setFacultyId(int _facultyId) {
      this.facultyId = _facultyId;
   }
   
   public int getPaperId() {
      return this.paperId;
   }
   
   public void setPaperId(int _paperId) {
      this.paperId = _paperId;
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
   
   //fecth method by facultyId

   
   //fetch method by paperId
    public boolean fetch() throws DLException {
      ArrayList<ArrayList<String>> result;
      ArrayList<String> inner = new ArrayList<>();
      ArrayList<String> strVals = new ArrayList<>(); 
      
      
       try {
         mysql.connect();
       } catch(DLException dle) {
          throw new DLException(dle, "fetch:93", "Can't Connect");
       }
      
      String select = "SELECT * FROM " + "authorship WHERE paperId = ?" + " AND facultyId = ?";
      strVals.add(Integer.toString(this.getPaperId()));
        try {                                        
         result = mysql.getData(select, strVals);
         if(result == null || result.size() == 0) {
            return false;
         } else {
            for (int i = 0; i < result.size(); i++) {
             inner = result.get(i);
            }
         }
         this.setFacultyId(Integer.parseInt(inner.get(0)));
         this.setPaperId(Integer.parseInt(inner.get(1)));         
        } catch(DLException dle) {
           throw new DLException(dle, "fetch:97", "SELECT Statement Error");
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
     
         //2.a
         String update = "UPDATE Authorship SET facultyId = ?" + 
                         ", paperId = ?"                       + 
                         " WHERE facultyId = ?" + " AND paperId = ?";                                             
         mysql.setData(update, strVals);
        } catch(DLException dle) {
           throw new DLException(dle, "put:145", "UPDATE Statement Error");
        }        
      
      mysql.close();
      return true;  
   }//end put by facultyId
   
   
   /** 
   * post method takes attributes from Authorship object
   * and adds as NEW entry into database 
   * using a mySQL CREATE statement for the Authorship
   * object that calls it.
   *
   * @param mysql - the database to use
   * @return a boolean
   */
   public boolean post(int facultyId,  int paperId) throws DLException{
      ArrayList<String> strVals = new ArrayList<String>();

      strVals.add(Integer.toString(facultyId));      
      strVals.add(Integer.toString(paperId)); 
         
      try {
         mysql.connect();                 
      } catch(DLException dle) {
         throw new DLException(dle,"An Error Has Occurred", "post:156");
      }
      
      try {   
          
         String insert = "INSERT INTO Authorship VALUES(?" + ", ?" + ");";  
         mysql.setData(insert, strVals);
        } catch(DLException dle) {
           throw new DLException(dle, "post:177", "INSERT Statement Error");
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

      strVals.add(Integer.toString(this.getFacultyId()));    
   
      try {
         mysql.connect();                 
      } catch(DLException dle) {
         throw new DLException(dle,"An Error Has Occurred", "delete:184");
      }   
      
      try {  
         // 2.a   
         String deleteCommand = ("DELETE FROM Authorship WHERE facultyId = ?" + " AND paperId = ?"); 
         mysql.setData(deleteCommand, strVals);
        } catch(DLException dle) {
           throw new DLException(dle, "delete:206", "DELETE Statement Error");
        }        
      mysql.close();
      return true;
   }//end delete method by facultyId
   
}//end class Authorship