package com.xmlmachines;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

import org.apache.log4j.Logger;

import com.xmlmachines.providers.MarkLogicContentSourceProvider;

public class TestHelper {

	private final Logger LOG = Logger.getLogger(TestHelper.class);
	private final String RESOURCE_XML_FILE = "src/main/resources/medsamp2011.xml";

	// private final ContentSource cs;

	/*
	 * public TestHelper(ContentSource markLogicContentSource) { this.cs =
	 * markLogicContentSource; }
	 */

	public void processMedlineXML(String mode) throws ValidityException,
			ParsingException, IOException {
		if ("xom".equals(mode)) {
			// LOG.info("processing medline...");
			Builder builder = new Builder();
			Document doc = builder.build(new File(RESOURCE_XML_FILE));
			// LOG.info("got doc...");
			// System.out.println(doc.toXML());
			processDocumentRoot(doc.getRootElement());
		} else if ("vtd-xml".equals(mode)) {
			// TODO - complete this method
		} else {
			LOG.error("Missing Parameter : you need to specify a mode for the processor to use (xom | vtd-xml)");
		}

	}

	/**
	 * The vtd-xml mode
	 * 
	 */

	/**
	 * The XOM mode
	 * 
	 * @param root
	 */
	private void processDocumentRoot(Element root) {
		Elements elems = root.getChildElements("MedlineCitation");
		LOG.info("Got " + elems.size() + " items for insert");

		BlockingQueue<XccProcess> queue = new ArrayBlockingQueue<XccProcess>(
				elems.size());

		for (int i = 0; i < elems.size(); i++) {
			StringBuilder sb = new StringBuilder();
			// System.out.println(elems.get(0).toXML());
			Elements tmp = elems.get(i).getChildElements("PMID");
			sb.append("/content/").append(i).append("-")
					.append(tmp.get(0).getValue()).append(".xml");
			// LOG.info(sb.toString());
			// LOG.debug(adHocQueryTmpl(sb.toString(), elems.get(i).toXML()));

			// TODO - hardcoded database path!! BAD!!
			/*
			 * try { queue.put(new XccProcess(adHocQueryTmpl(sb.toString(),
			 * elems.get(i) .toXML()),
			 * MarkLogicContentSourceProvider.getInstance()
			 * .getProductionContentSource().newSession("BasicTests"))); } catch
			 * (InterruptedException e) {
			 * 
			 * e.printStackTrace(); }
			 */
			queue.add(new XccProcess(adHocQueryTmpl(sb.toString(), elems.get(i)
					.toXML()), MarkLogicContentSourceProvider.getInstance()
					.getProductionContentSource().newSession("BasicTests")));

		}
		for (XccProcess xp : queue) {
			xp.run();
		}

	}

	private String adHocQueryTmpl(String uri, String XML) {
		StringBuilder sb = new StringBuilder();
		sb.append(Consts.MARKLOGIC_XQUERY_DECL);
		sb.append("declare variable $xml as element() := ").append(XML)
				.append(";\n");
		sb.append("xdmp:document-insert(\"").append(uri).append("\", $xml)");
		// System.out.println(sb.toString());
		return sb.toString();
	}
}
