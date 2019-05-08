package com.cpr.api.rest;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.cpr.api.bean.Record;
import com.cpr.api.service.RecordService;
import com.cpr.api.util.ResponseHandler;
import com.cpr.api.util.ThreadLocalConnection;
import com.cpr.api.util.Util;

@Path("/v1/diff")
public class DiffResource
{
    private static final Logger log = Logger.getLogger(DiffResource.class);

    /**
	 * Save the record for Left Side.
	 * 
	 * @param id will be the primary key and unique identifier for this data. 
	 *           
	 * @return Returns the result message.
	 */
    @POST
    @Path("/{id}/left")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLeft(@Context HttpServletRequest sRequest, @PathParam("id") String id, String data) throws Exception
    {
    	try {
    		String message = "Record was successfully saved";
        	new RecordService().saveRecord(Long.parseLong(id), Util.parseDataJson(data), false);
        	log.info(message);
        	return ResponseHandler.success(message, Response.Status.CREATED);	
    	} catch (Exception e) {
    		log.error(e);
    		return ResponseHandler.error(e);
    	}
    	
    }
    
    /**
	 * Save the record for Right Side.
	 * 
	 * @param id will be the primary key and unique identifier for this data. 
	 *           
	 * @return Returns the result message.
	 */
    @POST
    @Path("/{id}/right")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRight(@Context HttpServletRequest sRequest, @PathParam("id") String id, String data) throws Exception
    {
    	try {
    		String message = "Record was successfully saved";
        	new RecordService().saveRecord(Long.parseLong(id), Util.parseDataJson(data), true);
        	Response.status(Response.Status.CREATED);
        	log.info(message);
        	return ResponseHandler.success(message, Response.Status.CREATED);	
    	} catch (Exception e) {
    		log.error(e);
    		return ResponseHandler.error(e);
    	} finally {
    		ThreadLocalConnection.unset();
    	}
    }
    
    /**
   	 * Perform the comparison between the Right and Left given id.
   	 * 
   	 * @param id the primary key and unique identifier for this data. 
   	 *           
   	 * @return Returns the result message.
   	 */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDiff(@PathParam("id") String id) throws Exception
    {
    	try {
    		RecordService service = new RecordService();
        	Record rec = service.getRecord(Long.parseLong(id));
        	String message = service.diff(rec);
        	log.info(message);
        	return ResponseHandler.success(message);	
    	} catch (Exception e) {
    		log.error(e);
    		return ResponseHandler.success(e.getMessage());
    	} finally {
    		ThreadLocalConnection.unset();
    	}
    }
}
