package render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.stb.STBImage.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class Texture {
	private int id;
	private int width;
	private int height;
	
	public Texture(String fileName) {
		try {
			//Reads the image
			IntBuffer width = BufferUtils.createIntBuffer(1);
			IntBuffer height = BufferUtils.createIntBuffer(1);
			IntBuffer comp = BufferUtils.createIntBuffer(1);

			ByteBuffer data = stbi_load("./res/" + fileName, width, height, comp, 4);

			id = glGenTextures();
			this.width = width.get();
			this.height = height.get();
			
			glBindTexture(GL_TEXTURE_2D, id);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, this.width, this.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
			stbi_image_free(data);
			
			System.out.println("Successfully loaded texture ./res/" + fileName + "!");
		}
		catch(Exception e) {
			e.printStackTrace();
			
			System.out.println("Could not load texture ./res/" + fileName + " !");
		}
	}
	
	public void bind(int sampler) {
		if (sampler >= 0 && sampler <= 31) {
			glActiveTexture(GL_TEXTURE0 + sampler);
		}
		else {
			System.out.println("Sampler out of range");
		}

		glBindTexture(GL_TEXTURE_2D, id);
	}
	
	@Override
	public void finalize() throws Throwable {
		glDeleteTextures(id);
		super.finalize();
	}
	
	public int getWidth() { return width; }
	public int getHeight() { return height; }
}
