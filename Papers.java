import java.sql.*;
import java.util.*;

/*
Database access class for the Papers table
*/

public class Papers {

   //global variables
   public int id;
   public String title;
   public String abstractText;
   public String citation;

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
   
   //fetch method
   public void fetch(String sql) {
      
   }

}
