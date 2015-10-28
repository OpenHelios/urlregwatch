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
    private boolean hasChanged;
    private String resultMessage;

    public UrlReg(final String url, final String regEx, final String expected) throws IOException {
        this.url = url;
        this.regEx = regEx;
        this.expected = expected;
    }

    public boolean load() throws IOException {
        final Browser browser = new Browser();
        final String content = browser.getUrl(new URL(url));
        final Pattern pattern = Pattern.compile(regEx, Pattern.MULTILINE|Pattern.DOTALL);
        final Matcher matcher = pattern.matcher(content);
        final boolean found;
        if (matcher.find()) {
            final String part = matcher.group(1);
            if (expected.equals(part)) {
                found = true;
            } else {
                found = false;
                resultMessage = "expected  \"" + expected + "\"\n"
				+ "but found \"" + part + "\".";
            }
        } else {
            found = false;
            resultMessage = "Expression \"" + regEx + "\" has not been found in content.";
        }
        hasChanged = !found;
        return hasChanged;
    }

    public boolean hasChanged() {
        return hasChanged;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        if (hasChanged) {
            sb.append(url).append(":\n").append(resultMessage);
        } else {
            sb.append("OK: ").append(url);
        }
        return sb.toString();
    }
    
}
