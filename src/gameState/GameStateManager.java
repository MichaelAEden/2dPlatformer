package gameState;

import entities.EntityMap;
import io.Window;
import render.Camera;
import render.Shader;
import tileMap.TileMap;

public class GameStateManager {
	
	public static int ACTIVE = 0;
	public static int TESTSTATE = 1;
	
	private GameState state;

	public GameStateManager() {
		
	}

	public void setGameState(int gameState) {
		state = gameState == ACTIVE ? new GameStateActive(this) :
			gameState == TESTSTATE ? new GameStateActive(this) : 
			null;
		if (state == null) System.out.println("Game state does not exist!");
	}
	
	public void update(Window window, Camera camera, TileMap tileMap, EntityMap entityMap) {
		state.update(window, camera, tileMap, entityMap);
	}
	
	public void render(Window window, Shader shader, Camera camera, TileMap tileMap, EntityMap entityMap) {
		state.render(window, shader, camera, tileMap, entityMap);
	}
}
