package render;

import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.glfw.GLFW.*;

import java.awt.image.*;
import java.io.*;
import java.nio.*;

import javax.imageio.*;

import org.lwjgl.*;
import org.lwjgl.glfw.GLFWImage;

public class Texture {

	private int id, width, height;
	
	public Texture(String Filename){
		
		BufferedImage bi;
		
		try {
			bi = ImageIO.read(new File("resources/textures/"+Filename+".png"));
			width = bi.getWidth();
			height = bi.getHeight();
			
			int[] pixels_raw = new int [width*height*4];
			pixels_raw = bi.getRGB(0, 0, width, height, null, 0, width);
			
			ByteBuffer pixels =  BufferUtils.createByteBuffer(width * height * 4);
			
			for (int j = 0; j < height; j++) {
				for(int i = 0; i < width; i++) {
					int pixel = pixels_raw[i * width + j];
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
			
	
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
			
			pixels = null;
			pixels_raw = null;
			bi = null;
		
		
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}
	
	private static GLFWImage[] getImageBuffer(String filename) {
		InputStream stream = null;
		try {
			stream = new FileInputStream(filename);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    BufferedImage image = null;
		try {
			image = ImageIO.read(stream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    int width = image.getWidth();
	    int height = image.getHeight();

	    int[] pixels = new int[width * height];
	    image.getRGB(0, 0, width, height, pixels, 0, width);

	    // convert image to RGBA format
	    ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);

	    for (int y = 0; y < height; y++)
	    {
	        for (int x = 0; x < width; x++)
	        {
	            int pixel = pixels[y * width + x];

	            buffer.put((byte) ((pixel >> 16) & 0xFF));  // red
	            buffer.put((byte) ((pixel >> 8) & 0xFF));   // green
	            buffer.put((byte) (pixel & 0xFF));          // blue
	            buffer.put((byte) ((pixel >> 24) & 0xFF));  // alpha
	        }
	    }
	    buffer.flip(); // this will flip the cursor image vertically

	    // create a GLFWImage
	    GLFWImage[] cursorImg = new GLFWImage[1];
	    cursorImg[0] = GLFWImage.create();
	    cursorImg[0].width(width);     // setup the images' width
	    cursorImg[0].height(height);   // setup the images' height
	    cursorImg[0].pixels(buffer);   // pass image data
		
	    width = 0;
	    height = 0;
	    
	    buffer = null;
	    
	    return cursorImg;
	}
	
	public static void setCursor(long window, String filename) {
		
		GLFWImage[] images = getImageBuffer(filename);
		
	    long cursorID = glfwCreateCursor(images[0], 0 , 0);

	    // set current cursor
	    glfwSetCursor(window, cursorID);
	    
	    filename = null; images = null; cursorID = 0; window = 0;
	}
	
	public static void setIcon(long window, String filename) {
		
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
