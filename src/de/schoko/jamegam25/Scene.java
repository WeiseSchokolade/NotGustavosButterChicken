package de.schoko.jamegam25;

import java.awt.Font;

import de.schoko.rendering.Graph;
import de.schoko.rendering.HUDGraph;

public class Scene {
	public static final double TIME_PER_CHARACTER = 100;
	private String prefix;
	private String text;
	private double t;
	private String path;
	private Sound sound;
	private SceneRenderer renderer;
	
	public Scene(String prefix, String text, SceneRenderer renderer) {
		this.prefix = prefix; // In this case, prefix means something that is written before the actual text
		this.text = text;
		this.renderer = renderer;
	}

	public Scene(String prefix, String text, SceneRenderer renderer, String path) {
		this.prefix = prefix; // In this case, prefix means something that is written before the actual text
		this.text = text;
		this.renderer = renderer;
		this.path = path;
	}

	public void start() {
		if (path != null) {
			Sound sound = new Sound(path);
			sound.start();
		}
	}

	public void stop() {
		if (sound != null) {
			sound.stop();
		}
	}

	public void render(HUDGraph g) {
		renderer.drawScene(g, this);
	}

	public void advanceScene(double deltaTimeMS) {
		//String previousText = getText();
		t += deltaTimeMS;
		//if (!previousText.equalsIgnoreCase(getText())) {
		//	
		//	Sound sound = new Sound("de/schoko/jamegam25/assets/gurgle.wav");
		//	sound.start();
		//}
	}

	public String getText() {
		int characterAmount = (int) (t / TIME_PER_CHARACTER);
		characterAmount = (characterAmount > text.length()) ? text.length() : characterAmount;
		return prefix + text.substring(0, characterAmount);
	}

	public void skipToEnd() {
		t = text.length() * TIME_PER_CHARACTER;
	}

	public boolean isComplete() {
		return (getText().equals(prefix + text));
	}

	public double getT() {
		return t;
	}

	public void drawText(HUDGraph hud) {
		double margin = 20;
		hud.drawRect(0, hud.getHeight() - 200, hud.getWidth(), 200, Graph.getColor(0, 0, 0, 200));
		String[] lines = getText().split("\\n");
		for (int i = 0; i < lines.length; i++) {
			hud.drawText(lines[i], 5, hud.getHeight() - 200 + margin + 15 + 40 * i, Graph.getColor(255, 255, 255), new Font("Segoe UI", Font.PLAIN, 35));
		}
	}
}
