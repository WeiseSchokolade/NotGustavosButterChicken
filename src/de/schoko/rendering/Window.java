package de.schoko.rendering;

import de.schoko.rendering.debugchanger.ChangeApplier;

public class Window {
	private String title;
	private Panel panel;
	private Renderer renderer;
	private SwingWindow swingWindow;
	private RendererSettings rendererSettings;
	private Context context;
	private Image windowIcon;
	
	/**
	 * Creates a new invisible Window with the given titel and renderer object.
	 * To make the window visible, use {@link Window#open()}
	 * @param renderer
	 * @param title
	 */
	public Window(Renderer renderer, String title) {
		this.renderer = renderer;
		this.title = title;
		this.rendererSettings = new RendererSettings();
	}
	
	/**
	 * Calls {@link Renderer#onLoad(Context)} and makes the window visible.
	 */
	public void open() {
		panel = new Panel(renderer, rendererSettings);
		
		// Sets up the context and loads it into the renderer
		this.context = new Context(
				this,
				this.rendererSettings,
				this.panel.getCamera(),
				new Keyboard(),
				new Mouse(panel),
				new ImagePool());
		this.panel.setContext(context);
		this.renderer.setContext(context);
		this.renderer.onLoad(context);
		
		// Opens the window and starts the rendering process
		swingWindow = new SwingWindow(title, panel);
		if (windowIcon != null) {
			swingWindow.setIconImage(windowIcon.getAWTImage());
		}
	}

	public void openEditPanel(String initialText, ChangeApplier ca) {
		swingWindow.openEditPanel(initialText, ca);
	}
	
	public void closeEditPanel() {
		swingWindow.closeEditPanel();
	}
	
	public RendererSettings getSettings() {
		return rendererSettings;
	}
	
	public Panel getPanel() {
		return panel;
	}
	
	protected SwingWindow getSwingWindow() {
		return swingWindow;
	}
	
	public void setWindowIcon(Image image) {
		if (swingWindow != null) {
			swingWindow.setIconImage(image.getAWTImage());
		} else {
			windowIcon = image;
		}
	}
}
