package com.xmlmachines.providers;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.log4j.Logger;

import com.xmlmachines.Consts;
import com.xmlmachines.beans.ApplicationServerConfig;
import com.xmlmachines.beans.XmlServerConfig;

/**
 * Global Configuration Provider - all config values are stored in an XML
 * Document then added to a Singleton Configuration bean.
 * 
 * As this is a singleton, use getInstance() to access the class
 * 
 * @author Alex Bleasdale
 * 
 */
public class ConfigurationProvider {

	private final Logger LOG = Logger.getLogger(ConfigurationProvider.class);

	/**
	 * TODO - traverse the XML Document creating the Objects on the fly.
	 */

	private XmlServerConfig xmlServerConfig;
	private XmlServerConfig testNgConfig;
	private ApplicationServerConfig applicationServerConfig;

	private ConfigurationProvider() {
		LOG.info("Creating the configuration Object [Singleton]");
		try {
			XMLConfiguration config = new XMLConfiguration(
					"config/db-admin.xml");
			LOG.debug("Loaded Config xml Document");

			/*
			 * Create instance for "production" server
			 */
			xmlServerConfig = new XmlServerConfig();
			xmlServerConfig.setHostName(config
					.getString("production-xmldb.hostname"));
			xmlServerConfig.setHostPort(config
					.getString("production-xmldb.port"));
			xmlServerConfig.setUserName(config
					.getString("production-xmldb.username"));
			xmlServerConfig.setPassword(config
					.getString("production-xmldb.password"));
			xmlServerConfig.setDatabaseName(config
					.getString("production-xmldb.database"));

			/*
			 * Create instance for TestNG server
			 */

			testNgConfig = new XmlServerConfig();
			testNgConfig.setHostName(config.getString("testng-xmldb.hostname"));
			testNgConfig.setHostPort(config.getString("testng-xmldb.port"));
			testNgConfig.setUserName(config.getString("testng-xmldb.username"));
			testNgConfig.setPassword(config.getString("testng-xmldb.password"));
			testNgConfig.setDatabaseName(config
					.getString("testng-xmldb.database"));

			/*
			 * Create instance for Application Server
			 */

			applicationServerConfig = new ApplicationServerConfig();
			applicationServerConfig.setHostName(config
					.getString("application-settings.hostname"));
			applicationServerConfig.setHostPort(config
					.getString("application-settings.port"));
			applicationServerConfig.setResourceFolderPath(config
					.getString("application-settings.resource-folder-path"));

		} catch (ConfigurationException e) {
			LOG.error(Consts.returnExceptionString(e));
		}

	}

	private static class ConfigurationProviderHolder {
		private static final ConfigurationProvider INSTANCE = new ConfigurationProvider();
	}

	public static ConfigurationProvider getInstance() {
		return ConfigurationProviderHolder.INSTANCE;
	}

	/**
	 * Getters
	 */
	public XmlServerConfig getXmlServerConfig() {
		return xmlServerConfig;
	}

	public XmlServerConfig getTestNgConfig() {
		return testNgConfig;
	}

	public ApplicationServerConfig getApplicationServerConfig() {
		return applicationServerConfig;
	}
}