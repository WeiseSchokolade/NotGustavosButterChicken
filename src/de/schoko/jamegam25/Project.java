package de.schoko.jamegam25;

import de.schoko.rendering.Context;
import de.schoko.rendering.Graph;
import de.schoko.rendering.Renderer;
import de.schoko.rendering.Window;

public class Project extends Renderer {
	public static void main(String[] args) {
		Window window = new Window(new Project(), "Jame Gam 25");
		window.getSettings().setRenderCoordinateSystem(false);
		window.getSettings().setDisplayStartedNotification(false);
		window.open();
	}

	private Menu menu;

	@Override
	public void onLoad(Context context) {
		setMenu(new MainMenu());
	}

	@Override
	public void render(Graph g, double deltaTimeMS) {
		menu.render(g, deltaTimeMS);
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
		this.menu.setContext(getContext());
		this.menu.setProject(this);
		this.menu.onLoad(getContext());
	}
}
