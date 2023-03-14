package de.schoko.jamegam25;

import de.schoko.rendering.Graph;
import de.schoko.rendering.HUDGraph;
import de.schoko.rendering.Image;
import de.schoko.rendering.ImagePool;

public class BarrelBossFight extends Boss {
	private Game game;
	private double t;

	public BarrelBossFight(Game game) {
		super("Mary's Barrels");
		t = 50000;
		this.game = game;
		ImagePool imagePool = game.getContext().getImagePool();
		game.playScene(new Scene(game, "Mary: ", "Argh, I want my butter chicken! Get the barrels, boys!", (HUDGraph hud, Scene scene) -> {
			double scale = (50 / hud.getWidth());
			Image image = imagePool.getImage("antagonist");
			hud.drawImage(hud.getWidth() - image.getAWTImage().getWidth(null) / scale, hud.getHeight() - image.getAWTImage().getHeight(null) / scale - 100, image, scale);
			scene.drawText(hud);
		}));
	}

	@Override
	public void render(Graph g, double deltaTimeMS) {
		t -= deltaTimeMS;

		g.drawImage(game.getContext().getImagePool().getImage("antagonist").getAWTImage(), game.getWidth() / 2 + 2, 0.0, 16.0);

		if (Math.random() > 0.99) {
			game.addObject(new Barrel(game.getWidth() / 2 + 1, Math.random() * game.getHeight() - game.getHeight() / 2, game));
		}

		setProgress((50000 - t) / 50000);
		if (t <= 0) {
			setCompleted(true);
		}
	}
	

}
