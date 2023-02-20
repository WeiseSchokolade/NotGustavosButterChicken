package de.schoko.jamegam25;

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
	private Image[] images;
	private double speed;
	private double shootCooldown;
	private double maxShootCooldown = 300;

	public Player(Game game, Context context, double x, double y) {
		super(x, y);
		this.context = context;
		this.game = game;
		this.speed = 4;
		images = new Image[4];
		images[0] = context.getImagePool().getImage("playerUp");
		images[1] = context.getImagePool().getImage("playerDown");
		images[2] = context.getImagePool().getImage("playerRight");
		images[3] = context.getImagePool().getImage("playerLeft");

		imageFrame = new ImageFrame(x, y, images[direction], 16);
	}

	@Override
	public void render(Graph g, double deltaTimeMS) {
		// Moving Part
		Keyboard keyboard = context.getKeyboard();

		if (keyboard.isPressed(Keyboard.A) || keyboard.isPressed(Keyboard.LEFT)) {
			direction = 3;
			x -= speed * deltaTimeMS / 1000;
		} else if (keyboard.isPressed(Keyboard.D) || keyboard.isPressed(Keyboard.RIGHT)) {
			direction = 2;
			x += speed * deltaTimeMS / 1000;
		} else if (keyboard.isPressed(Keyboard.W) || keyboard.isPressed(Keyboard.UP)) {
			direction = 0;
			y += speed * deltaTimeMS / 1000;
		} else if (keyboard.isPressed(Keyboard.S) || keyboard.isPressed(Keyboard.DOWN)) {
			direction = 1;
			y -= speed * deltaTimeMS / 1000;
		}

		// Shooting Part
		Mouse mouse = context.getMouse();
		if (mouse.isPressed(Mouse.LEFT_BUTTON) && shootCooldown <= 0) {
			shootCooldown = maxShootCooldown;
			double a = mouse.getX() - this.x;
			double b = mouse.getY() - this.y;
			double direction = Math.atan2(b, a);
			game.addObject(new Bullet(this.x, this.y, direction));
		} else if (shootCooldown > 0) {
			shootCooldown -= deltaTimeMS;
		}

		// Drawing Part
		imageFrame.setX(this.x);
		imageFrame.setY(this.y);
		imageFrame.setImage(images[direction]);
		g.draw(imageFrame);
	}
	
}
