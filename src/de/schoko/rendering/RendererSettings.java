package de.schoko.rendering;

import java.awt.Color;

public class RendererSettings {
	private boolean renderCoordinateSystem;
	private boolean autoCam;
	private Color backgroundColor;
	private boolean crashOnException;
	
	protected RendererSettings() {
		renderCoordinateSystem = true;
		autoCam = true;
		backgroundColor = Color.LIGHT_GRAY;
		crashOnException = true;
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
	
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
	public boolean isCrashingOnException() {
		return crashOnException;
	}
	
	public void setCrashOnException(boolean crashOnException) {
		this.crashOnException = crashOnException;
	}
}
