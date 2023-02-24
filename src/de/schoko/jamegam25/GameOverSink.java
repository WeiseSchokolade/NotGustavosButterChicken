package de.schoko.jamegam25;

import java.util.ArrayList;

import de.schoko.rendering.Camera;
import de.schoko.rendering.CameraPath;
import de.schoko.rendering.CameraPathPoint;
import de.schoko.rendering.Context;
import de.schoko.rendering.Graph;
import de.schoko.rendering.HUDGraph;
import de.schoko.rendering.Image;
import de.schoko.rendering.ImageLocation;
import de.schoko.rendering.ImagePool;
import de.schoko.rendering.Keyboard;

public class GameOverSink extends Menu {
	private Game game;
	private ArrayList<Scene> scenes;
	private Scene currentScene;
	private double interactionCooldown;
	private double maxInteractionCooldown = 150;
	private Image shipImage;
	private double t;
	private boolean fading;
	private double fade;

	public GameOverSink(Game game) {
		super(true);
		this.game = game;
	}

	@Override
	public void onLoad(Context context) {
		// Array instanciation
		scenes = new ArrayList<>();

		// Settingschange

		context.getSettings().setBackgroundColor(186, 255, 255);
		context.getCamera().setCameraPath(new CameraPath() {
			@Override
			public CameraPathPoint getPoint(Camera camera, double deltaTimeMS) {
				return new CameraPathPoint(0, 0, 50);
			}
		});

		// Asset Loading
		String basePath = Project.ASSET_PATH;
		ImagePool imagePool = context.getImagePool();
		imagePool.addImage("pirate", basePath + "pirate.png", ImageLocation.JAR);
		imagePool.addImage("antagonist", basePath + "antagonist.png", ImageLocation.JAR);
		imagePool.addImage("backdrop", basePath + "backdrop.png", ImageLocation.JAR);
		imagePool.addImage("butterChicken", basePath + "butter_chicken.png", ImageLocation.JAR);
		shipImage = imagePool.getImage("smallShip", basePath + "small_ship.png", ImageLocation.JAR);

		// Scene Preload
		SceneRenderer protRenderer = (HUDGraph hud, Scene scene) -> {
			double scale = (50 / hud.getWidth());
			hud.drawImage(-25, hud.getHeight() - imagePool.getImage("pirate").getAWTImage().getHeight(null) / scale - 100, imagePool.getImage("pirate"), scale);
			scene.drawText(hud);
		};
		SceneRenderer antRenderer = (HUDGraph hud, Scene scene) -> {
			double scale = (50 / hud.getWidth());
			hud.drawImage(hud.getWidth() - imagePool.getImage("antagonist").getAWTImage().getWidth(null) / scale, hud.getHeight() - imagePool.getImage("antagonist").getAWTImage().getHeight(null) / scale - 100, imagePool.getImage("antagonist"), 50 / hud.getWidth());
			scene.drawText(hud);
		};

		// Scene Loading

		scenes.add(new Scene(this, "Gustavo: ", "Oh no, what's happening?", protRenderer));
		scenes.add(new Scene(this, "Gustavo: ", "Looks like the Butter Chicken is too heavy for the ship...", protRenderer));
		scenes.add(new Scene(this, "Mary: ", "Oh no, what did you do??", antRenderer));
		scenes.add(new Scene(this, "Mary: ", "First my butter chicken, then my crew and now my ship?", antRenderer));
		scenes.add(new Scene(this, "Gustavo: ", "I'm sorry, I di-", protRenderer));
		scenes.add(new Scene(this, "Mary: ", "How dare you? This requires revenge!", antRenderer));
		
		// First Scene Loading
		currentScene = scenes.remove(0);
	}

	@Override
	public void render(Graph g, double deltaTimeMS) {
		t += deltaTimeMS / 1000;
		int alpha = (int) (fade * 255 / 8);

		HUDGraph hud = g.getHUD();
		Keyboard keyboard = getContext().getKeyboard();
		
		if (alpha > 255) {
			getProject().setMenu(new GameOverMenu(game));
			return;
		}
		

		// Scene
		if (!fading && keyboard.isPressed(Keyboard.SPACE)) {
			if (interactionCooldown > 0) {
				interactionCooldown = maxInteractionCooldown;
			} else {
				if (currentScene.isComplete()) {
					if (scenes.size() == 0) {
						fading = true;
						getContext().getCamera().setCameraPath(new CameraPath() {
							private double t;
							@Override
							public CameraPathPoint getPoint(Camera camera, double deltaTimeMS) {
								t += deltaTimeMS / 1000;
								return new CameraPathPoint(0, t, 50);	
							}
						});
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

		// Drawing Part
		g.fillRect(-hud.getWidth(), -1, hud.getWidth(), 0, Graph.getColor(55, 129, 244));
		g.drawRotatedImage(shipImage.getAWTImage(), 0, 1 - (t / 20) - Math.cos(t * 1.2) * 0.1, 4, Math.sin(t) * 2 + 90);
		g.fillRect(-hud.getWidth(), -hud.getHeight(), hud.getWidth(), -1, Graph.getColor(55, 129, 244));

		if (!fading) {
			currentScene.advanceScene(deltaTimeMS);
			currentScene.render(hud);
		} else {
			hud.drawRect(0, 0, hud.getWidth(), hud.getHeight(), Graph.getColor(0, 0, 0, alpha));
			fade += deltaTimeMS / 1000;
		}
	}
	
	public void nextScene() {
		currentScene = scenes.remove(0);
	}
}
