package com.xmlmachines.providers;

import nux.xom.pool.XSLTransformFactory;

import org.apache.log4j.Logger;

/**
 * Base provider Class for the Saxon 9 TransformerFactory
 * 
 * As this is a singleton, use getInstance() to access the class
 * 
 * @author Alex Bleasdale
 * 
 */
public class Saxon9FactoryProvider {

	private static final Logger LOG = Logger
			.getLogger(Saxon9FactoryProvider.class);

	private final XSLTransformFactory factory;

	private Saxon9FactoryProvider() {
		LOG.info("Creating the XSLTransformFactory Object [Singleton]");
		factory = new XSLTransformFactory() {
			@Override
			protected String[] getPreferredTransformerFactories() {
				return new String[] { "net.sf.saxon.TransformerFactoryImpl",
						"org.apache.xalan.processor.TransformerFactoryImpl" };
			}
		};
	}

	private static class Saxon9FactoryProviderHolder {
		private final static Saxon9FactoryProvider INSTANCE = new Saxon9FactoryProvider();
	}

	public static Saxon9FactoryProvider getInstance() {
		return Saxon9FactoryProviderHolder.INSTANCE;
	}

	public XSLTransformFactory getFactory() {
		return factory;
	}

}