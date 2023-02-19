package de.schoko.renderingeximpl;

import de.schoko.rendering.Camera;
import de.schoko.rendering.CameraPath;
import de.schoko.rendering.CameraPathPoint;
import de.schoko.rendering.Context;
import de.schoko.rendering.Graph;
import de.schoko.rendering.ImageLocation;
import de.schoko.rendering.Renderer;
import de.schoko.rendering.Window;
import de.schoko.rendering.shapes.Circle;
import de.schoko.rendering.shapes.ImageFrame;
import de.schoko.rendering.shapes.Rectangle;

public class RenderingExampleImplementation {

	public static void main(String[] args) {
		Window window = new Window(new Renderer() {
			private double x = 0;
			private Rectangle r;
			private ImageFrame imageFrame;
			
			@Override
			public void onLoad(Context context) {
				context.getCamera().setCameraPath(new CameraPath() {
					private double x;
					
					@Override
					public CameraPathPoint getPoint(Camera camera, double deltaTimeMS) {
						x += deltaTimeMS / 1000;
						return new CameraPathPoint(x, Math.sin(x * 0.5), (Math.sin(x)) * 5 + 50);
					}
				});
				
				r = new Rectangle(0, 0, 2, 2);
				imageFrame = new ImageFrame(0, 0, 
						context.getImagePool().getImage("testImg", "C:/Users/Steve/Desktop/Projekte/JavaScript/WeiseSchokolade.github.io/games/space/assets/ufo.png", ImageLocation.FILE), 
						64);
			}
			
			@Override
			public void render(Graph g, double deltaTimeMS) {
				imageFrame.setX(x);
				imageFrame.setY(Math.sin(x));
				g.draw(r);
				g.draw(imageFrame);
				g.draw(new Circle(-1, -1, 0.5, null));
				x += deltaTimeMS / 1000;
			}
		}, "Rendering example implementation");
		window.open();
	}

}
