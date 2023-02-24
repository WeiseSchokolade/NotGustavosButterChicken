package de.schoko.jamegam25;

import de.schoko.rendering.Graph;

public abstract class Boss {
	private boolean completed;
	private String name;
	private double progress;

	public Boss(String name) {
		this.name = name;
	}

	public static Boss getRandom(Game game) {
		int random = (int) Math.floor(Math.random() * 3);
		switch (random) {
			case 0:
				return new MaryJumpBossFight(game);
			case 1: 
				return new EnemyBossFight(game);
			case 2:
				return new BarrelBossFight(game);
			default:
				return null; // This case should never occure
		}
	}

	public abstract void render(Graph g, double deltaTimeMS);

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public String getName() {
		return name;
	}

	public double getProgress() {
		return progress;
	}

	public void setProgress(double progress) {
		this.progress = progress;
	}
}
