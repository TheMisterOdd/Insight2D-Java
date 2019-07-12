package render;

import static org.lwjgl.opengl.GL46.*;

import java.io.*;
import java.nio.*;

import org.joml.*;
import org.lwjgl.*;

public class Shader {

	private int program;
	private int vs;
	private int fs;
	
	public Shader(String Filename) {
		
		program = glCreateProgram();

		vs = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vs, readFile(Filename+".vs"));
		glCompileShader(vs);
		
		if(glGetShaderi(vs, GL_COMPILE_STATUS) != 1) {
			System.err.println(glGetShaderInfoLog(vs));
			System.exit(-1);
		}
		
		fs = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fs, readFile(Filename+".fs"));
		glCompileShader(fs);
		
		if(glGetShaderi(fs, GL_COMPILE_STATUS) != 1) {
			System.err.println(glGetShaderInfoLog(fs));
			System.exit(-1);
		}
		
		glAttachShader(program, vs);
		glAttachShader(program, fs);
		
		glBindAttribLocation(program, 0, "vertices");
		glBindAttribLocation(program, 1, "textures");
		
		glLinkProgram(program);
		if(glGetProgrami(program, GL_LINK_STATUS) != 1) {
			System.err.println(glGetProgramInfoLog(program));
			System.exit(-1);
		}
		
		
		glValidateProgram(program);
		if(glGetProgrami(program, GL_VALIDATE_STATUS) != 1) {
			System.err.println(glGetProgramInfoLog(program));
			System.exit(-1);
		}
		
		Filename = null;
		
	}
	
	protected void finalize() {
		glDetachShader(program, vs);
		glDetachShader(program, fs);
		glDeleteShader(vs);
		glDeleteShader(fs);
		glDeleteProgram(program);
	}
	
	public void setUniform(String name, float value) {
		
		int location = glGetUniformLocation(program, name);
		if(location != -1) {
			glUniform1f(location, value);
		}
		
	}
	
	public void setUniform(String uniformName, Vector4f value) {
		int location = glGetUniformLocation(program, uniformName);
		if(location != -1) {
			glUniform4f(location, value.x, value.y, value.z, value.w);
		}
	}
	
    public void setUniform(String name, Matrix4f value) {
		
		int location = glGetUniformLocation(program, name);
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		value.get(buffer);
		
		if(location != -1) {
			glUniformMatrix4fv(location, false, buffer);
		}
		
	}
	
	public void bind() {
		
		glUseProgram(program);
	}
	
	private String readFile(String Filename) {
		
		StringBuilder string = new StringBuilder();
		BufferedReader br;
		
		try {
			
			br = new BufferedReader(new FileReader(new File("resources/shaders/" + Filename)));
			String line;
			while((line = br.readLine()) != null) {
				
				string.append(line);
				string.append("\n");
				
			}
			
			br.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		br = null;
		Filename = null;
		
		return string.toString();
		
	}
	
}
