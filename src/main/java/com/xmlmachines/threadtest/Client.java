package com.xmlmachines.threadtest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.filterchain.FilterChainBuilder;
import org.glassfish.grizzly.filterchain.TransportFilter;
import org.glassfish.grizzly.http.HttpClientFilter;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.impl.SafeFutureImpl;
import org.glassfish.grizzly.nio.transport.TCPNIOTransport;
import org.glassfish.grizzly.nio.transport.TCPNIOTransportBuilder;
import org.glassfish.grizzly.utils.DelayedExecutor;
import org.glassfish.grizzly.utils.IdleTimeoutFilter;

import com.xmlmachines.ClientDownloadFilter;

public class Client {
	private static final Logger logger = Grizzly.logger(Client.class);

	public static void main(String[] args) throws IOException,
			URISyntaxException {

		final URI uri = new URI("http://www.google.co.uk");
		final String host = uri.getHost();
		final int port = uri.getPort() > 0 ? uri.getPort() : 80;
		final DelayedExecutor timeoutExecutor = IdleTimeoutFilter
				.createDefaultIdleDelayedExecutor();
		timeoutExecutor.start();
		final FutureImpl<String> completeFuture = SafeFutureImpl.create();
		FilterChainBuilder clientFilterChainBuilder = FilterChainBuilder
				.stateless();
		clientFilterChainBuilder.add(new TransportFilter());
		clientFilterChainBuilder.add(new IdleTimeoutFilter(timeoutExecutor, 10,
				TimeUnit.SECONDS));
		// Add HttpClientFilter, which transforms Buffer <-> HttpContent
		clientFilterChainBuilder.add(new HttpClientFilter());
		// Add HTTP client download filter, which is responsible for downloading
		// HTTP resource asynchronously
		clientFilterChainBuilder.add(new ClientDownloadFilter(uri,
				completeFuture));
		final TCPNIOTransport transport = TCPNIOTransportBuilder.newInstance()
				.build();
		transport.setProcessor(clientFilterChainBuilder.build());

		try {
			transport.start();

			Connection<?> connection = null;

			makeConnection(host, port, completeFuture, transport, connection);
			makeConnection(host, port, completeFuture, transport, connection);
			makeConnection(host, port, completeFuture, transport, connection);
			makeConnection(host, port, completeFuture, transport, connection);

		} finally {
			logger.info("Stopping transport...");
			transport.stop();
			timeoutExecutor.stop();
			logger.info("Stopped transport...");
		}
	}

	/**
	 * This is where all the action happens
	 * 
	 * @param host
	 * @param port
	 * @param completeFuture
	 * @param transport
	 * @param connection
	 * @throws IOException
	 */
	private static void makeConnection(final String host, final int port,
			final FutureImpl<String> completeFuture,
			final TCPNIOTransport transport, Connection<?> connection)
			throws IOException {
		Future<Connection> connectFuture = transport.connect(host, port);
		try {
			connection = connectFuture.get(10, TimeUnit.SECONDS);
			String filename = completeFuture.get();
			logger.log(Level.INFO, "File " + filename
					+ " was successfully downloaded");
		} catch (Exception e) {
			if (connection == null) {
				logger.log(Level.WARNING,
						"Can not connect to the target resource");
			} else {
				logger.log(Level.WARNING, "Error downloading the resource");
			}
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}
}