/**Java Database Connectivity Final Project
*Course Title: Java Data Connectivity and Access 
*Course Number: ISTE-330
*Instructor: Professor Floeser
*@author Jenna Tillotson, Louis Trapani, Rosalee Hacker, Steven Ricci
*@version 1.0, 12/7/2016
*
*Description: Papers Data Layer Class
*"This program conatins methods to support Paper table in the Database. Including
*connection to the database,
*getting data from the data, setting data in the database, executing statments
*and queries, describing tables and our class uses transactions"
*
*/
import java.sql.*;
import java.util.*;

/*
Data layer class for the Papers table
*/

public class Papers {

   //global variables
   private int id;
   private String title;
   private String abstractText;
   private String citation;
   
   private Database mysql = new Database();

    /**
    *Default Constructor
    */
   public Papers() {
      id = 0;
      title = "";
      abstractText = "";
      citation = "";
   }
   /**Constructor with id
   *@param int- id
   */
   public Papers(int _id) {
      id = _id;
   }
   
   /**Contructor with all parameters
   *@param int- id
   *@param String- title
   *@param String- abstract test
   *@param String- citation
   */
   public Papers(int _id, String _title, String _abstractText, String _citation) throws DLException {
      id = _id;
      title = _title;
      abstractText = _abstractText;
      citation = _citation;
   }
   
   //accessors
   /**Get id
   *@return int- id
   */
   public int getId() {
      return id;
   }
   /**Get Title
   *@return String- title 
   */
   public String getTitle() {
      return title;
   }
   /**Get Abstract Text
   *@return String- Abstract Text
   */
   public String getAbstractText() {
      return abstractText;
   }
   /**Get Citation
   *@return String- citation
   */
   public String getCitation() {
      return citation;
   }
   
   //mutators
   /**Set id
   *@param int- id 
   */
   public void setId(int _id) {
      id = _id;
   }
   /**Set title
   *@param String- title 
   */
   public void setTitle(String _title) {
      title = _title;
   }
   /**Set Abstract Text
   *@param String- abstract text
   */
   public void setAbstractText(String _abstractText) {
      abstractText = _abstractText;
   }
   /**Set Citation
   *@param String- citation 
   */
   public void setCitation(String _citation) {
      citation = _citation;
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
          throw new DLException(dle, "fetch:73", "Can't Connect");
       }
      
      String select = "SELECT * FROM " + "papers WHERE id = ?";
      strVals.add(Integer.toString(this.getId()));


        try {                                        
         result = mysql.getData(select, strVals);
         if(result == null || result.size() == 0) {
            return false;
         } else {
            for (int i = 0; i < result.size(); i++) {
             inner = result.get(i);
            }
         }
         this.setId(Integer.parseInt(inner.get(0)));
         this.setTitle(inner.get(1));
         this.setAbstractText(inner.get(2));
         this.setCitation(inner.get(3));
         
         

        } catch(DLException dle) {
           throw new DLException(dle, "fetch:97", "SELECT Statement Error");
        }      
 
      mysql.close();
      return true;
   }   
      
   
   /** 
   * put method takes object attributes and updates existing 
   * database entry using the mySQL UPDATE statement for the 
   * Equipment object that calls it.
   *
   * @param mysql - the database to use
   * @param int- id
   * @param String- title
   * @param string abstractText
   * @param String- citation
   * @param int- key
   * @return boolean true- for successful update
   * @return boolean false- for unsuccessful update
   */
   public boolean put(int id, String title, String abstractText, String citation, int key) throws DLException {  
   ArrayList<String> strVals = new ArrayList<String>();
   
      try {
         mysql.connect();                 
      } catch(DLException dle) {
         throw new DLException(dle,"An Error Has Occurred", "put:117");
      }                                         
      
      strVals.add(Integer.toString(id)); 
      strVals.add(title); 
      strVals.add(abstractText);
      strVals.add(citation);         
      strVals.add(Integer.toString(key));       
            
           
      try {
         String update = "UPDATE papers SET id = ?" + ", title = ?" + 
                         ", abstract = ?" + ", citation = ?" + " WHERE id = ?";                                             
         mysql.setData(update, strVals);
        } catch(DLException dle) {
           throw new DLException(dle, "put:136", "UPDATE Statement Error");
        }        
      
      mysql.close();
      return true;  
   }
   
   
   /** 
   * post method takes attributes from Equipment object
   * and adds as NEW entry into database 
   * using a mySQL CREATE statement for the Equipment
   * object that calls it.
   *
   * @param int- id
   * @param String- title
   * @param string abstractText
   * @param String- citation
   * @return boolean true- for successful post
   * @return boolean false- for unsuccessful post
   */
   public boolean post(int id, String title, String abstractText, String citation) throws DLException{
      ArrayList<String> strVals = new ArrayList<String>();

      strVals.add(title);
      strVals.add(abstractText);            
      strVals.add(citation);            
         
      try {
         mysql.connect();                 
      } catch(DLException dle) {
         throw new DLException(dle,"An Error Has Occurred", "post:185");
      }
      
      try {   
         String insert = "INSERT INTO papers (`title`, `abstract`, `citation`) VALUES(?" + ", ?" + ", ?" + ");";  
         mysql.setData(insert, strVals);
        } catch(DLException dle) {
           throw new DLException(dle, "post:177", "INSERT Statement Error");
        }       
      mysql.close();
      return true;
   }
   
   
   
   /** 
   * delete method deletes a db entry/row
   * uses the setData() method from the DB class
   * and enters a mySQL DELETE statement for the  
   * Equipment object that cals it.
   *
   * @param mysql - the database to use
   * @return boolean true- successful delete
   * @return boolean false- unsuccessful delete
   */
   public boolean delete() throws DLException {
      ArrayList<String> strVals = new ArrayList<String>();

      strVals.add(Integer.toString(this.getId()));    
   
      try {
         mysql.connect();                 
      } catch(DLException dle) {
         throw new DLException(dle,"An Error Has Occurred", "delete:218");
      }   
      
      try {  
         String deleteCommand = ("DELETE FROM papers WHERE id = ?"); 
         mysql.setData(deleteCommand, strVals);
        } catch(DLException dle) {
           throw new DLException(dle, "delete:206", "DELETE Statement Error");
        }        
      mysql.close();
      return true;
   }   
}
