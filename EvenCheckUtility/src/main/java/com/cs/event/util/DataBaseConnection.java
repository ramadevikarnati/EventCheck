package com.cs.event.util;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class DataBaseConnection {
   private static Connection con = null;
   private DataBaseConnection() {
   }
   private static final Logger logger = LogManager.getLogger(DataBaseConnection.class);
   public static synchronized Connection getConnection() {
      try {
         Class.forName("org.hsqldb.jdbc.JDBCDriver");
         if(con==null) {
        	 con = DriverManager.getConnection("jdbc:hsqldb:file:testdb/testdb", "SA", "");
        	 logger.debug("connection created");
         }
         
      }  catch (Exception e) {
         e.printStackTrace(System.out);
      }
      return con;
   }
}
