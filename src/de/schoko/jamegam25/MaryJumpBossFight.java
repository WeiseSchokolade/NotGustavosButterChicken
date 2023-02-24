package de.schoko.jamegam25;

import de.schoko.rendering.Graph;
import de.schoko.rendering.HUDGraph;
import de.schoko.rendering.Image;
import de.schoko.rendering.ImagePool;
import de.schoko.rendering.shapes.ImageFrame;

public class MaryJumpBossFight extends Boss {
	private Game game;
	private JumpingMary mary;
	private double t;
	private double totalTime;
	private boolean loopComplete;

	public MaryJumpBossFight(Game game) {
		super("Jumping Mary");
		this.game = game;
		ImagePool imagePool = game.getContext().getImagePool();
		game.playScene(new Scene(game, "Mary: ", "Fine, I'll do it myself!", (HUDGraph hud, Scene scene) -> {
			double scale = (50 / hud.getWidth());
			Image image = imagePool.getImage("antagonist");
			hud.drawImage(hud.getWidth() - image.getAWTImage().getWidth(null) / scale, hud.getHeight() - image.getAWTImage().getHeight(null) / scale - 100, image, scale);
			scene.drawText(hud);
		}));

		mary = new JumpingMary(game, game.getWidth() / 2 + 2, 0.0);
		totalTime = JumpingMary.LOOP_TIME * 4; // It loops 4 times
	}

	@Override
	public void render(Graph g, double deltaTimeMS) {
		t += deltaTimeMS / 1000;
		mary.render(g, deltaTimeMS);
		mary.drawTarget(g);
		// It loops 4 times
		if (t > totalTime && !loopComplete) {
			loopComplete = true;
			mary.moveTo(game.getWidth() / 2 + 2, 0.0);
			game.playScene(new Scene(game, "Mary: ", "Ugh, this is exhausting!", (HUDGraph hud, Scene scene) -> {
				double scale = (50 / hud.getWidth());
				Image image = game.getContext().getImagePool().getImage("antagonist");
				hud.drawImage(hud.getWidth() - image.getAWTImage().getWidth(null) / scale, hud.getHeight() - image.getAWTImage().getHeight(null) / scale - 100, image, scale);
				scene.drawText(hud);
			}));
		}
		if (t > totalTime + JumpingMary.LOOP_TIME) {
			setCompleted(true);
		}
		setProgress(t / (totalTime + JumpingMary.LOOP_TIME));
	}

	private class JumpingMary extends GameObject {
		public static final double LOOP_TIME = 5; // Time before it loops again

		private Game game;

		private double warpProgress;
		private double targetX, targetY;
		private boolean damaged;

		private double radius;

		private ImageFrame drawing;

		public JumpingMary(Game game, double x, double y) {
			super(x, y, 200);
			this.game = game;
			this.radius = 2;
			drawing = new ImageFrame(x, y, game.getContext().getImagePool().getImage("antagonist"), 16);
		}

		@Override
		public void render(Graph g, double deltaTimeMS) {
			warpProgress += deltaTimeMS / 1000;

			
			if (!damaged && warpProgress > 4) {
				damaged = true;
				this.x = targetX;
				this.y = targetY;
				Sound sound = new Sound(game, Project.ASSET_PATH + "stomp.wav", false);
				sound.start();
				if (distanceTo(game.getPlayer()) < radius) {
					game.getPlayer().applyDamage(Double.MAX_VALUE);
				}
			}

			drawing.setX(x);
			if (warpProgress > 3 && warpProgress < 4) {
				drawing.setY(y + (warpProgress - 3) * 10);
			} else {
				drawing.setY(y);
			}
			if (warpProgress > LOOP_TIME) {
				moveTo(game.getPlayer().getX(), game.getPlayer().getY());
			}
			g.draw(drawing);
		}

		public void moveTo(double x, double y) {
			warpProgress = 0;
			damaged = false;
			targetX = x;
			targetY = y;
		}

		public void drawTarget(Graph g) {
			if (warpProgress < 4) {
				g.fillCircle(targetX, targetY, Graph.getColor(255, 0, 0, 127), radius);
			}
		}
	}
}
