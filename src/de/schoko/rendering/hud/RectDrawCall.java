package de.schoko.rendering.hud;

import java.awt.Color;
import java.awt.Graphics2D;

public class RectDrawCall extends DrawCall {
	private double x, y;
	private double width, height;
	private Color fillColor;
	
	public RectDrawCall(double x, double y, double width, double height, Color fillColor) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.fillColor = fillColor;
	}

	@Override
	public void call(Graphics2D g2D) {
		g2D.setColor(fillColor);
		g2D.fillRect((int) x, (int) y, (int) width, (int) height);
	}
}
