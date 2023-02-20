package de.schoko.jamegam25;

import de.schoko.rendering.Graph;
import de.schoko.rendering.shapes.Rectangle;

public class Enemy extends GameObject {
	private Player player;
	private double direction;
	private double speed = 2;

    public Enemy(Player player, double x, double y) {
        super(x, y);
		this.player = player;
    }

    @Override
    public void render(Graph g, double deltaTimeMS) {
		direction = Math.atan2(player.getY() - this.y, player.getX() - this.x);

        this.x += Math.cos(direction) * deltaTimeMS / 1000 * speed;
        this.y += Math.sin(direction) * deltaTimeMS / 1000 * speed;

		g.draw(new Rectangle(x - 0.5, y - 0.5, 1, 1));
    }
    
}
