package de.schoko.rendering;

public class CameraPathPoint {
	public double x, y;
	public double zoom;
	
	public CameraPathPoint(double x, double y) {
		this.x = x;
		this.y = y;
		this.zoom = -1;
	}
	
	public CameraPathPoint(double x, double y, double zoom) {
		this.x = x;
		this.y = y;
		this.zoom = zoom;
	}
	
	public CameraPathPoint() {
		this.x = 0;
		this.y = 0;
		this.zoom = -1;
	}
}
