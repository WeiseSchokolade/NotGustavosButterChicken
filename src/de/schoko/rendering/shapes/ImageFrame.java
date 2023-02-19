package de.schoko.rendering.shapes;

import de.schoko.rendering.Graph;
import de.schoko.rendering.Image;

public class ImageFrame extends Shape {
	private double x, y;
	private Image image;
	private double scale;
	
	public ImageFrame(double x, double y, Image image, double scale) {
		this.x = x;
		this.y = y;
		this.image = image;
		this.scale = scale;
	}
	
	public Image getImage() {
		return image;
	}
	
	@Override
	public void render(Graph g) {
		g.drawImage(image.getAWTImage(), x, y, scale);
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

	public void setImage(Image image) {
		this.image = image;
	}
}
