package de.schoko.rendering;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import de.schoko.rendering.debugchanger.ChangeApplier;
import de.schoko.rendering.debugchanger.EditPanel;

public class SwingWindow extends JFrame {
	private static final long serialVersionUID = 6777578284226687323L;
	private EditPanel editPanel;
	
	public SwingWindow(String title, Panel panel) {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		this.add(panel);
		
		editPanel = new EditPanel();
		
		this.add(editPanel, BorderLayout.EAST);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setTitle(title);
		this.setVisible(true);
	}

	public void openEditPanel(String initialText, ChangeApplier ca) {
		editPanel.update(initialText, ca);
	}

	public void closeEditPanel() {
		editPanel.close();
	}

}
