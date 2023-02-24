package de.schoko.jamegam25;

import java.awt.Font;

import javax.swing.JOptionPane;

import java.awt.Color;

import de.schoko.rendering.Context;
import de.schoko.rendering.Graph;
import de.schoko.rendering.HUDGraph;
import de.schoko.rendering.Keyboard;
import de.schoko.rendering.Renderer;
import de.schoko.rendering.Window;

public class Project extends Renderer {
	public static final String ASSET_PATH = "de/schoko/jamegam25/assets/";

	public static void main(String[] args) {
		Window window = new Window(new Project(), "Not Gustavo's Butter Chicken");
		window.getSettings().setRenderCoordinateSystem(false);
		window.getSettings().setDisplayStartedNotification(false);
		window.getSettings().setMaximizedByDefault(true);
		window.open();
	}

	private boolean paused;
	private Menu menu;

	@Override
	public void onLoad(Context context) {
		setMenu(new LoadingMenu());
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
			String text = "Your game is paused. Press ESCAPE to unpause.";
			Font font = new Font("Segoe UI", Font.PLAIN, 15);
			double width = Graph.getStringWidth(text, font);
			HUDGraph hudGraph = g.getHUD();
			hudGraph.drawText(text, hudGraph.getWidth() / 2 - width / 2, hudGraph.getHeight() / 2, Color.BLACK, font);
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
	}

	// This method is used to open a menu that has been open before (The onLoad has already been called)
	public void returnToMenu(Menu menu) {
		if (this.menu != null) {
			this.menu.onLeave(getContext());
			this.menu.stopSounds();
		}
		this.menu = menu;
	}
}
