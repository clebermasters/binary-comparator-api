package com.cpr.api.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {
	
	static final String TABLE_EXITS = "42S01";
	private static final Logger log = Logger.getLogger(Util.class);
	
	/**
	 *  Parse and retrieve the data from JSON. 
	 *  
	 *  @param JSON data
	 */
	public static String parseDataJson(String data) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = null;
		jsonNode = mapper.readTree(data);
		return jsonNode.get("data").asText();
	}
	
	
	/**
	 *  Create the database (H2) if not exits yet.
	 *  
	 */
	public static void createDabase() throws SQLException {
		Connection conn = null; 
		Statement stmt = null; 
		try { 
			conn = ConnectionHandler.getConnection();
			 
			stmt = conn.createStatement(); 
			String sql =  "CREATE TABLE record " + 
					"(id bigint not NULL, " + 
					" right text, " +  
					" left text, " +  
					" PRIMARY KEY ( id ))";  
			stmt.executeUpdate(sql);
			
			log.info("Created table in given database...");
			stmt.close(); 
			
		} catch(SQLException se) {
			if (se.getSQLState().equals(TABLE_EXITS)) {
				log.info("The database is already exists! Skipping creation process.");
			} else {
				log.error(se);
			}
		} catch(Exception e) { 
			log.error(e);
		} finally { 
			ThreadLocalConnection.unset();
		}
	} 
}
