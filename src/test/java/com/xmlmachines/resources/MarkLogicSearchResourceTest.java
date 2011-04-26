package com.xmlmachines.resources;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class MarkLogicSearchResourceTest {

	private static final Logger LOG = Logger
			.getLogger(MarkLogicSearchResourceTest.class);

	static Client client;
	static WebResource webResource;
	static WebResource webResource2;

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

}
