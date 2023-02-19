package de.schoko.rendering;

import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener {
	private int x, y;
	private boolean[] pressed = new boolean[MouseInfo.getNumberOfButtons() + 1];
	private boolean inPanel = false;
	
	public Mouse(Panel panel) {
		panel.addMouseListener(this);
		panel.addMouseMotionListener(this);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
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
}
