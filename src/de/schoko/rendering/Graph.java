package de.schoko.rendering;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import de.schoko.rendering.shapes.Shape;

public class Graph {
	private static final int POINT_RADIUS = 2;
	private static final float DEBUG_FONT_SIZE = 14f;
	private static final float NOTIFICATION_FONT_SIZE = 25f;
	
	private Panel panel;
	private Graphics2D g2D;
	private int width, height;
	private int camX, camY;
	private double zoom = 50;
	private ArrayList<String> debugStrings;
	private HUDGraph hud;
	
	public Graph(Panel panel, Graphics gEntered, int width, int height, Camera camera, RendererSettings rendererSettings) {
		this.panel = panel;
		this.g2D = (Graphics2D) gEntered;
		this.width = width;
		this.height = height;
		this.zoom = camera.getZoom();
		this.camX = (int) (camera.getX() * zoom);
		this.camY = (int) -(camera.getY() * zoom);
		this.hud = new HUDGraph(g2D, width, height);
		g2D.setColor(rendererSettings.getBackgroundColor());
		g2D.fillRect(0, 0, width, height);
		g2D.setStroke(new BasicStroke((float) camera.getZoom() / 10));
		
		if (rendererSettings.isRenderingCoordinateSystem()) {
			g2D.setColor(Color.BLUE);
			g2D.drawLine(width / 2 - camX, 0, width / 2 - camX, height);
			g2D.setColor(Color.RED);
			g2D.drawLine(0, height / 2 - camY, width, height / 2 - camY);
			for (int i = -10; i <= 10; i++) {
				if (i == 0) continue;
				drawLine(i, 0.1, i, -0.1, Color.RED);
			}
			for (int i = -10; i <= 10; i++) {
				if (i == 0) continue;
				drawLine(-0.1, i, 0.1, i, Color.BLUE);
			}
		}
		debugStrings = new ArrayList<>();
	}
	
	public void finalize() {
		hud.draw();
		
		// Draw Notifications
		g2D.setColor(Color.BLACK);
		g2D.setFont(new Font("Segoe UI", Font.PLAIN, (int) NOTIFICATION_FONT_SIZE));
		double boxOffset = 5;
		double boxMargin = 4;
		
		// The higher the offset the lower the oldest message
		List<Notification> notifications = panel.getNotifications();
		double notificationHeight = NOTIFICATION_FONT_SIZE + boxMargin * 2;
		double offset = notifications.size() * (notificationHeight + boxOffset);
		for (int i = 0; i < notifications.size(); i++) {
			Notification n = notifications.get(i);
			int stringWidth = g2D.getFontMetrics().stringWidth(n.getMessage());
			g2D.setColor(new Color(0.1f, 0.1f, 0.1f, 0.1f));
			g2D.fillRect((int) (width / 2 - stringWidth / 2 - boxMargin), (int) (offset - NOTIFICATION_FONT_SIZE - boxMargin), (int) (stringWidth + boxMargin * 2), (int) (notificationHeight + boxMargin));
			g2D.setColor(Color.BLACK);
			g2D.drawString(n.getMessage(), width / 2 - stringWidth / 2, (int) offset);
			offset -= notificationHeight + boxOffset;
		}
		
		g2D.setColor(Color.BLUE);
		g2D.setFont(g2D.getFont().deriveFont(DEBUG_FONT_SIZE));
		for (int i = 0; i < debugStrings.size(); i++) {
			g2D.drawString(debugStrings.get(i), 1, i * (DEBUG_FONT_SIZE + 2) + DEBUG_FONT_SIZE);
		}
	}
	
	public void draw(Shape... shapes) {
		for (int i = 0; i < shapes.length; i++) {
			shapes[i].render(this);
		}
	}
	
	public void drawString(String s, double x, double y) {
		g2D.setColor(Color.BLACK);
		g2D.drawString(s, convSX(x), convSY(y));
	}
	
	public void drawString(String s, double x, double y, Color c) {
		g2D.setColor(c);
		g2D.drawString(s, convSX(x), convSY(y));
	}
	
	public void drawString(String s, double x, double y, Color c, Font font) {
		Font previousFont = g2D.getFont();
		g2D.setFont(font);
		g2D.setColor(c);
		g2D.drawString(s, convSX(x), convSY(y));
		g2D.setFont(previousFont);
	}
	
	/**
	 * Calls {@link Graph#drawPoint(double, double, Color)} with the color black
	 */
	public void drawPoint(double x, double y) {
		drawPoint(x, y, Color.BLACK);
	}
	
	/**
	 * Similar to {@link Graph#drawCircle(double, double, Color, double)} but will always use a radius of 0.2
	 */
	public void drawPoint(double x, double y, Color c) {
		g2D.setColor(c);
		g2D.fillArc(convSX(x) - POINT_RADIUS, convSY(y) + POINT_RADIUS, POINT_RADIUS * 2, POINT_RADIUS * 2, 0, 360);
	}
	
	/**
	 * Draws a circle
	 * @param x coordinate of center
	 * @param y coordinate of center
	 * @param c Color of circle
	 * @param radius Radius of circle
	 */
	public void drawCircle(double x, double y, Color c, double radius) {
		g2D.setColor(c);
		g2D.drawArc(convSX(x - radius / 2), convSY(y + radius / 2), convSW(radius), convSH(radius), 0, 360);
	}
	
	public void drawLine(double x0, double y0, double x1, double y1, Color c) {
		g2D.setColor(c);
		g2D.drawLine(convSX(x0), convSY(y0), convSX(x1), convSY(y1));
	}
	
	public void drawRect(double x0, double y0, double x1, double y1) {
		drawRect(x0, y0, x1, y1, Color.BLACK);
	}
	
	public void drawRect(double x0, double y0, double x1, double y1, Color c) {
		g2D.setColor(c);
		g2D.drawRect(convSX(x0), convSY(y1), convSW(x1 - x0), convSH(y1 - y0));
	}

	public void drawImage(Image image, double x, double y, double scale) {
		int imgWidth = (int) convSW(image.getWidth(null) / scale);
		int imgHeight = (int) convSH(image.getHeight(null) / scale);
		g2D.drawImage(image, convSX(x) - imgWidth / 2, convSY(y) - imgHeight / 2, imgWidth, imgHeight, null);
	}
	
	public void drawRotatedImage(Image image, double x, double y, double scale, double degrees) {
		double imgWidth = convSW(image.getWidth(null) / scale);
		double imgHeight = convSH(image.getHeight(null) / scale);
		int areaSize = (int) (imgWidth > imgHeight ? imgWidth * 2 : imgHeight * 2);
		Graphics2D area = (Graphics2D) g2D.create(convSX(x) - (int) imgWidth / 2, convSY(y) - (int) imgHeight / 2, (int) areaSize, (int) areaSize);
		double angle = convSA(degrees);
		area.rotate(angle, imgWidth / 2, imgHeight / 2);
		area.drawImage(image, 0, 0, (int) imgWidth, (int) imgHeight, null);
		area.dispose();
	}
	
	public void addDebugString(String s) {
		this.debugStrings.add(s);
	}
	
	/**
	 * Adds a notification once.
	 * @param message The message
	 * @param time The time the notification is displayed
	 */
	public void addNotification(String message, double time) {
		panel.addNotification(new Notification(message, time));
	}
	
	public int convSX(double x) {
		return (int) (x * zoom + width / 2) - camX;
	}
	
	public int convSY(double y) {
		return (int) (y * -zoom + height / 2) - camY;
	}
	
	public double convBackFromSX(double x) {
		return ((x + camX) - width / 2) / zoom;
	}
	
	public double convBackFromSY(double y) {
		return ((y + camY) - height / 2) / -zoom;
	}

    public int convSW(double w) {
        return (int) (zoom * w);
    }
    
    public int convSH(double h) {
        return (int) (zoom * h);
    }
    
    /**
     * Converts degrees to screen radians
     * @param degrees
     * @return radians
     */
    public double convSA(double degrees) {
    	return Math.toRadians((degrees - 90));
    }
    
    public HUDGraph getHUD() {
    	return hud;
    }
}
