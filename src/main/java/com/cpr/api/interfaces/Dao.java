package com.cpr.api.interfaces;

import com.cpr.api.bean.Record;

/**
 * This is the contract to manage the Record on database.
 */
public interface Dao {
	void save(long id, String data, boolean isRight) throws Exception;
	Record get(long id) throws Exception;
	long idExists(long id) throws Exception;
}
