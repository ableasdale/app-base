package com.xmlmachines.providers;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.log4j.Logger;

import com.marklogic.xcc.ContentSource;
import com.marklogic.xcc.ContentSourceFactory;
import com.marklogic.xcc.exceptions.XccConfigException;
import com.xmlmachines.Consts;
import com.xmlmachines.Consts.Env;
import com.xmlmachines.beans.XmlServerConfig;

/**
 * Base Provider Class for a MarkLogic Content Source Factory - a singleton
 * provider which should only be instantiated once; each individual connection
 * to the XML Server should use this ContentSourceFactory
 * 
 * As this is a singleton, use getInstance() to access the class
 * 
 * @author Alex Bleasdale
 * 
 */
public class MarkLogicContentSourceProvider {
	private final Logger LOG = Logger
			.getLogger(MarkLogicContentSourceProvider.class);

	ContentSource productionContentSource;
	ContentSource testNgContentSource;

	private MarkLogicContentSourceProvider() {
		LOG.debug("Creating the MarkLogic ContentSourceFactory provider for both Live and Test configurations");
		XmlServerConfig prod = ConfigurationProvider.getInstance()
				.getXmlServerConfig();

		XmlServerConfig test = ConfigurationProvider.getInstance()
				.getTestNgConfig();

		try {
			URI uri = new URI(generateXdbcConnectionUri(prod));
			productionContentSource = ContentSourceFactory
					.newContentSource(uri);
			URI testUri = new URI(generateXdbcConnectionUri(test));
			productionContentSource = ContentSourceFactory
					.newContentSource(testUri);
		} catch (URISyntaxException e) {
			LOG.error(Consts.returnExceptionString(e));
		} catch (XccConfigException e) {
			LOG.error(Consts.returnExceptionString(e));
		}

	}

	private String generateXdbcConnectionUri(XmlServerConfig cfg) {
		StringBuilder sb = new StringBuilder();
		sb.append("xdbc://").append(cfg.getUserName()).append(":")
				.append(cfg.getPassword()).append("@")
				.append(cfg.getHostName()).append(":")
				.append(cfg.getHostPort());
		LOG.info("Conn: " + sb.toString());
		return sb.toString();
	}

	private static class MarkLogicContentSourceProviderHolder {
		private static final MarkLogicContentSourceProvider INSTANCE = new MarkLogicContentSourceProvider();
	}

	public static MarkLogicContentSourceProvider getInstance() {
		return MarkLogicContentSourceProviderHolder.INSTANCE;
	}

	public ContentSource getContentSource(Env e) {
		switch (e) {
		case PROD:
			return getProductionContentSource();

		case TEST:
			LOG.info("should get here");
			return getTestContentSource();
		}
		// default
		return null;
	}

	public ContentSource getProductionContentSource() {
		return productionContentSource;
	}

	public ContentSource getTestContentSource() {
		return testNgContentSource;
	}

}