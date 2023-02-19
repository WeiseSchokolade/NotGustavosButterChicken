package de.schoko.rendering;

public enum ImageLocation {
	JAR((String path) -> {
		return ImgGetter.getImageFromResources(path);
	}),
	FILE((String path) -> {
		return ImgGetter.getImageFromFile(path);
	}),
	WEB((String path) -> {
		return ImgGetter.getImageFromWeb(path);
	});
	
	private ImageGetter imageGetter;
	
	ImageLocation(ImageGetter imageGetter) {
		this.imageGetter = imageGetter;
	}
	
	public java.awt.Image getImage(String path) {
		return imageGetter.getImage(path);
	}
	
	private interface ImageGetter {
		java.awt.Image getImage(String path);
	}
}
