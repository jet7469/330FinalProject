import java.util.*;
import java.sql.*;

public class Faculty {

   // attributes
   private int id;
   private String fName;
   private String lName;
   private String password;
   private String email;

   private Database mysql = new Database();


   public Faculty() {}
   
   public Faculty(int _id) {
      id = _id;
   }
   public Faculty(int _id, String _fName, String _lName, String _password, String _email) {
      id = _id;
      fName = _fName;
      lName = _lName;
      password = _password;
      email = _email;
   }
   
   // Accessors and Mutators
   public int getId(){
      return this.id;
   }
   public void setId(int _id) {
      this.id = _id;
   }
   
   public String getFName() {
      return this.fName;
   }
   public void setFName(String _fName) {
      this.fName = _fName;
   }
   
   public String getLName() {
      return this.lName;
   }
   public void setLName(String _lName) {
      this.lName = _lName;
   }
   
   public String getPassword() {
      return this.password;
   }
   public void setPassword(String _password) {
      this.password = _password;
   }
   
   public String getEmail() {
      return this.email;
   }
   public void setEmail(String _email) {
      this.email = _email;
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
      
      String select = "SELECT * FROM " + "faculty WHERE id = ?";
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
         this.setFName(inner.get(1));
         this.setLName(inner.get(2));
         this.setPassword(inner.get(3));         
         this.setEmail(inner.get(4));         

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
   public boolean put(int id, String fname, String lname, String password, String email, int key) throws DLException {  
   ArrayList<String> strVals = new ArrayList<String>();
   
      try {
         mysql.connect();                 
      } catch(DLException dle) {
         throw new DLException(dle,"An Error Has Occurred", "put:117");
      }                                         
      
      strVals.add(Integer.toString(id)); 
      strVals.add(fname);      
      strVals.add(lname);      
      strVals.add(password); 
      strVals.add(email);      
      strVals.add(Integer.toString(key));       
            
           
      try {
     
         String update = "UPDATE faculty SET id = ?" + ", fname = ?" + 
                         ", lname = ?" + ", password = ?" + ", email = ?" +
                         " WHERE id = ?";                                             
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
   public boolean post(int id, String fname, String lname, String password, String email) throws DLException{
      ArrayList<String> strVals = new ArrayList<String>();

      strVals.add(Integer.toString(id)); 
      strVals.add(fname);      
      strVals.add(lname);      
      strVals.add(password); 
      strVals.add(email);      
         
      try {
         mysql.connect();                 
      } catch(DLException dle) {
         throw new DLException(dle,"An Error Has Occurred", "post:185");
      }
      
      try {   
         String insert = "INSERT INTO equipment VALUES(?" + ", ?" + ", ?" +", ?" + ");";  
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
         String deleteCommand = ("DELETE FROM equipment WHERE equipid = ?"); 
         mysql.setData(deleteCommand, strVals);
        } catch(DLException dle) {
           throw new DLException(dle, "delete:206", "DELETE Statement Error");
        }        
      mysql.close();
      return true;
   } 

   public void swap(int id2) throws DLException {
      Faculty fac2 = new Faculty(id2); 
      fac2.fetch();
      System.out.println(this.getFName());
      System.out.println(fac2.getFName());
 
      
      boolean swap1 = this.put(this.getId(), fac2.getFName(), this.getLName(), this.getPassword(), this.getEmail(), this.getId());
      boolean swap2 = fac2.put(fac2.getId(), this.getFName(), fac2.getLName(), fac2.getPassword(), fac2.getEmail(), fac2.getId());      
      
      System.out.println(this.getFName());
      System.out.println(fac2.getFName());
      try {
         mysql.connect();
      } catch(DLException dle) {
         throw new DLException(dle,"An Error Has Occurred", "delete:184");
      }  
      
      mysql.startTrans();
      
      if (swap1 && swap2) {
          mysql.commitTrans();
      } else {
         mysql.rollbackTrans();
      }
      mysql.endTrans();     
   }     
}