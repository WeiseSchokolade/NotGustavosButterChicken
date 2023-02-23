package de.schoko.jamegam25;

import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	private Clip clip;

	/**
	 * @param path Path to location in jar
	 */
	public Sound(String path) {
		try {
			clip = AudioSystem.getClip();
			InputStream stream = new BufferedInputStream(Sound.class.getClassLoader().getResourceAsStream(path));
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(stream);
			clip.open(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
			clip = null;
		}
	}

	public void start() {
		if (clip != null) {
			clip.start();
		}
	}

	public void stop() {
		if (clip != null) {
			clip.stop();
		}
	}
}
