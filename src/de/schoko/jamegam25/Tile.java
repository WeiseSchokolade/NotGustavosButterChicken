package de.schoko.jamegam25;

import de.schoko.jamegam25.shapes.AnimatedImageFrame;
import de.schoko.rendering.Graph;
import de.schoko.rendering.Image;
import de.schoko.rendering.shapes.ImageFrame;
import de.schoko.rendering.shapes.Rectangle;
import de.schoko.rendering.shapes.Shape;

public class Tile {
	private double x, y;
    private Shape shape;

    public Tile(Shape shape) {
        this.shape = shape;
    }

    public Tile(double x, double y) {
        this.shape = new Rectangle(x, y, 1, 1);
		this.x = x;
		this.y = y;
    }

	public Tile(double x, double y, ImageFrame shape) {
		this.shape = shape;
		shape.setX(x);
		shape.setY(y);
		this.x = x;
		this.y = y;
	}

	public Tile(double x, double y, AnimatedImageFrame shape) {
		this.shape = shape;
		shape.setX(x);
		shape.setY(y);
		this.x = x;
		this.y = y;
	}

    public Tile(double x, double y, Image image, double scale) {
        shape = new ImageFrame(x, y, image, scale);
		this.x = x;
		this.y = y;
    }

    public void render(Graph g, double deltaTimeMS) {
		if (shape instanceof AnimatedImageFrame) {
			AnimatedImageFrame animatedImageFrame = (AnimatedImageFrame) shape;
			animatedImageFrame.update(deltaTimeMS);
		}
        shape.render(g);
    }

	public void setShape(Shape shape) {
		if (shape instanceof ImageFrame) {
			ImageFrame imageFrame = (ImageFrame) shape;
			imageFrame.setX(x);
			imageFrame.setY(y);
		}
		if (shape instanceof AnimatedImageFrame) {
			AnimatedImageFrame animatedImageFrame = (AnimatedImageFrame) shape;
			animatedImageFrame.setX(x);
			animatedImageFrame.setY(y);
		}
		this.shape = shape;
	}
}
