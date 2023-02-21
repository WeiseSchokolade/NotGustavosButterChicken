package de.schoko.jamegam25;

import java.util.ArrayList;

import de.schoko.rendering.Context;
import de.schoko.rendering.Graph;
import de.schoko.rendering.Image;
import de.schoko.rendering.Keyboard;
import de.schoko.rendering.Mouse;
import de.schoko.rendering.shapes.ImageFrame;

public class Player extends GameObject {
	private Context context;
	private Game game;
	private ImageFrame imageFrame;
	private int direction;
	private double health;
	private double damage;
	private Image[] images;
	private double speed;
	private double shootCooldown;
	private double maxShootCooldown = 300;
	private ArrayList<Integer> pressedKeys;

	public Player(Game game, Context context, double x, double y) {
		super(x, y);
		this.context = context;
		this.game = game;
		this.speed = 4;
		this.health = 3;
		this.damage = 1;
		images = new Image[4];
		images[0] = context.getImagePool().getImage("playerUp");
		images[1] = context.getImagePool().getImage("playerDown");
		images[2] = context.getImagePool().getImage("playerRight");
		images[3] = context.getImagePool().getImage("playerLeft");

		imageFrame = new ImageFrame(x, y, images[direction], 16);

		pressedKeys = new ArrayList<>();
	}

	@Override
	public void render(Graph g, double deltaTimeMS) {
		// Moving Part
		Keyboard keyboard = context.getKeyboard();

		double oldX = x;
		double oldY = y;

		checkKey(Keyboard.A);
		checkKey(Keyboard.LEFT);
		checkKey(Keyboard.D);
		checkKey(Keyboard.RIGHT);
		checkKey(Keyboard.S);
		checkKey(Keyboard.DOWN);
		checkKey(Keyboard.W);
		checkKey(Keyboard.UP);
		int lastMovementKey = -1;
		if (pressedKeys.size() > 0) {
			lastMovementKey = pressedKeys.get(0);
		}
		if (lastMovementKey == Keyboard.A || lastMovementKey == Keyboard.LEFT) {
			direction = 3;
			x -= speed * deltaTimeMS / 1000;
		} else if (lastMovementKey == Keyboard.D || lastMovementKey == Keyboard.RIGHT) {
			direction = 2;
			x += speed * deltaTimeMS / 1000;
		} else if (lastMovementKey == Keyboard.W || lastMovementKey == Keyboard.UP) {
			direction = 0;
			y += speed * deltaTimeMS / 1000;
		} else if (lastMovementKey == Keyboard.S || lastMovementKey == Keyboard.DOWN) {
			direction = 1;
			y -= speed * deltaTimeMS / 1000;
		}

		// Bounding box part

		if (x > game.getWidth() / 2 || x < -game.getWidth() / 2) {
			x = oldX;
		}
		if (y > game.getHeight() / 2 || y < -game.getHeight() / 2) {
			y = oldY;
		}


		// Shooting Part
		Mouse mouse = context.getMouse();
		if (mouse.isPressed(Mouse.LEFT_BUTTON) && shootCooldown <= 0) {
			shootCooldown = maxShootCooldown;
			double a = mouse.getX() - this.x;
			double b = mouse.getY() - this.y;
			double direction = Math.atan2(b, a);
			game.addObject(new Bullet(this.x, this.y, direction, this.damage, game.getEnemies()));
		} else if (shootCooldown > 0) {
			shootCooldown -= deltaTimeMS;
		}

		// Drawing Part
		imageFrame.setX(this.x);
		imageFrame.setY(this.y);
		imageFrame.setImage(images[direction]);
		g.draw(imageFrame);
	}

	public void checkKey(int key) {
		Keyboard keyboard = context.getKeyboard();
		if (keyboard.wasRecentlyPressed(key)) {
			pressedKeys.add(0, (Integer) key);
		}
		if (!keyboard.isPressed(key) && pressedKeys.contains(key)) {
			pressedKeys.remove((Integer) key);
		}
	}
	
	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
	}

	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}
}
