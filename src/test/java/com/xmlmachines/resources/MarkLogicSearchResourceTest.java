package com.xmlmachines.resources;

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
		webResource = client.resource("http://localhost:8888/search");
	}

	@Test
	public void testGet() throws Exception {
		String s = webResource.get(String.class);
		// XMLAssert.assertXMLEqual("Comparing Responses", s, "<xml>get</xml>");
		LOG.info(s);
	}

}
