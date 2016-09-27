package render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class Model {
	private int drawCount;
	private int v_ID;
	private int t_ID;
	private int i_ID;
	
	public Model() {}
	
	public Model(float[] vertices, float[] texCoords, int[] indices) {
		drawCount = indices.length;
		
		v_ID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, v_ID);
		glBufferData(GL_ARRAY_BUFFER, createFloatBuffer(vertices), GL_STATIC_DRAW);
		
		t_ID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, t_ID);
		glBufferData(GL_ARRAY_BUFFER, createFloatBuffer(texCoords), GL_STATIC_DRAW);
		
		i_ID = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, i_ID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, createIntBuffer(indices), GL_STATIC_DRAW);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	@Override
	protected void finalize() throws Throwable {
		glDeleteBuffers(v_ID);
		glDeleteBuffers(t_ID);
		glDeleteBuffers(i_ID);
		super.finalize();
	}
	
	public void render() {
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		
		glBindBuffer(GL_ARRAY_BUFFER, v_ID);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, t_ID);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, i_ID);
		glDrawElements(GL_TRIANGLES, drawCount, GL_UNSIGNED_INT, 0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
	}
	
	protected FloatBuffer createFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		
		return buffer;
	}
	
	protected IntBuffer createIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		
		return buffer;
	}
}
