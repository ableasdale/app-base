package com.xmlmachines.providers;

import java.lang.reflect.Type;
import java.text.MessageFormat;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.Context;

import org.apache.log4j.Logger;

import com.sun.jersey.core.spi.component.ComponentContext;
import com.sun.jersey.core.spi.component.ComponentScope;
import com.sun.jersey.server.impl.inject.AbstractHttpContextInjectable;
import com.sun.jersey.spi.inject.Injectable;
import com.sun.jersey.spi.inject.InjectableProvider;

/**
 * The Class AbstractInjectableProvider.
 * 
 * See <a href=
 * "http://codahale.com/what-makes-jersey-interesting-injection-providers/">this
 * article for more information</a>
 * 
 * @param <E>
 *            the element type
 */
public abstract class AbstractInjectableProvider<E> extends
		AbstractHttpContextInjectable<E> implements
		InjectableProvider<Context, Type> {

	private static final Logger LOG = Logger
			.getLogger(AbstractInjectableProvider.class);

	/** The t. */
	private final Type t;

	/**
	 * Instantiates a new abstract injectable provider.
	 * 
	 * @param t
	 *            the t
	 */
	public AbstractInjectableProvider(Type t) {
		this.t = t;
	}

	/**
	 * PostConstruct Actions
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@PostConstruct
	public void init() throws Exception {
		LOG.debug(MessageFormat.format(
				"In the init PostConstruct annotation {0}", t.toString()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sun.jersey.spi.inject.InjectableProvider#getInjectable(com.sun.jersey
	 * .core.spi.component.ComponentContext, java.lang.annotation.Annotation,
	 * java.lang.Object)
	 */
	@Override
	public Injectable<E> getInjectable(ComponentContext ic, Context a, Type c) {
		if (c.equals(t)) {
			return getInjectable(ic, a);
		}

		return null;
	}

	/**
	 * Gets the injectable.
	 * 
	 * @param ic
	 *            the ic
	 * @param a
	 *            the a
	 * @return the injectable
	 */
	public Injectable<E> getInjectable(ComponentContext ic, Context a) {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.jersey.spi.inject.InjectableProvider#getScope()
	 */
	@Override
	public ComponentScope getScope() {
		return ComponentScope.PerRequest;
	}
}