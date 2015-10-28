package de.buehmann.tools.urlregwatch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class Browser {

	public Browser() {
		HttpsURLConnection.setFollowRedirects(true);
		createCookieManager();
		// work around to avoid a bug in Java 7:
		// javax.net.ssl.SSLProtocolException: handshake alert:
		// unrecognized_name
		// sun.security.ssl.ClientHandshaker.handshakeAlert(ClientHandshaker.java:1380)
		// [...]
		// sun.net.www.protocol.https.HttpsURLConnectionImpl.getOutputStream(HttpsURLConnectionImpl.java:250)
		// see
		// http://stackoverflow.com/questions/7615645/ssl-handshake-alert-unrecognized-name-error-since-upgrade-to-java-1-7-0
		System.setProperty("jsse.enableSNIExtension", "false");
	}

	 private static void createCookieManager() {
		final CookieManager cookieManager = new CookieManager();
		cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
		CookieHandler.setDefault(cookieManager);
		printCookies(cookieManager);
	 }

	private static void printCookies(final CookieManager cookieManager) {
		List<HttpCookie> cookies = cookieManager.getCookieStore().getCookies();
		for (final HttpCookie cookie : cookies) {
			System.out.println(cookie.getName() + "=" + cookie.getValue());
		}
	}

	public String getUrl(final URL url) throws IOException {
		final URLConnection connection = url.openConnection();
		connection.setDoInput(true);
		// read response data
		final StringBuilder sb = new StringBuilder();
		BufferedReader input = null;
		try {
			input = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "UTF-8"));
			String line;
			while (null != (line = input.readLine())) {
				sb.append(line);
			}
		} finally {
			if (null != input) {
				input.close();
			}
		}
                if (connection instanceof HttpsURLConnection) {
                    final int responseCode = getHttpsConnection(connection)
                                    .getResponseCode();
                    if (200 != responseCode) {
                            throw new IOException("Response message was not OK (200), but: "
                                            + responseCode);
                    }
                }
		return sb.toString();
	}

	private static HttpsURLConnection getHttpsConnection(final URLConnection connection)
			throws IOException {
		if (connection instanceof HttpsURLConnection) {
			return (HttpsURLConnection) connection;
		} else {
			throw new IOException("Expected https connection, but given: "
					+ connection.getURL().toString());
		}
	}

}
