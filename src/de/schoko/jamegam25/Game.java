package de.schoko.jamegam25;

import java.awt.Color;
import java.awt.Font;
import java.awt.Canvas;
import java.util.ArrayList;

import de.schoko.jamegam25.shapes.AnimatedImage;
import de.schoko.jamegam25.shapes.AnimatedImageFrame;
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
import de.schoko.rendering.Mouse;

public class Game extends Menu {
	public static final double MAX_WAVE_COOLDOWN = 10000;
	public static final int BOSS_FREQUENCY = 5; // Boss every 5 waves
	public static final int WIDTH = 20, HEIGHT = 10;
	public static final double TILE_SIZE = 15.5;

	private InventoryItem[] inventory;
	
	private Player player;
	private Tile[][] tiles;
	private ArrayList<GameObject> gameObjects;
	private ArrayList<GameObject> enemies;
	
	private int waveEnemyAmount;
	private int enemyLevel;
	private int wave;
	private double waveCooldown;
	private Boss bossFight;
	private int level;

	private SceneRenderer protRenderer;
	
	private ArrayList<Scene> pendingScenes;
	private Scene currentScene;

	private int totalKilledEnemyAmount;
	private double totalTime;
	private int totalPuddleAmount;

	public Game() {
		super(true);
	}

	@Override
	public void onLoad(Context context) {
		// Array Instanciation
		this.gameObjects = new ArrayList<>();
		this.enemies = new ArrayList<>();
		this.inventory = new InventoryItem[4];
		this.pendingScenes = new ArrayList<>();

		// Loading Stuff
		String basePath = Project.ASSET_PATH;
		ImagePool imagePool = context.getImagePool();
		imagePool.loadImage("tile", basePath + "tile.png", ImageLocation.JAR);
		imagePool.loadImage("tileHole", basePath + "tileHole.png", ImageLocation.JAR);
		imagePool.loadImage("tileHandrail", basePath + "tile_handrail.png", ImageLocation.JAR);
		imagePool.loadImage("wallTopFront", basePath + "wall_top_front.png", ImageLocation.JAR);
		imagePool.loadImage("wallLeftFront", basePath + "wall_left_front.png", ImageLocation.JAR);
		imagePool.loadImage("wallTopLeftFront", basePath + "wall_top_left_front.png", ImageLocation.JAR);
		imagePool.loadImage("wallRightFront", basePath + "wall_right_front.png", ImageLocation.JAR);
		imagePool.loadImage("wallTopRightFront", basePath + "wall_top_right_front.png", ImageLocation.JAR);
		imagePool.loadImage("wallTileFront", basePath + "wall_tile_front.png", ImageLocation.JAR);
		imagePool.loadImage("wallTopBack", basePath + "wall_top_back.png", ImageLocation.JAR);
		imagePool.loadImage("wallWater0", basePath + "wallWater_0.png", ImageLocation.JAR);
		imagePool.loadImage("wallWater1", basePath + "wallWater_1.png", ImageLocation.JAR);
		imagePool.loadImage("wallWater2", basePath + "wallWater_2.png", ImageLocation.JAR);
		imagePool.loadImage("wallBullseye", basePath + "wallBullseye.png", ImageLocation.JAR);
		imagePool.loadImage("wallCannon", basePath + "wallCannon.png", ImageLocation.JAR);
		imagePool.loadImage("wallHandrail", basePath + "wall_handrail.png", ImageLocation.JAR);
		imagePool.loadImage("slope1", basePath + "slope_1.png", ImageLocation.JAR);
		imagePool.loadImage("slope2", basePath + "slope_2.png", ImageLocation.JAR);
		imagePool.loadImage("handrailCorner", basePath + "handrail_corner.png", ImageLocation.JAR);
		imagePool.loadImage("handrailFree", basePath + "handrail_free.png", ImageLocation.JAR);
		imagePool.loadImage("playerLeft", basePath + "playerLeft.png", ImageLocation.JAR);
		imagePool.loadImage("playerRight", basePath + "playerRight.png", ImageLocation.JAR);
		imagePool.loadImage("playerLeftDamage", basePath + "playerLeftDamage.png", ImageLocation.JAR);
		imagePool.loadImage("playerRightDamage", basePath + "playerRightDamage.png", ImageLocation.JAR);
		imagePool.loadImage("protagonist", basePath + "pirate.png", ImageLocation.JAR);
		imagePool.loadImage("antagonist", basePath + "antagonist.png", ImageLocation.JAR);
		imagePool.loadImage("strongEnemy", basePath + "enemy_weak_left.png", ImageLocation.JAR);
		imagePool.loadImage("enemyWeakLeft", basePath + "enemy_weak_left.png", ImageLocation.JAR);
		imagePool.loadImage("enemyWeakRight", basePath + "enemy_weak_right.png", ImageLocation.JAR);
		imagePool.loadImage("enemyWeakLeftDamage", basePath + "enemy_weak_left_damage.png", ImageLocation.JAR);
		imagePool.loadImage("enemyWeakRightDamage", basePath + "enemy_weak_right_damage.png", ImageLocation.JAR);
		imagePool.loadImage("enemyStrongLeft", basePath + "enemy_strong_left.png", ImageLocation.JAR);
		imagePool.loadImage("enemyStrongRight", basePath + "enemy_strong_right.png", ImageLocation.JAR);
		imagePool.loadImage("enemyStrongLeftDamage", basePath + "enemy_strong_left_damage.png", ImageLocation.JAR);
		imagePool.loadImage("enemyStrongRightDamage", basePath + "enemy_strong_right_damage.png", ImageLocation.JAR);
		imagePool.loadImage("apple", basePath + "apple.png", ImageLocation.JAR);
		imagePool.loadImage("chilli", basePath + "chilli.png", ImageLocation.JAR);
		imagePool.loadImage("melon", basePath + "melon.png", ImageLocation.JAR);
		imagePool.loadImage("soda", basePath + "soda.png", ImageLocation.JAR);
		imagePool.loadImage("butterChicken", basePath + "butter_chicken.png", ImageLocation.JAR);
		imagePool.loadImage("butterChickenShadow", basePath + "butter_chicken_shadow.png", ImageLocation.JAR);
		imagePool.loadImage("puddle_s0", basePath + "puddle_s0.png", ImageLocation.JAR);
		imagePool.loadImage("puddle_s1", basePath + "puddle_s1.png", ImageLocation.JAR);
		imagePool.loadImage("puddle_s2", basePath + "puddle_s2.png", ImageLocation.JAR);
		imagePool.loadImage("puddle_s3", basePath + "puddle_s3.png", ImageLocation.JAR);
		imagePool.loadImage("barrel_s0", basePath + "barrel_s0.png", ImageLocation.JAR);
		imagePool.loadImage("barrel_s1", basePath + "barrel_s1.png", ImageLocation.JAR);
		imagePool.loadImage("barrelShadow", basePath + "barrel_shadow.png", ImageLocation.JAR);
		
		// Tile setup part
		this.tiles = genTiles();

		// Item Setup Part
		inventory[0] = new InventoryItem(imagePool.getImage("apple"), 0, "Apple", "Heals you", Keyboard.ONE, (Player player) -> {
			player.setMaxHealth(player.getMaxHealth() + 1);
		});
		inventory[1] = new InventoryItem(imagePool.getImage("chilli"), 0, "Chilli", "Makes you faster", Keyboard.TWO, (Player player) -> {
			player.setSpeed(player.getSpeed() + 0.5);
		});
		inventory[2] = new InventoryItem(imagePool.getImage("melon"), 0, "Melon", "Heals you over time", Keyboard.THREE, (Player player) -> {
			player.addHeal();
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
		context.getSettings().setBackgroundColor(55, 129, 244);

		// Scene Preload

		protRenderer = (HUDGraph hud, Scene scene) -> {
			double scale = (50 / hud.getWidth());
			Image image = imagePool.getImage("protagonist");
			hud.drawImage(25, hud.getHeight() - image.getAWTImage().getHeight(null) / scale - 100, image, scale);
			
			scene.drawText(hud);
		};

		// Object Creation
		player = new Player(this, context, 0, 0);

		playScene(new Scene(this, "Gustavo: ", "This soup is hot, maybe I can throw it using the left mouse button.", protRenderer));
		playScene(new Scene(this, "Gustavo: ", "Oh no, I did not intend the side effect: I should not step into the puddles.", protRenderer));
		playScene(new Scene(this, "Gustavo: ", "Maybe I can also walk around using WASD/↑←↓→", protRenderer));
	}

	@Override
	public void render(Graph g, double deltaTimeMS) {
		HUDGraph hud = g.getHUD();
		Keyboard keyboard = getContext().getKeyboard();
		Mouse mouse = getContext().getMouse();

		// Stats part
		totalTime += deltaTimeMS / 1000;

		// Wave Part

		if (wave == 0 && currentScene != null) {
			waveCooldown = 5000;
		}

		if (wave % BOSS_FREQUENCY != 0 || wave == 0) {
			if (waveCooldown > 0) {
				waveCooldown -= deltaTimeMS;
			} else {
				if (enemies.size() > 0) {
					// There are already enemies

					enemies.removeIf((GameObject enemy) -> {
						if (enemy.isRemoved()) {
							totalKilledEnemyAmount++;
							return true;
						} else {
							return false;
						}
					});

					if (enemies.size() == 0) {
						waveCooldown = MAX_WAVE_COOLDOWN;
						enemyLevel++;
						wave++;
						if (wave % BOSS_FREQUENCY == 0) {
							bossFight = Boss.getRandom(this);
						}
					}
				} else {
					// Spawn new enemies
					waveEnemyAmount = (int) Math.round((enemyLevel * enemyLevel) * 0.05 + enemyLevel * Math.random() + 1);
					spawn(waveEnemyAmount);
				}
			}
		} else {
			if (bossFight.isCompleted()) {
				wave++;
				bossFight = null;
			}
			enemies.removeIf((GameObject enemy) -> {
				if (enemy.isRemoved()) {
					totalKilledEnemyAmount++;
					return true;
				} else {
					return false;
				}
			});
		}

		// Game Object Stuff
		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[x].length; y++) {
				if (tiles[x][y] != null) {
					tiles[x][y].render(g, deltaTimeMS);
				}
			}
		}
		g.drawRect(-WIDTH / 2.0 - 0.5, -HEIGHT / 2.0 - 0.5, WIDTH / 2.0 + 0.5, HEIGHT / 2.0 + 0.5);
		
		this.gameObjects.sort((GameObject a, GameObject b) -> {
			return a.getZ() - b.getZ();
		});

		int puddleAmount = 0;
		for (int i = 0; i < gameObjects.size(); i++) {
			gameObjects.get(i).render(g, deltaTimeMS);
			if (gameObjects.get(i) instanceof Puddle) {
				puddleAmount++;
			}
		}
		if (puddleAmount == 125 && level == 0) {
			increaseLevel();
			playScene(new Scene(this, "Gustavo: ", "Wait a minute... Did the ship just sink deeper?", protRenderer));
			playScene(new Scene(this, "Gustavo: ", "Oh no... what an unintended side effect...", protRenderer));
		}
		if (puddleAmount == 250 && level == 1) {
			increaseLevel();
		}
		
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).render(g, deltaTimeMS);
		}

		if (bossFight != null) {
			bossFight.render(g, deltaTimeMS);
		}

		player.render(g, deltaTimeMS);

		// Removing objects that are marked as removed
		gameObjects.removeIf((GameObject gameObject) -> {
			return gameObject.isRemoved();
		});
		
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

		// Scene stuff
		if (currentScene != null) {
			if (keyboard.wasRecentlyPressed(Keyboard.SPACE)) {
				if (currentScene.isComplete()) {
					endScene();
				} else {
					currentScene.skipToEnd();
				}
			}
			if (currentScene != null) {
				currentScene.advanceScene(deltaTimeMS);
				currentScene.render(hud);
			}
		}

		// HUD Rendering Part
		hud.drawText("Wave: " + wave, 5, 25, Color.RED, new Font("Segoe UI", Font.BOLD, 25));
		
		// Wave Bar Part
		if (wave == 0) {
			waveEnemyAmount = 1;
		}
		double perc = enemies.size() / waveEnemyAmount;
		String title = "Enemies: " + enemies.size() + " / " + waveEnemyAmount;
		if (waveCooldown > 0) {
			perc = (MAX_WAVE_COOLDOWN - waveCooldown) / MAX_WAVE_COOLDOWN;
			title = "Building Wave " + wave;
		}
		if (bossFight != null) {
			// Boss wave
			title = bossFight.getName();
			perc = bossFight.getProgress();
		}
		if (wave == 0) {
			perc = 1;
			title = "Wave Coming";
		}
		hud.drawBar(5, 38.5, 100, 15, perc, Graph.getColor(177, 0, 150), Graph.getColor(127, 0, 100));
		hud.drawText(title, 10, 50, Color.BLACK, new Font("Segoe UI", Font.PLAIN, 12));
		hud.drawBar(5, 60, 100, 15, player.getHealth() / player.getMaxHealth(), Graph.getColor(255, 0, 0), Graph.getColor(200, 0, 0));
		hud.drawText("Health: " + (int) (Math.round(player.getHealth())) + " / " + (int) (Math.round(player.getMaxHealth())), 10, 71.5, Color.BLACK, new Font("Segoe UI", Font.PLAIN, 12));
	}

	public void spawn(int max) {
		double spawnWidth = WIDTH * 0.9;
		for (int i = 0; i < max; i++) {
			double x = Math.random() * spawnWidth - WIDTH / 2;
			double y = 0;
			if (Math.random() >= 0.5) {
				// Spawn at top
				y = HEIGHT / 2 + 0.5;
			} else {
				// Spawn at bottom
				y = - HEIGHT / 2 - 0.5;
			}
			addEnemy(new Enemy(this, player, x, y));
		}
	}

	// TODO: Implement actual ship sinking (Game Over)
	// Makes ship heavier so it will be lower in the water and eventually sink
	public void increaseLevel() {
		level++;
		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[x].length; y++) {
				if (y == level - 1) {
					tiles[x][y] = null;
				}
				if (y == level) {
					tiles[x][y].setShape(getWaterWallTile());
				}
			}
		}
	}

	public Tile[][] genTiles() {
		Tile[][] tiles = new Tile[WIDTH + 2 + 25][HEIGHT + 2 + 8];
		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[x].length; y++) {
				String img = "tile";
				if (x > tiles.length - 10) {
					if (y < 6) {
						img = "wallTileFront";
						if (y > 3) {
							if (x == tiles.length - 9) {
								img = "wallLeftFront";
							}
						}
					} else if (y == 6) {
						if (x == tiles.length - 9) {
							img = "wallTopLeftFront";
						} else {
							img = "wallTopFront";
						}
					} else if (y == 7) {
						img = "wallHandrail";
					} else if (y > 7) {
						if (y == tiles[0].length - 1) {
							if (x == tiles.length - 9) {
								img = "handrailCorner";
							} else {
								img = "handrailFree";
							}
						} else {
							if (x == tiles.length - 9) {
								img = "tileHandrail";
							}
						}
					}
				} else if (x < 6) {
					if (y < 6) {
						img = "wallTileFront";
						if (y > 3) {
							if (x == 5) {
								img = "wallRightFront";
							}
						}
					} else if (y == 6) {
						if (x == 5) {
							img = "wallTopRightFront";
						} else {
							img = "wallTopFront";
						}
					} else if (y == 7) {
						img = "wallHandrail";
					} else if (y > 7) {
						if (y == tiles[0].length - 1) {
							if (x == 5) {
								img = "handrailCorner";
							} else {
								img = "handrailFree";
							}
						} else {
							if (x == 5) {
								img = "tileHandrail";
							}
						}
					}
				} else if (y == 2 &&
					      ((x + 0) % 6 == 1)) {
					img = "wallBullseye";
				} else if (y == 2 &&
						  ((x + 3) % 6 == 1)) {
		  			img = "wallCannon";
	  			} else if (y < 3) {
					img = "wallTileFront";
				} else if (y == 3) {
					img = "wallTopFront";
				} else if (y >= tiles[x].length - 3) {
					continue;
				} else if (y == tiles[x].length - 4) {
					img = "wallTopBack";
				}
				if (y == 0) {
					tiles[x][y] = new Tile(x - tiles.length / 2 + 0.5, y - tiles[x].length / 2 + 0.5, getWaterWallTile());
					continue;
				}
				tiles[x][y] = new Tile(x - tiles.length / 2 + 0.5, y - tiles[x].length / 2 + 0.5, getContext().getImagePool().getImage(img), TILE_SIZE);
			}
		}
		return tiles;
	}

	public void playScene(Scene scene) {
		if (currentScene == null) {
			currentScene = scene;
		} else {
			pendingScenes.add(scene);
		}
	}

	public void endScene() {
		if (pendingScenes.size() > 0) {
			currentScene = pendingScenes.remove(0);
		} else {
			currentScene = null;
		}
	}

	public void addObject(GameObject gameObject) {
		if (gameObject instanceof Puddle) totalPuddleAmount++;
		this.gameObjects.add(gameObject);
	}

	public void addEnemy(GameObject enemy) {
		this.enemies.add(enemy);
	}

	public Tile getTile(double x, double y) {
		return tiles[(int) (Math.floor(x))][(int) (Math.floor(y))];
	}

	public void setTile(Tile tile, double x, double y) {
		tiles[(int) (Math.floor(x))][(int) (Math.floor(y))] = tile;
	}

	public AnimatedImageFrame getWaterWallTile() {
		return new AnimatedImageFrame(0, 0,
						TILE_SIZE, new AnimatedImage(getContext().getImagePool().getImage("wallWater0"), 500),
						new AnimatedImage(getContext().getImagePool().getImage("wallWater1"), 500),
						new AnimatedImage(getContext().getImagePool().getImage("wallWater2"), 500));
	}

	public ArrayList<GameObject> getEnemies() {
		return enemies;
	}

	public ArrayList<GameObject> getObjects() {
		return gameObjects;
	}

	public double getWidth() {
		return WIDTH;
	}

	public double getHeight() {
		return HEIGHT;
	}

	public Tile[][] getTiles() {
		return tiles;
	}

	public InventoryItem[] getInventory() {
		return inventory;
	}

	public Player getPlayer() {
		return player;
	}

	public int getTotalKilledEnemyAmount() {
		return totalKilledEnemyAmount;
	}

	public double getTotalTime() {
		return totalTime;
	}

	public int getTotalPuddleAmount() {
		return totalPuddleAmount;
	}
}
