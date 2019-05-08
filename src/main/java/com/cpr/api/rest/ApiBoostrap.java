package com.cpr.api.rest;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;

import com.cpr.api.util.Util;

@WebListener
public class ApiBoostrap implements ServletContextListener
{
    private static final Logger log = Logger.getLogger(ApiBoostrap.class);
    
	/**
   	 * Boostrap the API and create the database if not exits.
   	 * 
   	 */
    @Override
    public void contextInitialized(ServletContextEvent sce)
    {
        try
        {
        	log.info("Init API ...");
        	Util.createDabase();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.fatal(e, e);
        }
    }

	@Override
	public void contextDestroyed(ServletContextEvent arg) {}
}
