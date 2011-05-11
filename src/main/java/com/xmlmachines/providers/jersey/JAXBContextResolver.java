package com.xmlmachines.providers.jersey;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;

import org.apache.log4j.Logger;

import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;
import com.xmlmachines.beans.jaxb.ExampleBean;

@Provider
public final class JAXBContextResolver implements ContextResolver<JAXBContext> {

	private static final Logger LOG = Logger
			.getLogger(JAXBContextResolver.class);

	private final JAXBContext context;

	private final Set<Class<?>> types;

	private final Class<?>[] cTypes = { ExampleBean.class };

	public JAXBContextResolver() throws Exception {
		LOG.info("Setting up JAXB Context resolver..");
		this.types = new HashSet<Class<?>>(Arrays.asList(cTypes));
		this.context = new JSONJAXBContext(JSONConfiguration.natural().build(),
				cTypes);
	}

	@Override
	public JAXBContext getContext(Class<?> objectType) {
		LOG.info("Do we have a context? " + types.contains(objectType));
		return (types.contains(objectType)) ? context : null;
	}
}