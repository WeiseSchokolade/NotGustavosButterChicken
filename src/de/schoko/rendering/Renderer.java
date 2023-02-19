package de.schoko.rendering;

public abstract class Renderer {
	private Context context;
	
	/**
	 * Optional event for when the window is opened. You will probably want to override it.
	 * You should keep the reference so you can access it again at a later point in time.
	 * @param context Reference to all important information of renderer.
	 */
	public void onLoad(Context context) {
	}
	
	/**
	 * Required event for renderer.
	 * @param g Graph used to draw stuff
	 * @param deltaTime Time since last call in milliseconds
	 */
	public abstract void render(Graph g, double deltaTimeMS);
	
	final void setContext(Context context) {
		this.context = context;
	}
	
	/**
	 * Returns the window reference this renderer belongs to
	 * @return The {@link Context} of this renderer
	 */
	public Context getContext() {
		return context;
	}
}
