package de.schoko.jamegam25;

import de.schoko.rendering.Graph;
import de.schoko.rendering.shapes.ImageFrame;

public class Item extends GameObject {
	private InventoryItem inventoryItem;
	private Player player;
	private double t;
	private ImageFrame imageFrame;

	public Item(Player player, double x, double y, InventoryItem inventoryItem) {
		super(x, y, 100);
		this.player = player;
		this.inventoryItem = inventoryItem;
		this.imageFrame = new ImageFrame(x, y, inventoryItem.getImage(), 16 * 2);
		t += Math.random() * 2 * Math.PI;
	}

	@Override
	public void render(Graph g, double deltaTimeMS) {
		t += deltaTimeMS / 1000;
		
		double dist = distanceTo(player);
		if (dist < 0.25) {
			inventoryItem.setAmount(inventoryItem.getAmount() + 1);
			this.remove();
			return;
		} else if (dist < 2) {
			x += ((player.x - x) / (dist * dist) * deltaTimeMS / 1000);
			y += ((player.y - y) / (dist * dist) * deltaTimeMS / 1000);
		}

		this.imageFrame.setX(this.getX());
		this.imageFrame.setY(this.getY() + Math.cos(t) * 0.05);
		g.draw(imageFrame);
	}

	public void use(Player player) {
		inventoryItem.use(player);
	}

	public InventoryItem getType() {
		return inventoryItem;
	}
}
