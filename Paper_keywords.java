import java.sql.*;
import java.util.*;

/**
 * data layer for the paper_keywords table 
 */

public class Paper_keywords {

   public int id;
   public String keyword;

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
   
}