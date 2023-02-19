package de.schoko.rendering;

public class Image {
	private String name;
	private java.awt.Image image;
	
	public Image(String name, java.awt.Image image) {
		this.name = name;
		this.image = image;
	}
	
	public String getName() {
		return name;
	}
	
	public java.awt.Image getAWTImage() {
		return image;
	}
}
