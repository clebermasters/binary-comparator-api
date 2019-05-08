package com.cpr.api.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {
	static final String USER = "userdb"; 
	static final String PASS = "pass**"; 
	static final String JDBC_DRIVER = "org.h2.Driver";  
	
	/**
	 * Register the Drive and parameters for connections
	 */
	public static Connection getConnection(){
		Connection c = null;
		try {
			Class.forName(JDBC_DRIVER);
			c = DriverManager.getConnection("jdbc:h2:~/internaldb", USER, PASS);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
		}
		return c;
	}
}