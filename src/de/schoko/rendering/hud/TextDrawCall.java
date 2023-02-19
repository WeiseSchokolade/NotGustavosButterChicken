package de.schoko.rendering.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class TextDrawCall extends DrawCall {
	private String text;
	private double x, y;
	private Color color;
	private Font font;
	
	public TextDrawCall(String text, double x, double y, Color color, Font font) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.color = color;
		this.font = font;
	}
	
	@Override
	public void call(Graphics2D g2D) {
		g2D.setColor(color);
		g2D.setFont(font);
		g2D.drawString(text, (float) x, (float) y);
	}

}
