package de.buehmann.tools.urlregwatch;

import java.io.IOException;

/**
 * @author remo
 */
public class Main {
	public static void main(String[] args) throws IOException, InterruptedException {
		final UrlRegs urls = new UrlRegs();
		boolean isOk = true;
		for (UrlReg url : urls.getUrls()) {
			if (url.hasChanged()) {
				isOk = false;
			}
		}
		if (!isOk) {
			System.exit(1);
		}
	}
}
