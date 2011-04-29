package com.xmlmachines;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

import org.apache.log4j.Logger;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class TikaDemo {

	private static final Logger LOG = Logger.getLogger(TikaDemo.class);

	private static String fileName = "src/main/resources/pdfs/postpn228.pdf";

	public static void main(String[] args) throws IOException, SAXException,
			TikaException {

		InputStream input = new FileInputStream(new File(fileName));
		ContentHandler textHandler = new BodyContentHandler();
		Metadata metadata = new Metadata();
		PDFParser parser = new PDFParser();
		parser.parse(input, textHandler, metadata, new ParseContext());
		input.close();

		StringBuilder sb = new StringBuilder();
		appendRootStartElement(sb);
		for (String n : metadata.names()) {
			sb.append(constructNode(n, metadata.get(n)));
		}
		sb.append(constructNode("content", textHandler.toString()));

		appendRootEndElement(sb);
		LOG.info(sb.toString());
	}

	private static StringBuilder appendRootStartElement(StringBuilder sb) {
		return sb
				.append("<pdf xmlns:xmpTPg=\"http://ns.adobe.com/xap/1.0/t/pg/\">\n");
	}

	private static StringBuilder appendRootEndElement(StringBuilder sb) {
		return sb.append("</pdf>");
	}

	private static String constructNode(String nodeName, String nodeValue) {
		return MessageFormat.format("<{0}>{1}</{2}>\n", nodeName, nodeValue,
				nodeName);
	}
}