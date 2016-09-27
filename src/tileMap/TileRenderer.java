package tileMap;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import render.Camera;
import render.Shader;

public class TileRenderer {
	
	public static void renderTile(int id, int x, int y, Shader shader, Camera camera, TileMap tileMap) {
		shader.bind();
		
		Tile.getTileAtID(id).getTexture().bind(0);
		Matrix4f tilePos = new Matrix4f().translate(new Vector3f(x, y, 0));
		Matrix4f target = new Matrix4f();
		
		camera.getProjection().mul(tileMap.getMatrix(), target);
		target.mul(tilePos);
		
		shader.setUniform("sampler", 0);
		shader.setUniform("projection", target);
		
		tileMap.getTileAt(x, y).getModel().render();
	}
}
