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
			if (this.x > this.game.getWidth() / 2 + 2) {
				this.remove();
			}
		} else {
			this.x -= deltaTimeMS / 1000 * speed;
			if (this.x < -this.game.getWidth() / 2 - 2) {
				this.remove();
			}
		}

		Player player = game.getPlayer();
		double width = 0.5;
		double height = 0.5;
		double playerWidth = 0.5;
		double playerHeight = 0.5;

		if (player.x < this.x + width &&
			player.x + playerWidth > this.x &&
			player.y < this.y + height &&
			player.y + playerHeight > this.y ) {
			double direction = Math.atan2(player.getY() - this.y, player.getX() - this.x);
			player.vx += Math.cos(direction) * deltaTimeMS / 1000 * speed;
        	player.vy += Math.sin(direction) * deltaTimeMS / 1000 * speed;
			player.stunned = 500;
			player.applyDamage(1);
		}

		drawing.setX(this.x);
		drawing.setY(this.y);
		drawing.update(deltaTimeMS);
		shadow.setX(drawing.getX() + 0.25);
		shadow.setY(drawing.getY() - 0.25);
		g.draw(shadow);
		g.draw(drawing);
	}
	
}
