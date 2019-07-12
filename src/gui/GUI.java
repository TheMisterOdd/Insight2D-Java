package gui;

import org.joml.*;

import render.*;
import assest.*;
import display.Display;

public class GUI {
	private Shader shader;
	private Camera camera;
	private TileSheet tiles;
	private Display window;
	
	public GUI(Display window, String TileSheet, int amountOfTiles, int position) {
		shader = new Shader("gui");
		camera = new Camera(window.getWidth(), window.getHeight());
		tiles = new TileSheet(TileSheet, amountOfTiles);
		this.window = window;
	}
	
	public void resizeCamera(Display window) {
		camera.setProjection(window.getWidth(), window.getHeight());
	}
	
	public void Render(int ScreenPosX, int ScreenPosY, int SpriteposX, int SpriteposY, int scale) {
		Matrix4f mat = new Matrix4f();
		camera.getProjection().scale(scale, mat);
		mat.translate(window.getWidth() / ScreenPosX, window.getHeight() / ScreenPosY, 0);
		
		shader.bind();
		shader.setUniform("projection", mat);
		
		tiles.bindTile(shader, SpriteposX, SpriteposY);
		
		Assest.getModel().render();
	}
	
}
