package com.xmlmachines;

import static java.lang.System.out;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class TikaDemo {

	public static void main(String[] args) throws IOException, SAXException,
			TikaException {
		// String fname = "C:/test-doc.pdf";
		String fname = "src/main/resources/pdfs/postpn227.pdf";
		InputStream input = new FileInputStream(new File(fname));
		ContentHandler textHandler = new BodyContentHandler();
		Metadata metadata = new Metadata();
		PDFParser parser = new PDFParser();
		// parser.
		parser.parse(input, textHandler, metadata);
		input.close();

		for (String n : metadata.names()) {
			out.println("element {\"" + n + "\"} {" + metadata.get(n) + "}");
		}
		// args.out.println("Title: " + metadata.get("title"));
		// out.println("Author: " + metadata.get("Author"));
		// out.println("content: " + textHandler.toString());

	}
}
