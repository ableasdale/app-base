package com.xmlmachines;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.xml.sax.InputSource;

import com.sun.codemodel.JCodeModel;
import com.sun.tools.xjc.api.S2JJAXBModel;
import com.sun.tools.xjc.api.SchemaCompiler;
import com.sun.tools.xjc.api.XJC;

public class JaxbDynamicClassGen {
	public static void main(String[] args) throws IOException {
		// Configure sources & output
		String schemaPath = "src/main/resources/schema2.xsd";
		String outputDirectory = "src/main/resources/generated";

		// Setup schema compiler
		SchemaCompiler sc = XJC.createSchemaCompiler();
		sc.forcePackageName("com.xmlmachines.schema.generated");

		// Setup SAX InputSource
		File schemaFile = new File(schemaPath);
		InputSource is = new InputSource(new FileInputStream(schemaFile));
		// is.setSystemId(schemaFile.getAbsolutePath());
		is.setSystemId(schemaFile.getAbsolutePath().replace('\\', '/'));

		// Parse & build
		sc.parseSchema(is);
		S2JJAXBModel model = sc.bind();
		JCodeModel jCodeModel = model.generateCode(null, null);
		jCodeModel.build(new File(outputDirectory));

	}
}