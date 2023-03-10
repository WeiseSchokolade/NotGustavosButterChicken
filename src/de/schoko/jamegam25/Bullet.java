package de.schoko.jamegam25;

import java.util.ArrayList;

import de.schoko.rendering.Graph;
import de.schoko.rendering.shapes.ImageFrame;

public class Bullet extends GameObject{
	private Game game;
	private ArrayList<GameObject> enemies;
	private ImageFrame drawing;
	private ImageFrame shadow;
	private double direction;
	private double speed;
	private double damage;
	private double height;
	private double dHeight; // change of height
	private boolean spawnedPuddle;

	public Bullet(double x, double y, double direction, double damage, Game game) {
		super(x, y, 50);
		this.game = game;
		this.enemies = game.getEnemies();
		this.drawing = new ImageFrame(x, y, game.getContext().getImagePool().getImage("butterChicken"), 32);
		this.shadow = new ImageFrame(x, y, game.getContext().getImagePool().getImage("butterChickenShadow"), 32);
		this.direction = direction;
		this.speed = 9;
		this.damage = damage;
		this.height = 0.25;
		this.dHeight = 0.25;
	}

	@Override
	public void render(Graph g, double deltaTimeMS) {
		this.x += Math.cos(direction) * deltaTimeMS / 1000 * speed;
		this.y += Math.sin(direction) * deltaTimeMS / 1000 * speed;

		if (x > game.getWidth() / 2 || x < -game.getWidth() / 2) {
			remove();
			return;
		}
		if (y > game.getHeight() / 2 || y < -game.getHeight() / 2) {
			remove();
			return;
		}

		for (int i = 0; i < enemies.size(); i++) {
			GameObject enemy = enemies.get(i);
			if (distanceTo(enemy) < 0.5) {
				if (enemy instanceof Enemy) {
					((Enemy) enemy).applyDamage(this.damage);
				}
				if (enemy instanceof StrongEnemy) {
					((StrongEnemy) enemy).applyDamage(this.damage);
				}
				this.remove();
			}
		}

		this.dHeight -= deltaTimeMS / 1000;
		this.height += this.dHeight / 10;

		if (this.height <= 0) {
			// Destory
			this.remove();
		}

		// Rendering part
		this.drawing.setX(this.x);
		this.drawing.setY(this.y);
		
		this.shadow.setX(this.drawing.getX());
		this.shadow.setY(this.drawing.getY() - height);
		g.draw(shadow);
		g.draw(drawing);
		//g.draw(new Circle(x, y, 0.1, Color.WHITE));
	}
	
	@Override
	public void remove() {
		super.remove();
		if (!spawnedPuddle) {
			game.addObject(new Puddle(x, y, game));
			spawnedPuddle = true;
		}
	}
}
