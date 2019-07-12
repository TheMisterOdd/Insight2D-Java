package entity;

import org.joml.*;

import assest.*;
import collision.*;
import display.*;
import render.*;
import world.*;

public class Entity {

	//private static Model model;
	protected AABB bounding_boxes;
	protected Animation[] animations;
	private int use_animation;
	public static Transform transform;
	
	public Vector2f gravity;
	static final public Vector2f uVelocity = new Vector2f(0, 10); 
	public Vector2f velocity;
	
	public Entity(int max_animations, Transform transform) {

			this.animations = new Animation[max_animations];
			this.transform = transform;
			this.use_animation = 0;
				
			bounding_boxes = new AABB(new Vector2f(transform.pos.x, transform.pos.y), new Vector2f(transform.scale.x, transform.scale.y));
		
			this.gravity = new Vector2f(0, -10);
			this.velocity = uVelocity;
	}
	
	
	protected void setAnimation(int index, Animation animation) {
		animations[index] = animation;	
	}
	
	public void useAnimation(int index) {
		this.use_animation = index;	
	}
	
	public void move(Vector2f direction) {
		transform.pos.add(new Vector3f(direction, 0));
		
		bounding_boxes.getCenter().set(transform.pos.x,transform.pos.y);
	}
	
	public void collideWithTiles(World world) {
	
		
		AABB[] boxes = new AABB[25];
		for(int r = 0; r < 1; r++) {
		for(int i = 0; i < 5; i++)
			for(int j = 0; j < 5; j++)
				boxes[i + j * 5] = world.getTilesBoundingBox(
						(int)(((transform.pos.x / 2) + 0.5f) - (5/2)) + i,
						(int)(((-transform.pos.y / 2) + 0.5f) - (5/2)) + j	
			);
		
		
		
		AABB box = null;
		for(int i = 0; i< boxes.length; i++)
			if(boxes[i] != null) {
				if(box == null) box = boxes[i];
				
				Vector2f lenght1 = box.getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());
				Vector2f lenght2 = boxes[i].getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());
				
				if(lenght1.lengthSquared() > lenght2.lengthSquared()) {
					box = boxes[i];
				}
			}
				
		if(box!=null) {
			
			Collision data = bounding_boxes.getCollision(box);
				if(data.isIntersecting) {
					bounding_boxes.correctPosition(box, data);
					transform.pos.set(bounding_boxes.getCenter(), 0);
				}
			}
		
		}
		
	}
	
	public void update(float delta, Display window, Camera camera, World world) {	
		collideWithTiles(world);
	}
	
	public void render(Shader shader, Camera camera, World world) {
		Matrix4f target = camera.getProjection();
		target.mul(world.getWorldMatrix());
		shader.bind();
		shader.setUniform("sampler", 0);
		shader.setUniform("projection", transform.getProjection(target));
		animations[use_animation].bind(0);
		Assest.getModel().render();
	}
	
	public void collideWithTiles(Entity entity) {
		Collision collision = bounding_boxes.getCollision(entity.bounding_boxes);
		
		if(collision.isIntersecting) {
			collision.distance.x /=2;
			collision.distance.y/=2;
			
			bounding_boxes.correctPosition(entity.bounding_boxes, collision);
			transform.pos.set(bounding_boxes.getCenter().x, bounding_boxes.getCenter().y, 0);
			
			entity.bounding_boxes.correctPosition(bounding_boxes, collision);
			entity.transform.pos.set(entity.bounding_boxes.getCenter().x, entity.bounding_boxes.getCenter().y, 0);
		}
	}
}
