package com.xmlmachines.providers.jersey;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.log4j.Logger;

import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;
import com.sun.jersey.server.impl.container.config.AnnotatedClassScanner;

@Provider
public final class JAXBContextResolver implements ContextResolver<JAXBContext> {

	private static final Logger LOG = Logger
			.getLogger(JAXBContextResolver.class);

	private final JAXBContext context;

	private final HashSet<Class<?>> types; // = new HashSet<Class<?>>();

	public JAXBContextResolver() throws Exception {
		LOG.info("Setting up the JAXBContextResolver");
		AnnotatedClassScanner classScanner = new AnnotatedClassScanner(
				XmlRootElement.class, XmlType.class);
		Set<Class<?>> cTypes = classScanner
				.scan(new String[] { "com.xmlmachines.beans" });
		this.types = new HashSet(Arrays.asList(cTypes));
		this.context = new JSONJAXBContext(JSONConfiguration.natural().build(),
				cTypes.toArray(new Class[cTypes.size()]));
	}

	@Override
	public JAXBContext getContext(Class<?> objectType) {
		LOG.info(MessageFormat.format("Getting context for: {0}", objectType));
		return (types.contains(objectType)) ? context : null;
	}
}