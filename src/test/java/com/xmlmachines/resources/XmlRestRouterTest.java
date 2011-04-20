package com.xmlmachines.resources;

import org.apache.log4j.Logger;
import org.custommonkey.xmlunit.XMLAssert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class XmlRestRouterTest {
	private static final Logger LOG = Logger.getLogger(XmlRestRouterTest.class);

	static Client client;
	static WebResource webResource;
	static WebResource webResource2;

	@BeforeClass
	public static void setUp() throws Exception {
		LOG.info("before class method");
		com.xmlmachines.providers.GrizzlyContainerProvider.getInstance()
				.startServer();
		client = Client.create();
		webResource = client.resource("http://localhost:8888/xml");
		// webResource2 = client.resource("http://localhost:8888/xp/xpl/pipe");
	}

	@Test
	public void testGet() throws Exception {
		LOG.debug("testing REST GET");
		String s = webResource.get(String.class);
		XMLAssert.assertXMLEqual("Comparing Responses", s, "<xml>get</xml>");
		LOG.debug(s);
	}

	@Test
	public void testPost() throws Exception {
		LOG.debug("testing REST POST");
		String s = webResource.post(String.class);
		XMLAssert.assertXMLEqual("Comparing Responses", s, "<xml>post</xml>");
		LOG.debug(s);
	}

	@Test
	public void testPut() throws Exception {
		LOG.debug("testing REST PUT");
		String s = webResource.put(String.class);
		XMLAssert.assertXMLEqual("Comparing Responses", s, "<xml>put</xml>");
		LOG.debug(s);
	}

	@Test
	public void testDelete() throws Exception {
		LOG.debug("testing REST DELETE");
		String s = webResource.delete(String.class);
		XMLAssert.assertXMLEqual("Comparing Responses", s, "<xml>delete</xml>");
		LOG.debug(s);
	}

	/**
	 * Test REST Calabash GET Invocation.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	// @Test
	public void testRESTGetForCalabash() throws Exception {
		String s = webResource2.get(String.class);
		LOG.debug("Got this: " + s);
		XMLAssert.assertXpathEvaluatesTo("XProc Pipeline", "//doc", s);
	}

	@AfterClass
	public static void tearDown() throws Exception {
		com.xmlmachines.providers.GrizzlyContainerProvider.getInstance()
				.stopServer();
		LOG.debug("Successfully torn down server");

	}
}
