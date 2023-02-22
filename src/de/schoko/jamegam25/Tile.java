package de.schoko.jamegam25;

import de.schoko.jamegam25.shapes.AnimatedImageFrame;
import de.schoko.rendering.Graph;
import de.schoko.rendering.Image;
import de.schoko.rendering.shapes.ImageFrame;
import de.schoko.rendering.shapes.Rectangle;
import de.schoko.rendering.shapes.Shape;

public class Tile {
    private Shape shape;

    public Tile(Shape shape) {
        this.shape = shape;
    }

    public Tile(double x, double y) {
        this.shape = new Rectangle(x, y, 1, 1);
    }

	public Tile(double x, double y, ImageFrame shape) {
		this.shape = shape;
		shape.setX(x);
		shape.setY(y);
	}

    public Tile(double x, double y, Image image, double scale) {
        shape = new ImageFrame(x, y, image, scale);
    }

    public void render(Graph g, double deltaTimeMS) {
		if (shape instanceof AnimatedImageFrame) {
			AnimatedImageFrame animatedImageFrame = (AnimatedImageFrame) shape;
			animatedImageFrame.update(deltaTimeMS);
		}
        shape.render(g);
    }
}
