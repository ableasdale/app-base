package com.xmlmachines;

/**
 * The Class Process.
 */
public class XccProcess implements Runnable {

	public XccProcess(String xccAdHocQuery) {
		System.out.println("getting ready to process " + xccAdHocQuery);
	}

	/**
	 * Save page.
	 */
	public void writeDoc() {
		System.out.println("about to write doc here");

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
