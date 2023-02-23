package de.schoko.jamegam25;

import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;


public class Sound {
	private Clip clip;
	private boolean loop;
	private double length;
	private long lastTime;

	/**
	 * @param path Path to location in jar
	 */
	public Sound(String path, boolean loop) {
		this.loop = loop;
		try {
			clip = AudioSystem.getClip();
			InputStream stream = new BufferedInputStream(Sound.class.getClassLoader().getResourceAsStream(path));
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(stream);
			clip.open(inputStream);
			
			if (loop) {
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			}
		} catch (Exception e) {
			e.printStackTrace();
			clip = null;
		}
	}
	
	/**
	 * Enables sound repetition blocking
	 * @param path Path to location in jar
	 * @param length estimated length of the sound before it can be played again
	 */
	public Sound(String path, double length) {
		this.loop = false;
		this.length = length;
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
			if (System.currentTimeMillis() - lastTime < length) {
				return;
			}
			clip.setFramePosition(0);
			if (loop) {
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			}
			clip.start();
			lastTime = System.currentTimeMillis();
		}
	}

	public void stop() {
		if (clip != null) {
			clip.stop();
		}
	}

	public void end() {
		if (clip != null) {
			clip.loop(0);
		}
	}

	public double getVolume() {
		if (clip == null);
		FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		return Math.pow(10.0f, volumeControl.getValue() / 20.0f);
	}

	public void setVolume(double volume) {
		FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		volumeControl.setValue((float) (Math.log10(volume) * 20.0));
	}
}
