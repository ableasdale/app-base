package com.xmlmachines.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.marklogic.xcc.exceptions.RequestException;

@Path("/")
public class BaseResource {

	private final Logger LOG = Logger.getLogger(BaseResource.class);

	@GET
	@Produces("application/xml")
	public Response getBase() throws RequestException {

		return Response.ok("<status>active</status>").build();
	}

}
