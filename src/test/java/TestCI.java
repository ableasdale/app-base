import java.io.IOException;

import junit.framework.Assert;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.marklogic.xcc.Request;
import com.marklogic.xcc.ResultSequence;
import com.marklogic.xcc.Session;
import com.marklogic.xcc.exceptions.RequestException;
import com.xmlmachines.Consts;
import com.xmlmachines.TestHelper;
import com.xmlmachines.providers.IOUtilsProvider;
import com.xmlmachines.providers.MarkLogicContentSourceProvider;

public class TestCI {

	private final Logger LOG = Logger.getLogger(TestCI.class);

	@Before
	public void setUp() throws RequestException {
		// LOG.info("creating ML database for tests");
		Session s = MarkLogicContentSourceProvider.getInstance()
				.getProductionContentSource().newSession();

		s.submitRequest(
				s.newAdhocQuery(IOUtilsProvider.getInstance().readFileAsString(
						"src/main/resources/xqy/basic-test-setup.xqy")))
				.close();
		// Populate
		TestHelper t = new TestHelper();
		try {
			t.processMedlineXML();
		} catch (ValidityException e) {
			LOG.error(e);
		} catch (ParsingException e) {
			LOG.error(e);
		} catch (IOException e) {
			LOG.error(e);
		}

	}

	@Test
	public void wasIngestCompleted() throws RequestException {
		Session s = MarkLogicContentSourceProvider.getInstance()
				.getProductionContentSource().newSession(Consts.UNIT_TEST_DB);
		Request r = s.newAdhocQuery("xdmp:estimate(doc())");
		ResultSequence rs = s.submitRequest(r);
		Assert.assertEquals("156", rs.asString());
		s.close();
	}

	@Test
	public void isFirstDocumentElemOfExpectedType() throws RequestException {
		Session s = MarkLogicContentSourceProvider.getInstance()
				.getProductionContentSource().newSession(Consts.UNIT_TEST_DB);
		Request r = s
				.newAdhocQuery("fn:name(doc()[1]/element()) eq 'MedlineCitation'");
		ResultSequence rs = s.submitRequest(r);
		Assert.assertEquals("true", rs.asString());
		s.close();
	}

	@After
	public void tearDown() throws RequestException {
		Session s = MarkLogicContentSourceProvider.getInstance()
				.getProductionContentSource().newSession();

		s.submitRequest(
				s.newAdhocQuery(IOUtilsProvider.getInstance().readFileAsString(
						"src/main/resources/xqy/basic-test-teardown.xqy")))
				.close();

	}

}
