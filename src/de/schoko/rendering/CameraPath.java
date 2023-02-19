package de.schoko.rendering;

public interface CameraPath {
	public CameraPathPoint getPoint(Camera camera, double deltaTimeMS);
}
