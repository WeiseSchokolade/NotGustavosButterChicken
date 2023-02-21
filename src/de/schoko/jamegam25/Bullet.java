package de.schoko.jamegam25;

import java.util.ArrayList;

import java.awt.Color;

import de.schoko.rendering.Graph;
import de.schoko.rendering.shapes.Circle;

public class Bullet extends GameObject{
	private ArrayList<Enemy> enemies;
	private double direction;
	private double speed;
	private double damage;

	public Bullet(double x, double y, double direction, double damage, ArrayList<Enemy> enemies) {
		super(x, y);
		this.direction = direction;
		this.speed = 7;
		this.enemies = enemies;
		this.damage = damage;
	}

	@Override
	public void render(Graph g, double deltaTimeMS) {
		this.x += Math.cos(direction) * deltaTimeMS / 1000 * speed;
		this.y += Math.sin(direction) * deltaTimeMS / 1000 * speed;

		for (int i = 0; i < enemies.size(); i++) {
			Enemy enemy = enemies.get(i);
			if (distanceTo(enemy) < 0.5) {
				enemy.applyDamage(this.damage);
				this.remove();
			}
		}

		g.draw(new Circle(x, y, 0.1, Color.WHITE));
	}
	
}
