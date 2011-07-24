package com.xmlmachines.resources;

import java.text.MessageFormat;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class JaxbMarshallTest {

	private final static Logger LOG = Logger.getLogger(JaxbMarshallTest.class);

	@Test
	public void testForBasicServerResponse() {
		Client client = Client.create();
		WebResource webResource = client.resource("http://localhost:9995/jaxb");
		String s = webResource.post(String.class);
		LOG.info(s);
		Assert.assertNotNull(s);
	}

	@Test
	public void testPersistDocument() {
		Client client = Client.create();
		WebResource webResource = client
				.resource("http://localhost:9995/jaxb/save/999-999-999-999");
		String s = webResource.post(String.class);
		LOG.debug(MessageFormat.format("TEST has: {0}", s));
		Assert.assertEquals("999-999-999-999.xml has been stored", s);

	}

	@Test
	public void testNegotiateContentAsXml() {
		Client client = Client.create();
		WebResource webResource = client
				.resource("http://localhost:9995/jaxb/context/json");
		String s = webResource.accept("application/xml").get(String.class);
		LOG.debug(s);
		Assert.assertTrue(s
				.contains("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"));
	}

	@Test
	public void testNegotiateContentAsJson() {
		Client client = Client.create();
		WebResource webResource = client
				.resource("http://localhost:9995/jaxb/context/json");
		String s = webResource.accept("application/json").get(String.class);
		LOG.debug(s);
		Assert.assertTrue(s.contains("{\"text\":\"test json or xml\"}"));
	}

	@Test
	public void testMultipleXmlChildElementsInRootElement() {
		Client client = Client.create();
		WebResource webResource = client
				.resource("http://localhost:9995/jaxb/context/json/multiples");
		String s = webResource.accept("application/xml").get(String.class);
		LOG.debug(s);
		Assert.assertTrue(s
				.contains("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"));
		Assert.assertTrue(s.contains("<exampleBean><text>first child in list"));
		Assert.assertTrue(s.contains("<exampleBean><text>second child in list"));
	}

	@Test
	public void testMultiplJsonArrayItemsReturned() {
		Client client = Client.create();
		WebResource webResource = client
				.resource("http://localhost:9995/jaxb/context/json/multiples");
		String s = webResource.accept("application/json").get(String.class);
		LOG.debug(s);
		Assert.assertTrue(s.contains("{\"text\":\"first child in list\"}"));
		Assert.assertTrue(s.contains("{\"text\":\"second child in list\"}"));
	}

	// TODO - delete after

	// TODO - Do a hibernate style saveAndUpdate()

}