package de.schoko.rendering.shapes;

import java.awt.Color;

import de.schoko.rendering.Graph;

public class Rectangle extends Shape {
	private double x, y;
	private double width, height;
	private Color fillColor;
	
	public Rectangle(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.fillColor = Color.BLACK;
	}
	
	public Rectangle(double x, double y, double width, double height, Color fillColor) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.fillColor = fillColor;
	}
	
	@Override
	public void render(Graph g) {
		g.drawRect(x, y, x + width, y + height, fillColor);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}
	
}
