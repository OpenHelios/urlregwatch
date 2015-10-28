package de.buehmann.tools.urlregwatch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LinuxNotifier {

	public void sendMessage(final String message) throws IOException {
		final List<String> cmd = new ArrayList<String>();
		cmd.add("/usr/bin/notify-send");
		cmd.add("-u");
		cmd.add("critical");
		cmd.add("-i");
		cmd.add("/usr/share/icons/Adwaita/48x48/actions/appointment-new.png");
		cmd.add("WG-Info");
		cmd.add(message);
		playNotifySound();
		Runtime.getRuntime().exec(cmd.toArray(new String[cmd.size()]));
	}

	private void playNotifySound() {
		// new AudioPlayer().play("/usr/lib/wginfo/sounds-969-isnt-it.wav");
	}

}
