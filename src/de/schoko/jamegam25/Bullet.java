package de.schoko.jamegam25;

import java.util.ArrayList;

import de.schoko.rendering.Graph;
import de.schoko.rendering.shapes.ImageFrame;

public class Bullet extends GameObject{
	private Game game;
	private ArrayList<Enemy> enemies;
	private ImageFrame drawing;
	private ImageFrame shadow;
	private double direction;
	private double speed;
	private double damage;
	private double height;

	public Bullet(double x, double y, double direction, double damage, Game game) {
		super(x, y);
		this.game = game;
		this.enemies = game.getEnemies();
		this.drawing = new ImageFrame(x, y, game.getContext().getImagePool().getImage("butterChicken"), 32);
		this.shadow = new ImageFrame(x, y, game.getContext().getImagePool().getImage("butterChickenShadow"), 32);
		this.direction = direction;
		this.speed = 9;
		this.damage = damage;
		this.height = 1;
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
			Enemy enemy = enemies.get(i);
			if (distanceTo(enemy) < 0.5) {
				enemy.applyDamage(this.damage);
				this.remove();
			}
		}

		this.height -= deltaTimeMS / 1000;

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
		game.addObject(new Puddle(x, y, game.getContext().getImagePool()));
	}
}
