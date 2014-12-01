package main;

import java.sql.Connection;
import java.sql.SQLException;

public class DBSwitch {
	private Connection _conn = null;
	
	/**
	 * Open the database connection.
	 * @param dbUser
	 * @param dbPass
	 * @param dbSID
	 * @param dbHost
	 * @param port
	 * @return
	 */
	public String openDBConnection(String dbUser, String dbPass, String dbSID, String dbHost, int port) {
	    String res="";
	    if (_conn != null) {
	    	closeDBConnection();
	    }
	  
	    try {
	    	_conn = DBUtils.openDBConnection(dbUser, dbPass, dbSID, dbHost, port);
	    	System.out.println("Opened a connection");
	    	res = DBUtils.testConnection(_conn);
	    } 
	    catch (SQLException sqle) {
	    	sqle.printStackTrace(System.err);
	    } 
	    catch (ClassNotFoundException cnfe) {
	    	cnfe.printStackTrace(System.err);
	    }
	    return res;
	}
	
	public String openDBConnection() {
	    String res="";
	    if (_conn != null) {
	    	closeDBConnection();
	    }
	  
	    try {
	    	_conn = DBUtils.openDBConnection("", "", "", "", 0);
	    	System.out.println("Opened a connection");
	    	res = DBUtils.testConnection(_conn);
	    } 
	    catch (SQLException sqle) {
	    	sqle.printStackTrace(System.err);
	    } 
	    catch (ClassNotFoundException cnfe) {
	    	cnfe.printStackTrace(System.err);
	    }
	    return res;
	}
	
	/**
	 * Close the database connection.
	 */
	public void closeDBConnection() {
	    try {
	    	DBUtils.closeDBConnection(_conn);
	    	System.out.println("Closed a connection");
	    } 
	    catch (SQLException sqle) {
	    	sqle.printStackTrace(System.err);
	    }
	}

	public Connection get_conn() {
		return _conn;
	}
	
}
