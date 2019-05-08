package com.cpr.api.util;

import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ResponseHandler {
	
	public static Response success(Object entity, javax.ws.rs.core.Response.Status status) {
		/**
		 *  Handler the success response for the EndPoints
		 *  
		 *  @param entity that will be return formatted into JSON
		 */
		
		try {
			return Response.ok().status(status).entity(new ObjectMapper().writeValueAsString(entity)).build();
		} catch (JsonProcessingException e) {
			return ResponseHandler.error(e);
		} finally {
			ThreadLocalConnection.unset();	
		}
		
	}
	
	public static Response success(Object entity) {
		/**
		 *  Handler the success response for the EndPoints
		 *  
		 *  @param entity that will be return formatted into JSON
		 */
		
		try {
			return Response.ok().entity(new ObjectMapper().writeValueAsString(entity)).build();
		} catch (JsonProcessingException e) {
			return ResponseHandler.error(e);
		} finally {
			ThreadLocalConnection.unset();	
		}
		
	}
	
	/**
	 *  Handler the success response for the EndPoints with no message
	 */
	public static Response success(javax.ws.rs.core.Response.Status status) {
		try {
			try {
				return Response.ok().status(status).entity(new ObjectMapper().writeValueAsString(new String("success"))).build();
			} catch (JsonProcessingException e) {
				return ResponseHandler.error(e);
			}
		} finally {
			ThreadLocalConnection.unset();	
		}
		
	}
	
	/**
	 *  Handler the success response for the EndPoints with no message
	 */
	public static Response success() {
		try {
			try {
				return Response.ok().entity(new ObjectMapper().writeValueAsString(new String("success"))).build();
			} catch (JsonProcessingException e) {
				return ResponseHandler.error(e);
			}
		} finally {
			ThreadLocalConnection.unset();	
		}
		
	}
	
	/**
	 *  Handler the error response for the EndPoints
	 *  
	 *  @param The current Exception object for the corresponding error.
	 */
	public static Response error(Exception e) {
		e.printStackTrace();
		ThreadLocalConnection.unset();
		try {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ObjectMapper().writeValueAsString(e.getMessage())).build();
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build(); 
	}
}
