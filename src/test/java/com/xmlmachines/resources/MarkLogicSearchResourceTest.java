package com.xmlmachines.resources;

import javax.ws.rs.core.MultivaluedMap;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class MarkLogicSearchResourceTest {

	private static final Logger LOG = Logger
			.getLogger(MarkLogicSearchResourceTest.class);

	static Client client;
	static WebResource webResource;

	// static WebResource webResource2;

	@BeforeClass
	public static void setUp() throws Exception {
		client = Client.create();
		webResource = client.resource("http://localhost:8888/search/test");
	}

	@Test
	public void testGet() throws Exception {
		String s = webResource.get(String.class);
		Assert.assertTrue(s.contains("<search:qtext>test</search:qtext>"));
		// XMLAssert.assertXMLEqual("Comparing Responses", s, "<xml>get</xml>");
		// LOG.info(s);
	}

	@Test
	public void testAnotherGet() throws Exception {
		webResource = client
				.resource("http://localhost:8888/search/secondTest");
		String s = webResource.get(String.class);
		Assert.assertTrue(s.contains("<search:qtext>secondTest</search:qtext>"));
		// XMLAssert.assertXMLEqual("Comparing Responses", s, "<xml>get</xml>");
		// LOG.info(s);
	}

	@Test
	public void testPost() {
		webResource = client.resource("http://localhost:8888/search");
		MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
		formData.add("term", "test");
		// ClientResponse response
		String s = webResource.type("application/x-www-form-urlencoded").post(
				String.class, formData);
		Assert.assertTrue(s.contains("<search:qtext>test</search:qtext>"));
	}

	@Test
	public void testAnotherPost() {
		webResource = client.resource("http://localhost:8888/search");
		MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
		formData.add("term", "Another Test");
		// ClientResponse response
		String s = webResource.type("application/x-www-form-urlencoded").post(
				String.class, formData);
		Assert.assertTrue(s
				.contains("<search:qtext>Another Test</search:qtext>"));
	}
}
