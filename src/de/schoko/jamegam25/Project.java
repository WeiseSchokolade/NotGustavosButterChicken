package de.schoko.jamegam25;

import java.awt.Font;

import javax.swing.JOptionPane;

import java.awt.Color;

import de.schoko.rendering.Context;
import de.schoko.rendering.Graph;
import de.schoko.rendering.HUDGraph;
import de.schoko.rendering.Image;
import de.schoko.rendering.ImageLocation;
import de.schoko.rendering.Keyboard;
import de.schoko.rendering.Mouse;
import de.schoko.rendering.Renderer;
import de.schoko.rendering.Window;

public class Project extends Renderer {
	public static final String ASSET_PATH = "assets/";

	public static void main(String[] args) {
		Window window = new Window(new Project(), "Not Gustavo's Butter Chicken");
		window.getSettings().setRenderCoordinateSystem(false);
		window.getSettings().setDisplayStartedNotification(false);
		window.getSettings().setMaximizedByDefault(true);
		window.open();
	}

	private boolean paused;
	private Menu menu;
	private boolean mainMenuButtonPressed;
	private Image mainMenuButton;

	@Override
	public void onLoad(Context context) {
		setMenu(new LoadingMenu());
		context.getWindow().setWindowIcon(context.getImagePool().getImage("butterChickenHQ", ASSET_PATH + "butter_chicken_hq.png", ImageLocation.JAR));
		mainMenuButton = context.getImagePool().getImage("button", ASSET_PATH + "button.png", ImageLocation.JAR);
	}

	@Override
	public void render(Graph g, double deltaTimeMS) {
		if (getContext().getKeyboard().wasRecentlyPressed(Keyboard.ESCAPE)) {
			if (!paused) {
				if (menu.isPausable()) {
					paused = true;
				}
			} else {
				// You can always unpause
				paused = false;
			}
		}
		if (!paused) {
			try {
				menu.render(g, deltaTimeMS);
			} catch (Exception e) {
				e.printStackTrace();				
				int result = JOptionPane.showConfirmDialog(null, "An exception occured:\n" + e.getMessage() + "\n Continue?", "Exception", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.NO_OPTION) {
					System.exit(0);
				}
			}
		} else {
			HUDGraph hud = g.getHUD();
			
			String text = "Your game is paused. Press ESCAPE to unpause.";
			Font font = new Font("Segoe UI", Font.PLAIN, 20);
			double width = Graph.getStringWidth(text, font);
			hud.drawText(text, hud.getWidth() / 2 - width / 2, hud.getHeight() / 2, Color.BLACK, font);

			drawMainMenuButton(hud);
		}
	}

	public void setMenu(Menu menu) {
		if (this.menu != null) {
			this.menu.onLeave(getContext());
			this.menu.stopSounds();
		}
		this.menu = menu;
		this.menu.setContext(getContext());
		this.menu.setProject(this);
		this.menu.onLoad(getContext());
		mainMenuButtonPressed = false;
	}

	// This method is used to open a menu that has been open before (The onLoad has already been called)
	public void returnToMenu(Menu menu) {
		if (this.menu != null) {
			this.menu.onLeave(getContext());
			this.menu.stopSounds();
		}
		this.menu = menu;
		mainMenuButtonPressed = false;
	}

	public void drawMainMenuButton(HUDGraph hud) {
		Mouse mouse = getContext().getMouse();
		double scale = 0.16;
		int fontSize = 27;
		double imgWidth = mainMenuButton.getAWTImage().getWidth(null) / scale;
		double imgHeight = mainMenuButton.getAWTImage().getHeight(null) / scale;

		if (mouse.getScreenX() > hud.getWidth() / 2 - imgWidth / 2
				&& mouse.getScreenX() < hud.getWidth() / 2 + imgWidth / 2 &&
				mouse.getScreenY() > hud.getHeight() - imgHeight && mouse.getScreenY() < hud.getHeight()) {
			// Hovering
			if (mouse.isPressed(Mouse.LEFT_BUTTON)) {
				mainMenuButtonPressed = true;
			} else if (mainMenuButtonPressed) {
				setMenu(new MainMenu());
				paused = false;
				return;
			}
		} else {
			scale = 0.2;
			fontSize = 25;
			imgWidth = mainMenuButton.getAWTImage().getWidth(null) / scale;
			imgHeight = mainMenuButton.getAWTImage().getHeight(null) / scale;
		}

		hud.drawImage(hud.getWidth() / 2 - imgWidth / 2, hud.getHeight() - imgHeight, mainMenuButton, scale);

		String text = "Main Menu";
		Font font = new Font("Segoe UI", Font.BOLD, fontSize);
		double width = Graph.getStringWidth(text, font);
		hud.drawText(text, hud.getWidth() / 2 - width / 2, hud.getHeight() - imgHeight / 2, Color.WHITE, font);
	}
}
