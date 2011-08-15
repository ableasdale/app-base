package com.xmlmachines.temp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.text.MessageFormat;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.log4j.Logger;

import com.marklogic.xcc.Content;
import com.marklogic.xcc.ContentCreateOptions;
import com.marklogic.xcc.ContentFactory;
import com.marklogic.xcc.ContentSource;
import com.marklogic.xcc.ContentSourceFactory;
import com.marklogic.xcc.Session;
import com.marklogic.xcc.exceptions.RequestException;
import com.ximpleware.AutoPilot;
import com.ximpleware.EOFException;
import com.ximpleware.EncodingException;
import com.ximpleware.EntityException;
import com.ximpleware.NavException;
import com.ximpleware.ParseException;
import com.ximpleware.VTDGen;
import com.ximpleware.VTDNav;
import com.ximpleware.XPathEvalException;
import com.ximpleware.XPathParseException;
import com.xmlmachines.TestExtractXML;

public class PrepareMedlineXMLForIngest {

	private final static Logger LOG = Logger.getLogger(TestExtractXML.class);
	private final static String path = "/home/ableasdale/workspace/medline-data/";
	private final static String uriStr = "xcc://admin:admin@localhost:8010/Medline";
	private static ContentSource cs;

	public static void main(String[] s) throws Exception {
		PrepareMedlineXMLForIngest ingest = new PrepareMedlineXMLForIngest();
		URI uri = new URI(uriStr);
		cs = ContentSourceFactory.newContentSource(uri);

		LOG.info("Scanning folder: " + path);
		// for all xml files in folder do
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		for (File f : listOfFiles) {
			if (f.isFile()) {
				if (f.getName().endsWith(".xml")) {
					XmlProcessorThread xpt = ingest.new XmlProcessorThread(f);
					new Thread(xpt).start();
				}
			}
			/*
			 * else if (listOfFiles[i].isDirectory()) {
			 * System.out.println("Directory " + listOfFiles[i].getName()); }
			 */
		}

	}

	private static void logException(Exception e) {
		LOG.error("Error encountered" + e.getMessage(), e);
	}

	private static void processAsXml(File f) throws FileNotFoundException,
			IOException, EncodingException, EOFException, EntityException,
			ParseException, XPathParseException, XPathEvalException,
			NavException {
		Session session = cs.newSession();

		int count = 0;
		FileInputStream fis = new FileInputStream(f);
		byte[] b = new byte[(int) f.length()];
		fis.read(b);
		VTDGen vg = new VTDGen();
		vg.setDoc(b);
		vg.parse(true);

		VTDNav vn = vg.getNav();
		AutoPilot ap = new AutoPilot();
		ap.bind(vn);
		byte[] ba = vn.getXML().getBytes();
		ap.selectXPath("/MedlineCitationSet/MedlineCitation");
		int i = -1;
		while ((i = ap.evalXPath()) != -1) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			long l = vn.getElementFragment();
			int offset = (int) l;
			int len = (int) (l >> 32);
			baos.write(ba, offset, len);
			baos.flush();
			try {
				ContentCreateOptions opts = new ContentCreateOptions();
				Content c = ContentFactory.newContent(
						String.format("%05d", count) + "-" + f.getName(),
						baos.toByteArray(), opts);
				session.insertContent(c);
			} catch (RequestException e) {
				logException(e);
			}
			count++;
		}
		session.close();
		LOG.info(MessageFormat.format("Processed {0} documents", count));
	}

	public class XmlProcessorThread implements Runnable {

		File file;

		public XmlProcessorThread(File f) {
			this.file = f;
			LOG.info(MessageFormat.format("Processing file: {0}",
					file.getName()));
		}

		// private final BoundRequestBuilder r;

		@Override
		public void run() {
			try {
				processAsXml(file);
			} catch (Exception e) {
				logException(e);
			}
			LOG.debug(MessageFormat.format("Ingest completed for {0}",
					file.getName()));
		}
	}
}