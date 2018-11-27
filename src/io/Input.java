package io;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWKeyCallback;


public class Input extends GLFWKeyCallback{
	
	private long window;
	
	private boolean keys[];
	
	public Input(long window) {
		this.window = window;
		this.keys = new boolean[GLFW_KEY_LAST];
		for(int i = 0; i < GLFW_KEY_LAST; i++)
			keys[i] = false;
	}
	
	public boolean isKeyDown(int Key) {	
		return glfwGetKey(window, Key) == 1;
	}
	
	public boolean isKeyPressed(int Key) {
		return (isKeyDown(Key) && !keys[Key]);
	}
	
	public boolean isKeyReleased(int Key) {
		return (!isKeyDown(Key) && keys[Key]);
	}
	
	public int isKeyMouseButtonDown(int Button) {	
		return glfwGetMouseButton(window, Button);
	}

	public void update() {
		for(int i = GLFW_KEY_0; i < GLFW_KEY_LAST; i++)
			keys[i] = isKeyDown(i);
	}
	
	public void invoke(long window, int key, int scancode, int action, int mods) {
        System.out.println(key);
        keys[key] = action != GLFW_RELEASE;
    }
}
