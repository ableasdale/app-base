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
		WebResource webResource = client.resource("http://localhost:8888/jaxb");
		String s = webResource.post(String.class);
		LOG.info(s);
		Assert.assertNotNull(s);
	}

	@Test
	public void testPersistDocument() {
		Client client = Client.create();
		WebResource webResource = client
				.resource("http://localhost:8888/jaxb/save/999-999-999-999");
		String s = webResource.post(String.class);
		LOG.debug(MessageFormat.format("TEST has: {0}", s));
		Assert.assertEquals("999-999-999-999.xml has been stored", s);

	}

	// TODO - delete after

	// TODO - Do a hibernate style saveAndUpdate()

}