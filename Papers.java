import java.sql.*;
import java.util.*;

/*
Data layer class for the Papers table
*/

public class Papers {

   //global variables
   public int id;
   public String title;
   public String abstractText;
   public String citation;

   private Database mysql = new Database();

   //default constructor
   public void Papers() {
      id = 0;
      title = "";
      abstractText = "";
      citation = "";
   }
   
   //parameterized constructor
   public void Papers(int _id, String _title, String _abstractText, String _citation) {
      id = _id;
      title = _title;
      abstractText = _abstractText;
      citation = _citation;
   }
   
   //accessors
   public int getId() {
      return id;
   }
   
   public String getTitle() {
      return title;
   }
   
   public String getAbstractText() {
      return abstractText;
   }
   
   public String getCitation() {
      return citation;
   }
   
   //mutators
   public void setId(int _id) {
      id = _id;
   }
   
   public void setTitle(String _title) {
      title = _title;
   }
   
   public void setAbstractText(String _abstractText) {
      abstractText = _abstractText;
   }
   
   public void setCitation(String _citation) {
      citation = _citation;
   }
   
  public boolean fetch() throws DLException {
      ArrayList<ArrayList<String>> result;
      ArrayList<String> inner = new ArrayList<>();
      ArrayList<String> strVals = new ArrayList<>();
      
      
       try {
         mysql.connect();
       } catch(DLException dle) {
          throw new DLException(dle, "fetch:73", "Can't Connect");
       }
      
      String select = "SELECT * FROM " + "papers WHERE id = ? and WHERE keyword = ?";
      strVals.add(Integer.toString(this.getId()));
      strVals.add(this.getTitle());
      strVals.add(this.getAbstractText());
      strVals.add(this.getCitation());

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
   * @return a boolean
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
   * @param mysql - the database to use
   * @return a boolean
   */
   public boolean post(int id, String title, String abstractText, String citation) throws DLException{
      ArrayList<String> strVals = new ArrayList<String>();

      strVals.add(Integer.toString(id)); 
      strVals.add(title);
      strVals.add(abstractText);            
      strVals.add(citation);            
         
      try {
         mysql.connect();                 
      } catch(DLException dle) {
         throw new DLException(dle,"An Error Has Occurred", "post:185");
      }
      
      try {   
         String insert = "INSERT INTO papers VALUES(?" + ", ?" + ", ?" + ", ?" + ");";  
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
   * @return a boolean
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
