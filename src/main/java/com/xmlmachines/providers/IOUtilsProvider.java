package com.xmlmachines.providers;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import javax.ws.rs.core.Response;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import nu.xom.Builder;
import nu.xom.ParsingException;
import nu.xom.Serializer;
import nu.xom.xslt.XSLException;
import nu.xom.xslt.XSLTransform;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import com.xmlmachines.Consts;

/**
 * Tools for common I/O options
 * 
 * As this is a singleton, use getInstance() to access the class
 * 
 * @author Alex Bleasdale
 * 
 *         TODO - break this up so the String providers are all in another class
 */
public class IOUtilsProvider {

	private static final Logger LOG = Logger.getLogger(IOUtilsProvider.class);

	private XSLTransform SCHEMATRON_TRANSFORMER;

	private IOUtilsProvider() {
		LOG.info("Creating the IOUtils instance");
	}

	private static class IOUtilsHolder {
		private static final IOUtilsProvider INSTANCE = new IOUtilsProvider();
	}

	public static IOUtilsProvider getInstance() {
		return IOUtilsHolder.INSTANCE;
	}

	/**
	 * This solution does NOT seem to work. Leaving it in here for reference for
	 * the time being.
	 * 
	 * @return
	 */
	public String readInputFile(String filePath) {
		StringWriter sw = new StringWriter();

		try {
			FileInputStream in = new FileInputStream(filePath);
			FileChannel ch = in.getChannel();
			ByteBuffer buffer = ByteBuffer.allocateDirect(8192);
			Charset cs = Charset.forName("UTF-8");

			while ((ch.read(buffer)) != -1) {
				buffer.rewind();
				CharBuffer chbuf = cs.decode(buffer);
				for (int j = 0; j < chbuf.length(); j++) {
					sw.append(chbuf.get());
				}
				buffer.clear();

			}
		} catch (FileNotFoundException e) {
			LOG.error(Consts.returnExceptionString(e));
		} catch (IOException e) {
			LOG.error(Consts.returnExceptionString(e));
		}
		return sw.toString();
	}

	/**
	 * Takes a filePath and returns the String representation of the file's
	 * contents
	 * 
	 * @param filePath
	 * @return
	 */
	public String readFileAsString(String filePath) {
		byte[] buffer = new byte[(int) new File(filePath).length()];
		try {
			BufferedInputStream f = new BufferedInputStream(
					new FileInputStream(filePath));
			f.read(buffer);
		} catch (FileNotFoundException e) {
			LOG.error(Consts.returnExceptionString(e));
		} catch (IOException e) {
			LOG.error(Consts.returnExceptionString(e));
		}
		return new String(buffer);
	}

	/**
	 * Uses XOM to create a pretty printed XML Document from a String
	 * 
	 * @param xml
	 * @return
	 * @throws ParsingException
	 * @throws IOException
	 */
	public String xomXmlPrettyPrint(String xml) throws ParsingException,
			IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Serializer serializer = new Serializer(out);
		serializer.setIndent(4);
		serializer.write(new Builder().build(xml, ""));
		return out.toString("UTF-8");
	}

	/**
	 * Uses XSLT to pretty print an XML Document to System.out
	 * 
	 * @param doc
	 * @param out
	 * @throws Exception
	 */
	public void xsltXmlPrettyPrinter(Document doc, OutputStream out)
			throws Exception {

		TransformerFactory tfactory = TransformerFactory.newInstance();
		Transformer serializer;
		try {
			serializer = tfactory.newTransformer();
			// Setup indenting to "pretty print"
			serializer.setOutputProperty(OutputKeys.INDENT, "yes");
			serializer.setOutputProperty(
					"{http://xml.apache.org/xslt}indent-amount", "2");

			serializer.transform(new DOMSource(doc), new StreamResult(out));
		} catch (TransformerException e) {
			// this is fatal, just dump the stack and throw a runtime exception
			e.printStackTrace();

			throw new RuntimeException(e);
		}
	}

	public XSLTransform getSchematronTransformer() {
		if (SCHEMATRON_TRANSFORMER == null) {
			try {
				SCHEMATRON_TRANSFORMER = XSLTransformPoolProvider.getInstance()
						.getPool()
						.getTransform(new File(Consts.SCHEMATRON_TRANSFORMER));
			} catch (XSLException e) {
				LOG.error(Consts.returnExceptionString(e));
			} catch (ParsingException e) {
				LOG.error(Consts.returnExceptionString(e));
			} catch (IOException e) {
				LOG.error(Consts.returnExceptionString(e));
			}
		}
		return SCHEMATRON_TRANSFORMER;
	}

	/**
	 * Generic REST resource responder pattern
	 * 
	 * @param s
	 * @return
	 */
	public Response wrapResponseString(String s) {
		return Response.ok(s).header("Content-Transfer-Encoding", "binary")
				.build();
	}

	/**
	 * Returns a StringBuilder Object with the XML Processing Instruction
	 * already appended.
	 * 
	 * @return
	 */
	public StringBuilder getXMLStringBuilder() {
		StringBuilder sb = new StringBuilder();
		sb.append(Consts.XML_PI);
		return sb;
	}

	/**
	 * Generates a path for a resource e.g. ("/resources/xsl/books.xsl")
	 * 
	 * @param segmentOne
	 * @param segmentTwo
	 * @param fileExtension
	 * @return
	 */
	public StringBuilder buildResourceUri(String segmentOne, String segmentTwo,
			String fileExtension) {
		StringBuilder sb = new StringBuilder();
		sb.append(
				ConfigurationProvider.getInstance()
						.getApplicationServerConfig().getResourceFolderPath())
				.append("/").append(segmentOne).append("/").append(segmentTwo)
				.append(".").append(fileExtension);
		return sb;
	}

	/**
	 * Creates a MarkLogic-compliant XQuery StringBuilder complete with
	 * processing instruction already appended.
	 * 
	 * @return
	 */
	public StringBuilder createMarkLogicXQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append(Consts.MARKLOGIC_XQUERY_DECL);
		return sb;
	}

	/**
	 * Basic test to ensure the connection and request/response chain are
	 * working between the client and the server
	 * 
	 * TODO - maybe move these over to a separate class if we end up with many
	 * of them
	 * 
	 * @return
	 */
	public String getBasicTestQuery() {
		StringBuilder sb = IOUtilsProvider.getInstance()
				.createMarkLogicXQuery();
		sb.append("let $a := (3, 5, 15)\nreturn\n<results><max>{ max($a) }</max><min>{ min($a) }</min></results>");
		return sb.toString();
	}

	public String getBasicMLTestQuery() {
		StringBuilder sb = IOUtilsProvider.getInstance()
				.createMarkLogicXQuery();

		sb.append("fn:codepoints-to-string((97, 32, 98, 99))");
		return sb.toString();
	}

	public String getBasicMLTestQueryWithBinding() {
		StringBuilder sb = IOUtilsProvider.getInstance()
				.createMarkLogicXQuery();
		sb.append("declare variable $x as xs:string external; $x");
		return sb.toString();
	}
}
