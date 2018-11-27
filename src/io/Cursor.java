package io;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWImage;


public class Cursor {
	
	private long window;
	
	public Cursor(String Filename, long window) {
		this.window = window;
		
	    InputStream stream = null;
		try {
			stream = new FileInputStream(Filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	    GLFWImage cursorImg= GLFWImage.create();
	    cursorImg.width(width);     // setup the images' width
	    cursorImg.height(height);   // setup the images' height
	    cursorImg.pixels(buffer);   // pass image data

	    // create custom cursor and store its ID
	    int hotspotX = 0;
	    int hotspotY = 0;
	    long cursorID = GLFW.glfwCreateCursor(cursorImg, hotspotX , hotspotY);
	    
	    SetCursor(cursorID);
		
		
	}
	
	public void SetCursor(long cursorID) {
		

	    // set current cursor
		glfwSetCursor(this.window, cursorID);
		
	}
	
}
