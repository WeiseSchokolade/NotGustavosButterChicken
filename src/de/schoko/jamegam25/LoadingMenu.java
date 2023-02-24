package de.schoko.jamegam25;

import java.awt.Color;
import java.awt.Font;
import java.security.InvalidParameterException;

import de.schoko.rendering.Context;
import de.schoko.rendering.Graph;
import de.schoko.rendering.HUDGraph;
import de.schoko.rendering.ImageLocation;
import de.schoko.rendering.ImagePool;

public class LoadingMenu extends Menu {

	public LoadingMenu() {
		super(false);
	}

	@Override
	public void onLoad(Context context) {
		load(context, 
				"sign", "sign.png",
				"button", "button.png",
				"pirate", "pirate.png",
				"antagonist", "antagonist.png",
				"backdrop", "backdrop.png",
				"butterChicken", "butter_chicken.png");

		getProject().setMenu(new MainMenu());
	}

	@Override
	public void render(Graph g, double deltaTimeMS) {
		HUDGraph hud = g.getHUD();
		
		String text = "Loading...";
		Font font = new Font("Segoe UI", Font.BOLD, 20);
		double textWidth = Graph.getStringWidth(text, font);
		hud.drawText(text, hud.getWidth() / 2 - textWidth / 2, hud.getHeight() / 2, Color.WHITE, font);
	}
	
	public void load(Context context, String... names) {
		ImagePool imagePool = context.getImagePool();

		if (names.length % 2 == 1) throw new InvalidParameterException("Uneven number of arguments");
		
		for (int i = 0; i < names.length; i += 2) {
			String name = names[i];
			String path = names[i + 1];
			imagePool.loadImage(name, Project.ASSET_PATH + path, ImageLocation.JAR);
		}
	}
}
