package world;


import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import org.joml.*;

import java.util.*;

import collision.*;
import display.*;
import render.*;
import entity.*;

public class World {
	
	private final int view = 32;
	private byte[] tiles;
	private AABB[] bounding_boxes;
	private List<Entity> entities;
	private int width, height, scale;
	
	private Matrix4f world;
	
	public World(String world, Camera camera) {
		try {
			BufferedImage tile_sheet = ImageIO.read(new File("resources/levels/" + world + "_tiles.png"));
			BufferedImage entity_sheet = ImageIO.read(new File("resources/levels/" + world + "_entity.png"));
			
			width = tile_sheet.getWidth();   // getting the width of the world by getting the width of the world image
			height = tile_sheet.getHeight(); // getting the height of the world by getting the height of the world image
			scale = 36;
			
			this.world = new Matrix4f().setTranslation(new Vector3f(0));
			this.world.scale(scale);
			
			int[] colorTileSheet = tile_sheet.getRGB(0, 0, width, height, null, 0, width);
			int[] colorEntitySheet = entity_sheet.getRGB(0, 0, width, height, null, 0, width);
			
			tiles = new byte[width * height];
			bounding_boxes = new AABB[width*height];
			entities = new ArrayList<Entity>();
			
			Transform transform;
			
			for(int y = 0; y < height; y++) {
				for(int x = 0; x < width; x++) {
					
					int red = (colorTileSheet[x + y * width] >> 16) & 0xFF;			
					int entity_index = (colorEntitySheet[x + y * width] >> 16) & 0xFF;
					int entity_alpha = (colorEntitySheet[x + y * width] >> 24) & 0xFF;
					
					Tiles t ;
					
					try {
						t = Tiles.tiles[red];
					} catch (ArrayIndexOutOfBoundsException e) {
						t = null;
					}
					
					if(t != null) setTile(t, x, y);
					else { setTile(null, x, y); }
						
						
					
					if(entity_alpha > 0) {
						transform = new Transform();
						transform.pos.x = x*2;
						transform.pos.y = -y*2;
						
						switch (entity_index) {
						case 1:
							
							Player player = new Player(transform);
							entities.add(player);
							camera.getPosition().set(transform.pos.mul(-scale, new Vector3f()));
							break;

						default:
							break;
						}
						
					}
						
					
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public World() {
		width = 256;
		height = 256;
		scale = 64;
		
		tiles = new byte[width * height * scale];
		bounding_boxes = new AABB[width * height * scale];
		
		world = new Matrix4f().setTranslation(new Vector3f(0));
		world.scale(scale);
		

	}	
	
	public void render(TileRender render, Shader shader, Camera camera, Display window) {
		int posX = ((int)camera.getPosition().x + (window.getWidth() / 2)) / (scale*2);
		int posY = ((int)camera.getPosition().y - (window.getHeight() / 2)) / (scale*2);
	
		for(int i = 0; i < view; i++) {
			for(int j = 0; j < view; j++) {
				Tiles t = getTiles(i-posX, j+posY);
				if(t !=null)
					render.renderTile(t, i-posX, -j-posY, shader, world, camera);
			}
		}
		
		
		for(Entity entity : entities) {
			entity.render(shader, camera, this);
		}
	
	}
	
	public void update(float delta, Display window, Camera camera) {
		for(Entity entity : entities) {
			entity.update(delta, window, camera, this);
		}
		
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).collideWithTiles(this);
			for(int j = i+1; j < entities.size(); j++) {
				entities.get(i).collideWithTiles(entities.get(j));
			}
			
			entities.get(i).collideWithTiles(this);
		}				
	}
	
	public void correctCamera(Camera camera, Display window) {
		
		Vector3f pos = camera.getPosition();
		
		int w = -width * scale * 2;
		int h = height * scale * 2;
		
		System.out.println(pos.x);
		
		if(pos.x > -(window.getWidth()/2) + scale)
			pos.x = -(window.getWidth()/2) + scale;
		
		if(pos.x < w + (window.getWidth()/2) + scale)
			pos.x = w + (window.getWidth()/2) + scale;
		
		if(pos.y < (window.getHeight()/2) - scale)
			pos.y = (window.getHeight()/2) - scale;
		
		if(pos.y > h -(window.getHeight()/2) - scale)
			pos.y = h -(window.getHeight()/2) - scale;
		
	
		
	}
	
	public void setTile(Tiles tile, int x, int y) {
		tiles[x + y * width] = tile.getId();
		if(tile.isSolid()) {
			bounding_boxes[x + y * width] = new AABB(new Vector2f(x*2, -y*2), new Vector2f(1,1));
		}else {
			bounding_boxes[x + y * width] = null;
		}
	}
	
	public Tiles getTiles(int x, int y) {
		
		try {
			return Tiles.tiles[tiles[x + y * width]];
		}
		catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public AABB getTilesBoundingBox(int x, int y) {
		
		try {
			return bounding_boxes[x + y * width];
		}
		catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}

	public int getScale() { return scale; }
	public Matrix4f getWorldMatrix() { return world; }
	
	
	
}
