package com.xmlmachines;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

class JerseySchemaOutputResolver extends SchemaOutputResolver {

	File baseDir = new File("src/main/resources");

	@Override
	public Result createOutput(String namespaceUri, String suggestedFileName)
			throws IOException {
		return new StreamResult(new File(baseDir, suggestedFileName));
	}
}