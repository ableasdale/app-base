package com.xmlmachines;

import java.io.File;
import java.io.FileInputStream;
import java.text.MessageFormat;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.log4j.Logger;

import com.ximpleware.AutoPilot;
import com.ximpleware.VTDGen;
import com.ximpleware.VTDNav;

public class TestExtractXML {

	private final static Logger LOG = Logger.getLogger(TestExtractXML.class);

	public static void main(String[] s) throws Exception {
		File f = new File("src/main/resources/medsamp2011.xml");
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
			// LOG.debug(baos.toString("utf-8"));
			count++;
		}
		LOG.info(MessageFormat.format("Total: {0}", count));
	}
}