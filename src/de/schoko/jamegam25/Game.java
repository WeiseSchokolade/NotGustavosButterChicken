package de.schoko.jamegam25;

import java.awt.Color;
import java.awt.Font;
import java.awt.Canvas;
import java.util.ArrayList;

import de.schoko.rendering.Camera;
import de.schoko.rendering.CameraPath;
import de.schoko.rendering.CameraPathPoint;
import de.schoko.rendering.Context;
import de.schoko.rendering.Graph;
import de.schoko.rendering.HUDGraph;
import de.schoko.rendering.ImageLocation;
import de.schoko.rendering.ImagePool;
import de.schoko.rendering.Keyboard;
import de.schoko.rendering.Mouse;

public class Game extends Menu {
	public static final double MAX_WAVE_COOLDOWN = 10000;

	private Player player;
	private Tile[][] tiles;
	private InventoryItem[] inventory;
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
		this.inventory = new InventoryItem[4];
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
		imagePool.addImage("apple", basePath + "apple.png", ImageLocation.JAR);
		imagePool.addImage("chilli", basePath + "chilli.png", ImageLocation.JAR);
		imagePool.addImage("melon", basePath + "melon.png", ImageLocation.JAR);
		imagePool.addImage("soda", basePath + "soda.png", ImageLocation.JAR);
		
		// Item Setup Part
		inventory[0] = new InventoryItem(imagePool.getImage("apple"), 0, "Apple", "Heals you", Keyboard.ONE, (Player player) -> {
			player.setMaxHealth(player.getMaxHealth() + 1);
		});
		inventory[1] = new InventoryItem(imagePool.getImage("chilli"), 0, "Chilli", "Makes you faster", Keyboard.TWO, (Player player) -> {
			player.setSpeed(player.getSpeed() + 0.5);
		});
		inventory[2] = new InventoryItem(imagePool.getImage("melon"), 0, "Melon", "Heals you over time", Keyboard.THREE, (Player player) -> {
			// TODO: Heal player over time
		});
		inventory[3] = new InventoryItem(imagePool.getImage("soda"), 0, "Soda", "Makes you stronger", Keyboard.FOUR, (Player player) -> {
			player.setDamage(player.getDamage() + 0.5);
		});

		// Visual Stuff
		context.getCamera().setCameraPath(new CameraPath() {
			private double t;
			@Override
			public CameraPathPoint getPoint(Camera camera, double deltaTimeMS) {
				t += deltaTimeMS / 1000;
				return new CameraPathPoint(player.getX(), player.getY() + Math.sin(t) * 0.25, 60);
			}
		});
		context.getSettings().setBackgroundColor(0, 20, 200);
		
		// Object Creation
		player = new Player(this, context, 0, 0);

		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[x].length; y++) {
				tiles[x][y] = new Tile(x - width / 2 + 0.5, y - height / 2 + 0.5, imagePool.getImage("tile"), 16);
			}
		}
	}

	@Override
	public void render(Graph g, double deltaTimeMS) {
		HUDGraph hud = g.getHUD();
		Keyboard keyboard = getContext().getKeyboard();
		Mouse mouse = getContext().getMouse();

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
				waveEnemyAmount = (int) Math.round((wave * wave) * 0.05 + wave * Math.random() + 1);
				spawn(waveEnemyAmount);
			}
		}

		// Inventory Part
		if (getContext().getKeyboard().isPressed(Keyboard.TAB)) {
			double extraSpacing = 25;
			double textureScale = 0.5;
			final double itemTextureSize = 16 / textureScale;

			double inventoryWidth = 200;
			double inventoryHeight = 100;
			
			hud.drawRect(hud.getWidth() / 2 - inventoryWidth / 2, 10, inventoryWidth, inventoryHeight, Graph.getColor(127, 127, 127, 200));
			double baseX = hud.getWidth() / 2 - inventoryWidth / 2 + extraSpacing;
			double baseY = 10 + extraSpacing;
			Font font = new Font("Segoe UI", Font.PLAIN, 15);
			hud.drawText("Key: ", baseX - extraSpacing + 2, baseY - 10, Color.BLACK, font);
			for (int i = 0; i < inventory.length; i++) {
				InventoryItem item = inventory[i];
				double drawX = baseX + ((double) i / inventory.length) * (inventoryWidth - extraSpacing);
				hud.drawText("" + (i + 1), drawX + 10, baseY - 10, Color.WHITE, font);
				hud.drawImage(drawX, baseY, item.getImage(), textureScale);
				Color color = Color.WHITE;
				if (item.getAmount() == 0) {
					color = Color.RED;
				} else if (item.getAmount() == 1) {
					color = Color.YELLOW;
				}
				hud.drawText("  " + item.getAmount(), drawX + 10, baseY + 30, color, font);
				if (keyboard.wasRecentlyPressed(item.getKey())) {
					item.use(player);
				}
			}
			for (int i = 0; i < inventory.length; i++) {
				InventoryItem item = inventory[i];
				double drawX = baseX + ((double) i / inventory.length) * (inventoryWidth - extraSpacing);
				if (mouse.getScreenX() > drawX + extraSpacing / 2 - itemTextureSize / 2 && mouse.getScreenX() < drawX + extraSpacing / 2 + itemTextureSize / 2) {
					if (mouse.getScreenY() > baseY && mouse.getScreenY() < baseY + itemTextureSize) {
						Canvas canvas = new Canvas();
						final int margin = 2;
						String text1 = "" + item.getAmount() + " x " + item.getName();
						double nameWidth = canvas.getFontMetrics(font).stringWidth(text1);
						double descriptionWidth = canvas.getFontMetrics(font).stringWidth(item.getDescription());
						double width = (nameWidth > descriptionWidth) ? nameWidth : descriptionWidth;
						hud.drawRect(mouse.getScreenX(), mouse.getScreenY() - 13, width + 2 * 2, 15 * 2 + margin + margin * 2, Color.WHITE);
						hud.drawText(text1, mouse.getScreenX() + margin, mouse.getScreenY() + margin, Color.BLACK, font);
						hud.drawText(item.getDescription(), mouse.getScreenX() + margin, mouse.getScreenY() + margin * 2 + 15, Color.BLACK, font);
					}
				}
			}
		} else {
			String text = "Press TAB to view inventory";
			Font font = new Font("Segoe UI", Font.PLAIN, 15);
			Canvas canvas = new Canvas();
			double textWidth = canvas.getFontMetrics(font).stringWidth(text);
			hud.drawText(text, hud.getWidth() / 2 - textWidth / 2, 25, Color.WHITE, font);
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
		hud.drawText("Wave: " + wave, 5, 25, Color.RED, new Font("Segoe UI", Font.BOLD, 25));
		
		// Wave Bar Part
		double perc = enemies.size() / waveEnemyAmount;
		String title = "Enemies: " + enemies.size() + " / " + waveEnemyAmount;
		if (waveCooldown > 0) {
			perc = (MAX_WAVE_COOLDOWN - waveCooldown) / MAX_WAVE_COOLDOWN;
			title = "Building Wave " + wave;
		}
		hud.drawBar(5, 38.5, 100, 15, perc, Graph.getColor(177, 0, 150), Graph.getColor(127, 0, 100));
		hud.drawText(title, 10, 50, Color.BLACK, new Font("Segoe UI", Font.PLAIN, 12));
		hud.drawBar(5, 60, 100, 15, player.getHealth() / player.getMaxHealth(), Graph.getColor(255, 0, 0), Graph.getColor(200, 0, 0));
		hud.drawText("Health: " + (int) (Math.round(player.getHealth())) + " / " + (int) (Math.round(player.getMaxHealth())), 10, 71.5, Color.BLACK, new Font("Segoe UI", Font.PLAIN, 12));
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
			addEnemy(new Enemy(this, player, x, y));
		}
	}

	public void addObject(GameObject gameObject) {
		this.gameObjects.add(gameObject);
	}

	public void addEnemy(Enemy enemy) {
		this.enemies.add(enemy);
	}

	public Tile getTile(double x, double y) {
		return tiles[(int) (Math.floor(x))][(int) (Math.floor(y))];
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public InventoryItem[] getInventory() {
		return inventory;
	}

	public Player getPlayer() {
		return player;
	}
}
