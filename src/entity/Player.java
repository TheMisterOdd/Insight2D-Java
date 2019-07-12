package entity;


import static org.lwjgl.glfw.GLFW.*;
import org.joml.*;

import display.*;
import render.*;
import world.*;


public class Player extends Entity {

 public static int health = 1;
 private float degrees = -180f;
 private boolean jump = false;
 private float deltaPos = 0;
 
	public Player(Transform transform) {
		
		super(4, transform);
		setAnimation(0, new Animation(3, 1, "player/idle/idle"));
		setAnimation(1, new Animation(10, 10, "player/X-Axes/r"));
		setAnimation(2, new Animation(1, 1, "player/Y-Axes/d"));
		setAnimation(3, new Animation(4, 10, "player/attack/a"));
		
	}	
	@Override
	public void update(float delta, Display window, Camera camera, World world) {
		
		int speed = 20; 
		Vector2f movement = new Vector2f(); 
		
		
		if(window.getInput().isKeyDown(GLFW_KEY_LEFT_SHIFT)) speed += 3;
		
		if(window.getInput().isKeyDown(GLFW_KEY_D) || (window.getInput().isMouseButtonPressed(GLFW_MOUSE_BUTTON_2) && window.getInput().getMousePosition().x > 0)) {
			movement.add(speed*delta,0); useAnimation(1); 
			
			if(degrees == 180f) {
				degrees = -180f;
				transform.scale.rotateY(degrees);
			}
		} 
		
		
		if(window.getInput().isKeyDown(GLFW_KEY_A) || (window.getInput().isMouseButtonPressed(GLFW_MOUSE_BUTTON_2) && window.getInput().getMousePosition().x < 0 )) {
			movement.add(-speed*delta,0); useAnimation(1);
			if(degrees == -180f) {
				degrees = 180f;
				transform.scale.rotateY(degrees);
			}	
		} 
		
		if (window.getInput().isKeyPressed(GLFW_KEY_SPACE) && jump == false) 
			jump = true;	 
		
		if (jump) {
			
			movement.add(new Vector2f(velocity.x  * delta, velocity.y * delta));
			this.velocity = this.velocity.add(new Vector2f(0, gravity.y * delta));
			
			if (velocity.y < -10.f) 
			{
				this.velocity.y = 10.f;
				jump = false;
			}
			
		}
		
		if(movement.x == 0) useAnimation(0);
		
		move(movement);
		super.update(delta, window, camera, world);
		camera.getPosition().lerp(transform.pos.mul(-world.getScale(), new Vector3f()), 0.05f);
	}

}

