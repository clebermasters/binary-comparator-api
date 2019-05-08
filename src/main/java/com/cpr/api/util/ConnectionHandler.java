package com.cpr.api.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 *  Wrapper the control of connection
 */
public class ConnectionHandler {
	private static final Logger log = Logger.getLogger(ConnectionHandler.class);
	public ConnectionHandler() throws SQLException{
		getConnection();
 	}
	
	
	/**
	 *  Guarantee that we have one Connection by Thread (Client Request)
	 */
	public synchronized static Connection getConnection() throws SQLException
    {
		if (ThreadLocalConnection.get() != null && ThreadLocalConnection.get().isClosed()) {
			System.out.println("Connection is closed ! Trying connect again");
		}
        if (ThreadLocalConnection.get() == null) {
        	log.info("Opened database successfully");
        	ThreadLocalConnection.set(ConnectionFactory.getConnection());
        	
        } else {
        	log.info("Use the connection already opened");
        }

        return ThreadLocalConnection.get();
    }
	
	public synchronized void closeConnection(){
		try {
			if (ThreadLocalConnection.get() != null){
				ThreadLocalConnection.get().close();
				ThreadLocalConnection.unset();
				log.info("Connection Closed !");
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e);
		}
	}

	/**
	 *  Set the transaction state for this connection.
	 *  
	 *  @param commit False Or True if the transaction will be AutoCommit
	 */
	public void setAutoCommit(boolean commit){
		try {
			ThreadLocalConnection.get().setAutoCommit(commit);
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e);
			closeConnection();
		}
	}
	
	/**
	 *  Wrapper the data in single quote
	 *  
	 *  @param the data to escape into single quote
	 */
	public String formatString(String data) {
		return "'" + data + "'";
	}
}
