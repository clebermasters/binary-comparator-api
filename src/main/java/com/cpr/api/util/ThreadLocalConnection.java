package com.cpr.api.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 *  Guarantee that we have one Connection by Thread (Client Request)
 */
public class ThreadLocalConnection {
	private static final Logger log = Logger.getLogger(ThreadLocalConnection.class);
	private static final ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<Connection>();

	public static void set(Connection connection) {
		connectionThreadLocal.set(connection);
	}

	/**
	 *  Removes the Reference for our current Connection and close it.
	 */
	public static void unset() {
		if(connectionThreadLocal.get() != null)
			try {
				
				if (!connectionThreadLocal.get().isClosed())
					connectionThreadLocal.get().close();
				
				connectionThreadLocal.remove();
				log.info("Connection was closed !");
			} catch (SQLException e) {
				e.printStackTrace();
				log.error(e);
			}
	}

	/**
	 *  Returns the current Connection
	 */
	public static Connection get() {
		return connectionThreadLocal.get();
	}
}