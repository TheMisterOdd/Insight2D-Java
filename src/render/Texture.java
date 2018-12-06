package render;


import static org.lwjgl.opengl.GL46.*;

import java.awt.image.*;
import java.io.*;
import java.nio.*;

import javax.imageio.*;

import org.lwjgl.*;

public class Texture {

	private int id;
	private int width;
	private int height;
	
	
	
	public Texture(String Filename){
		
		BufferedImage bi;
		
		try {
			bi = ImageIO.read(new File("./resource/textures/"+Filename+".png"));
			width = bi.getWidth();
			height = bi.getHeight();
			
			int[] pixels_raw = new int [width*height*4];
			pixels_raw = bi.getRGB(0, 0, width, height, null, 0, width);
			
			ByteBuffer pixels =  BufferUtils.createByteBuffer(width * height * 4);
			
			for (int j = 0; j < height; j++) {
				for(int i = 0; i < width; i++) {
					int pixel = pixels_raw[i*width+j];
					pixels.put((byte) ((pixel >> 16) & 0xFF));
					pixels.put((byte) ((pixel >> 8) & 0xFF));
					pixels.put((byte) (pixel & 0xFF));
					pixels.put((byte) ((pixel >> 24) & 0xFF));
				}
				
			}
			
			
			pixels.flip();
			
			id = glGenTextures();
			glBindTexture(GL_TEXTURE_2D, id);
			
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			
	
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE,pixels);
		
		
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
	}
	
	protected void finalize() throws Throwable {
		glDeleteTextures(id);
		super.finalize();
	}
	
	public void bind(int sampler) {
		
		if(sampler >= 0 && sampler <= 31) {
			glActiveTexture(GL_TEXTURE0 + sampler);
			glBindTexture(GL_TEXTURE_2D, id);
		}
		
		
	}
	
}
