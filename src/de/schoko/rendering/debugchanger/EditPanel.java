package de.schoko.rendering.debugchanger;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class EditPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 8449486965432877301L;
	private JTextArea textArea;
	private JButton confirmButton;
	private JButton closeButton;
	private ChangeApplier ca;
	
	public EditPanel() {
		this.setVisible(false);
		this.setLayout(new BorderLayout());
		textArea = new JTextArea();
		textArea.setFont(new Font("Consolas", Font.PLAIN, 20));
		JScrollPane pane = new JScrollPane(textArea);
		pane.setPreferredSize(new Dimension(400, 500));
		this.add(pane);
		
		JPanel bottomPanel = new JPanel();
		confirmButton = new JButton("Apply");
		confirmButton.addActionListener(this);
		bottomPanel.add(confirmButton);
		closeButton = new JButton("Close");
		closeButton.addActionListener(this);
		bottomPanel.add(closeButton);
		this.add(bottomPanel, BorderLayout.SOUTH);
	}
	
	public void update(String initialText, ChangeApplier ca) {
		textArea.setText(initialText);
		textArea.setEditable(true);
		this.setVisible(true);
		this.ca = ca;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(confirmButton)) {
			textArea.setEditable(false);
			this.setVisible(false);
			ca.applyChanges(textArea.getText());
		}
		if (e.getSource().equals(closeButton)) {
			textArea.setEditable(false);
			this.setVisible(false);
		}
	}

	public void close() {
		this.setVisible(false);
		textArea.setEditable(false);
		this.setFocusable(false);
	}
}
