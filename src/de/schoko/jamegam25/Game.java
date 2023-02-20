package de.schoko.jamegam25;

import java.util.ArrayList;

import de.schoko.rendering.Camera;
import de.schoko.rendering.CameraPath;
import de.schoko.rendering.CameraPathPoint;
import de.schoko.rendering.Context;
import de.schoko.rendering.Graph;
import de.schoko.rendering.ImageLocation;
import de.schoko.rendering.ImagePool;

public class Game extends Menu {
	private Player player;
	private Tile[][] tiles;
	private ArrayList<GameObject> gameObjects;

	@Override
	public void onLoad(Context context) {
		this.gameObjects = new ArrayList<>();
		this.tiles = new Tile[20][10];

		String basePath = "de/schoko/jamegam25/assets/";
		ImagePool imagePool = context.getImagePool();
		imagePool.addImage("tile", basePath + "tile.png", ImageLocation.JAR);
		imagePool.addImage("playerUp", basePath + "playerUp.png", ImageLocation.JAR);
		imagePool.addImage("playerDown", basePath + "playerDown.png", ImageLocation.JAR);
		imagePool.addImage("playerLeft", basePath + "playerLeft.png", ImageLocation.JAR);
		imagePool.addImage("playerRight", basePath + "playerRight.png", ImageLocation.JAR);

		context.getCamera().setCameraPath(new CameraPath() {
			private double t;
			@Override
			public CameraPathPoint getPoint(Camera camera, double deltaTimeMS) {
				t += deltaTimeMS / 1000;
				return new CameraPathPoint(player.getX(), player.getY() + Math.sin(t) * 0.25, 60);
			}
		});

		player = new Player(this, context, 0, 0);


		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[x].length; y++) {
				tiles[x][y] = new Tile(x - tiles.length / 2 + 0.5, y - tiles.length / 2 + 0.5, imagePool.getImage("tile"), 16);
			}			
		}
	}

	@Override
	public void render(Graph g, double deltaTimeMS) {
		double width = tiles.length;
		double height = tiles[0].length;

		g.drawRect(-width / 2, -height / 2, width / 2, height / 2);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (tiles[x][y] != null) {
					tiles[x][y].render(g, deltaTimeMS);
				}
			}
		}
		
		player.render(g, deltaTimeMS);

		for (int i = 0; i < gameObjects.size(); i++) {
			gameObjects.get(i).render(g, deltaTimeMS);
		}

	}

	public void addObject(GameObject gameObject) {
		this.gameObjects.add(gameObject);
	}
}
