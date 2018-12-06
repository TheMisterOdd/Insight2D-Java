package entity;


import static org.lwjgl.glfw.GLFW.*;
import org.joml.*;
import gameEngine.*;
import render.*;
import world.*;


public class Player extends Entity {
 public static final int ANIMATION_IDLE = 0;
 public static final int ANIMATION_WALK_U = 1;
 public static final int ANIMATION_WALK_D = 2;
 public static final int ANIMATION_WALK_R = 3;
 public static final int ANIMATION_WALK_L = 4;
 public static final int ANIMATION_SIZE = 5;
 public static int health = 1;
	public Player(Transform transform) {
		super(ANIMATION_SIZE, transform);
		setAnimation(ANIMATION_IDLE, new Animation(2, 1, "player/idle/fase"));
		setAnimation(ANIMATION_WALK_U, new Animation(4, 5, "player/walking/up/u"));
		setAnimation(ANIMATION_WALK_D, new Animation(4, 5, "player/walking/up/u"));
		setAnimation(ANIMATION_WALK_R, new Animation(6, 10, "player/walking/right/r"));
		setAnimation(ANIMATION_WALK_L, new Animation(6, 10, "player/walking/left/l"));	
	}
	
	@Override
	public void update(float delta, Window window, Camera camera, World world) {
		int speed = 5;
		Vector2f movement = new Vector2f();
		
		
		if(window.getInput().isKeyDown(GLFW_KEY_LEFT_SHIFT)) 
			speed = 7;
		
		if(window.getInput().isKeyPressed(GLFW_KEY_ESCAPE))
			glfwSetWindowShouldClose(window.getWindow(), true);

		if(window.getInput().isKeyDown(GLFW_KEY_W)) {
			movement.add(0,speed*delta);
		}

		if(window.getInput().isKeyDown(GLFW_KEY_S)) {
			movement.add(0,-speed*delta);
		}
			
		if(window.getInput().isKeyDown(GLFW_KEY_A)) {
			movement.add(-speed*delta,0);
		}
			
		if(window.getInput().isKeyDown(GLFW_KEY_D)) {
			movement.add(speed*delta,0);	
		}
	
		
		
		else
		useAnimation(0);

		
		move(movement);
		super.update(delta, window, camera, world);
		camera.getPosition().lerp(transform.pos.mul(-world.getScale(), new Vector3f()), 0.1f);
	}

}

