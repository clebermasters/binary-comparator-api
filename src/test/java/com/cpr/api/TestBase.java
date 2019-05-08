package com.cpr.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import javax.ws.rs.HttpMethod;


public class TestBase {
  static final String HOST = "http://localhost:8999";
//  static final String CONTEXT = "/";
  static final String CONTEXT = "/cpr-api/";
  static final String CONTENTTYPE = "application/json";
  
  public URI buildURL(String endpoint) throws URISyntaxException {
	  return new URI(HOST + CONTEXT + endpoint);
  }
  public String consume(String payload, String httpMethod, URI url) throws Exception
  {
      HttpURLConnection conn = makeConnection(httpMethod, url);
      
      System.out.println("Calling ... " + url.toString());
      
      writeRequest(payload, conn);
      return readResponse(conn);
  }
  
  private void writeRequest(String body, HttpURLConnection conn) throws IOException
  {
      conn.setRequestProperty("Content-Length", String.valueOf(body.length()));
      if (!body.isEmpty())
          conn.getOutputStream().write(body.getBytes(StandardCharsets.UTF_8));
  }
  
  private HttpURLConnection makeConnection(String httpMethod, URI url) throws IOException, MalformedURLException, ProtocolException
  {
  	if (httpMethod == null) httpMethod = HttpMethod.GET;
  	
      HttpURLConnection conn = (HttpURLConnection) url.toURL().openConnection();
      
      if (httpMethod == HttpMethod.POST || httpMethod == HttpMethod.PUT) 
      	conn.setRequestProperty("Content-Type", CONTENTTYPE);
      
      conn.setRequestMethod(httpMethod);
      conn.setDoOutput(true);
      return conn;
  }
  
  private String readResponse(HttpURLConnection conn) throws IOException
  {
      if (conn.getResponseCode() >= 400)
          throw new RuntimeException(readError(conn));
      try (BufferedReader bodyReader = new BufferedReader(new InputStreamReader(conn.getInputStream())))
      {
          return readLines(bodyReader);
      }
      finally
      {
          conn.disconnect();
      }
  }
  
  private String readError(HttpURLConnection conn) throws IOException
  {
      if (conn.getErrorStream() == null || conn.getResponseCode() != 401)
          return conn.getResponseCode() + " " + conn.getResponseMessage();

      try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(conn.getErrorStream())))
      {
          return readLines(errorReader);
      }
  }

  private String readLines(BufferedReader reader) throws IOException
  {
      StringBuilder sb = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null)
          sb.append(line).append("\n");
      return sb.toString();
  }
}
