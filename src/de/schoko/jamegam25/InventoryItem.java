package de.schoko.jamegam25;

import de.schoko.rendering.Image;

public class InventoryItem {
	private Image image;
	private UseEffect useEffect;
	private int amount;
	private String name;
	private String description;
	private int key;

	public InventoryItem(Image image, int amount, String name, String description, int key, UseEffect useEffect) {
		this.image = image;
		this.useEffect = useEffect;
		this.amount = amount;
		this.name = name;
		this.description = description;
		this.key = key;
	}

	public void use(Player player) {
		if (this.amount > 0) {
			useEffect.use(player);
			this.amount--;
		}
	}

	public Image getImage() {
		return image;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public int getKey() {
		return key;
	}
}
