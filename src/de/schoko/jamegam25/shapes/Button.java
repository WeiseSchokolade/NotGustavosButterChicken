package de.schoko.jamegam25.shapes;

import java.awt.Color;
import java.awt.Font;

import de.schoko.rendering.Context;
import de.schoko.rendering.Graph;
import de.schoko.rendering.HUDGraph;
import de.schoko.rendering.Mouse;
import de.schoko.rendering.shapes.ImageFrame;
import de.schoko.rendering.shapes.Shape;

public class Button extends Shape {
	private Context context;
	private double x, y;
	private double width, height;
	private ImageFrame image;
	private String text;
	private double scale;

	public Button(String text, double x, double y, double width, double height, String imgName, double imgScale, Context context) {
		this.context = context;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.image = new ImageFrame(this.x, this.y, context.getImagePool().getImage(imgName), imgScale);
		this.text = text;
		this.scale = imgScale;
	}

	public boolean touching() {
		Mouse mouse = context.getMouse();
		return (mouse.getX() > x - width / 2 && mouse.getX() < x + width / 2 && mouse.getY() > y - height / 2 && mouse.getY() < y + height / 2);
	}

	public boolean pressed() {
		Mouse mouse = context.getMouse();
		return (touching() && mouse.isPressed(Mouse.LEFT_BUTTON));
	}

	@Override
	public void render(Graph g) {
		Font font = new Font("Segoe UI", Font.BOLD, 20);
		if (touching()) {
			this.image.setScale(scale - 2);
			font = font.deriveFont(22.0f);
		} else {
			this.image.setScale(scale);
		}

		g.draw(image);
		// TODO: Make this cleaner by implementing drawing centered text into the rendering engine
		HUDGraph hud = g.getHUD();
		double textWidth = Graph.getStringWidth(text, font);
		hud.drawText(text, g.convSX(x) - textWidth / 2, g.convSY(y), Color.WHITE, font);
	}
}
