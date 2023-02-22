package de.schoko.jamegam25;

import de.schoko.rendering.Context;
import de.schoko.rendering.Graph;

public abstract class Menu {
	private Context context;
	private Project project;

	public void onLoad(Context context) {

	}

	public void onLeave(Context context) {
		
	}

	public abstract void render(Graph g, double deltaTimeMS);
	
	public Project getProject() {
		return project;
	}

	protected void setProject(Project project) {
		this.project = project;
	}

	public Context getContext() {
		return context;
	}

	protected void setContext(Context context) {
		this.context = context;
	}
}
