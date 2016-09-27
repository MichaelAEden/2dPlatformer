package io;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;

public class Window {
	private long window;
	
	public static int WIDTH = 640;
	public static int HEIGHT = 480;
	public static boolean FULLSCREEN = false;
	public static String TITLE;
	public static int SCALE = 1;
	
	private Input input;

	public static void setCallbacks() {
		glfwSetErrorCallback(new GLFWErrorCallback() {
			@Override
			public void invoke(int error, long description) {
				throw new IllegalStateException(GLFWErrorCallback.getDescription(description));
			}
		});
	}
	
	public Window(String title, int width, int height, boolean fullscreen) {
		setSize(width, height);
		setFullscreen(fullscreen);
		TITLE = title;
		
		createWindow();
	}
	
	public void createWindow() {
		window = glfwCreateWindow(
				WIDTH,
				HEIGHT,
				TITLE,
				FULLSCREEN ? glfwGetPrimaryMonitor() : NULL,
				NULL);
		
		if (window == NULL) throw new IllegalStateException("Failed to create window!");
		
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		
		if (!FULLSCREEN)
		{
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			
			glfwSetWindowPos(
					window, 
					(vidmode.width() - WIDTH) / 2, 
					(vidmode.height() - HEIGHT) / 2
					);
		}
		
		glfwShowWindow(window);
		
		glfwMakeContextCurrent(window);
		
		input = new Input(window);
	}
	
	public void update() {
		glfwSwapBuffers(window);
		
		input.update();
		glfwPollEvents();
	}
	
	public void setSize(int width, int height) {
		WIDTH = width;
		HEIGHT = height;
	}
	
	public void setFullscreen(boolean fullscreen) {
		FULLSCREEN = fullscreen;
	}
	
	public void close() {
		glfwSetWindowShouldClose(window, true);
		
		//glfwFreeCallbacks(window);
		//glfwDestroyWindow(window);
	}
	
	public boolean shouldClose() { return glfwWindowShouldClose(window); }
	public long getWindow() { return window; }
	public Input getInput() { return input; }
	
	/*public int getWidth() {	return width; }
	public int getHeight() { return height; }
	public boolean isFullscreen() { return fullscreen; }*/
}
