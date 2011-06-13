package com.xmlmachines;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Nodes;
import nu.xom.ParsingException;
import nu.xom.ValidityException;
import nu.xom.XPathContext;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClient.BoundRequestBuilder;
import com.ning.http.client.Realm;
import com.ning.http.client.Response;

public class ClientTest {

	private static long startTime;
	private static long endTime;
	private static AtomicInteger counter;
	private static final int THREADS = 400;
	private static List<String> results;
	private static Realm realm;

	public static void main(String[] args) throws IOException,
			InterruptedException, ExecutionException, TimeoutException {
		// Client + threadpool created once here
		AsyncHttpClient client = new AsyncHttpClient();
		/*
		 * realm = new Realm.RealmBuilder().setPrincipal("admin")
		 * .setPassword("admin").setUsePreemptiveAuth(true)
		 * .setScheme(AuthScheme.BASIC).build();
		 */

		results = new ArrayList<String>();
		counter = new AtomicInteger(0);
		ClientTest ct = new ClientTest();

		for (int i = 0; i < THREADS; i++) {
			SearchThread s = ct.new SearchThread(client);
			new Thread(s).start();
		}
		startTime = System.currentTimeMillis();
		System.out.println(THREADS + " Threads started.");
	}

	private synchronized void putAndReport(String responsebody) {
		results.add(responsebody);
		counter.incrementAndGet();

		if (counter.get() == THREADS) {
			endTime = System.currentTimeMillis();
			System.out.println("All Threads accounted for in: "
					+ (endTime - startTime) + " miliseconds \n"
					+ results.size() + " responses returned");
			// chartResults(results);
		}

	}

	private void chartResults(List<String> results) {
		System.out.println("compiling results");
		XPathContext context = new XPathContext("search",
				"http://marklogic.com/appservices/search");
		// Map map = new HashMap();
		// map.put("search", "http://marklogic.com/appservices/search");
		Builder builder = new Builder();

		for (String s : results) {
			try {
				Document doc = builder.build(s, "");

				Nodes qtext = doc.query("//search:qtext", context);

				for (int i = 0; i < qtext.size(); i++) {
					System.out.println(qtext.get(i).getValue());
				}

				Nodes qtext2 = doc.query("//search:metrics/search:total-time",
						context);
				for (int i = 0; i < qtext2.size(); i++) {
					System.out.println(qtext2.get(i).toString() + " | "
							+ qtext2.get(i).getValue());
				}

			} catch (ValidityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParsingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public class SearchThread extends Thread implements Runnable {

		public Random randomGenerator = new Random();

		private int getRandom() {
			int randomInt = randomGenerator.nextInt(6);
			if (randomInt == 0) {
				return getRandom();
			} else {
				return randomInt;
			}
		};

		private String generateGetRequestParams() {
			StringBuilder sb = new StringBuilder();

			int randomInt = getRandom();
			for (int i = 0; i < randomInt; i++) {

				sb.append(Values.qTerms[randomGenerator
						.nextInt(Values.qTerms.length)]);

				if (i < (randomInt - 1)) {
					sb.append("+")
							.append(Values.operators[randomGenerator
									.nextInt(Values.operators.length)])
							.append("+");
				}

			}
			// System.out.println(sb.toString());
			return sb.toString();
		}

		private String getBaseRequestUri() {
			StringBuilder sb = new StringBuilder();
			sb.append(Values.BASE_HREF).append(":").append(Values.PORT)
					.append("?q=");
			return sb.toString();
		}

		private final BoundRequestBuilder r;

		public SearchThread(AsyncHttpClient c) {
			String req = getBaseRequestUri() + generateGetRequestParams();
			// generateGetRequestUri();

			// System.out.println(req);
			// TODO - add the realm back in
			// .setRealm(realm)
			this.r = c.prepareGet(req);
			/*
			 * this.r = c.prepareGet(req).setRealm(
			 * 
			 * (new Realm.RealmBuilder()).setPrincipal("admin")
			 * .setPassword("admin").setRealmName("casper") .build());
			 */

		}

		@Override
		public void run() {
			// System.out.println("Executing thread (" + getId() + ")");

			try {
				Response resp = r.execute().get(3600, TimeUnit.SECONDS);
				putAndReport(resp.getResponseBody());
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
}