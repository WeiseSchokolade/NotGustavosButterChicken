package de.schoko.renderingeximplspace;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import de.schoko.rendering.Camera;
import de.schoko.rendering.CameraPathPoint;
import de.schoko.rendering.Context;
import de.schoko.rendering.Graph;
import de.schoko.rendering.Image;
import de.schoko.rendering.ImageLocation;
import de.schoko.rendering.ImagePool;
import de.schoko.rendering.Renderer;
import de.schoko.rendering.Window;

public class SpaceGame extends Renderer {
	public static void main(String[] args) {
		Window window = new Window(new SpaceGame(), "Game");
		window.getSettings().setAutoCam(false);
		float[] color = Color.RGBtoHSB(56, 113, 54, null);
		window.getSettings().setBackgroundColor(Color.getHSBColor(color[0], color[1], color[2]));
		window.getSettings().setRenderCoordinateSystem(false);
		window.open();
	}
	
	private static final String ASSET_PATH = "de/schoko/renderingeximplspace/assets/";
	
	private Image backgroundTile;
	private UFO ufo;
	private ArrayList<Cow> cows;
	
	// In milliseconds
	private double cowSpawnCooldown = 0;
	private double maxCowSpawnCooldown = 1500;
	
	public SpaceGame() {
		cows = new ArrayList<>();
	}
	
	@Override
	public void onLoad(Context context) {
		ImagePool imagePool = context.getImagePool();
		imagePool.addImage("beam", ASSET_PATH + "beam.png", ImageLocation.JAR);
		imagePool.addImage("cowLeft", ASSET_PATH + "cowLeft.png", ImageLocation.JAR);
		imagePool.addImage("cowRight", ASSET_PATH + "cowRight.png", ImageLocation.JAR);
		imagePool.addImage("backgroundTile", ASSET_PATH + "grass.png", ImageLocation.JAR);
		imagePool.addImage("shadow", ASSET_PATH + "shadow.png", ImageLocation.JAR);
		imagePool.addImage("ufo", ASSET_PATH + "ufo.png", ImageLocation.JAR);
		
		backgroundTile = imagePool.getImage("backgroundTile");
		context.getCamera().setCameraPath((Camera camera, double deltaTimeMS) -> {
			return new CameraPathPoint(0, ufo.getY() + 3);
		});
		
		ufo = new UFO(cows);
		ufo.load(context);
	}
	
	@Override
	public void render(Graph g, double deltaTimeMS) {
		double scrollY = ufo.getY();
		
		for (int i = (int) (scrollY - 15); i <= scrollY + 15; i++) {
			for (int j = -15; j <= 15; j++) {
				g.drawImage(backgroundTile.getAWTImage(), j, i, 16);
			}
		}
		
		cowSpawnCooldown -= deltaTimeMS;
		if (cowSpawnCooldown < 0) {
			cows.add(new Cow(Math.random() * 10 - 5, scrollY + 15, ASSET_PATH, getContext().getImagePool()));
			cowSpawnCooldown = maxCowSpawnCooldown;
		}
		
		for (int i = 0; i < cows.size(); i++) {
			cows.get(i).render(g, deltaTimeMS);
		}
		
		ufo.render(g, deltaTimeMS);
		
		g.getHUD().drawText("OK", 5, 20, Color.BLACK, new Font("Segoe UI", Font.PLAIN, 25));
		
		cows.removeIf((cow) -> {
			return cow.isRemoved() || cow.getY() < scrollY - 15;
		});
	}
}
