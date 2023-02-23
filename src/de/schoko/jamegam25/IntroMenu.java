package de.schoko.jamegam25;

import java.util.ArrayList;

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
	private double interactionCooldown;
	private double maxInteractionCooldown = 150;
	
	public IntroMenu() {
		super(true);
	}

	@Override
	public void onLoad(Context context) {
		// Array instanciation
		scenes = new ArrayList<>();

		// Asset Loading
		String basePath = Project.ASSET_PATH;
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
			scene.drawText(hud);
		};
		SceneRenderer antRenderer = (HUDGraph hud, Scene scene) -> {
			hud.drawRect(0, 0, hud.getWidth(), hud.getHeight(), Graph.getColor(145, 108, 73));
			hud.drawImage(hud.getWidth() - imagePool.getImage("antagonist").getAWTImage().getWidth(null) / (25 / hud.getWidth()), -25, imagePool.getImage("antagonist"), 25 / hud.getWidth());
			scene.drawText(hud);
		};
		SceneRenderer butterChickenRenderer = (HUDGraph hud, Scene scene) -> {
			hud.drawRect(0, 0, hud.getWidth(), hud.getHeight(), Graph.getColor(145, 108, 73));
			hud.drawImage(-25, -25, imagePool.getImage("pirate"), 25 / hud.getWidth());
			Image image = imagePool.getImage("butterChicken");
			java.awt.Image awtImage = image.getAWTImage();
			hud.drawImage(hud.getWidth() - (awtImage.getWidth(null) / (25 / hud.getWidth()) / 2),
							hud.getHeight() * 0.25,
							image,  (50 / hud.getWidth()));
			scene.drawText(hud);
		};
		

		// Scene Loading

		scenes.add(new Scene(this, "", "", wakeUpRenderer));
		scenes.add(new Scene(this, "Gustavo: ", "u-uh w-where am I?", protRenderer));
		scenes.add(new Scene(this, "Gustavo: ", "Is this a ship?", protRenderer));
		scenes.add(new Scene(this, "Gustavo: ", "WAIT, WHAT AM I DOING ON SEA?", protRenderer));
		scenes.add(new Scene(this, "Gustavo: ", "I can't remember anything...", protRenderer));
		scenes.add(new Scene(this, "Gustavo: ", "What is this in my hand?", protRenderer));
		scenes.add(new Scene(this, "Gustavo: ", "An infinite supply of butter chicken?", butterChickenRenderer));
		scenes.add(new Scene(this, "Mary: ", "Oi!", antRenderer));
		scenes.add(new Scene(this, "Mary: ", "You stole my precious butter chicken my grandma made for me!", antRenderer));
		scenes.add(new Scene(this, "Mary: ", "Get him, boys!!!", antRenderer));

		// First Scene Loading
		currentScene = scenes.remove(0);

		// Play Wave Sound
		Sound sound = new Sound(this, Project.ASSET_PATH + "wave.wav", true);
		sound.start();
		sound.setVolume(0.25);
	}

	@Override
	public void render(Graph g, double deltaTimeMS) {
		HUDGraph hud = g.getHUD();
		Keyboard keyboard = getContext().getKeyboard();
		
		if (keyboard.isPressed(Keyboard.SPACE)) {
			if (interactionCooldown > 0) {
				interactionCooldown = maxInteractionCooldown;
			} else {
				if (currentScene.isComplete()) {
					if (scenes.size() == 0) {
						getProject().setMenu(new Game());
						return;
					} else {
						nextScene();
						interactionCooldown = maxInteractionCooldown;
					}
				} else {
					currentScene.skipToEnd();
					interactionCooldown = maxInteractionCooldown;
				}
			}
		}
		if (interactionCooldown > 0) {
			interactionCooldown -= deltaTimeMS;
		}

		currentScene.advanceScene(deltaTimeMS);

		// Drawing Part
		currentScene.render(hud);
	}
	
	public void nextScene() {
		currentScene = scenes.remove(0);
	}
}
