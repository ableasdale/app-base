package com.xmlmachines;

import java.io.IOException;

import com.marklogic.xcc.exceptions.RequestException;

public class TestStartup {

	public static void main(String[] args) throws IOException, RequestException {
		com.xmlmachines.providers.GrizzlyContainerProvider.getInstance()
				.startServer();
		System.in.read();

		/*
		 * Session s = MarkLogicContentSourceProvider.getInstance()
		 * .getProductionContentSource().newSession();
		 * 
		 * s.submitRequest(
		 * s.newAdhocQuery(IOUtilsProvider.getInstance().readFileAsString(
		 * "src/main/resources/xqy/basic-test-setup.xqy"))) .close(); //
		 * Populate TestHelper t = new TestHelper(); try {
		 * t.processMedlineXML(); } catch (ValidityException e) {
		 * 
		 * } catch (ParsingException e) {
		 * 
		 * } catch (IOException e) {
		 * 
		 * }
		 */

	}

}