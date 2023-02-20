package de.schoko.jamegam25;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import de.schoko.rendering.Camera;
import de.schoko.rendering.CameraPath;
import de.schoko.rendering.CameraPathPoint;
import de.schoko.rendering.Context;
import de.schoko.rendering.Graph;
import de.schoko.rendering.Mouse;

public class MainMenu extends Menu {
	private Sound sound;

	@Override
	public void onLoad(Context context) {
		try {
			sound = new Sound("de/schoko/jamegam25/assets/sound.wav");
			sound.start();
		} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		}
		context.getCamera().setCameraPath(new CameraPath() {
			private double t;
			@Override
			public CameraPathPoint getPoint(Camera camera, double deltaTimeMS) {
				t += deltaTimeMS / 1000;
				return new CameraPathPoint(0, Math.sin(t) * 0.0625, 150);
			}
			
		});
	}


	@Override
	public void render(Graph g, double deltaTimeMS) {
		Mouse mouse = getContext().getMouse();
		g.drawString("Game Name", -0.5, 0);
		
		if (mouse.getX() >= -0.5 && mouse.getX() < 0) {
			if (mouse.getY() <= -0.125 && mouse.getY() >= -0.375) {
				g.drawString("> Play", -0.5, -0.25);
				if  (mouse.isPressed(Mouse.LEFT_BUTTON)) {
					getProject().setMenu(new Game());
				}
			} else {
				g.drawString("Play", -0.5, -0.25);
			}
		} else {
			g.drawString("Play", -0.5, -0.25);
		}
	}
}
