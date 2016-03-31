package de.buehmann.tools.urlregwatch;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author remo
 */
public class UrlReg {

	private final String url;
	private final String regEx;
	private final String expected;
	private boolean isLoaded;
	private boolean hasChanged;
	private String resultMessage;

	public UrlReg(final String url, final String regEx, final String expected) throws IOException {
		this.url = url;
		this.regEx = regEx;
		this.expected = expected;
	}

	public String getUrl() {
		return url;
	}

	public boolean load() throws IOException {
		final Browser browser = new Browser();
		final String content;
		try {
			content = browser.getUrl(new URL(url));
		} catch (final IOException e) {
			resultMessage = e.toString();
			throw e;
		}
		final Pattern pattern = Pattern.compile(regEx, Pattern.MULTILINE | Pattern.DOTALL);
		final Matcher matcher = pattern.matcher(content);
		isLoaded = true;
		final boolean found;
		if (matcher.find()) {
			final String part = matcher.group(1);
			if (expected.equals(part)) {
				found = true;
			} else {
				found = false;
				resultMessage = "\texpected \"" + expected + "\"\n\t" + "but found \"" + part + "\".";
			}
		} else {
			found = false;
			resultMessage = "\tExpression \"" + regEx + "\" has not been found in content.";
		}
		hasChanged = !found;
		return hasChanged;
	}

	public boolean isLoaded() {
		return isLoaded;
	}

	public boolean hasChanged() {
		return hasChanged;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();
		if (isLoaded && !hasChanged) {
			sb.append("OK: ").append(url);
		} else {
			sb.append(url).append("\n").append(resultMessage);
		}
		return sb.toString();
	}

}
