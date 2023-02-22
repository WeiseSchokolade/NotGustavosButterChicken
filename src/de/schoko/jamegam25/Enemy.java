package de.schoko.jamegam25;

import java.util.ArrayList;

import de.schoko.rendering.Graph;
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

    public Enemy(Game game, Player player, double x, double y) {
        super(x, y);
		this.game = game;
		this.player = player;
		this.health = 3;
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
		this.x += vx;
		this.y += vy;

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

		// Rendering
		g.draw(new Rectangle(x - 0.5, y - 0.5, 1, 1));
		g.drawString("Health: " + health, this.x - 0.5, this.y + 0.75);
		if (damaged > 0) {
			g.draw(new Rectangle(x - 0.5, y - 0.5, 1, 1, Graph.getColor(255, 0, 0, 80)));
			damaged -= deltaTimeMS;
		}
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
