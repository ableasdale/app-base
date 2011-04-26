package com.xmlmachines;

import java.io.IOException;

public class TestStartup {

	public static void main(String[] args) throws IOException {
		com.xmlmachines.providers.GrizzlyContainerProvider.getInstance()
				.startServer();
	}

}