package entity;

import org.joml.*;

public class Transform {
	
	public Vector3f pos;
	public Vector3f scale;
	
	public Transform() {
		
		pos = new Vector3f();
		scale = new Vector3f(3f,3f,1f);
	}
	
	public Vector3f getScale() {
		
		return pos;
		
	}
	
	public Matrix4f getProjection(Matrix4f target) {
		target.translate(pos);
		target.scale(scale);
		return target;
	}
	
}
