package de.schoko.jamegam25;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	private Clip clip;

	/**
	 * @param path Path to location in jar
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 * @throws LineUnavailableException
	 */
	public Sound(String path) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
		clip = AudioSystem.getClip();
		InputStream stream = new BufferedInputStream(Sound.class.getClassLoader().getResourceAsStream(path));
		AudioInputStream inputStream = AudioSystem.getAudioInputStream(stream);
		clip.open(inputStream);
	}

	public void start() {
		clip.start();
	}

	public void stop() {
		clip.stop();
	}
}
