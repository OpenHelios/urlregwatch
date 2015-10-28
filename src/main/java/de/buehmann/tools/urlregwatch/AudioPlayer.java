

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer {

	public void play(final String filename) {
		try (final AudioInputStream in = AudioSystem
				.getAudioInputStream(new File(filename))) {
			play(in);
		} catch (final IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}

	public void play(final InputStream resourceAsStream) {
		try (final AudioInputStream in = AudioSystem
				.getAudioInputStream(resourceAsStream)) {
			play(in);
		} catch (IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Plays the stream of a WAV file in standard Windows PCM format.
	 * 
	 * @param resourceAsStream
	 * @throws IOException 
	 * @throws LineUnavailableException 
	 */
	public void play(final AudioInputStream in) throws IOException {
		final Line.Info info = new Line.Info(SourceDataLine.class);
		try {
			final SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
			if (null != line) {
				line.open();
				line.start();
				stream(in, line);
				line.drain();
				line.stop();
			}
		} catch (final LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	private void stream(final AudioInputStream in, final SourceDataLine line)
			throws IOException {
		final byte[] buffer = new byte[4096];
		for (int n = 0; n != -1; n = in.read(buffer, 0, buffer.length)) {
			line.write(buffer, 0, n);
		}
	}

}
