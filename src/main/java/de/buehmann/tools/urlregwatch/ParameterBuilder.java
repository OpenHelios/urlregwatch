import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * This parameter builder creates a string of parameter names and values used in
 * a URL for method GET or POST.
 */
public class ParameterBuilder {

	private StringBuilder stringBuilder;

	public ParameterBuilder add(final String name, final String value) throws UnsupportedEncodingException {
		if (null == stringBuilder) {
			stringBuilder = new StringBuilder();
		} else {
			stringBuilder.append('&');
		}
		stringBuilder.append(name).append('=').append(URLEncoder.encode(value, "utf-8"));
		return this;
	}

	public String toString() {
		return stringBuilder.toString();
	}

}
