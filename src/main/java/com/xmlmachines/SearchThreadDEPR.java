package com.xmlmachines;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClient.BoundRequestBuilder;
import com.ning.http.client.Realm;
import com.ning.http.client.Response;

/**
 * The Class Process.
 */
public class SearchThreadDEPR extends Thread implements Runnable {

	public Random randomGenerator = new Random();

	private int getRandom() {
		int randomInt = randomGenerator.nextInt(6);
		if (randomInt == 0) {
			return 1;
		} else {
			return randomInt;
		}
	};

	private String generateGetRequestUri() {
		StringBuilder sb = new StringBuilder();
		sb.append(Values.BASE_HREF).append(":").append(Values.PORT)
				.append("?q=");
		int randomInt = getRandom();
		for (int i = 0; i < randomInt; i++) {

			sb.append(Values.qTerms[randomGenerator
					.nextInt(Values.qTerms.length)]);

			if (i < (randomInt - 1)) {
				sb.append("+")
						.append(Values.operators[randomGenerator
								.nextInt(Values.operators.length)]).append("+");
			}

		}
		System.out.println(sb.toString());
		return sb.toString();
	}

	private final BoundRequestBuilder r;

	public SearchThreadDEPR(AsyncHttpClient c) {
		// AsyncHttpClient c
		// System.out.println("Starting request");

		this.r = c.prepareGet(generateGetRequestUri()).setRealm(
				(new Realm.RealmBuilder()).setPrincipal("admin")
						.setPassword("admin").setRealmName("casper").build());

		// System.out.println("searchthread ready " + this.getId());

	}

	/**
	 * Save page.
	 */
	public void writeDoc() {

		// System.out.println(session.getConnectionUri());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */

	@Override
	public void run() {
		System.out.println("Executing thread (" + getId() + ")");

		try {
			Future<Response> f = r.execute();
			Response resp = f.get(60, TimeUnit.SECONDS);
			// System.out.println(resp.getResponseBody());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

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
