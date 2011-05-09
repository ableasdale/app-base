package com.xmlmachines.resolvers;

import javax.ws.rs.ext.ContextResolver;
import javax.xml.bind.JAXBContext;

import com.xmlmachines.beans.jaxb.ExampleBean;

/**
 * NOT USED RIGHT NOW!
 * 
 * @author 35430Al
 * 
 */
// @Provider
public class JAXBContextResolver implements ContextResolver<JAXBContext> {

	private final JAXBContext context;
	// TODO - create this dynamically - go through the com.marklogic.beans
	// folder for example
	private final Class[] types = { ExampleBean.class };

	public JAXBContextResolver() throws Exception {
		// this.context = new
		// JSONJAXBContext(JSONConfiguration.natural().build(),
		// types);
		this.context = JAXBContext.newInstance(types);
	}

	@Override
	public JAXBContext getContext(Class<?> objectType) {
		for (Class type : types) {
			if (type == objectType) {
				return context;
			}
		}

		return context;
	}

	// public JAXBContext getContext(final Class<?> ignored) {
	// JAXBContext returnValue = null;
	// final Set<Class<?>> types = this.types.get();
	// if (types != null && !types.isEmpty()) {
	// try {
	// returnValue = JAXBContext.newInstance(types
	// .toArray(new Class<?>[types.size()]));
	// } catch (final JAXBException jaxbException) {
	// // Here's where you'd log the error. You WILL log the error,
	// // right?
	// } finally {
	// this.types.remove();
	// }
	// }
	// return returnValue;
	// }

}