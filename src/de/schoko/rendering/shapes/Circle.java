package de.schoko.rendering.shapes;

import java.awt.Color;

import de.schoko.rendering.Graph;

public class Circle extends Shape {
	private double x, y;
	private double radius;
	private Color color;

	public Circle(double x, double y, double radius) {
		this.x = x;
		this.y = y;
		this.radius = radius;
	}
	
	public Circle(double x, double y, double radius, Color color) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.color = color;
	}
	
	@Override
	public void render(Graph g) {
		g.drawCircle(x, y, color, radius);
	}

}
