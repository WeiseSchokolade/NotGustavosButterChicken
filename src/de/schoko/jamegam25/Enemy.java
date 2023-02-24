package de.schoko.jamegam25;

import java.util.ArrayList;

import de.schoko.rendering.Graph;
import de.schoko.rendering.Image;
import de.schoko.rendering.shapes.ImageFrame;
import de.schoko.rendering.shapes.Rectangle;

public class Enemy extends GameObject {
	private Game game;
	private Player player;
	private double health;
	// vx and vy are used for velocity
	private double vx, vy;
	// Damaged is used for the red damage indication
	private double damaged;
	private double direction;
	private double speed = 2;
	// Stunned is used for the time the enemy is stunned, usually 2000 ms (2 seconds)
	private double stunned;
	private ImageFrame drawing;
	private Image[] images;

    public Enemy(Game game, Player player, double x, double y) {
        super(x, y);
		this.game = game;
		this.player = player;
		this.health = 3;
		this.drawing = new ImageFrame(x, y, game.getContext().getImagePool().getImage("enemyWeak"), 16);
		images = new Image[] {
			game.getContext().getImagePool().getImage("enemyWeakLeft"), 
			game.getContext().getImagePool().getImage("enemyWeakRight"),
			game.getContext().getImagePool().getImage("enemyWeakLeftDamage"), 
			game.getContext().getImagePool().getImage("enemyWeakRightDamage")};
    }

    @Override
    public void render(Graph g, double deltaTimeMS) {
		// Movement
		direction = Math.atan2(player.getY() - this.y, player.getX() - this.x);
		
		if (stunned <= 0) {
        	this.vx += Math.cos(direction) * deltaTimeMS / 1000 * speed;
        	this.vy += Math.sin(direction) * deltaTimeMS / 1000 * speed;
			this.vx *= 0.5;
			this.vy *= 0.5;
		} else {
			this.vx *= 0.99;
			this.vy *= 0.99;
			this.stunned -= deltaTimeMS;
		}

		// Damage to player
		if (player.x > this.x - 0.5 && player.x < this.x + 0.5 && player.y > this.y - 0.5 && player.y < this.y + 0.5) {
			this.vx -= Math.cos(direction) * deltaTimeMS / 1000 * speed * 5;
        	this.vy -= Math.sin(direction) * deltaTimeMS / 1000 * speed * 5;
			player.vx += Math.cos(direction) * deltaTimeMS / 1000 * speed;
        	player.vy += Math.sin(direction) * deltaTimeMS / 1000 * speed;
			g.drawString("Hitbox", x, y);
			this.stunned = 2000;
			player.stunned = 1000;
			player.applyDamage(1);
		}

		// Movement apply
		if (isInArea()) {
			double oldX = x, oldY = y;
			this.x += vx;
			if (!isInArea()) {
				this.x = oldX;
				stunned = 0;
			}
			this.y += vy;
			if (!isInArea()) {
				this.y = oldY;
				stunned = 0;
			}
		} else {
			this.x += vx;
			this.y += vy;
		}

		// Damage by puddles
		ArrayList<GameObject> objects = game.getObjects();
		for (int i = 0; i < objects.size(); i++) {
			GameObject gameObject = objects.get(i);
			if (gameObject instanceof Puddle) {
				Puddle puddle = (Puddle) gameObject;
				if (puddle.x > this.x - 0.5 && puddle.x < this.x + 0.5 && puddle.y > this.y - 0.5 && puddle.y < this.y + 0.5) {
					puddle.setStage(puddle.getStage() + deltaTimeMS / 1000);
					applyDamage(deltaTimeMS / 1000);
					break;
				}
			}
		}

		if (damaged > 0) {
			damaged -= deltaTimeMS;
		}

		// Rendering
		if (game.getPlayer().getX() > x) {
			if (damaged > 0) {
				drawing.setImage(images[3]);
			} else {
				drawing.setImage(images[1]);
			}
		} else {
			if (damaged > 0) {
				drawing.setImage(images[2]);
			} else {
				drawing.setImage(images[0]);
			}
		}
		drawing.setX(x);
		drawing.setY(y);
		g.draw(drawing);
		
	}

	public boolean isInArea() {
		return (this.x > - game.getWidth() / 2 && this.x < game.getWidth() / 2 && this.y > - game.getHeight() / 2 && this.y < game.getHeight() / 2);
	}

	public void applyDamage(double damage) {
		this.health -= damage;
		damaged = 200;
		if (this.health <= 0) {
			this.remove();
		}
	}
    
	@Override
	public void remove() {
		super.remove();
		InventoryItem[] inventory = game.getInventory();

		double maxPossibleItemAmount = 4;
		double itemAmount = Math.random() * maxPossibleItemAmount * 2 - maxPossibleItemAmount;
		for (int i = 0; i < Math.random() * itemAmount; i++) {
			InventoryItem item = inventory[(int) (Math.random() * inventory.length)];
			game.addObject(new Item(game.getPlayer(), x + (Math.random() - 0.5), y + (Math.random() - 0.5), item));
		}
	}
}
