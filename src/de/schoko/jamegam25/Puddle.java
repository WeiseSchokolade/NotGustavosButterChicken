package de.schoko.jamegam25;

import de.schoko.rendering.Graph;
import de.schoko.rendering.ImagePool;
import de.schoko.rendering.shapes.ImageFrame;

public class Puddle extends GameObject {
	private double stage;
	private ImageFrame drawing;
	private ImagePool imagePool;

	public Puddle(double x, double y, ImagePool imagePool) {
		super(x, y);
		this.stage = 0;
		this.imagePool = imagePool;
		this.drawing = new ImageFrame(x, y, imagePool.getImage("puddle_s0"), 16);
	}

	@Override
	public void render(Graph g, double deltaTimeMS) {
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
