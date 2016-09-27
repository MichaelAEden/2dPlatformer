package render;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import io.Window;
import tileMap.TileMap;

public class Background {
	private Texture texture;
	private Model model;

	public Background(String texture) {
		this.texture = new Texture("backgrounds/" + texture + ".png");
		
		float[] vertices = new float[] {
				-0.5f, 0.5f, 0, 	//TOP LEFT		0
				0.5f, 0.5f, 0,		//TOP RIGHT		1
				0.5f, -0.5f, 0,		//BOTTOM RIGHT	2
				-0.5f, -0.5f, 0,	//BOTTOM LEFT	3
		};
		
		float[] texCoords = new float[] {
				0, 0, 	//TOP LEFT
				1, 0,	//TOP RIGHT
				1, 1,	//BOTTOM RIGHT
				0, 1,	//BOTTOM LEFT
		};
		
		int[] indices = new int[] {
				0, 1, 2,
				2, 3, 0
		};
		
		model = new Model(vertices, texCoords, indices);
	}
	
	public void render(Shader shader, Camera camera, TileMap tileMap, Window window) {
		shader.bind();
		texture.bind(0);
		
		Matrix4f backgroundPos = new Matrix4f().translate(new Vector3f(tileMap.getWidth() / 2, tileMap.getHeight() / 2, 0));
		Matrix4f target = new Matrix4f();
		
		camera.getProjection().mul(tileMap.getMatrix(), target);
		target.mul(backgroundPos);
		target.scale(new Vector3f(tileMap.getWidth(), tileMap.getHeight(), 0));
		
		shader.setUniform("sampler", 0);
		shader.setUniform("projection", target);
		
		model.render();
		
		/*shader.bind();
		texture.bind(0);
		
		Matrix4f scale = new Matrix4f().scale(new Vector3f(world.getWidth(), world.getHeight(), 0));
		Matrix4f target = new Matrix4f();
		
		camera.getProjection().mul(worldMatrix, target);
		target.mul(scale);
		target.sub(camera.getProjection());
		//target.add(new Matrix4f().translate(new Vector3f(5, 5, 0)));
		
		shader.setUniform("sampler", 0);
		shader.setUniform("projection", target);
		
		model.render();*/
	}

}
