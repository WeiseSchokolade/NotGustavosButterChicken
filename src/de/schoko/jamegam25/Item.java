package de.schoko.jamegam25;

import de.schoko.rendering.Graph;
import de.schoko.rendering.Image;
import de.schoko.rendering.shapes.ImageFrame;

public class Item extends GameObject {
	private int type;
	private double t;
	private ImageFrame imageFrame;

	public Item(double x, double y, int type, Image image, double scale) {
		super(x, y);
		this.type = type;
		this.imageFrame = new ImageFrame(x, y, image, scale);
	}

	@Override
	public void render(Graph g, double deltaTimeMS) {
		t += deltaTimeMS / 1000;

		this.imageFrame.setX(this.getX());
		this.imageFrame.setY(this.getY() + Math.cos(t) * 0.05);
	}

	public int getType() {
		return type;
	}
}
