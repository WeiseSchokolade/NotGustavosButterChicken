package de.schoko.jamegam25;

import de.schoko.rendering.Graph;

public class EnemyBossFight extends Boss {
	private static final String[] names = {};

	public EnemyBossFight() {
		super(names[(int) Math.floor(Math.random() * names.length)]);
	}

	@Override
	public void render(Graph g, double deltaTimeMS) {
		
	}
	
}
