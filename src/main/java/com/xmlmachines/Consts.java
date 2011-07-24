package com.xmlmachines;

import java.text.MessageFormat;

/**
 * Constant values held in one class for reference
 * 
 * @author Alex Bleasdale
 * 
 */
public class Consts {

	/**
	 * Constants to denote the server environment (Production or Test)
	 */
	public enum Env {
		PROD, TEST
	}

	public static final String UNIT_TEST_DB = "AppBaseTests";
	public static final String XML_PI = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
	public static final String SCHEMATRON_TRANSFORMER = "resources/schematron/iso_svrl_for_xslt2.xsl";
	public static final String XML_SCHEMA_URI = "http://www.w3.org/2001/XMLSchema";

	public static final String MARKLOGIC_XQUERY_DECL = "xquery version \"1.0-ml\" encoding \"utf-8\";\n";
	public static final String[] CALABASH_TEST_ARGS = {
			"-oresult=resources/output.calabash", "resources/xpl/pipe.xpl" };

	/**
	 * TODO - should this go elsewhere?.
	 * 
	 * @param e
	 *            the e
	 * @return the string
	 * @return
	 */
	public static String returnExceptionString(Exception e) {
		return MessageFormat.format("{0} caught: {1}", e.getClass().getName(),
				e);
	}
}