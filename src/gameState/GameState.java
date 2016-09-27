package gameState;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

import entities.EntityMap;
import io.Window;
import render.Camera;
import render.Shader;
import tileMap.TileMap;

public abstract class GameState {
	protected GameStateManager gsm;

	public GameState(GameStateManager gsm) {
		this.gsm = gsm;
	}
	
	public void update(Window window, Camera camera, TileMap tileMap, EntityMap entityMap) {
		if (window.getInput().isKeyPressed(GLFW_KEY_ESCAPE)) {
			window.close();
		}
	}
	
	public abstract void render(Window window, Shader shader, Camera camera, TileMap tileMap, EntityMap entityMap);
}
