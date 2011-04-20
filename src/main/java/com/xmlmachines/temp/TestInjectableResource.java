package com.xmlmachines.temp;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.Locale;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import nu.xom.Document;
import nu.xom.Nodes;
import nu.xom.xslt.XSLException;
import nu.xom.xslt.XSLTransform;

import org.apache.log4j.Logger;

import com.marklogic.xcc.Request;
import com.marklogic.xcc.ResultItem;
import com.marklogic.xcc.ResultSequence;
import com.marklogic.xcc.Session;
import com.marklogic.xcc.exceptions.RequestException;
import com.marklogic.xcc.types.XSString;
import com.marklogic.xcc.types.XdmItem;
import com.xmlmachines.Consts;

@Path("/test")
public class TestInjectableResource {

	private final Logger LOG = Logger.getLogger(TestInjectableResource.class);

	@Context
	Timestamp timestamp;

	@Context
	Locale locale;

	@Context
	XSLTransform xsltransform;

	@Context
	Session session;

	@Context
	Document document;

	@GET
	@Produces("application/xml")
	public Response getInjectableTimestamp() throws RequestException {
		// return theTimestamp(Timestamp);
		LOG.info(timestamp);
		LOG.info(locale);
		LOG.info(session);
		LOG.info(xsltransform);
		LOG.info(document);

		String strValue = "";
		Request request = session.newAdhocQuery("\"ml-rx\"");
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

		Nodes nodes0 = null;

		try {
			nodes0 = xsltransform.transform(document);
		} catch (XSLException e) {
			LOG.error(Consts.returnExceptionString(e));
		}

		for (int i = 0; i < nodes0.size(); i++) {
			LOG.info("node " + i + ": " + nodes0.get(i).toXML());
		}

		return Response.ok(
				MessageFormat.format("<response>{0}</response>", strValue))
				.build();
	}
}