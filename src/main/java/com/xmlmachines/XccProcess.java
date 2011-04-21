package com.xmlmachines;

import com.marklogic.xcc.Session;

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
		System.out.println("about to write adhoc q " + query);
		writeDoc();
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
