package game;

import org.lwjgl.*;
import org.lwjgl.opengl.GL;

import GUI.*;
import assest.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL46.*;

import gameEngine.*;
import render.*;
import world.*;


public class Main {

	@SuppressWarnings("unused")
	
	
	static Window win = new Window(1280, 720, "Shiver | OpenGL 4.6", false, 0);
	
	public static void main(String[] args) {
			
		Window.setCallbacks();
		Camera camera = new Camera(win.getWidth(), win.getHeight());
		win.Init();
		System.out.println("Using LWJGL " + Version.getVersion() + ", OpenGL "+ glGetString(GL_VERSION) +  ", in " + System.getProperty("os.name") +".");
		TileRender tiles = new TileRender();
		Assest.initAssest();
		Shader shader = new Shader("shader");	
		World world = new World("MAP");
		
		GUI gui = new GUI(win);

		double frame_cap = 1.0/60.0;double frame_time = 0;int frames = 0;double time = Timer.getTime();double unprocessed = 0;

		while(!win.shouldClose()) {

			
			
			double time_2 = Timer.getTime();
			double passed = time_2 - time;
			unprocessed+=passed;
			
			frame_time += passed;
			
			time = time_2;

			
			while(unprocessed>=frame_cap) {
				if(win.hasResize()) {
					camera.setProjection(win.getWidth(), win.getHeight());
					gui.resizeCamera(win);
					glViewport(0, 0, win.getWidth(), win.getHeight());
				}
				
				unprocessed-=frame_cap;
				
				win.update();
				
				world.update((float)frame_cap, win, camera);
				world.correctCamera(camera, win);
								
				if(frame_time >= 1.0) {frame_time = 0;System.out.println("FPS: " + frames);frames = 0;}
				
			}
				glClear(GL_COLOR_BUFFER_BIT);
				
				
				world.render(tiles, shader, camera, win);
				gui.Render();
				frames++;
				
				
				
				
			glfwSwapInterval(1);	
			glfwSwapBuffers(win.getWindow());
			
		}
		
		Assest.deleteAssest();
		
		glfwTerminate();
	}
	

}
