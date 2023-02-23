package de.schoko.jamegam25;

import de.schoko.jamegam25.shapes.AnimatedImage;
import de.schoko.jamegam25.shapes.AnimatedImageFrame;
import de.schoko.rendering.Graph;
import de.schoko.rendering.ImagePool;
import de.schoko.rendering.shapes.ImageFrame;

public class Barrel extends GameObject {
	private Game game;
	private AnimatedImageFrame drawing;
	private ImageFrame shadow;
	private boolean right; // If true, rolls to the left
	private double speed;
	
	public Barrel(double x, double y, Game game) {
		super(x, y, 150);
		this.game = game;
		ImagePool imagePool = game.getContext().getImagePool();
		drawing = new AnimatedImageFrame(x, y, 16,
			new AnimatedImage(imagePool.getImage("barrel_s0"), 250),
			new AnimatedImage(imagePool.getImage("barrel_s1"), 250)
			);
			shadow = new ImageFrame(x, y, imagePool.getImage("barrelShadow"), 16);
		this.speed = 5;
	}

	@Override
	public void render(Graph g, double deltaTimeMS) {
		if (right) {
			this.x += deltaTimeMS / 1000 * speed;
			if (this.x > this.game.getTiles().length / 2) {
				this.remove();
			}
		} else {
			this.x -= deltaTimeMS / 1000 * speed;
			if (this.x < -this.game.getTiles().length / 2) {
				this.remove();
			}
		}

		drawing.setX(this.x);
		drawing.setY(this.y);
		drawing.update(deltaTimeMS);
		shadow.setX(drawing.getX() + 0.25);
		shadow.setY(drawing.getY() - 0.25);
		//g.draw(shadow);
		g.draw(drawing);
	}
	
}
