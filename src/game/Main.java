package game;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL46.*;


import org.joml.Matrix4f;
import org.joml.Vector3f;


import gameEngine.Timer;
import gameEngine.Window;
import io.Cursor;
import io.Texture;
import render.Camera;
import render.Model;
import render.Shader;



public class Main {

	
	static Window win = new Window(1920, 1080, "Shiver | OpenGL 4.6", true, 0);

	
	static float[] vertices = new float[] {
		   -.5f, .5f, 0,
			.5f, .5f, 0,
			.5f, -.5f, 0,
			-.5f, -.5f, 0
	};
	static float[] texture = new float[] {
			   0, 0,
			   0, 1 ,
			   1, 1,
			   1, 0
	};
	static int []indices = new int [] {
			0, 1, 2,
			2, 3, 0
	};	
	public static int posX, posY;
	public static Camera camera = new Camera(win.getWidth(), win.getHeight());
	
	
	
	public static void main(String[] args) {
		
		
		Window.setCallbacks();
		
		win.Init();

		Shader shader = new Shader("shader");
		Matrix4f scale = new Matrix4f().scale(100);
		Matrix4f target = new Matrix4f();
		camera.SetPosition(new Vector3f(0, 0, 0));
		Cursor cursor = new Cursor("./res/textures/cursor/cursor.png", win.getWindow());
		//Define Textures and models: 
		Texture tex = new Texture("./res/textures/LWJGL.png");
		Model model = new Model(vertices, texture, indices);
		
		//Variables
		double frame_cap = 1.0/60.0;double frame_time = 0;int frames = 0;double time = Timer.getTime();double unprocessed = 0;
		
		while(!win.shouldClose()) {

			
			
			double time_2 = Timer.getTime();
			double passed = time_2 - time;
			unprocessed+=passed;
			
			frame_time += passed;
			
			time = time_2;

			
			while(unprocessed>=frame_cap) {
				unprocessed-=frame_cap;
				target = scale;
				
				win.update();
				
				if(win.getInput().isKeyPressed(GLFW_KEY_ESCAPE))
					glfwSetWindowShouldClose(win.getWindow(), true);
				
				
				
				if(frame_time >= 1.0) {
					frame_time = 0;
					System.out.println("FPS: " + frames);
					frames = 0;
				}
				
			}


				glClear(GL_COLOR_BUFFER_BIT);
				
				shader.bind();
				shader.setUniform("sampler", 0);
				shader.setUniform("projection", camera.projection().mul(target));
				tex.bind(0);
				model.render();
				frames++;

			glfwSwapBuffers(win.getWindow());
			
		}
		
		glfwTerminate();
	}


	
}
