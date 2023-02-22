package de.schoko.jamegam25;

import de.schoko.rendering.Graph;

public class TestBossFight extends Boss {
	private Game game;
	private double t;

	public TestBossFight(Game game) {
		super("TEST");
		t = 50000;
		this.game = game;
	}

	@Override
	public void render(Graph g, double deltaTimeMS) {
		t -= deltaTimeMS;

		g.drawString("Bossfight not ready", 0, 0);
		g.drawString("" + t + " / 50000", 0, -0.5);

		g.drawImage(game.getContext().getImagePool().getImage("antagonist").getAWTImage(), game.getWidth() / 2 + 2, 0.0, 16.0);

		if (Math.random() > 0.99) {
			game.addObject(new Barrel(game.getWidth() / 2 + 2, Math.random() * game.getHeight() - game.getHeight() / 2, game));
		}

		setProgress(t / 50000);
		if (t <= 0) {
			setCompleted(true);
		}
	}
	

}
