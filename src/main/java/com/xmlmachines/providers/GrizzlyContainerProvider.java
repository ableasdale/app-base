package com.xmlmachines.providers;

import java.io.IOException;
import java.net.URI;
import java.text.MessageFormat;

import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import org.apache.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
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
		BASE_URI = getBaseURI();
		/*Map<String, String> initParams = new HashMap<String, String>();
		initParams.put("com.sun.jersey.config.property.packages",
				"com.xmlmachines.resources;com.xmlmachines.providers"); */

		try {
			LOG.info(MessageFormat.format("Starting grizzly on port {0}",
					cfg.getHostPort()));
            ResourceConfig rc = new PackagesResourceConfig("com.xmlmachines.resources","com.xmlmachines.providers");
            httpServer = GrizzlyServerFactory
					.createHttpServer(BASE_URI, (ResourceConfig) rc);
		} catch (IOException e) {
			LOG.error(e);
		}
		LOG.info(MessageFormat.format(
				"Server Info - ServerName: {0} - Version: {1}", httpServer
						.getServerConfiguration().getHttpServerName(),
				httpServer.getServerConfiguration().getHttpServerVersion()));
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
		if (httpServer.isStarted()) {
			// restartServer();
			LOG.info("Server has been started and is available on "
					+ cfg.getHostPort());
		} else {
			httpServer.start();
		}
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
