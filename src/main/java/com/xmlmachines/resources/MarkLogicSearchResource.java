package com.xmlmachines.resources;

import java.text.MessageFormat;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.marklogic.xcc.Request;
import com.marklogic.xcc.ResultItem;
import com.marklogic.xcc.ResultSequence;
import com.marklogic.xcc.Session;
import com.marklogic.xcc.exceptions.RequestException;
import com.marklogic.xcc.types.XSString;
import com.marklogic.xcc.types.XdmItem;

@Path("/search")
public class MarkLogicSearchResource {

	private final Logger LOG = Logger.getLogger(MarkLogicSearchResource.class);

	@Context
	Session session;

	@GET
	@Produces("application/xml")
	public Response doSearch() throws RequestException {

		String strValue = "";
		Request request = session.newAdhocQuery("\"nanotechnology\"");
		ResultSequence rs = session.submitRequest(request);

		while (rs.hasNext()) {
			ResultItem rsItem = rs.next();
			XdmItem item = rsItem.getItem();

			if (item instanceof XSString) {
				strValue = item.asString();
				LOG.info(strValue);
			}
		}

		session.close();

		return Response.ok(
				MessageFormat.format("<response>{0}</response>", strValue))
				.build();
	}

}
