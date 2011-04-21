package com.xmlmachines.tests;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.custommonkey.xmlunit.NamespaceContext;
import org.custommonkey.xmlunit.SimpleNamespaceContext;
import org.custommonkey.xmlunit.XMLAssert;
import org.custommonkey.xmlunit.XMLUnit;
import org.custommonkey.xmlunit.XpathEngine;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.marklogic.xcc.ContentSource;
import com.marklogic.xcc.Request;
import com.marklogic.xcc.ResultItem;
import com.marklogic.xcc.ResultSequence;
import com.marklogic.xcc.Session;
import com.marklogic.xcc.types.XSString;
import com.marklogic.xcc.types.XdmElement;
import com.marklogic.xcc.types.XdmItem;
import com.xmlmachines.Consts.Env;
import com.xmlmachines.providers.IOUtilsProvider;
import com.xmlmachines.providers.MarkLogicContentSourceProvider;

public class MarkLogicConnectionTest {

	private static ContentSource contentSource;
	/*
	 * private final static Logger LOG = Logger
	 * .getLogger(MarkLogicConnectionTest.class);
	 */
	// private final File buildFile = new File("testng-build.xml");

	private static HashMap<String, String> m;
	private static XpathEngine engine;
	private static NamespaceContext ctx;
	private static Document d;

	// private Project p;

	/*
	 * TODO - create a real setup for this unit test, upload the following files
	 * and then tear down - test/test.xqy
	 */

	// @BeforeSuite(alwaysRun = true)
	@BeforeClass
	public static void setUp() throws Exception {
		/*
		 * LOG.info(
		 * "*** Start of Suite *** - Setting Up MarkLogic for all unit tests");
		 * p = new Project(); p.setUserProperty("ant.file",
		 * buildFile.getAbsolutePath()); p.init(); ProjectHelper helper =
		 * ProjectHelper.getProjectHelper(); p.addReference("ant.projectHelper",
		 * helper); helper.parse(p, buildFile); p.executeTarget("setup");
		 */
		XMLUnit.setControlParser("org.apache.xerces.jaxp.DocumentBuilderFactoryImpl");
		XMLUnit.setTestParser("org.apache.xerces.jaxp.DocumentBuilderFactoryImpl");
		XMLUnit.setSAXParserFactory("org.apache.xerces.jaxp.SAXParserFactoryImpl");
		XMLUnit.setTransformerFactory("org.apache.xalan.processor.TransformerFactoryImpl");
		XMLUnit.setIgnoreWhitespace(true);

		// TODO - set this up so the TEST params can be used
		contentSource = MarkLogicContentSourceProvider.getInstance()
				.getContentSource(Env.PROD);

		m = new HashMap<String, String>();
		m.put("search", "http://marklogic.com/appservices/search");
		ctx = new SimpleNamespaceContext(m);
		engine = XMLUnit.newXpathEngine();
		engine.setNamespaceContext(ctx);

	}

	@Test
	public void testAdHocQuery() throws Exception {
		Session session = contentSource.newSession("Documents");
		Request request = session.newAdhocQuery("\"TestNG\"");
		ResultSequence rs = session.submitRequest(request);

		while (rs.hasNext()) {
			ResultItem rsItem = rs.next();
			XdmItem item = rsItem.getItem();

			if (item instanceof XSString) {
				String strValue = item.asString();
				Assert.assertEquals(strValue, "TestNG");
			}
		}

		session.close();
	}

	@Test
	public void testMLSpecificAdHocQuery() throws Exception {
		Session session = contentSource.newSession("Documents");
		Request request = session.newAdhocQuery(IOUtilsProvider.getInstance()
				.getBasicMLTestQuery());
		ResultSequence rs = session.submitRequest(request);

		while (rs.hasNext()) {
			ResultItem rsItem = rs.next();
			XdmItem item = rsItem.getItem();

			if (item instanceof XSString) {
				String strValue = item.asString();
				Assert.assertEquals(strValue, "a bc");
			}
		}

		session.close();

	}

	@Test
	public void testModuleStoredOnServer() throws Exception {
		Session session = contentSource.newSession("Documents");
		Request request = session.newModuleInvoke("/search.xquery");
		ResultSequence rs = session.submitRequest(request);
		while (rs.hasNext()) {
			ResultItem rsItem = rs.next();
			XdmItem item = rsItem.getItem();
			if (item instanceof XdmElement) {
				// System.out.println(rsItem.getItem().asString());
				d = XMLUnit.buildControlDocument(rsItem.getItem().asString());

				NodeList l = engine.getMatchingNodes("//search:qtext", d);
				assertEquals(1, l.getLength());
				assertEquals("search:qtext", l.item(0).getNodeName());
				assertEquals(Node.ELEMENT_NODE, l.item(0).getNodeType());
				// examine searched value
				assertEquals("heart", engine.evaluate("//search:qtext", d));
				XMLAssert.assertXpathExists("//*[local-name()=\"qtext\"]",
						rsItem.getItem().asString());
				// XMLAssert.assertXpathEvaluatesTo("heart",
				// "//search:qtext/text()", d);

				/*
				 * XMLAssert.assertXpathEvaluatesTo("heart", "/search:qtext",
				 * rsItem.getItem().asString());
				 */
			}
			rs.next();
		}
		/*
		 * while (rs.hasNext()) { ResultItem rsItem = rs.next(); XdmItem item =
		 * rsItem.getItem();
		 * 
		 * if (item instanceof XdmElement) { String strValue = item.asString();
		 * XMLAssert.assertXpathEvaluatesTo("15", "//max", strValue);
		 * XMLAssert.assertXpathEvaluatesTo("3", "//min", strValue); } }
		 */

		session.close();
	}

	@Test
	public void testLocallyStoredModuleOnFilesystem() throws Exception {
		String filePath = "src/test/resources/xqy/test2.xqy";
		Session session = contentSource.newSession("Documents");
		Request request = session.newAdhocQuery(IOUtilsProvider.getInstance()
				.readFileAsString(filePath));
		ResultSequence rs = session.submitRequest(request);
		while (rs.hasNext()) {
			ResultItem rsItem = rs.next();
			XdmItem item = rsItem.getItem();

			if (item instanceof XdmElement) {
				String strValue = item.asString();
				XMLAssert.assertXpathEvaluatesTo("2000", "//max", strValue);
				XMLAssert.assertXpathEvaluatesTo("2", "//min", strValue);
			}
		}

		session.close();

	}

	// @Test - to delete
	public void testXmlUnitCapability() throws Exception {
		String myControlXML = "<msg><uuid>0x00435A8C</uuid></msg>";
		String myTestXML = "<msg><localId>2376</localId></msg>";
		XMLAssert.assertXMLEqual("comparing test xml to control xml",
				myControlXML, myTestXML);
		XMLAssert.assertXMLNotEqual("test xml not similar to control xml",
				myControlXML, myTestXML);

	}

	/*
	 * // @AfterSuite(alwaysRun = true)
	 * 
	 * @AfterClass public void endsuite() throws Exception {
	 * LOG.info("*** End of Suite *** - Tearing down all unit tests");
	 * p.executeTarget("teardown"); }
	 */

}