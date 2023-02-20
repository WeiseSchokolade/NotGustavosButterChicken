package de.schoko.jamegam25;

import de.schoko.rendering.Graph;

public abstract class GameObject {
	protected double x, y;
	
	public GameObject(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public abstract void render(Graph g, double deltaTimeMS);

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
}
