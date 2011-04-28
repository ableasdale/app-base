package com.xmlmachines.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.junit.Test;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class MixedDocumentParserTest {

	private static final Logger LOG = Logger
			.getLogger(MixedDocumentParserTest.class);

	private final Parser parser = new AutoDetectParser();

	@Test
	public void testAutoDetectPDF() throws Exception {
		Metadata m = parseResource(
				"src/test/resources/unit-test-content/test-doc.pdf", parser);
		Assert.assertEquals("application/pdf", m.get("Content-Type"));
	}

	@Test
	public void testAutoDetectExif() throws Exception {
		Metadata m = parseResource(
				"src/test/resources/unit-test-content/jpeg.jpg", parser);
		Assert.assertEquals("image/jpeg", m.get("Content-Type"));
	}

	private Metadata parseResource(String resourceLocation, Parser parser)
			throws IOException, SAXException, TikaException {

		// InputStream input = this.getClass().getClassLoader()
		// .getResourceAsStream(resourceLocation);
		InputStream input = new FileInputStream(new File(resourceLocation));
		ContentHandler textHandler = new BodyContentHandler();
		Metadata metadata = new Metadata();
		parser.parse(input, textHandler, metadata, new ParseContext());
		input.close();
		return metadata;
	}
}
