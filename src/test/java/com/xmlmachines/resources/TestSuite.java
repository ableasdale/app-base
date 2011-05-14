package com.xmlmachines.resources;

import java.io.IOException;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

//@RunWith(JUnit4ClassRunner.class)
@RunWith(Suite.class)
@SuiteClasses({ XmlRestRouterTest.class, MarkLogicSearchResourceTest.class,
		JaxbMarshallTest.class })
public class TestSuite {

	// @BeforeClass
	public static void setUp() throws IOException {
		com.xmlmachines.providers.GrizzlyContainerProvider.getInstance()
				.startServer();
	}

	// @AfterClass
	public static void tearDown() {
		com.xmlmachines.providers.GrizzlyContainerProvider.getInstance()
				.stopServer();
	}

}