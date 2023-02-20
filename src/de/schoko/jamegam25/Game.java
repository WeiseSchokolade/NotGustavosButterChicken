package de.schoko.jamegam25;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import de.schoko.rendering.Camera;
import de.schoko.rendering.CameraPath;
import de.schoko.rendering.CameraPathPoint;
import de.schoko.rendering.Context;
import de.schoko.rendering.Graph;
import de.schoko.rendering.HUDGraph;
import de.schoko.rendering.ImageLocation;
import de.schoko.rendering.ImagePool;

public class Game extends Menu {
	public static final double MAX_WAVE_COOLDOWN = 10000;

	private Player player;
	private Tile[][] tiles;
	private double width, height;
	private ArrayList<GameObject> gameObjects;
	private ArrayList<Enemy> enemies;
	private int waveEnemyAmount;
	private int wave;
	private double waveCooldown;

	@Override
	public void onLoad(Context context) {
		// Array Instanciation
		this.gameObjects = new ArrayList<>();
		this.enemies = new ArrayList<>();
		this.tiles = new Tile[20][10];
		width = tiles.length;
		height = tiles[0].length;

		// Loading Stuff
		String basePath = "de/schoko/jamegam25/assets/";
		ImagePool imagePool = context.getImagePool();
		imagePool.addImage("tile", basePath + "tile.png", ImageLocation.JAR);
		imagePool.addImage("playerUp", basePath + "playerUp.png", ImageLocation.JAR);
		imagePool.addImage("playerDown", basePath + "playerDown.png", ImageLocation.JAR);
		imagePool.addImage("playerLeft", basePath + "playerLeft.png", ImageLocation.JAR);
		imagePool.addImage("playerRight", basePath + "playerRight.png", ImageLocation.JAR);

		// Visual Stuff
		context.getCamera().setCameraPath(new CameraPath() {
			private double t;
			@Override
			public CameraPathPoint getPoint(Camera camera, double deltaTimeMS) {
				t += deltaTimeMS / 1000;
				return new CameraPathPoint(player.getX(), player.getY() + Math.sin(t) * 0.25, 50);
			}
		});
		context.getSettings().setBackgroundColor(0, 20, 200);
		

		// Object Creation
		player = new Player(this, context, 0, 0);

		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[x].length; y++) {
				tiles[x][y] = new Tile(x - tiles.length / 2 + 0.5, y - tiles[x].length / 2 + 0.5, imagePool.getImage("tile"), 8);
			}			
		}
	}

	@Override
	public void render(Graph g, double deltaTimeMS) {

		// Wave Part
		if (waveCooldown > 0) {
			waveCooldown -= deltaTimeMS;
		} else {
			if (enemies.size() > 0) {
				// There are already enemies

				enemies.removeIf((Enemy enemy) -> {
					return enemy.isRemoved();
				});

				if (enemies.size() == 0) {
					waveCooldown = MAX_WAVE_COOLDOWN;
					wave++;
					
				}
			} else {
				// Spawn new enemies
				waveEnemyAmount = (int) Math.round((wave * wave) * 0.05) + wave + 1;
				spawn(waveEnemyAmount);
			}
		}

		// Rendering Part
		g.drawRect(-width / 2, -height / 2, width / 2, height / 2);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (tiles[x][y] != null) {
					tiles[x][y].render(g, deltaTimeMS);
				}
			}
		}
		
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).render(g, deltaTimeMS);
		}

		for (int i = 0; i < gameObjects.size(); i++) {
			gameObjects.get(i).render(g, deltaTimeMS);
		}

		player.render(g, deltaTimeMS);

		// Removing objects that are marked as removed
		gameObjects.removeIf((GameObject gameObject) -> {
			return gameObject.isRemoved();
		});

		// HUD Rendering Part
		HUDGraph hud = g.getHUD();
		hud.drawText("Wave: " + wave, 5, 25, Color.RED, new Font("Segoe UI", Font.BOLD, 25));
		
		double perc = enemies.size() / waveEnemyAmount;
		String title = "Enemies: " + enemies.size() + " / " + waveEnemyAmount;
		if (waveCooldown > 0) {
			perc = (MAX_WAVE_COOLDOWN - waveCooldown) / MAX_WAVE_COOLDOWN;
			title = "Building Wave " + wave;
		}

		hud.drawBar(5, 38.5, 100, 15, perc, Graph.getColor(177, 0, 150), Graph.getColor(127, 0, 100));
		hud.drawText(title, 10, 50, Color.BLACK, new Font("Segoe UI", Font.PLAIN, 12));
	}

	public void spawn(int max) {
		double spawnWidth = width * 0.9;
		for (int i = 0; i < max; i++) {
			double x = Math.random() * spawnWidth - width / 2;
			double y = 0;
			if (Math.random() >= 0.5) {
				// Spawn at top
				y = height / 2 + 2;
			} else {
				// Spawn at bottom
				y = - height / 2 - 2;
			}
			addEnemy(new Enemy(player, x, y));
		}
	}

	public void addObject(GameObject gameObject) {
		this.gameObjects.add(gameObject);
	}

	public void addEnemy(Enemy enemy) {
		this.enemies.add(enemy);
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}
}
