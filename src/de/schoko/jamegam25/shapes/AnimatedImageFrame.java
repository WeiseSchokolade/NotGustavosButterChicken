package de.schoko.jamegam25.shapes;

import java.util.ArrayList;

import de.schoko.rendering.Graph;
import de.schoko.rendering.shapes.Shape;

public class AnimatedImageFrame extends Shape {
	private double x, y;
	private ArrayList<AnimatedImage> images;
	private int currentImage;
	private double displayTime;
	private double scale;

	public AnimatedImageFrame(double x, double y, ArrayList<AnimatedImage> images, double scale) {
		this.x = x;
		this.y = y;
		this.images = images;
		this.scale = scale;
	}
	
	@Override
	public void render(Graph g) {
		AnimatedImage image = images.get(currentImage);
		g.drawImage(image.getAWTImage(), x, y, scale);
	}

	public void update(double deltaTimeMS) {
		displayTime -= deltaTimeMS;
		if (displayTime <= 0) {
			currentImage += 1;
			if (currentImage >= images.size()) {
				currentImage = 0;
			}
			displayTime += images.get(currentImage).getDisplayTime();
		}
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

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	public void addImage(AnimatedImage animatedImage) {
		this.images.add(animatedImage);
	}

	
}
