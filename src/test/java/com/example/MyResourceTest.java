package com.example;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;

public class MyResourceTest {

  private HttpServer server;
  private WebTarget target;

  public static final String BASE_URI = "http://localhost:8080/myapp/";

  /**
   * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
   * @return Grizzly HTTP server.
   */
  public static HttpServer startServer() {
    // create a resource config that scans for JAX-RS resources and providers
    // in $package package
    final ResourceConfig rc = new ResourceConfig().packages("com.example");

    // create and start a new instance of grizzly http server
    // exposing the Jersey application at BASE_URI
    return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
  }


  @Before
  public void setUp() throws Exception {
    server = startServer();
    Client c = ClientBuilder.newClient();
    target = c.target(BASE_URI);
  }

  @After
  public void tearDown() throws Exception {
    server.shutdownNow();
  }

  /**
   * Test to see that the message "Got it!" is sent in the response.
   */
  @Test
  public void testGetIt() {
    String responseMsg = target.path("myresource").request().get(String.class);
    Assert.assertEquals("Got it!", responseMsg);
  }
}