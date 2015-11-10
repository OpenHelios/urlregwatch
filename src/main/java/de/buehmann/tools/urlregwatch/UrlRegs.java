package de.buehmann.tools.urlregwatch;
 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author remo
 */
public class UrlRegs {

    private static final String SETTINGS_FILE = System.getProperty("user.home") + "/.urlregwatch/urlregs.txt";

    private final List<UrlReg> urls = new ArrayList<>();
    
    public UrlRegs() throws IOException {
        urls.clear();
        try (final BufferedReader in = new BufferedReader(new FileReader(
                SETTINGS_FILE))) {
            final StringBuilder sb = new StringBuilder();
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
        if (!urls.isEmpty()) {
            for (final UrlReg url : urls) {
		try {
	                url.load();
		} catch (final IOException e) {
			// ignore here
		}
                System.out.println(url.toString());
            }
        }
    }

    public List<UrlReg> getUrls() {
        return urls;
    }

}
