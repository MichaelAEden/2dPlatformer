package io;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.DoubleBuffer;

import org.jbox2d.common.Vec2;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import render.Camera;
import tileMap.TileMap;

public class Input {
	private long window;
	
	private boolean[] keys;
	private boolean[] buttons;
	
	private int mouseButtons = 2;
	
	public Input(long window) {
		this.window = window;
		this.keys = new boolean[GLFW_KEY_LAST];
		this.buttons = new boolean[mouseButtons];
		
		for(int i = 0; i < GLFW_KEY_LAST; i++) { 
			keys[i] = false; 
		}
		
		for(int i = 0; i < mouseButtons; i++) { 
			buttons[i] = false; 
		}
	}
	
	public boolean isKeyDown(int key) {
		try {
			return glfwGetKey(window, key) == 1;
		}
		catch (Exception e) {
			System.out.println("Key " + key + " invalid!");
			return false;
		}
	}
	public boolean isMouseDown(int button) {
		try {
			return glfwGetMouseButton(window, button) == 1;
		}
		catch (Exception e) {
			System.out.println("Button " + button + " invalid!");
			return false;
		}
	}
	
	public boolean isKeyPressed(int key) {
		return (isKeyDown(key) && !keys[key]);
	}
	public boolean isKeyReleased(int key) {
		return (!isKeyDown(key) && keys[key]);
	}
	public boolean isMousePressed(int button) {
		return (isMouseDown(button) && !buttons[button]);
	}
	public boolean isMouseReleased(int button) {
		return (!isMouseDown(button) && buttons[button]);
	}
	
	//Gets mouse cursor location in pixels
	public Vector3f getMouseCursor() {
		DoubleBuffer posX = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer posY = BufferUtils.createDoubleBuffer(1);

		glfwGetCursorPos(window, posX, posY);
		
		return new Vector3f((float)posX.get(0), (float)posY.get(0), 0);
	}
	
	//Gets mouse cursor location on the tileMap
	public Vec2 getMouseCursorOnTileMap(Camera camera, Window window, TileMap tileMap) {
		Vector3f mouse = getMouseCursor();
		Vector3f tilePos = (new Vector3f(mouse.x, Window.HEIGHT - mouse.y, 0)
				.add(new Vector3f(-camera.getPosition().x - Window.WIDTH / 2 + tileMap.getScale() / 2, -camera.getPosition().y - Window.HEIGHT / 2 + tileMap.getScale() / 2, 0)))
				.div(tileMap.getScale());
		return new Vec2(tilePos.x, tilePos.y);
	}
	
	public void update() {
		for(int i = 32; i < GLFW_KEY_LAST; i++) { 
			keys[i] = isKeyDown(i); 
		}
		for(int i = 0; i < mouseButtons; i++) { 
			buttons[i] = isMouseDown(i); 
		}
	}

}
