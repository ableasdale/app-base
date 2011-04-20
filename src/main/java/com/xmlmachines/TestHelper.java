package com.xmlmachines;

import java.io.File;
import java.io.IOException;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

public class TestHelper {

	public void processMedlineXML() throws ValidityException, ParsingException,
			IOException {
		Builder builder = new Builder();
		Document doc = builder.build(new File(
				"src/main/resources/medsamp2011.xml"));
		// System.out.println(doc.toXML());
		processDocumentRoot(doc.getRootElement());
	}

	private void processDocumentRoot(Element root) {
		Elements elems = root.getChildElements("MedlineCitation");
		for (int i = 0; i < elems.size(); i++) {
			StringBuilder sb = new StringBuilder();
			// System.out.println(elems.get(0).toXML());
			Elements tmp = elems.get(i).getChildElements("PMID");
			sb.append("/content/").append(i).append("-")
					.append(tmp.get(0).getValue()).append(".xml");
			adHocQueryTmpl(sb.toString(), elems.get(i).toXML());
		}
	}

	private String adHocQueryTmpl(String uri, String XML) {
		StringBuilder sb = new StringBuilder();
		sb.append(Consts.MARKLOGIC_XQUERY_DECL);
		sb.append("declare variable $element as xs:string := ").append(XML)
				.append(";\n");
		sb.append("return\n");
		sb.append("xdmp:document-insert(\"").append(uri).append("\", $xml)");
		// System.out.println(sb.toString());
		return sb.toString();
	}
}
