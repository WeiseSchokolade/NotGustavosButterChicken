package de.schoko.jamegam25;

import de.schoko.rendering.Graph;
import de.schoko.rendering.shapes.Rectangle;

public class Enemy extends GameObject {
	private Game game;
	private Player player;
	private double health;
	// Damaged is used for the red damage indication
	private double damaged;
	private double direction;
	private double speed = 2;

    public Enemy(Game game, Player player, double x, double y) {
        super(x, y);
		this.game = game;
		this.player = player;
		this.health = 3;
    }

    @Override
    public void render(Graph g, double deltaTimeMS) {
		direction = Math.atan2(player.getY() - this.y, player.getX() - this.x);

		
        this.x += Math.cos(direction) * deltaTimeMS / 1000 * speed;
        this.y += Math.sin(direction) * deltaTimeMS / 1000 * speed;

		g.draw(new Rectangle(x - 0.5, y - 0.5, 1, 1));
		g.drawString("Health: " + health, this.x, this.y);
		if (damaged > 0) {
			g.draw(new Rectangle(x - 0.5, y - 0.5, 1, 1, Graph.getColor(255, 0, 0, 80)));
			damaged -= deltaTimeMS;
		}
    }

	public void applyDamage(double damage) {
		this.health -= damage;
		damaged = 200;
		if (this.health <= 0) {
			this.remove();
		}
	}
    
	@Override
	public void remove() {
		super.remove();
		InventoryItem[] inventory = game.getInventory();

		double maxPossibleItemAmount = 4;
		double itemAmount = Math.random() * maxPossibleItemAmount * 2 - maxPossibleItemAmount;
		for (int i = 0; i < Math.random() * itemAmount; i++) {
			InventoryItem item = inventory[(int) (Math.random() * inventory.length)];
			game.addObject(new Item(game.getPlayer(), x + (Math.random() - 0.5), y + (Math.random() - 0.5), item));
		}
	}
}
