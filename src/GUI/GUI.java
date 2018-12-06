package GUI;

import org.joml.*;
import render.*;
import assest.*;
import entity.Player;
import gameEngine.Window;

public class GUI {
	private Shader shader;
	private Camera camera;
	private TileSheet heart1;
	private Window window;
	
	public GUI(Window window) {
		shader = new Shader("gui");
		camera = new Camera(window.getWidth(), window.getHeight());
		heart1 = new TileSheet("HUD", 3);
		this.window = window;
	}
	
	public void resizeCamera(Window window) {
		camera.setProjection(window.getWidth(), window.getHeight());
	}
	
	public void Render() {
		Matrix4f mat = new Matrix4f();
		camera.getProjection().scale(25, mat);
		mat.translate(window.getWidth() / 52, window.getHeight() / 55, 0);
		
		shader.bind();
		shader.setUniform("projection", mat);
		
		if(Player.health >= 1) 
			heart1.bindTile(shader, 1, 2);
		
		Assest.getModel().render();
	}
}
