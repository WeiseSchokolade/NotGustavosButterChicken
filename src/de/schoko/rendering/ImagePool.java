package de.schoko.rendering;

import java.util.HashMap;

public class ImagePool {
	private HashMap<String, Image> cache;
	
	public ImagePool() {
		cache = new HashMap<>();
	}
	
	public Image getImage(String name) {
		return cache.get(name);
	}
	
	public Image getImage(String name, String path, ImageLocation imageLocation) {
		if (cache.containsKey(name)) {
			return cache.get(name);
		} else {
			cache.put(name, new Image(name, imageLocation.getImage(path)));
			return cache.get(name);
		}
	}
	
	public void addImage(String name, String path, ImageLocation imageLocation) {
		cache.put(name, new Image(name, imageLocation.getImage(path)));
	}
	
	public void loadImage(String name, String path, ImageLocation imageLocation) {
		if (!cache.containsKey(name)) {
			cache.put(name, new Image(name, imageLocation.getImage(path)));
		}
	}
}
