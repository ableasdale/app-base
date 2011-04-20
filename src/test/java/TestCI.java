import java.io.IOException;

import nu.xom.ParsingException;
import nu.xom.ValidityException;

import org.junit.Test;

import com.xmlmachines.TestHelper;

public class TestCI {

	@Test
	public void testFirstSpike() throws ValidityException, ParsingException,
			IOException {
		TestHelper t = new TestHelper();
		t.processMedlineXML();
	}
}
