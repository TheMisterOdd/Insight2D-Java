package display;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL46.*;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL33;

import io.*;


public class Display {
	

	private long window;
	private int width, height;
	private boolean fullscreen, hasResized;
	private Input input;
	private GLFWWindowSizeCallback windowSizeCallback;
	
	public Display(int width, int height, boolean fullscreen) {
		
		this.width = width;  this.height = height;
		this.fullscreen = fullscreen;
		this.hasResized = false;

	}

//Init the window
  public void Init(String title) {
	
	if(!glfwInit()) 
	{
		System.out.println("Couldn't load GLFW");
		System.exit(-1);
	}
	
	
	glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
	glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
	
	
	this.window = glfwCreateWindow(width, height, title, fullscreen ? glfwGetPrimaryMonitor() : 0, 0);
	
	
	if(window == 0) 
		System.exit(-1);
		
	
	GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
	glfwSetWindowPos(window, (videoMode.width() - width) / 2, (videoMode.height() - height) / 2);
	
	//glfwSetWindowAspectRatio(window, width, height);
	
	glfwMakeContextCurrent(window);
	
	input = new Input (window);
	setLocalCallbacks();
	GL.createCapabilities();
	
	glEnable(GL_TEXTURE_2D);
	
	glEnable(GL_BLEND);
	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	
	glfwShowWindow(window);
	
	System.out.println("Using OpenGL " + glGetString(GL_VERSION));
	System.out.println("Using GLSL " + glGetString(GL_SHADING_LANGUAGE_VERSION));
	System.out.println("Using GLFW " + glfwGetVersionString());
	
	videoMode = null;
	
 }

  
  public void cleanUp() {
	  windowSizeCallback.close();
  }
  
  
  //ERROR INFO 
  public static void setCallbacks() {
	  glfwSetErrorCallback(new GLFWErrorCallback() {
		
		@Override
		public void invoke(int error, long description) {
			
			throw new IllegalStateException(GLFWErrorCallback.getDescription(description));
			
			
		}
	});
	  
	  
  }
  
  private void setLocalCallbacks() {
	  windowSizeCallback = new GLFWWindowSizeCallback() {
		@Override
		public void invoke(long argWindow, int argWidth, int argHeight) {
			width = argWidth;
			height = argHeight;
			hasResized = true;
		}
	};
	
	glfwSetWindowSizeCallback(window, windowSizeCallback);
  }
  
  
  
  
  
// RETURN MOST OF THE VARIABLES VIA FUNCTIONS
  public long getWindow() {  return window; }
  public int getWidth() {  return width;  } 
  public int getHeight() {  return height;  }
  public boolean hasResize() { return hasResized; }
  public boolean isFullscreen() {  return fullscreen;  }
  public boolean shouldClose() {  
	  
	  hasResized = false;
	  input.update(); 
	  
	  GL33.glFlush();
	  
	  glfwPollEvents();
	  glfwSwapBuffers(this.window);
	  
	  return glfwWindowShouldClose(window);
	  
  }
  public Input getInput() { return input; }
  public void setSize(int w, int h) {
	  this.width = w;
	  this.height = h;
	  
	  glfwSetWindowSize(this.window, w, h);
  }

}
