package gameEngine;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL46.*;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;

import io.Cursor;
import io.Input;


public class Window {
	@SuppressWarnings("unused")
	

	private long window;
	private String title;
	private int width;
	private int height;
	private int share;
	private boolean fullscreen;
	private GLFWVidMode videoMode;
	private Input input;
	private boolean hasResized;
	private GLFWWindowSizeCallback windowSizeCallback;

	
	
	public Window(int width, int height, String title, boolean fullscreen, int share) {
		
		this.width = width;
		this.height = height;
		this.title = title;
		this.fullscreen = fullscreen;
		this.share = share;
		hasResized = false;

	}
	
	

	
	//Init the window
  public void Init() {
	
	if(!glfwInit()) 
		System.exit(-1);
	
	glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
	
	window = glfwCreateWindow(width, height, title, fullscreen ? glfwGetPrimaryMonitor() : 0,share);
	
	if(window == 0) 
		System.exit(-1);
		
	
		videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (videoMode.width() - width) / 2, (videoMode.height() - height) / 2);

	glfwMakeContextCurrent(window);
	glfwShowWindow(window);
	
	input = new Input (window);
	setLocalCallbacks();
	GL.createCapabilities();
	
	glEnable(GL_TEXTURE_2D);
	
	glEnable(GL_BLEND);
	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	
	@SuppressWarnings("unused")
	Cursor cursor = new Cursor("./resource/textures/cursor/cursor.png", window);

	
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
  
  
  
  
  //RETURN MOST OF THE VARIABLES VIA FUNCTIONS
  public long getPrimaryMonitor() {  return glfwGetPrimaryMonitor(); }  
  public long getWindow() {  return window; }
  public int getWidth() {  return width;  } 
  public int getHeight() {  return height;  }
  public boolean hasResize() { return hasResized; }
  public String getTitle() { return title; }
  public int isSharing() { return share; }
  public GLFWVidMode getVidMode() {  return videoMode; } 
  public boolean isFullscreen() {  return fullscreen;  }
  public boolean shouldClose() {  return glfwWindowShouldClose(window);  }
  public Input getInput() { return input; }
  public void update() { 
	  hasResized = false;
	  input.update(); 
	  glfwPollEvents();
  }


}
