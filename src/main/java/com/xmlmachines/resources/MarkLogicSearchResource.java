package com.xmlmachines.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.marklogic.xcc.Request;
import com.marklogic.xcc.ResultSequence;
import com.marklogic.xcc.Session;
import com.marklogic.xcc.exceptions.RequestException;
import com.xmlmachines.providers.IOUtilsProvider;

@Path("/search/{term}")
public class MarkLogicSearchResource {

	private final Logger LOG = Logger.getLogger(MarkLogicSearchResource.class);

	@Context
	Session session;

	@GET
	@Produces("application/xml")
	public Response doSearch(@PathParam("term") String term)
			throws RequestException {
		LOG.info("Creating an ad-hoc search with the term " + term);
		LOG.info("TODO - this Module is being loaded and evaluated each request - this *should* be stored");
		StringBuilder strValue = new StringBuilder();
		Request request = session.newAdhocQuery(IOUtilsProvider.getInstance()
				.readFileAsString("src/main/resources/xqy/basic-search.xqy"));
		request.setNewStringVariable("term", term);
		ResultSequence rs = session.submitRequest(request);

		strValue.append(rs.asString());
		/*
		 * while (rs.hasNext()) { strValue.append(rs.asString()); rs.next(); }
		 */

		session.close();

		return Response.ok(strValue.toString()).build();
	}
}
