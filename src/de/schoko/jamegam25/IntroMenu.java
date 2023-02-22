package de.schoko.jamegam25;

import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import de.schoko.rendering.Context;
import de.schoko.rendering.Graph;
import de.schoko.rendering.HUDGraph;
import de.schoko.rendering.Image;
import de.schoko.rendering.ImageLocation;
import de.schoko.rendering.ImagePool;
import de.schoko.rendering.Keyboard;

public class IntroMenu extends Menu {
	private ArrayList<Scene> scenes;
	private Scene currentScene;
	private double spacePressCooldown;
	
	@Override
	public void onLoad(Context context) {
		// Array instanciation
		scenes = new ArrayList<>();

		// Asset Loading
		String basePath = "de/schoko/jamegam25/assets/";
		ImagePool imagePool = context.getImagePool();
		imagePool.addImage("pirate", basePath + "pirate.png", ImageLocation.JAR);
		imagePool.addImage("antagonist", basePath + "antagonist.png", ImageLocation.JAR);
		imagePool.addImage("backdrop", basePath + "backdrop.png", ImageLocation.JAR);
		imagePool.addImage("butterChicken", basePath + "butter_chicken.png", ImageLocation.JAR);

		// Scene Preload
		SceneRenderer wakeUpRenderer = (HUDGraph hud, Scene scene) -> {
			hud.drawRect(0, 0, hud.getWidth(), hud.getHeight(), Graph.getColor(145, 108, 73));
			hud.drawImage(-25, -25, imagePool.getImage("pirate"), 25 / hud.getWidth());
			int alpha = (int) (scene.getT() / 1000 * 255 / 8);
			if (alpha > 255) {
				currentScene = scenes.remove(0);
				return;
			}
			hud.drawRect(0, 0, hud.getWidth(), hud.getHeight(), Graph.getColor(0, 0, 0, (255 - alpha)));
		};
		SceneRenderer protRenderer = (HUDGraph hud, Scene scene) -> {
			hud.drawRect(0, 0, hud.getWidth(), hud.getHeight(), Graph.getColor(145, 108, 73));
			hud.drawImage(-25, -25, imagePool.getImage("pirate"), 25 / hud.getWidth());
			drawText(hud);
		};
		SceneRenderer antRenderer = (HUDGraph hud, Scene scene) -> {
			hud.drawRect(0, 0, hud.getWidth(), hud.getHeight(), Graph.getColor(145, 108, 73));
			hud.drawImage(hud.getWidth() - imagePool.getImage("antagonist").getAWTImage().getWidth(null) / (25 / hud.getWidth()), -25, imagePool.getImage("antagonist"), 25 / hud.getWidth());
			drawText(hud);
		};
		SceneRenderer butterChickenRenderer = (HUDGraph hud, Scene scene) -> {
			hud.drawRect(0, 0, hud.getWidth(), hud.getHeight(), Graph.getColor(145, 108, 73));
			hud.drawImage(-25, -25, imagePool.getImage("pirate"), 25 / hud.getWidth());
			Image image = imagePool.getImage("butterChicken");
			java.awt.Image awtImage = image.getAWTImage();
			hud.drawImage(hud.getWidth() - (awtImage.getWidth(null) / (25 / hud.getWidth()) / 2),
							hud.getHeight() * 0.25,
							image,  (50 / hud.getWidth()));
			drawText(hud);
		};
		

		// Scene Loading

		scenes.add(new Scene("", "", wakeUpRenderer));
		scenes.add(new Scene("Gustavo: ", "Henlo I am protagonist Gustavo", protRenderer));
		scenes.add(new Scene("Mary: ", "Henlo me Mary", antRenderer));
		scenes.add(new Scene("Gustavo: ", "u-uh w-where am I?", protRenderer));
		scenes.add(new Scene("Gustavo: ", "Is this a ship?", protRenderer));
		scenes.add(new Scene("Gustavo: ", "WAIT, WHAT AM I DOING ON SEA?", protRenderer));
		scenes.add(new Scene("Gustavo: ", "I can't remember anything...", protRenderer));
		scenes.add(new Scene("Gustavo: ", "What is this in my hand?", protRenderer));
		scenes.add(new Scene("Gustavo: ", "An infinite supply of butter chicken?", butterChickenRenderer));
		scenes.add(new Scene("Mary: ", "Hey, you!", antRenderer));
		scenes.add(new Scene("Mary: ", "You stole my precious butter chicken my grandma made for me!", antRenderer));
		scenes.add(new Scene("Mary: ", "Get him, boys!!!", antRenderer));

		// First Scene Loading
		currentScene = scenes.remove(0);
	}

	@Override
	public void render(Graph g, double deltaTimeMS) {
		HUDGraph hud = g.getHUD();
		Keyboard keyboard = getContext().getKeyboard();
		
		
		if (keyboard.isPressed(Keyboard.SPACE)) {
			if (spacePressCooldown > 0) {
				spacePressCooldown = 200;
			} else {
				if (currentScene.isComplete()) {
					if (scenes.size() == 0) {
						getProject().setMenu(new Game());
						return;
					} else {
						currentScene = scenes.remove(0);
						spacePressCooldown = 200;
					}
				} else {
					currentScene.skipToEnd();
					spacePressCooldown = 200;
				}
			}
		}
		if (spacePressCooldown > 0) {
			spacePressCooldown -= deltaTimeMS;
		}

		currentScene.advanceScene(deltaTimeMS);

		// Drawing Part
		currentScene.render(hud);

		
	}

	public void drawText(HUDGraph hud) {
		double margin = 20;
		hud.drawRect(0, hud.getHeight() - 200, hud.getWidth(), 200, Graph.getColor(0, 0, 0, 200));
		String[] lines = currentScene.getText().split("\\n");
		for (int i = 0; i < lines.length; i++) {
			hud.drawText(lines[i], 2, hud.getHeight() - 200 + margin + 22 * i, Graph.getColor(255, 255, 255), new Font("Segoe UI", Font.PLAIN, 20));
		}
	}
	
	private class Scene {
		public static final double TIME_PER_CHARACTER = 100;
		private String prefix;
		private String text;
		private double t;
		private SceneRenderer renderer;
		
		public Scene(String prefix, String text, SceneRenderer renderer) {
			this.prefix = prefix; // In this case, prefix means something that is written before the actual text
			this.text = text;
			this.renderer = renderer;
		}

		public void render(HUDGraph g) {
			renderer.drawScene(g, this);
		}

		public void advanceScene(double deltaTimeMS) {
			String previousText = getText();
			t += deltaTimeMS;
			if (!previousText.equalsIgnoreCase(getText())) {
				try {
					Sound sound = new Sound("de/schoko/jamegam25/assets/gurgle.wav");
					sound.start();
				} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
					e.printStackTrace();
				}
			}
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
	}

	private interface SceneRenderer {
		public void drawScene(HUDGraph g, Scene scene);
	}
}