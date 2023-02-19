package de.schoko.renderingeximplspace;

import java.util.ArrayList;

import de.schoko.rendering.Context;
import de.schoko.rendering.Graph;
import de.schoko.rendering.ImagePool;
import de.schoko.rendering.Keyboard;
import de.schoko.rendering.shapes.ImageFrame;

public class UFO {
	private Context context;
	
	private ArrayList<Cow> cows;
	
	private double height = 1.3;
	
	private double x, y;
	private double ufoXSpeed = 7.5;
	private double ufoYSpeed = 1;
	
	private double beamCooldown;
	// How long the beam cannot be used after usage
	private final double maxBeamCooldown = 1000;
	// For how long the beam is visible
	private final double beamVisibility = 200;
	
	private ImageFrame ufo;
	private ImageFrame beam;
	private ImageFrame ufoShadow;
	
	public UFO(ArrayList<Cow> cows) {
		this.cows = cows;
	}
	
	public void load(Context context) {
		this.context = context;
		
		ImagePool imagePool = context.getImagePool();
		
		ufo = new ImageFrame(0, 0, imagePool.getImage("ufo"), 64);
		beam = new ImageFrame(0, 0, imagePool.getImage("beam"), 32);
		ufoShadow = new ImageFrame(0, 0, imagePool.getImage("shadow"), 32);
		
	}
	
	public void render(Graph g, double deltaTimeMS) {
		y += deltaTimeMS / 1000 * ufoYSpeed;
		Keyboard keyboard = context.getKeyboard();
		
		if (keyboard.isPressed(Keyboard.A) || keyboard.isPressed(Keyboard.LEFT)) {
			// * ufoXSpeed == ufoXSpeed tiles per 1000 milliseconds
			x -= deltaTimeMS / 1000 * ufoXSpeed;
		}
		if (keyboard.isPressed(Keyboard.D) || keyboard.isPressed(Keyboard.RIGHT)) {
			// * ufoXSpeed == ufoXSpeed tiles per 1000 milliseconds
			x += deltaTimeMS / 1000 * ufoXSpeed;
		}
		
		// Beam
		boolean displayingBeam = beamCooldown > maxBeamCooldown - beamVisibility;
		if (beamCooldown > 0) {
			beamCooldown -= deltaTimeMS;
		} else if (keyboard.isPressed(Keyboard.SPACE)) {
			beamCooldown = maxBeamCooldown;
		}
		
		if (displayingBeam) {
			cows.forEach((cow) -> {
				if (distance(cow) < 0.5) {
					cow.capture();
				} else if (distance(cow) < 2) {
					cow.fright(this);
				}
			});
		}
		
		ufo.setX(x);
		ufo.setY(y);
		ufoShadow.setX(ufo.getX());
		ufoShadow.setY(ufo.getY() - height);
		beam.setX(ufo.getX());
		beam.setY(ufo.getY() - height / 2 - 0.1);

		g.draw(ufoShadow);
		if (displayingBeam) {
			g.draw(beam);
		}
		g.draw(ufo);
	}
	
	public double distance(Cow cow) {
		// ufoShadow since the shadow is flying above the ground while shadow and the cows are touching it
		double dx = cow.getX() - ufoShadow.getX();
		double dy = cow.getY() - getGroundY();
		// a² + b² = c²
		return Math.sqrt(dx * dx + dy * dy);
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getGroundY() {
		return ufoShadow.getY();
	}
}
