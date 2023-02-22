package de.schoko.jamegam25;

import de.schoko.jamegam25.shapes.AnimatedImage;
import de.schoko.jamegam25.shapes.AnimatedImageFrame;
import de.schoko.rendering.Graph;
import de.schoko.rendering.ImagePool;

public class Barrel extends GameObject {
	private AnimatedImageFrame drawing;
	private boolean left; // If true, rolls to the left
	private double speed;
	
	public Barrel(double x, double y, Game game) {
		super(x, y, 150);
		ImagePool imagePool = game.getContext().getImagePool();
		drawing = new AnimatedImageFrame(x, y, 16,
			new AnimatedImage(imagePool.getImage("barrel_s0"), 250),
			new AnimatedImage(imagePool.getImage("barrel_s1"), 250)
			);
		this.speed = 5;
	}

	@Override
	public void render(Graph g, double deltaTimeMS) {
		if (left) {
			this.x += deltaTimeMS / 1000 * speed;
		} else {
			this.x -= deltaTimeMS / 1000 * speed;
		}

		drawing.setX(this.x);
		drawing.setY(this.y);
		drawing.update(deltaTimeMS);
		g.draw(drawing);
	}
	
}
