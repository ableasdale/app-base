package com.xmlmachines.providers.jersey;

import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

import com.marklogic.xcc.Session;
import com.sun.jersey.api.core.HttpContext;
import com.xmlmachines.Consts.Env;
import com.xmlmachines.providers.AbstractInjectableProvider;
import com.xmlmachines.providers.ConfigurationProvider;
import com.xmlmachines.providers.MarkLogicContentSourceProvider;

@Provider
public class XccSessionProvider extends AbstractInjectableProvider<Session> {

	private static final Logger LOG = Logger
			.getLogger(XccSessionProvider.class);

	public XccSessionProvider() {
		super(Session.class);
		LOG.debug("XccSessionProvider [Constructor]");
	}

	@Override
	public Session getValue(HttpContext c) {

		/**
		 * TODO - this is still hard-coded at the moment - it should work out
		 * whether it's a test or live request based on the HttpContext
		 */
		LOG.debug("Injecting XCC ML Session.");
		return MarkLogicContentSourceProvider
				.getInstance()
				.getContentSource(Env.PROD)
				.newSession(
						ConfigurationProvider.getInstance()
								.getXmlServerConfig().getDatabaseName());
	}

}