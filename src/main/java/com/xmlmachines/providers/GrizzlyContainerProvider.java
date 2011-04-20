package com.xmlmachines.providers;

import java.io.IOException;
import java.net.URI;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.UriBuilder;

import org.apache.log4j.Logger;

import com.sun.grizzly.http.SelectorThread;
import com.sun.jersey.api.container.grizzly.GrizzlyWebContainerFactory;
import com.xmlmachines.beans.ApplicationServerConfig;

public class GrizzlyContainerProvider {

	private SelectorThread threadSelector;
	private final ApplicationServerConfig cfg;
	private final int port;
	private final URI BASE_URI;

	private static final Logger LOG = Logger
			.getLogger(GrizzlyContainerProvider.class);

	private GrizzlyContainerProvider() {
		LOG.info("Creating the GrizzlyContainerProvider instance");
		cfg = ConfigurationProvider.getInstance().getApplicationServerConfig();
		port = Integer.parseInt(cfg.getHostPort());
		threadSelector = null;
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
	 * Initialises the SelectorThread and creates a ContainerFactory
	 * 
	 * @return
	 * @throws IOException
	 */
	public SelectorThread startServer() throws IOException {
        final Map<String, String> initParams = new HashMap<String, String>();

        initParams.put("com.sun.jersey.config.property.packages",
                "com.xmlmachines.resources;com.xmlmachines.providers");

        LOG.debug(MessageFormat.format("Starting grizzly on port {0}", cfg
                .getHostPort()));
        threadSelector = GrizzlyWebContainerFactory
                .create(BASE_URI, initParams);
        return threadSelector;
    }

	/**
	 * Stops the server and nulls the threadSelector
	 * 
	 * @return
	 * @throws IOException
	 */
	public void stopServer() {
		LOG.info("Attempting to stop Grizzly container");
		if (threadSelector != null) {
			threadSelector.stopEndpoint();
			LOG.info("Grizzly container stopped successfully");
			threadSelector = null;
		}
	}

	/**
	 * Allows the Container to be restarted.
	 * 
	 * TODO - Write unit tests for this functionality!
	 * 
	 * @return
	 * @throws IOException
	 */
	public SelectorThread restartServer() throws IOException {
		LOG.info("Restarting Grizzly container");
		stopServer();
		return startServer();
	}

}
