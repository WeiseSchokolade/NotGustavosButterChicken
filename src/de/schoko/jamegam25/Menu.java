package de.schoko.jamegam25;

import java.util.ArrayList;

import de.schoko.rendering.Context;
import de.schoko.rendering.Graph;

public abstract class Menu {
	private Context context;
	private Project project;
	private boolean pausable;
	private ArrayList<Sound> sounds;

	public Menu(boolean isPausable) {
		this.pausable = isPausable;
		this.sounds = new ArrayList<>();
	}

	public void onLoad(Context context) {

	}

	public final void onLeave(Context context) {
		
	}

	public abstract void render(Graph g, double deltaTimeMS);
	
	public void addSound(Sound sound) {
		this.sounds.add(sound);
	}

	public void stopSounds() {
		for (int i = 0; i < sounds.size(); i++) {
			sounds.get(i).stop();
		}
		sounds.clear();
	}

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

	public boolean isPausable() {
		return pausable;
	}
}
