package de.buehmann.tools.urlregwatch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author remo
 */
public class UrlRegs {

	private class Downloader implements Runnable {
		private final UrlReg urlReg;
		private Downloader(UrlReg urlReg) {
			this.urlReg = urlReg;
		}
		public void run() {
			try {
				urlReg.load();
			} catch (IOException e) {
				// ignore exception
			}
			System.out.println(urlReg.toString());
		}
	}

	private static final String SETTINGS_FILE = System.getProperty("user.home") + "/.urlregwatch/urlregs.txt";

	private final List<UrlReg> urls;

	public UrlRegs() throws IOException, InterruptedException {
		urls = readUrlsFromSettings();
		final ExecutorService executor = Executors.newFixedThreadPool(urls.size());
		for (UrlReg urlReg : urls) {
			executor.execute(new Downloader(urlReg));
		}
		executor.shutdown();
		executor.awaitTermination(20, TimeUnit.SECONDS);
	}

	private static List<UrlReg> readUrlsFromSettings() throws IOException {
		final List<UrlReg> urls = new ArrayList<>();
		try (final BufferedReader in = new BufferedReader(new FileReader(SETTINGS_FILE))) {
			final String[] line = new String[3];
			int i = -1;
			while (null != (line[++i] = in.readLine())) {
				line[i] = line[i].trim();
				if (0 == line[i].length() || line[i].startsWith("#")) {
					i--;
				} else if (i == 2) {
					final UrlReg urlReg = new UrlReg(line[0], line[1], line[2]);
					urls.add(urlReg);
					i = -1;
				}
			}
		}
		return urls;
	}

	public List<UrlReg> getUrls() {
		return urls;
	}

}
