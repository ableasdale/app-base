import java.io.IOException;

import nu.xom.ParsingException;
import nu.xom.ValidityException;

import org.junit.Before;
import org.junit.Test;

import com.xmlmachines.TestHelper;
import com.xmlmachines.providers.MarkLogicContentSourceProvider;

public class TestCI {

	// private ContentSource cs;

	@Before
	public void setUp() {

	}

	@Test
	public void testFirstSpike() throws ValidityException, ParsingException,
			IOException {
		TestHelper t = new TestHelper(MarkLogicContentSourceProvider
				.getInstance().getTestContentSource());
		t.processMedlineXML();
	}
}
