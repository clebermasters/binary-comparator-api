package com.cpr.api.interfaces.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.cpr.api.bean.Record;
import com.cpr.api.interfaces.Dao;
import com.cpr.api.util.ConnectionHandler;

public class RecordDao extends ConnectionHandler implements Dao {
	private static final Logger log = Logger.getLogger(RecordDao.class);
	public RecordDao() throws SQLException {
		super();
	}
	
	/**
	 * Save the left or right data at database
	 * 
	 * @param id The PRIMARY KEY from our Record table to identify this data. 
	 * 
	 */
	@Override
	public void save(long id, String data, boolean isRight) throws Exception {
		Statement stmt = null;
		StringBuilder sql = new StringBuilder();
		stmt = getConnection().createStatement();
		// H2 Drive does not support prepareStatement, we should manually add the parameters
		
		if (idExists(id) >= 0) {
			if (isRight)
				sql.append("UPDATE record set right = ");
			else
				sql.append("UPDATE record set left = ");

			sql.append(formatString(data));
			sql.append(" where id = ");
			sql.append(id);
			
		} else {
			sql.append("INSERT INTO record (id, left, right) values (");
			sql.append(id);
			sql.append(",");
			sql.append(!isRight ? formatString(data) : "''");
			sql.append(",");
			sql.append(isRight ? formatString(data) : "''");
			sql.append(")");
		}

		stmt.executeUpdate(sql.toString()); 
		stmt.close(); 
		log.info("The Record was saved. isRight? " + isRight);
	}
	
	/**
	 * Get a Record from database with all columns.
	 * 
	 * @param id The PRIMARY KEY from our Record table. 
	 *           
	 * @return Returns the Record found at database.
	 */
	@Override
	public Record get(long id) throws Exception {
		Connection conn = null; 
		Statement stmt = null; 
		Record rec = new Record();
		conn = getConnection();

		String sql = "select id, left, right from record where id = " + id;
		stmt = conn.createStatement(); 
		ResultSet rs = stmt.executeQuery(sql);

		while(rs.next()) {
			rec.setId(rs.getLong("id"));
			rec.setDataLeft(rs.getString("left"));
			rec.setDataRight(rs.getString("right"));
			log.info("The data was found, id: " + rec.getId());
		}
		stmt.close(); 
		
		return rec;
	}
	
	/**
	 * Check if the ID already exists on database
	 * 
	 * @param id The PRIMARY KEY from our Record table. 
	 *           
	 * @return Returns itself if exists or -1 if not found
	 */
	@Override
	public long idExists(long id) throws Exception {
		
		String sql = "select id from record where id = " + id;
		
        Statement stmt = getConnection().createStatement();

		ResultSet rs = stmt.executeQuery(sql);

		while(rs.next()) 
			return rs.getLong("id");

		stmt.close(); 
		return -1;
	}
}