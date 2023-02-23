package de.schoko.jamegam25;

import java.awt.Color;
import java.awt.Font;

import de.schoko.jamegam25.shapes.Button;
import de.schoko.rendering.Camera;
import de.schoko.rendering.CameraPath;
import de.schoko.rendering.CameraPathPoint;
import de.schoko.rendering.Context;
import de.schoko.rendering.Graph;
import de.schoko.rendering.ImageLocation;
import de.schoko.rendering.ImagePool;

public class MainMenu extends Menu {
	private Sound sound;
	private Button playButton;
	private Button skipButton;

	public MainMenu() {
		super(false);
	}

	@Override
	public void onLoad(Context context) {
		context.getSettings().setBackgroundColor(78, 188, 208);

		ImagePool imagePool = context.getImagePool();
		imagePool.addImage("button", Project.ASSET_PATH + "button.png", ImageLocation.JAR);

		playButton = new Button("Play", 0, 0, 1.5, 0.5, "button", 32, context);
		skipButton = new Button("Skip", 0, -0.5, 1.5, 0.5, "button", 32, context);

		sound = new Sound(this, Project.ASSET_PATH + "menu_song.wav", false);
		sound.start();
		
		context.getCamera().setCameraPath(new CameraPath() {
			private double t;
			@Override
			public CameraPathPoint getPoint(Camera camera, double deltaTimeMS) {
				t += deltaTimeMS / 1000;
				return new CameraPathPoint(0, Math.sin(t) * 0.0625, 150);
			}
		});
	}


	@Override
	public void render(Graph g, double deltaTimeMS) {
		
		Font font = new Font("Segoe UI", Font.PLAIN, 25);
		g.drawString("Game Name", -0.5, 0, Color.BLACK, font);
		
		if (playButton.pressed()) {
			getProject().setMenu(new IntroMenu());
			return;
		}
		if (skipButton.pressed()) {
			getProject().setMenu(new Game());
			return;
		}
		g.draw(playButton);
		g.draw(skipButton);
	}
}
