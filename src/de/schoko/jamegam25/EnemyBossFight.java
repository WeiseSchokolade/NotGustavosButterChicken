package de.schoko.jamegam25;

import de.schoko.rendering.Graph;
import de.schoko.rendering.HUDGraph;
import de.schoko.rendering.Image;
import de.schoko.rendering.ImagePool;

public class EnemyBossFight extends Boss {
	private static final String[] NAMES = {"Frank", "Piper", "Billie"};
	private Game game;
	private StrongEnemy enemy;

	public EnemyBossFight(Game game) {
		super(NAMES[(int) Math.floor(Math.random() * NAMES.length)]);
		this.game = game;

		ImagePool imagePool = game.getContext().getImagePool();
		game.playScene(new Scene(game, "Mary: ", getName() + ", get rid of them! I WANT MY SOUP!", (HUDGraph hud, Scene scene) -> {
			double scale = (50 / hud.getWidth());
			Image image = imagePool.getImage("antagonist");
			hud.drawImage(hud.getWidth() - image.getAWTImage().getWidth(null) / scale, hud.getHeight() - image.getAWTImage().getHeight(null) / scale - 100, image, scale);
			scene.drawText(hud);
		}));

		enemy = new StrongEnemy(game, game.getPlayer(), game.getWidth() / 2 + 2, 0);
		this.game.addEnemy(enemy);
	}

	@Override
	public void render(Graph g, double deltaTimeMS) {
		if (enemy.isRemoved()) {
			setCompleted(true);
		}
		setProgress(enemy.getHealth() / 200);
	}
	
	@Override
	public void setCompleted(boolean completed) {
		super.setCompleted(completed);
		if (completed) {
			enemy.remove();
		}
	}
}
