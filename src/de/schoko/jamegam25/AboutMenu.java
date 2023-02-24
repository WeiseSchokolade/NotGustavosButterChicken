package de.schoko.jamegam25;

import java.awt.Color;
import java.awt.Font;

import de.schoko.rendering.Context;
import de.schoko.rendering.Graph;
import de.schoko.rendering.HUDGraph;
import de.schoko.rendering.Keyboard;

public class AboutMenu extends Menu {
	private static final String[] aboutMessages = {
		"#About The Game",
		"The game was created for Jame Gam #25 in Java by a group of 4 people",
		"It uses a custom rendering library.",
		"",
		"#Implementation of Theme & Special Object (Spoiler)",
		"The theme is implemented in different aspects like the fact that you",
		"get damage by walking through puddles, the ship sinks due to the",
		"puddles etc. The implementation of the special object (Butter Chicken)",
		"is probably quite clear.",
		"",
		"#Thank you for playing!"
	};
	public AboutMenu() {
		super(false);
	}

	@Override
	public void onLoad(Context context) {
		context.getSettings().setBackgroundColor(0, 0, 0);
	}

	@Override
	public void render(Graph g, double deltaTimeMS) {
		Keyboard keyboard = new Keyboard();
		if (keyboard.isPressed(Keyboard.ESCAPE)) {
			getProject().setMenu(new MainMenu());
		}

		HUDGraph hud = g.getHUD();
		for (int i = 0; i < aboutMessages.length; i++) {
			String message = aboutMessages[i];
			Font font = new Font("Segoe UI", Font.PLAIN, 20);
			if (message.startsWith("#")) {
				font = font.deriveFont(Font.BOLD).deriveFont(22.0f);
				message = message.substring(1);
			} else if (message.startsWith("\\#")) {
				message = message.replaceFirst("\\#", "#");
			}
			hud.drawText(message, 5, 5 + i * 25 + 25, Color.WHITE, font);
		}

		getProject().drawMainMenuButton(hud);
	}
}
