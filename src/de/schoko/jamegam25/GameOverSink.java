package de.schoko.jamegam25;

import java.util.ArrayList;

import de.schoko.rendering.Context;
import de.schoko.rendering.Graph;
import de.schoko.rendering.HUDGraph;
import de.schoko.rendering.ImageLocation;
import de.schoko.rendering.ImagePool;
import de.schoko.rendering.Keyboard;

public class GameOverSink extends Menu {
	private ArrayList<Scene> scenes;
	private Scene currentScene;
	private double interactionCooldown;
	private double maxInteractionCooldown = 150;
	
	public GameOverSink() {
		super(true);
	}

	@Override
	public void onLoad(Context context) {
		// Array instanciation
		scenes = new ArrayList<>();

		// Settingschange

		context.getSettings().setBackgroundColor(186, 255, 255);

		// Asset Loading
		String basePath = Project.ASSET_PATH;
		ImagePool imagePool = context.getImagePool();
		imagePool.addImage("pirate", basePath + "pirate.png", ImageLocation.JAR);
		imagePool.addImage("antagonist", basePath + "antagonist.png", ImageLocation.JAR);
		imagePool.addImage("backdrop", basePath + "backdrop.png", ImageLocation.JAR);
		imagePool.addImage("butterChicken", basePath + "butter_chicken.png", ImageLocation.JAR);

		// Scene Preload
		SceneRenderer protRenderer = (HUDGraph hud, Scene scene) -> {
			hud.drawRect(0, hud.getHeight() / 2, hud.getWidth(), hud.getHeight() / 2, Graph.getColor(55, 129, 244));
			hud.drawImage(-25, -25, imagePool.getImage("pirate"), 25 / hud.getWidth());
			scene.drawText(hud);
		};
		SceneRenderer antRenderer = (HUDGraph hud, Scene scene) -> {
			hud.drawRect(0, hud.getHeight() / 2, hud.getWidth(), hud.getHeight() / 2, Graph.getColor(55, 129, 244));
			hud.drawImage(hud.getWidth() - imagePool.getImage("antagonist").getAWTImage().getWidth(null) / (25 / hud.getWidth()), -25, imagePool.getImage("antagonist"), 25 / hud.getWidth());
			scene.drawText(hud);
		};

		// Scene Loading

		scenes.add(new Scene(this, "Mary: ", "Oh no, what have you done?", protRenderer));
		
		// First Scene Loading
		currentScene = scenes.remove(0);
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
