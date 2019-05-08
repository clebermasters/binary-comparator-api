package com.cpr.api;

import static org.junit.Assert.assertEquals;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.HttpMethod;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RestIT extends TestBase {
	
	@Test
	public void insertLeft() throws Exception {
		long id = 5;
		String result = consume(generatePayload("Test"), HttpMethod.POST, buildURL( "v1/diff/"+ id + "/left"));
		String expect = "\"Record was successfully saved\"";
		assertEquals(expect, result.trim().replace("\r",""));
		System.out.println("Result: " + result);
	}
	
	@Test
	public void insertRight() throws Exception {
		long id = 5;
		String result = consume(generatePayload("Test"), HttpMethod.POST, buildURL( "v1/diff/"+ id + "/right"));
		String expect = "\"Record was successfully saved\"";
		assertEquals(expect, result.trim().replace("\r",""));
		System.out.println("Result: " + result);
	}
	
	@Test
	public void diffEqual() throws Exception {
		long id = 5;
		String result = consume("", HttpMethod.GET, buildURL("v1/diff/"+ id));
		System.out.println("Result: " + result);
		String expect = "\"The data are equal.\"";
		assertEquals(expect, result.trim().replace("\r",""));
	}
	
	
	@Test
	public void diffMissingRight() throws Exception {
		long id = 4;
		String result = consume("", HttpMethod.GET, buildURL("v1/diff/"+ id));
		System.out.println("Result: " + result);
		String expect = "\"This ID not exists on database!\"";
		assertEquals(expect, result.trim().replace("\r",""));
	}
	
	@Test
	public void diffSizeDiff() throws Exception {
		long id = 6;
		consume(generatePayload("Test"), HttpMethod.POST, buildURL( "v1/diff/"+ id + "/left"));
		consume(generatePayload("TestDiffSize"), HttpMethod.POST, buildURL( "v1/diff/"+ id + "/right"));
		
		String result = consume("", HttpMethod.GET, buildURL("v1/diff/"+ id));
		System.out.println("Result: " + result);
		String expect = "\"The size of both data are different.\"";
		assertEquals(expect, result.trim().replace("\r",""));
	}
	
	@Test
	public void diffSameSizeDiff() throws Exception {
		long id = 7;
		consume(generatePayload("Test"), HttpMethod.POST, buildURL( "v1/diff/"+ id + "/left"));
		consume(generatePayload("Tess"), HttpMethod.POST, buildURL( "v1/diff/"+ id + "/right"));
		
		String result = consume("", HttpMethod.GET, buildURL("v1/diff/"+ id));
		System.out.println("Result: " + result);
		String expect = "\"The both data are same size, however their offsets are different: 4, 5\"";
		assertEquals(expect, result.trim().replace("\r",""));
	}
	
	
	public String generatePayload(String data) {
		Map<String,String> map = new HashMap<String, String>();
		map.put("data", Base64.getEncoder().encodeToString(data.getBytes()));
		
		try {
			return new ObjectMapper().writeValueAsString(map).toString();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
}
