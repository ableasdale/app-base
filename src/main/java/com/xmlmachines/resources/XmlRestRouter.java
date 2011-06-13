package com.xmlmachines.resources;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.marklogic.xcc.exceptions.RequestException;
import com.xmlmachines.providers.IOUtilsProvider;

/**
 * Basic XML response resource pattern
 * 
 * @author Alex Bleasdale
 */
@Path("/xml")
public class XmlRestRouter {

	@GET
	@Produces("text/xml;charset=UTF-8")
	public Response doGet() throws RequestException {
		StringBuilder sb = IOUtilsProvider.getInstance().getXMLStringBuilder();
		sb.append("<xml>get</xml>");
		return IOUtilsProvider.getInstance().wrapResponseString(sb.toString());
	}

	@POST
	@Produces("text/xml;charset=UTF-8")
	public Response doPost() throws RequestException {
		StringBuilder sb = IOUtilsProvider.getInstance().getXMLStringBuilder();
		sb.append("<xml>post</xml>");
		return IOUtilsProvider.getInstance().wrapResponseString(sb.toString());
	}

	@PUT
	@Produces("text/xml;charset=UTF-8")
	public Response doPut() throws RequestException {
		StringBuilder sb = IOUtilsProvider.getInstance().getXMLStringBuilder();
		sb.append("<xml>put</xml>");
		return IOUtilsProvider.getInstance().wrapResponseString(sb.toString());
	}

	@DELETE
	@Produces("text/xml;charset=UTF-8")
	public Response doDelete() throws RequestException {
		StringBuilder sb = IOUtilsProvider.getInstance().getXMLStringBuilder();
		sb.append("<xml>delete</xml>");
		return IOUtilsProvider.getInstance().wrapResponseString(sb.toString());
	}
}