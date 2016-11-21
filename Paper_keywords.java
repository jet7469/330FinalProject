import java.sql.*;
import java.util.*;

/**
 * data layer for the paper_keywords table 
 */

public class Paper_keywords {

   public int id;
   public String keyword;
   
   private Database mysql = new Database();

   //this will connect to the class which calls connections to the DB
   public Paper_keywords() {
   
   }
   
   //gets the id and sets the object in the class
   public Paper_keywords(int _id) {
      id = _id;
   }
   
   //gets the keyword and sets the object in the class
   public Paper_keywords(String _keyword) {
      keyword = _keyword;
   }
   
   //constructor for both if needed
   public Paper_keywords(int _id, String _keyword) {
      id = _id;
      keyword = _keyword;
   }
   
   //accessors 
   //getter for the id
   public int getId() {
      return this.id;
   }
   
   //getter for the keyword
   public String getKeywords() {
      return this.keyword;
   }
      
   //setter for the id
   public void setId(int _id) {
      this.id = _id;;
   }
   
   //setter for the keyword
   public void setKeywords(String _keyword) {
      this.keyword = _keyword;
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
      
      String select = "SELECT * FROM " + "paper_keywords WHERE id = ?" + " AND WHERE keyword = ?";
      strVals.add(Integer.toString(this.getId()));
      strVals.add(this.getKeywords());
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
         this.setKeywords(inner.get(1));
         

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
   public boolean put(int id, String keyword, int key) throws DLException {  
   ArrayList<String> strVals = new ArrayList<String>();
   
      try {
         mysql.connect();                 
      } catch(DLException dle) {
         throw new DLException(dle,"An Error Has Occurred", "put:117");
      }                                         
      
      strVals.add(Integer.toString(id)); 
      strVals.add(keyword);         
      strVals.add(Integer.toString(key));       
            
           
      try {
         String update = "UPDATE paper_keywords SET id = ?" + ", keyword = ?" + 
                         " WHERE id = ?" + " AND keyword = ?";                                             
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
   public boolean post(int id, String keyword) throws DLException{
      ArrayList<String> strVals = new ArrayList<String>();

      strVals.add(Integer.toString(id)); 
      strVals.add(keyword);            
         
      try {
         mysql.connect();                 
      } catch(DLException dle) {
         throw new DLException(dle,"An Error Has Occurred", "post:185");
      }
      
      try {   
         String insert = "INSERT INTO paper_keywords VALUES(?" + ", ?" + ");";  
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
         String deleteCommand = ("DELETE FROM paper_keywords WHERE id = ?" + " AND keyword = ?"); 
         mysql.setData(deleteCommand, strVals);
        } catch(DLException dle) {
           throw new DLException(dle, "delete:206", "DELETE Statement Error");
        }        
      mysql.close();
      return true;
   } 

//    public void swap(int id2) throws DLException {
//       Faculty fac2 = new Faculty(id2); 
//       fac2.fetch();
//       System.out.println(this.getFName());
//       System.out.println(fac2.getFName());
//  
//       
//       boolean swap1 = this.put(this.getId(), fac2.getFName(), this.getLName(), this.getPassword(), this.getEmail(), this.getId());
//       boolean swap2 = fac2.put(fac2.getId(), this.getFName(), fac2.getLName(), fac2.getPassword(), fac2.getEmail(), fac2.getId());      
//       
//       System.out.println(this.getFName());
//       System.out.println(fac2.getFName());
//       try {
//          mysql.connect();
//       } catch(DLException dle) {
//          throw new DLException(dle,"An Error Has Occurred", "delete:184");
//       }  
//       
//       mysql.startTrans();
//       
//       if (swap1 && swap2) {
//           mysql.commitTrans();
//       } else {
//          mysql.rollbackTrans();
//       }
//       mysql.endTrans();     
//    }
   
}