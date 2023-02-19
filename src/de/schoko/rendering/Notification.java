package de.schoko.rendering;

public class Notification {
	private String message;
	private double timeLeft;
	
	/**
	 * @param message The message to display
	 * @param timeLeft Time to display this notification in seconds
	 */
	public Notification(String message, double timeLeft) {
		this.message = message;
		this.timeLeft = timeLeft * 1000;
	}

	public String getMessage() {
		return message;
	}
	
	/**
	 * @return The time left in milliseconds
	 */
	public double getTimeLeft() {
		return timeLeft;
	}
	
	/**
	 * @param timeLeft The time left in milliseconds
	 */
	public void setTimeLeft(double timeLeft) {
		this.timeLeft = timeLeft;
	}
}
