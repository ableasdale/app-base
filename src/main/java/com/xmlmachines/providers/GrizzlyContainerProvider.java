package com.xmlmachines.providers;

import java.io.IOException;
import java.net.URI;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.UriBuilder;

import org.apache.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;

import com.sun.jersey.api.container.grizzly2.GrizzlyWebContainerFactory;
import com.xmlmachines.beans.ApplicationServerConfig;

public class GrizzlyContainerProvider {

	private HttpServer httpServer;
	private final ApplicationServerConfig cfg;
	private final int port;
	private final URI BASE_URI;

	private static final Logger LOG = Logger
			.getLogger(GrizzlyContainerProvider.class);

	private GrizzlyContainerProvider() {
		LOG.info("Creating the GrizzlyContainerProvider instance");
		cfg = ConfigurationProvider.getInstance().getApplicationServerConfig();
		port = Integer.parseInt(cfg.getHostPort());
		// = null;
		BASE_URI = getBaseURI();
	}

	private static class GrizzlyContainerProviderHolder {
		private static final GrizzlyContainerProvider INSTANCE = new GrizzlyContainerProvider();
	}

	public static GrizzlyContainerProvider getInstance() {
		return GrizzlyContainerProviderHolder.INSTANCE;
	}

	/**
	 * TODO - configure so this isn't localhost.
	 * 
	 * @return
	 */
	private URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost/").port(port).build();
	}

	/**
	 * Creates a ContainerFactory
	 * 
	 * @return
	 * @throws IOException
	 */
	public void startServer() throws IOException {
		final Map<String, String> initParams = new HashMap<String, String>();

		initParams.put("com.sun.jersey.config.property.packages",
				"com.xmlmachines.resources;com.xmlmachines.providers");

		LOG.debug(MessageFormat.format("Starting grizzly on port {0}",
				cfg.getHostPort()));
		httpServer = GrizzlyWebContainerFactory.create(BASE_URI, initParams);
		httpServer.start();
	}

	/**
	 * Stops the server and nulls the threadSelector
	 * 
	 * @return
	 * @throws IOException
	 */
	public void stopServer() {
		LOG.info("Attempting to stop Grizzly container");
		httpServer.stop();
	}

	/**
	 * Allows the Container to be restarted.
	 * 
	 * TODO - Write unit tests for this functionality!
	 * 
	 * @return
	 * @throws IOException
	 */
	public void restartServer() throws IOException {
		LOG.info("Restarting Grizzly container");
		stopServer();
		startServer();
	}

}
