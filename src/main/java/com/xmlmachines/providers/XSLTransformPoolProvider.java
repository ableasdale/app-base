package com.xmlmachines.providers;

import nux.xom.pool.PoolConfig;
import nux.xom.pool.XSLTransformPool;

import org.apache.log4j.Logger;

/**
 * Creates the XSLTransformPool for threaded transforms
 * 
 * As this is a singleton, use getInstance() to access the class
 * 
 * @author Alex Bleasdale
 * 
 */
public class XSLTransformPoolProvider {

	private static final Logger LOG = Logger
			.getLogger(XSLTransformPoolProvider.class);

	private final XSLTransformPool xtp;

	private XSLTransformPoolProvider() {
		LOG.info("Creating the XSLTransformPoolProvider Object [Singleton]");

		/**
		 * TODO - at present default pool config values are used
		 */

		xtp = new XSLTransformPool(new PoolConfig(), Saxon9FactoryProvider
				.getInstance().getFactory());

	}

	private static class XSLTransformPoolProviderHolder {
		private final static XSLTransformPoolProvider INSTANCE = new XSLTransformPoolProvider();
	}

	public static XSLTransformPoolProvider getInstance() {
		return XSLTransformPoolProviderHolder.INSTANCE;
	}

	public XSLTransformPool getPool() {
		return xtp;
	}

}