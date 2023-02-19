package de.schoko.rendering.hud;

import java.awt.Color;
import java.awt.Graphics2D;

public class BarDrawCall extends DrawCall {
	private double x, y;
	private double width, height;
	private double percentage;
	private Color innerColor, outerColor;
	
	public BarDrawCall(double x, double y, double width, double height, double percentage, Color innerColor, Color outerColor) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.percentage = percentage;
		this.innerColor = innerColor;
		this.outerColor = outerColor;
	}
	
	@Override
	public void call(Graphics2D g2D) {
		// Outer Rect
		g2D.setColor(outerColor);
		g2D.fillRect((int) x, (int) y, (int) width, (int) height);
		// Inner Rect
		g2D.setColor(innerColor);
		g2D.fillRect((int) x, (int) y, (int) (width * percentage), (int) height);
	}
	
}
