package com.cpr.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.Base64;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cpr.api.bean.Record;
import com.cpr.api.service.RecordService;
import com.cpr.api.util.Util;

public class UnitTest {
	
	@BeforeClass
	public static void init() {
		try {
			Util.createDabase();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void insertLeft() throws Exception {
		boolean success = false;
		success = new RecordService().saveRecord(8, encode("insertLeft"), false);
		assertTrue(success);
	}
	
	@Test
	public void insertRight() throws Exception {
		boolean success = false;
		success = new RecordService().saveRecord(8, encode("insertRight"), true);
		assertTrue(success);
	}
	
	@Test
	public void getRecord() throws Exception {
		Record rec;
		rec = new RecordService().getRecord(8);
		assertNotNull(rec);
	}
	
	
	@Test
	public void diffEmpty() throws Exception {
		Record rec = new Record();
		rec.setDataLeft(encode("insertLeft"));
		rec.setDataRight(encode("insertRight"));
		rec.setId(0);
		
		String result = new RecordService().diff(rec);
		assertNotNull(result);
	}
	
	@Test
	public void diffSize() throws Exception {
		Record rec = new Record();
		rec.setDataLeft(encode("insertLeft1234"));
		rec.setDataRight(encode("insertRight"));
		rec.setId(1);
		
		String result = new RecordService().diff(rec);
		String expect = "The size of both data are different.";
		assertEquals(expect, result);
	}
	
	@Test
	public void diffEqual() throws Exception {
		Record rec = new Record();
		rec.setDataLeft(encode("insertRight"));
		rec.setDataRight(encode("insertRight"));
		rec.setId(1);
		
		String result = new RecordService().diff(rec);
		String expect = "The data are equal.";
		assertEquals(expect, result);
	}
	
	@Test
	public void diff() throws Exception {
		Record rec = new Record();
		rec.setDataLeft(encode("insertRight"));
		rec.setDataRight(encode("insertRight1"));
		rec.setId(1);
		
		String result = new RecordService().diff(rec);
		String expect = "The both data are same size, however their offsets are different: 15";
		assertEquals(expect, result);
	}
	
	public String encode(String data) {
		return Base64.getEncoder().encodeToString(data.getBytes());
	}
}
