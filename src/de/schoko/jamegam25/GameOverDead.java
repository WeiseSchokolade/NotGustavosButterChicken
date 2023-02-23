package de.schoko.jamegam25;

import java.awt.Color;
import java.awt.Font;

import de.schoko.jamegam25.shapes.Button;
import de.schoko.rendering.Camera;
import de.schoko.rendering.CameraPath;
import de.schoko.rendering.CameraPathPoint;
import de.schoko.rendering.Context;
import de.schoko.rendering.Graph;
import de.schoko.rendering.HUDGraph;
import de.schoko.rendering.ImageLocation;
import de.schoko.rendering.ImagePool;
import de.schoko.rendering.shapes.ImageFrame;

public class GameOverDead extends Menu {
	private Tile[][] tiles;
	private ImageFrame protagonist;
	private ImageFrame hat;
	private ImageFrame antagonist;
	private boolean fading;
	private double fade;
	private Button mainMenuButton;

	public GameOverDead(Game game) {
		super(true);
		tiles = game.genTiles();
	}

	@Override
	public void onLoad(Context context) {

		// Image Loading
		String basePath = Project.ASSET_PATH;
		ImagePool imagePool = context.getImagePool();
		imagePool.addImage("button", basePath + "button.png", ImageLocation.JAR);
		imagePool.addImage("pirateDead", basePath + "pirateDead.png", ImageLocation.JAR);
		imagePool.addImage("antagonist", basePath + "antagonist.png", ImageLocation.JAR);
		imagePool.addImage("hat", basePath + "hat.png", ImageLocation.JAR);

		// Object Instanciation
		protagonist = new ImageFrame(0, 0, imagePool.getImage("pirateDead"), 32);
		hat = new ImageFrame(1.5, 0.2, imagePool.getImage("hat"), 16);
		antagonist = new ImageFrame(-2, -2, imagePool.getImage("antagonist"), 14);
		
		// Camera
		context.getCamera().setCameraPath(new CameraPath() {
			private double t;
			@Override
			public CameraPathPoint getPoint(Camera camera, double deltaTimeMS) {
				t += deltaTimeMS / 1000;
				if (t > 10) {
					fading = true;
				}
				return new CameraPathPoint(0, 0, 50 + t * t * 2);
			}
		});
	}

	@Override
	public void render(Graph g, double deltaTimeMS) {
		HUDGraph hud = g.getHUD();

		int alpha = (int) (fade * 255 / 8);
		if (alpha > 255) {
			if (fading) {
				fading = false;
				getContext().getCamera().setCameraPath((Camera camera, double dt) -> {
					return new CameraPathPoint(0, 0, 75);
				});
				getContext().getSettings().setBackgroundColor(0, 0, 0);
				mainMenuButton = new Button("Main Menu", 0, -2, 1.5, 0.75, "button", 16, getContext());
			}
			g.draw(mainMenuButton);

			if (mainMenuButton.pressed()) {
				getProject().setMenu(new MainMenu());
				return;
			}

			String text = "GAME OVER";
			Font font = new Font("Consolas", Font.BOLD, 50);
			double textWidth = Graph.getStringWidth(text, font);
			hud.drawText("GAME OVER", hud.getWidth() / 2 - textWidth / 2, hud.getHeight() / 2, Color.RED, font);

			return; // Return because we don't need to render the other stuff
		}

		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[x].length; y++) {
				if (tiles[x][y] != null) {
					tiles[x][y].render(g, deltaTimeMS);
				}
			}
		}

		// Rendering
		g.draw(hat);
		g.draw(protagonist);
		g.draw(antagonist);

		if (fading) {
			hud.drawRect(0, 0, hud.getWidth(), hud.getHeight(), Graph.getColor(0, 0, 0, alpha));
			fade += deltaTimeMS / 1000;
		}
	}
}
