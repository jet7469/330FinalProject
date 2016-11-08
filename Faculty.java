import java.util.*;
import java.sql.*;

public class Faculty {
   Database mysql = new Database("jdbc:mysql://127.0.0.1/FacResearchDB?useSSL=false", 
                                                    "com.mysql.jdbc.Driver", "root", "Westridge1");
   // global vars
   private int id;
   private String fName;
   private String lName;
   private String password;
   private String email;



   // constructors
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
}