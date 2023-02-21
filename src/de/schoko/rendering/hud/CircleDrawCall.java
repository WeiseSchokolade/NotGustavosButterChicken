package de.schoko.rendering.hud;

import java.awt.Color;
import java.awt.Graphics2D;

public class CircleDrawCall extends DrawCall {
	private double x, y;
	private double radius;
	private Color color;
	
	public CircleDrawCall(double x, double y, double radius, Color color) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.color = color;
	}
	
	@Override
	public void call(Graphics2D g2D) {
		g2D.setColor(color);
		g2D.fillArc((int) (x - radius / 2), (int) (y - radius / 2), (int) radius, (int) radius, 0, 360);
	}

}
