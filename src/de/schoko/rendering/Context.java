package de.schoko.rendering;

public class Context {
	private Camera camera;
	private Graph lastGraph;
	private ImagePool imagePool;
	private Keyboard keyboard;
	private Mouse mouse;
	private RendererSettings settings;
	private Window window;
	
	public Context(Window window, RendererSettings settings, Camera camera, Keyboard keyboard, Mouse mouse, ImagePool imagePool) {
		this.window = window;
		this.settings = settings;
		this.camera = camera;
		this.keyboard = keyboard;
		this.mouse = mouse;
		this.imagePool = imagePool;
		this.mouse.setContext(this);
	}
	
	void setCamera(Camera camera) {
		this.camera = camera;
	}
	void setImagePool(ImagePool imagePool) {
		this.imagePool = imagePool;
	}
	void setKeyboard(Keyboard keyboard) {
		this.keyboard = keyboard;
	}
	void setLastGraph(Graph lastGraph) {
		this.lastGraph = lastGraph;
	}
	void setMouse(Mouse mouse) {
		this.mouse = mouse;
	}
	void setSettings(RendererSettings settings) {
		this.settings = settings;
	}
	void setWindow(Window window) {
		this.window = window;
	}
	public Camera getCamera() {
		return camera;
	}
	public ImagePool getImagePool() {
		return imagePool;
	}
	public Keyboard getKeyboard() {
		return keyboard;
	}
	public Graph getLastGraph() {
		return lastGraph;
	}
	public Mouse getMouse() {
		return mouse;
	}
	public RendererSettings getSettings() {
		return settings;
	}
	public Window getWindow() {
		return window;
	}
}
