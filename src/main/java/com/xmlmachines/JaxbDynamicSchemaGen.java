package com.xmlmachines;

import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import com.xmlmachines.beans.jaxb.ExampleBean;

public class JaxbDynamicSchemaGen {
	public static void main(String[] args) throws JAXBException {

		JAXBContext context = JAXBContext.newInstance(ExampleBean.class);
		try {
			context.generateSchema(new JerseySchemaOutputResolver());
			System.out.println("schema should be created");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}