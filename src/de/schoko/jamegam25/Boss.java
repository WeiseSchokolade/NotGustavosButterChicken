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
		Boss[] bossFights = new Boss[1];
		bossFights[0] = new TestBossFight(game);
		return bossFights[(int) (Math.floor(Math.random() * bossFights.length))];
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
