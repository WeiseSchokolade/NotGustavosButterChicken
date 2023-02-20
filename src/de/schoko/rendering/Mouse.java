package de.schoko.rendering;

import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener {
	public static final int LEFT_BUTTON = MouseEvent.BUTTON1;
	public static final int RIGHT_BUTTON = MouseEvent.BUTTON2;
	public static final int MIDDLE_BUTTON = MouseEvent.BUTTON3;
	
	private int x, y;
	private boolean[] pressed = new boolean[MouseInfo.getNumberOfButtons() + 1];
	private boolean inPanel = false;
	private Context context;
	
	public Mouse(Panel panel) {
		panel.addMouseListener(this);
		panel.addMouseMotionListener(this);
	}
	
	public double getX() {
		return context.getLastGraph().convBackFromSX(x);
	}
	
	public double getY() {
		return context.getLastGraph().convBackFromSY(y);
	}
	
	public boolean isPressed(int buttonNumber) {
		return pressed[buttonNumber];
	}
	
	public boolean isInPanel() {
		return inPanel;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		pressed[e.getButton()] = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		pressed[e.getButton()] = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		inPanel = true;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		inPanel = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}
	
	public void setContext(Context context) {
		this.context = context;
	}
}
