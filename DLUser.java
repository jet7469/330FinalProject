import java.util.ArrayList;
//import java.sql.*;
import java.security.MessageDigest;

/** 
* Louis Trapani
* DLUser.java
* PE009
* ISTE 330-02
*/

public class DLUser {

   // attributes
   private String username;
   private String password;
   private String name;
   private String access;
   
   private Database mysql = new Database();
   
   
   
   // Constructors
   public DLUser() {}
   public DLUser(String _username) {
      username = _username;
   }
   public DLUser(String _username, String _password, String _name, String _access) {
      username = _username;
      password = _password;
      name = _name;
      access = _access;
   }
   
   // Accessors and Mutators
   protected String getUsername(){return this.username;}
   protected void setUsername(String _username){this.username = _username;}
   protected String getPassword(){return this.password;}
   protected void setPassword(String _password){this.password = _password;}
   protected String getName(){return this.name;}
   protected void setName(String _name){this.name = _name;} 
   protected String getAccess(){return this.access;}  
   protected void setAccess(String _access){this.access = _access;}
   
   
   /** 
   * fetch method calls getData with a mySQL SELECT
   * statement to retrieve an entry from the DB.
   * Once entry is retrieved it will check if there
   * is data available. If so, it will set the object
   * attributes with that information and return true.
   * If data is not avialable it will return false
   *
   * @param mysql - the database to use
   * @return a boolean
   */
   protected boolean fetch() throws DLException {
      ArrayList<ArrayList<String>> result;
      ArrayList<String> inner = new ArrayList<>();
      ArrayList<String> strVals = new ArrayList<>(); // ?? not sure yet
      
      
       try {
         mysql.connect();
       } catch(DLException dle) {
          throw new DLException(dle, "user fetch:66", "Can't Connect");
       }
      
      String select = "SELECT * FROM " + "users WHERE username = ?";
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
         this.setUsername(inner.get(0));
         this.setPassword(inner.get(1));
         this.setName(inner.get(2));
         this.setAccess(inner.get(3));         
        } catch(DLException dle) {
           throw new DLException(dle, "user fetch:75", "SELECT Statement Error");
        }      
 
      mysql.close();
      return true;
   }
   
   /** 
   * put method takes object attributes and updates existing 
   * database entry using the mySQL UPDATE statement for the 
   * object that calls it.
   *
   * @param mysql - the database to use
   * @return a boolean
   */
   public boolean put(String _username, String _password, String _name, String _access, String _key) throws DLException {  
   ArrayList<String> strVals = new ArrayList<String>();
   
      try {
         mysql.connect();                 
      } catch(DLException dle) {
         throw new DLException(dle,"An Error Has Occurred", "users_put:109");
      }                                         
      
      strVals.add(_username); 
      strVals.add(_password);      
      strVals.add(_name);      
      strVals.add(_access);
      strVals.add(_key);       
                   
      try {
        String update = "UPDATE users SET username = ?" + ", password = ?" + 
                         ", name = ?" + ", access = ?" + 
                         " WHERE username = ?";                                             
         mysql.setData(update, strVals);
        } catch(DLException dle) {
           throw new DLException(dle, "users_put:127", "UPDATE Statement Error");
        }        
      
      mysql.close();
      return true;  
   }
   
   
   /** 
   * post method takes attributes from object
   * and adds as NEW entry into database 
   * using a mySQL CREATE statement for the
   * object that calls it.
   *
   * @param mysql - the database to use
   * @return a boolean
   */
   public boolean post(String _username, String _password, String _name, String _access) throws DLException{
      ArrayList<String> strVals = new ArrayList<String>();

      strVals.add(_username); 
      strVals.add(_password);      
      strVals.add(_name);      
      strVals.add(_access);
         
      try {
         mysql.connect();                 
      } catch(DLException dle) {
         throw new DLException(dle,"An Error Has Occurred", "users_post:155");
      }
      
      try {   
         String insert = "INSERT INTO users VALUES(?" + ", md5(" + "?" + ")" + ", ?" +", ?" + ");";  
         mysql.setData(insert, strVals);
        } catch(DLException dle) {
           throw new DLException(dle, "users_post:163", "INSERT Statement Error");
        }       
      mysql.close();
      return true;
   }
   
   
   
   /** 
   * delete method deletes a db entry/row
   * uses the setData() method from the DB class
   * and enters a mySQL DELETE statement for the  
   * object that cals it.
   *
   * @param mysql - the database to use
   * @return a boolean
   */
   public boolean delete() throws DLException {
      ArrayList<String> strVals = new ArrayList<String>();

      strVals.add(this.getUsername());    
   
      try {
         mysql.connect();                 
      } catch(DLException dle) {
         throw new DLException(dle,"An Error Has Occurred", "users_delete:188");
      }   
      
      try {  
         String deleteCommand = ("DELETE FROM users WHERE username = ?"); 
         mysql.setData(deleteCommand, strVals);
        } catch(DLException dle) {
           throw new DLException(dle, "users_delete:196", "DELETE Statement Error");
        }        
      mysql.close();
      return true;
   }   
   
       
   /**
   * login method verifies credentials. If successful
   * the rest of the user info will be filled in. If not,
   * false will be returned.
   *
   * @param username - users username
   * @param password - users password
   * @return boolean
   */ 
   protected boolean login(String _username, String _password) throws DLException, Exception {
      DLUser checkUser = new DLUser(_username);
      try {
         checkUser.fetch();
      } catch(DLException dle) {
         throw new DLException(dle, "An Error Has Occurred", "login:220", "arg1: " + _username + " arg2: " + _password);
      }
      
      String checkUsername = checkUser.getUsername();
      String checkPassword = checkUser.getPassword();
      
      // convert text password to md5
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(_password.getBytes());
		byte[] digest = md.digest();
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			sb.append(String.format("%02x", b & 0xff));
		}

      String md5Password = sb.toString();
      
      System.out.println("PASSWORD ENETERED: " + md5Password);
      System.out.println("PASSWORD STORED: " + checkPassword);           
                 
      if(_username.equals(checkUsername) && md5Password.equals(checkPassword)) {
         System.out.println("Welcome!");
         this.fetch();         
         return true;         
      } else {
         System.out.println("INCORRECT LOGIN INFORMATION");
         return false;
      }      
   }
}