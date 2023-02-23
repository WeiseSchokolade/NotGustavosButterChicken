package de.schoko.jamegam25;

import de.schoko.rendering.Graph;
import de.schoko.rendering.ImagePool;
import de.schoko.rendering.shapes.ImageFrame;

public class Puddle extends GameObject {
	public static final int MAX_AGE = 5000;

	private Game game;
	private double stage;
	private ImageFrame drawing;
	private ImagePool imagePool;
	private double age;
	private boolean destroyed;

	public Puddle(double x, double y, Game game) {
		super(x, y);
		this.game = game;
		this.stage = 0;
		this.imagePool = game.getContext().getImagePool();
		this.drawing = new ImageFrame(x, y, imagePool.getImage("puddle_s0"), 16);
		this.age = 0;
	}

	@Override
	public void render(Graph g, double deltaTimeMS) {
		this.age += deltaTimeMS;

		//if (this.age > MAX_AGE) {
		//	if (!destroyed) {
		//		Tile tile = game.getTile(this.x + game.getTiles().length / 2, this.y + game.getTiles()[0].length / 2);
		//		ImageFrame imageFrame = new ImageFrame(0, 0, imagePool.getImage("tileHole"), 16);
		//		tile.setShape(imageFrame);
		//		destroyed = true;
		//	} else {
		//		stage += deltaTimeMS / 1000;
		//	}
		//}


		if (stage < 4) {
			updateImage();
			g.draw(drawing);
		} else {
			remove();
		}
	}

	public void updateImage() {
		if (!drawing.getImage().getName().equals("puddle_s" + (int) (Math.floor(stage)))) {
			if (stage < 4) {
				drawing.setImage(imagePool.getImage("puddle_s" + (int) (Math.floor(stage))));
			}
		}
	}
	
	public double getStage() {
		return stage;
	}

	public void setStage(double stage) {
		this.stage = stage;
	}
}
