package de.schoko.jamegam25;

import java.awt.Color;
import java.awt.Font;

import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;
import java.awt.Toolkit;

import de.schoko.jamegam25.shapes.Button;
import de.schoko.rendering.Camera;
import de.schoko.rendering.CameraPathPoint;
import de.schoko.rendering.Context;
import de.schoko.rendering.Graph;
import de.schoko.rendering.HUDGraph;
import de.schoko.rendering.ImageLocation;
import de.schoko.rendering.ImagePool;

public class GameOverMenu extends Menu {
	private Game game;
	private Button mainMenuButton;
	private boolean mainMenuButtonPressed;
	private Button creditsButton;
	private boolean creditsButtonPressed;
	private Button copyResultsButton;
	private boolean copiedResults;

	public GameOverMenu(Game game) {
		super(false);
		this.game = game;
	}

	@Override
	public void onLoad(Context context) {
		String basePath = Project.ASSET_PATH;
		ImagePool imagePool = context.getImagePool();
		imagePool.addImage("button", basePath + "button.png", ImageLocation.JAR);

		getContext().getCamera().setCameraPath((Camera camera, double dt) -> {
			return new CameraPathPoint(0, 0, 75);
		});
		getContext().getSettings().setBackgroundColor(0, 0, 0);
		mainMenuButton = new Button("Main Menu", 0, -1, 3, 0.8, "button", 16, getContext());
		creditsButton = new Button("Credits", 0, -2, 3, 0.8, "button", 16, getContext());
		copyResultsButton = new Button("Copy Results", 0, -3, 3, 0.8, "button", 16, getContext());	
	}

	@Override
	public void render(Graph g, double deltaTimeMS) {
		HUDGraph hud = g.getHUD();
		
		g.draw(mainMenuButton);
		g.draw(creditsButton);
		g.draw(copyResultsButton);

		if (mainMenuButtonPressed && !mainMenuButton.pressed()) {
			getProject().setMenu(new MainMenu());
			return;
		}
		mainMenuButtonPressed = mainMenuButton.pressed();
		if (creditsButtonPressed && !creditsButton.pressed()) {
			getProject().setMenu(new Credits());
			return;
		}
		creditsButtonPressed = creditsButton.pressed();
		
		String text = "GAME OVER";
		Font font = new Font("Consolas", Font.BOLD, 50);
		double textWidth = Graph.getStringWidth(text, font);
		hud.drawText("GAME OVER", hud.getWidth() / 2 - textWidth / 2, hud.getHeight() / 2, Color.RED, font);

		font = new Font("Segoe UI", Font.PLAIN, 20);
		hud.drawText("Defeated Enemies: " + game.getTotalKilledEnemyAmount(), 5, hud.getHeight() - 70, Color.WHITE, font);
		
		String time = "";
		double totalTime = game.getTotalTime();
		int hours = (int) Math.floor(totalTime / 3600);
		if (hours != 0) {
			time += "" + hours + ":";
		}
		int minutes = (int) Math.floor((totalTime / 60) % 60);
		int seconds = (int) Math.floor(totalTime % 60);
		time += "" + minutes + ":";
		time += "" + seconds + "";
		hud.drawText("Time: " + time, 5, hud.getHeight() - 50, Color.WHITE, font);

		hud.drawText("Puddles: " + game.getTotalPuddleAmount(), 5, hud.getHeight() - 30, Color.WHITE, font);

		if (copyResultsButton.pressed()) {
			if (!copiedResults) {
				copyToClipboard("Not Gustavo's Butter Chicken\nDefeated Enemies: " + game.getTotalKilledEnemyAmount() + "\nTime: " + time + "\nPuddles: " + game.getTotalPuddleAmount());
				copiedResults = true;
			}
			copyResultsButton.setText("Copied to Clipboard");		
			
		} else {
			copyResultsButton.setText("Copy Results");
			copiedResults = false;
		}
	}
	
	public void copyToClipboard(String text) {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(new StringSelection(text), null);
	}
}
