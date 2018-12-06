package render;

import org.joml.*;

public class TileSheet {
	private Texture texture;
	private Matrix4f scale;
	private Matrix4f translation;
	
	public TileSheet(String texture, int amountOfTiles) {
		this.texture = new Texture("HUD/"+ texture);
		
		scale = new Matrix4f().scale(1.0f/(float)amountOfTiles);
		translation = new Matrix4f();
	}
	
	public void bindTile(Shader shader, int x, int y) {
		scale.translate(y, x, 0, translation);
		shader.setUniform("sampler", 0);
		shader.setUniform("texModifier", translation);
		texture.bind(0);
	}
	
}
