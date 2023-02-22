package de.schoko.jamegam25;

import de.schoko.rendering.Graph;

public abstract class GameObject {
	protected double x, y;
	protected int z;
	private boolean removed;
	
	public GameObject(double x, double y) {
		this.x = x;
		this.y = y;
		this.z = 0;
	}

	public GameObject(double x, double y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public abstract void render(Graph g, double deltaTimeMS);

	public void remove() {
		this.removed = true;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public boolean isRemoved() {
		return this.removed;
	}

	public double distanceTo(GameObject g) {
		double a = g.getX() - this.x;
		double b = g.getY() - this.y;
		return Math.sqrt(a * a + b * b);
	}

	public int getZ() {
		return z;
	}
}
