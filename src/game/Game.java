package game;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL;

import entities.EntityMap;
import gameState.GameStateManager;
import io.Timer;
import io.Window;
import render.Camera;
import render.Shader;
import tileMap.TileMap;

public class Game {
	
	public static int FPS = 60;
	
	private GameStateManager gsm;
	
	private Camera camera;
	private TileMap tileMap;
	private EntityMap entityMap;
	private Shader shader;
	private Window window;
	
	private void run() {
		try {
			init();
			loop();
		}
		finally {
			glfwTerminate();
			glfwSetErrorCallback(null).free();
		}
	}
	
	private void init() {
		Window.setCallbacks();
		
		if (!glfwInit()) throw new IllegalStateException("GLFW Failed to initialize!");
		
		window = new Window("Zombie Fortress", 1280, 800, true);
		
		glfwSwapInterval(1);
		GL.createCapabilities();
		
		gsm = new GameStateManager();
		gsm.setGameState(GameStateManager.ACTIVE);
		camera = new Camera(Window.WIDTH, Window.HEIGHT);
		glEnable(GL_TEXTURE_2D);
		
		tileMap = new TileMap(64, 16);
		entityMap = new EntityMap();
		
		shader = new Shader("shader");
	}
	
	private void loop() {
		double frameCap = 1.0 / (double)FPS;
		double startTime = Timer.getTime();
		double unprocessed = 0;
		
		double frameTime = 0;
		int frames = 0;
		
		//Maybe use System.wait
		
		while(!window.shouldClose()) {
			boolean canRender = false;
			
			double endTime = Timer.getTime();
			double elapsedTime = endTime - startTime;
			unprocessed += elapsedTime;
			frameTime += elapsedTime;
			
			startTime = endTime;
			
			while(unprocessed >= frameCap) {
				canRender = true;
				unprocessed -= frameCap;
				
				update();
				
				if(frameTime >= 1.0) {
					System.out.println("FPS: " + frames);
					
					frameTime = 0;
					frames = 0;
				}
			}
			
			if(canRender) {
				render();
				frames++;
			}
		}
	}
	
	private void update() {
		gsm.update(window, camera, tileMap, entityMap);
	}
	
	private void render() {
		gsm.render(window, shader, camera, tileMap, entityMap);
		
		window.update();
		
		glClear(GL_COLOR_BUFFER_BIT);
	}
	
	public static void main(String[] args) {
		System.out.println("Start");
		
		new Game().run();
		
		System.out.println("Done");
	}
}