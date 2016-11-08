import java.util.*;
import java.io.*;
import java.sql.*;

public class DLException extends Exception {

   Timestamp ts = new Timestamp(System.currentTimeMillis());
   File file;

   /**
   * Constructor that takes Exception and array of Strings
   */
   public DLException(Exception e, String blMsg, String... msgs) {
      super(blMsg);
      log(e, blMsg, msgs);
   }

   /**
   * Constructor that takes Excpeption
   */
   public DLException(Exception e) {
      this(e, "An Error Has Occurred");
   }
   
   
   /**
   * log method takes Exception and various Strings
   * to log messages to a text file when the exception is
   * thrown. This allows for admin to know what errors
   * are occurring
   *
   * @param e - an Exception
   * @param blMsg - a String for business layer message
   * @param msgs - array of Strings for various log readings
   */
   public void log(Exception e, String blMsg, String... msgs) {
      try{
            if (file == null) {      
   			   file = new File("log.txt");
            }
   			FileWriter writer = new FileWriter(file, true);
   			writer.write("\nTIMESTAMP" + ts + "\n");
            writer.write(blMsg + "\n");
            writer.write("Exception: " + e.getMessage() + "\n");
            for (int i = 0; i < msgs.length; i++) {
               writer.write(msgs[i] + "\n");        
            }
            writer.write("-----------------------------------");          
   			writer.flush();
   			writer.close();
      } catch (IOException ioe) {
         System.out.println(ioe.getMessage());
      }
   }
   
}