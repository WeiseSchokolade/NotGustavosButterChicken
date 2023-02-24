package de.schoko.jamegam25;

import java.awt.Color;
import java.awt.Font;

import de.schoko.rendering.Context;
import de.schoko.rendering.Graph;
import de.schoko.rendering.HUDGraph;
import de.schoko.rendering.ImageLocation;
import de.schoko.rendering.ImagePool;
import de.schoko.rendering.Keyboard;
import de.schoko.rendering.Image;

public class Credits extends Menu {
	private static final String creditString = "Thank you for playing!\n\n- Credits -\n\nDirector: WeiseSchokolade\n\nProgramming: WeiseSchokolade, wuaht\nDesign: wuaht, WeiseSchokolade\nMusic: Demaon, Namikah, WeiseSchokolade\nSynchro (Not Implemented): Demaon, Namikah\n\nTeam: Demaon, Wuaht, Namikah, WeiseSchokolade\n\nMade For Jame Gam #25\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nThank you for playing!	";

	private String[] credits;

	private double t;
	private Image sign;

	public Credits() {
		super(false);
		credits = creditString.split("\n");
	}

	@Override
	public void onLoad(Context context) {
		context.getSettings().setBackgroundColor(0, 0, 0);

		// Image Loading
		ImagePool imagePool = context.getImagePool();
		imagePool.loadImage("sign", Project.ASSET_PATH + "sign.png", ImageLocation.JAR);
		
		// Object Instanciation
		sign = imagePool.getImage("sign");
		
		t = -120; // Extra Space for the Logo
	}

	@Override
	public void render(Graph g, double deltaTimeMS) {
		t += deltaTimeMS / 1000 * 30;
		if (getContext().getKeyboard().isPressed(Keyboard.SPACE)) {
			t += deltaTimeMS / 1000 * 90;
		}
		HUDGraph hud = g.getHUD();
		for (int i = 0; i < credits.length; i++) {
			drawCenteredText(credits[i], hud, i * 25 - t);
		}
		drawRightAlignedText(hud.getWidth(), hud.getHeight(), "Space to Scroll; Escape to Close", hud);
		double scale = 0.5;
		double width = sign.getAWTImage().getWidth(null) / scale;
		hud.drawImage(hud.getWidth() / 2 - width / 2, hud.getHeight() - 80 - t, sign, scale);
		
		if (hud.getHeight() + 20 + (credits.length * 25 - t) < 0 || getContext().getKeyboard().isPressed(Keyboard.ESCAPE)) {
			getProject().setMenu(new MainMenu());
		}
	}
	
	public void drawCenteredText(String text, HUDGraph hud, double offset) {
		Font font = new Font("Consolas", Font.BOLD, 20);
		double textWidth = Graph.getStringWidth(text, font);
		hud.drawText(text, hud.getWidth() / 2 - textWidth / 2, hud.getHeight() + 20 + offset, Color.WHITE, font);
	}

	public void drawRightAlignedText(double x, double y, String text, HUDGraph hud) {
		Font font = new Font("Consolas", Font.BOLD, 20);
		double textWidth = Graph.getStringWidth(text, font);
		hud.drawText(text, x - textWidth - 5, y - 5, Color.GRAY, font);
	}
}
