package de.schoko.jamegam25;

import de.schoko.rendering.Graph;

public class TestBossFight extends Boss {
	private Game game;
	private double t;

	public TestBossFight(Game game) {
		super("TEST");
		t = 5000;
	}

	@Override
	public void render(Graph g, double deltaTimeMS) {
		t -= deltaTimeMS;

		g.drawString("Bossfight not ready", 0, 0);
		g.drawString("" + t + " / 5000", 0, -0.5);

		setProgress(t / 5000);
		if (t <= 0) {
			setCompleted(true);
		}
	}
	

}
