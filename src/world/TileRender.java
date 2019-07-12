package world;

import java.util.HashMap;

import org.joml.*;

import render.*;

public class TileRender {
	
	private HashMap<String, Texture> tile_textures;
	private Model model;
	
	float[] vertices = new float[] {
			   -1f,  1f,  0,
				1f,  1f,  0,
				1f, -1f,  0,
			   -1f, -1f,  0
			};
	float[] texture = new float[] {
			0, 0,
			0, 1,
			1, 1,
			1, 0
	};
	int []indices = new int [] {
			0, 1, 2,
			2, 3, 0
	};
	

	public TileRender() {
		
		tile_textures = new HashMap<String, Texture>();
		
			
		
		model = new Model(vertices, texture, indices);
		
		for(int i = 0; i < Tiles.tiles.length; i++) {
			if(Tiles.tiles[i] != null) {
				if(!tile_textures.containsKey(Tiles.tiles[i].getTexture())) {
					String tex = Tiles.tiles[i].getTexture();
					tile_textures.put(tex, new Texture(tex));
					
				}
			}
			
		}
		
	}
	
	public void renderTile(Tiles tile, int x, int y, Shader shader, Matrix4f world, Camera camera) {
		
		shader.bind();
		if(tile_textures.containsKey(tile.getTexture())) 
			tile_textures.get(tile.getTexture()).bind(0);

		
		Matrix4f tile_pos = new Matrix4f().translate(new Vector3f((x*2), (y*2), 0));
		Matrix4f target = new Matrix4f();
		
		camera.getProjection().mul(world, target);
		target.mul(tile_pos);
		
		shader.setUniform("sampler", 0);
		shader.setUniform("projection", target);
		
		model.render();
		
	}
}
