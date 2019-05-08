package com.cpr.api.service;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.StringJoiner;

import com.cpr.api.bean.Record;
import com.cpr.api.interfaces.impl.RecordDao;
import com.cpr.api.util.ConnectionHandler;

public class RecordService extends ConnectionHandler {

	public RecordService() throws SQLException {
		super();
	}
	
	/**
	 * Get a Record from database with all columns.
	 * 
	 * @param id The PRIMARY KEY from our Record table. 
	 *           
	 * @return Returns the Record found at database.
	 */
	public Record getRecord(long id) throws Exception {
		Record record = new RecordDao().get(id); 
		
		if (record.getId() == 0)
			throw new Exception("This ID not exists on database!");
		
		return record;
	}
	
	
	/**
	 * Save the left or right data at database
	 * 
	 * @param id The PRIMARY KEY from our Record table to identify this data. 
	 * 
	 */
	public boolean saveRecord(long id, String data, boolean isRight) throws Exception {
		new RecordDao().save(id, data, isRight);
		return true;
	}
	
	/**
	 * Perform basic validations with the data retrieved from database.
	 * 
	 * @param Record instance with all data populated.
	 *           
	 * @return Returns a message if at least one property is missing.
	 */
	private String validateRecord(Record rec){
		
		if (rec.getId() == 0)
			return "The data not found.";
		else if (rec.getDataLeft().isEmpty())
			return "The Left data is missing.";
		else if (rec.getDataRight().isEmpty())
			return "The Right data is missing.";
		
		return "";
	}
	
	/**
	 * Perform the Comparison between the Left and Right records 
	 * 
	 * @param Record instance with all data populated.
	 *           
	 * @return Returns a String with result from Diff.
	 */
	
	public String diff(Record rec) {
		String validateResult = validateRecord(rec);
		if (!validateResult.isEmpty()) return validateResult;
		
		byte[] bytesRightSide = rec.getDataRight().getBytes();
		byte[] bytesLeftSide = rec.getDataLeft().getBytes();

		boolean isEqual = Arrays.equals(bytesLeftSide, bytesRightSide);

		StringJoiner joinerOffsets = new StringJoiner(", ");
		
		if (isEqual)
			return "The data are equal.";
		else if (bytesLeftSide.length != bytesRightSide.length) {
			return "The size of both data are different.";
		} else {
			byte diff = 0;
			for (int idx = 0; idx < bytesLeftSide.length; idx++) {
				diff = (byte) (bytesLeftSide[idx] ^ bytesRightSide[idx]);
				if (diff != 0) 
					joinerOffsets.add(String.valueOf(idx));
			}
		}
		return "The both data are same size, however their offsets are different: " + joinerOffsets.toString();
	}

}
