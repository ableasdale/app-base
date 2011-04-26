package com.xmlmachines;

import com.marklogic.xcc.Request;
import com.marklogic.xcc.Session;
import com.marklogic.xcc.exceptions.RequestException;

/**
 * The Class Process.
 */
public class XccProcess implements Runnable {

	private final Session session;
	private final String query;

	public XccProcess(String xccAdHocQuery, Session markLogicSession) {
		// System.out.println("getting ready to process " + xccAdHocQuery);
		this.session = markLogicSession;
		this.query = xccAdHocQuery;
	}

	/**
	 * Save page.
	 */
	public void writeDoc() {

		System.out.println(session.getConnectionUri());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		// ThreadTimingBean tt = new ThreadTimingBean();
		// tt.setName(Thread.currentThread().getName());
		// tt.setStart(System.currentTimeMillis());
		/*
		 * System.out.println("attempting to insert..." +
		 * query.substring(query.length() - 50, query.length()));
		 */
		Request r = session.newAdhocQuery(query);
		try {
			session.submitRequest(r);
		} catch (RequestException e) {
			System.out.println("got an exception..");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			session.close();
		}

		// strValue.append(rs.asString());
		/*
		 * while (rs.hasNext()) { strValue.append(rs.asString()); rs.next(); }
		 */
		// LOG.info(strValue.toString());

		// System.out.println("about to write adhoc q ");
		// writeDoc();
		// tt.setEnd(System.currentTimeMillis());
		// putAndReport(tt);

		// System.out.println(Thread.currentThread().getName());
	}
}

/*
 * 
 * private synchronized void putAndReport(ThreadTimingBean t) {
 * t.setAtomicId(counter.incrementAndGet()); timings.add(t); //mgr.updateCsv(t);
 * 
 * if (counter.get() == THREADS) { applicationEndTime =
 * System.currentTimeMillis();
 * LOG.info("All Threads accounted for. Generating test statistics...\n" +
 * mgr.generateStats(THREADS, applicationStartTime, applicationEndTime,
 * timings)); }
 * 
 * }
 */
