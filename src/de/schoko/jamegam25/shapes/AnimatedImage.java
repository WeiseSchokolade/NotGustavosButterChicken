package de.schoko.jamegam25.shapes;

import de.schoko.rendering.Image;

public class AnimatedImage {
	private Image image;
	private double displayTime;

	public AnimatedImage(Image image, double displayTime) {
		this.image = image;
		this.displayTime = displayTime;
	}

	public java.awt.Image getAWTImage() {
		return image.getAWTImage();
	}

	public double getDisplayTime() {
		return displayTime;
	}
}
