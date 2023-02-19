package de.schoko.renderingeximplspace;

import de.schoko.rendering.Graph;
import de.schoko.rendering.Image;
import de.schoko.rendering.ImagePool;
import de.schoko.rendering.shapes.ImageFrame;

public class Cow {
	private double x, y;
	private ImageFrame cow;
	private double direction;
	private boolean removed;
	private Image leftImg;
	private Image rightImg;
	private Animation animation;
	
	public Cow(double x, double y, String basePath, ImagePool imagePool) {
		this.x = x;
		this.y = y;
		this.direction = Math.random() * 360;
		this.leftImg = imagePool.getImage("cowLeft");
		this.rightImg = imagePool.getImage("cowRight");
		this.cow = new ImageFrame(x, y, getImage(), 32);
		this.animation = new StandingAnimation();
	}
	
	public void render(Graph g, double deltaTimeMS) {
		animation.play(deltaTimeMS);
		
		cow.setX(x);
		cow.setY(y);
		cow.setImage(getImage());
		g.draw(cow);
	}
	
	public Image getImage() {
		if (direction > 180) {
			return leftImg;
		} else {
			return rightImg;
		}
	}
	
	public void capture() {
		play(new Animation() {
			@Override
			public void play(double deltaTimeMS) {
				y += deltaTimeMS / 1000 * 10;
				cow.setScale(cow.getScale() + deltaTimeMS);
				if (cow.getScale() > 100) {
					remove();
				}
			}
			@Override
			public boolean isLeavable() {
				return false;
			}
		});
	}

	public void fright(UFO ufo) {
		double a = ufo.getX() - this.getX();
		double b = ufo.getY() - this.getY();
		this.direction = Math.toDegrees(Math.atan(a / b) * 2);
		play(new Animation() {
			private double t;
			@Override
			public void play(double deltaTimeMS) {
				x += Math.cos(direction) * deltaTimeMS / 1000;
				y += Math.sin(direction) * deltaTimeMS / 1000;
				t += deltaTimeMS / 1000;
				if (t >= 5) {
					animation = new StandingAnimation();
				}
			}
			@Override
			public boolean isLeavable() {
				return false;
			}
		});
	}
	
	public void remove() {
		removed = true;
	}
	
	public boolean isRemoved() {
		return removed;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void play(Animation animation) {
		if (!this.animation.isLeavable()) {
			return;
		}
		this.animation.leave();
		this.animation = animation;
		animation.enter();
	}
	
	abstract class Animation {
		public abstract void play(double deltaTimeMS);
		public void enter() {
		};
		public void leave() {
		};
		public boolean isLeavable() {
			return true;
		};
	}
	
	class StandingAnimation extends Animation {
		@Override
		public void play(double deltaTimeMS) {
		}
	}
}
