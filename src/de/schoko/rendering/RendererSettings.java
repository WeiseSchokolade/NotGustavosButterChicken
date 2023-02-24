package de.schoko.rendering;

import java.awt.Color;

public class RendererSettings {
	private boolean autoCam;
	private Color backgroundColor;
	private boolean crashOnException;
	private boolean displayStartedNotification;
	private boolean maximizedByDefault;
	private boolean renderCoordinateSystem;
	
	protected RendererSettings() {
		autoCam = true;
		backgroundColor = Color.LIGHT_GRAY;
		crashOnException = true;
		displayStartedNotification = true;
		renderCoordinateSystem = true;
	}
	
	public boolean isRenderingCoordinateSystem() {
		return renderCoordinateSystem;
	}
	
	/**
	 * @param renderCoordinateSystem Whether a coordinate system for math is rendered
	 */
	public void setRenderCoordinateSystem(boolean renderCoordinateSystem) {
		this.renderCoordinateSystem = renderCoordinateSystem;
	}

	public boolean isAutoCam() {
		return autoCam;
	}
	
	/**
	 * @param autoCam Whether the user can move the camera by default
	 */
	public void setAutoCam(boolean autoCam) {
		this.autoCam = autoCam;
	}
	
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	
	/**
	 * Sets the background of the window to a {@link java.awt.Color}
	 * @param backgroundColor The color
	 */
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
	/**
	 * Sets the background of the window to a color in rgb color space.
	 * @param r Red Color Amount (Value between 0 and 255)
	 * @param g Green Color Amount (Value between 0 and 255)
	 * @param b Blue Color Amount (Value between 0 and 255)
	 */
	public void setBackgroundColor(int r, int g, int b) {
		backgroundColor = Graph.getColor(r, g, b);
	}
	
	public boolean isCrashingOnException() {
		return crashOnException;
	}
	
	public void setCrashOnException(boolean crashOnException) {
		this.crashOnException = crashOnException;
	}
	
	public boolean isDisplayingStartedNotification() {
		return displayStartedNotification;
	}
	
	/**
	 * Displays a Notification "Started" after starting when true. Defaults to true.
	 * @param displayStartedNotification Whether to display the "Started" Notification.
	 */
	public void setDisplayStartedNotification(boolean displayStartedNotification) {
		this.displayStartedNotification = displayStartedNotification;
	}
	
	public boolean isMaximizedByDefault() {
		return maximizedByDefault;
	}
	
	/**
	 * Maximizes the window after it opens. Defaults to false
	 * @param maximizedByDefault Whether to maximize the window.
	 */
	public void setMaximizedByDefault(boolean maximizedByDefault) {
		this.maximizedByDefault = maximizedByDefault;
	}
}
