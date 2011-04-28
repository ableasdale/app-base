package com.xmlmachines.providers;

import java.sql.Timestamp;

import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

import com.sun.jersey.api.core.HttpContext;

@Provider
public class TimestampProvider extends AbstractInjectableProvider<Timestamp> {

	private static final Logger LOG = Logger.getLogger(TimestampProvider.class);

	public TimestampProvider() {
		super(Timestamp.class);
		LOG.info("TimestampProvider");
	}

	@Override
	public Timestamp getValue(HttpContext c) {
		LOG.info("Injecting timestamp based on system time.");
		return new Timestamp(System.currentTimeMillis());
	}

}