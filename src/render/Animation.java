package render;

import io.Timer;

public class Animation{
	private int pointer = 0;
	private Texture[] frames;
	
	private double elapsedTime = 0;
	private double currentTime;
	private double lastTime;
	private double fps;

	public Animation(int frames, int fps, String filename) {
		this.frames = new Texture[frames];
		this.fps = 1d/(double)fps;
		
		for (int i = 0; i < this.frames.length; i++) {
			this.frames[i] = new Texture(filename + "_" + i + ".png");
		}
		
		lastTime = Timer.getTime();
	}

	public void bind(int sampler) {
		update();
		bindFrame(sampler);
	}
	
	private void update() {
		currentTime = Timer.getTime();
		elapsedTime += currentTime - lastTime;
		
		while (elapsedTime >= fps) {
			elapsedTime -= fps;
			pointer++;
		}
		
		if (pointer >= frames.length) pointer = 0;
		
		lastTime = currentTime;
	}
	
	private void bindFrame(int sampler) {
		frames[pointer].bind(sampler);
	}
	
	@Override
	protected void finalize() throws Throwable {
		for (int i = 0; i < frames.length; i++) {
			frames[i].finalize();
		}
		super.finalize();
	}
}


//Unused

/*
public class Animation{
	private Texture animation;
	private int pointer = 0;
	private int frames;
	private int frameWidth;
	private int frameHeight;
	
	private double elapsedTime = 0;
	private double currentTime;
	private double lastTime;
	private double fps;

	public Animation(int frames, int fps, String filename) {
		this.frames = frames;
		this.fps = 1d/(double)fps;
		
		animation = new Texture(filename + ".png");
		
		frameWidth = animation.getWidth() / frames;
		frameHeight = animation.getHeight();
		
		lastTime = Timer.getTime();
	}

	public void bind(int sampler) {
		update();
		render(sampler);
	}

	private void update() {
		currentTime = Timer.getTime();
		elapsedTime += currentTime - lastTime;
		
		while (elapsedTime >= fps) {
			elapsedTime -= fps;
			pointer++;
		}
		
		if (pointer >= frames) pointer = 0;
		
		lastTime = currentTime;		
	}
	
	private void render(int sampler) {
		int x1 = frameWidth * pointer;
		int y1 = 0;
		int x2 = frameWidth * pointer + frameWidth;
		int y2 = frameHeight;
		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		   glEnable(GL_TEXTURE_2D);
		   glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_DECAL);
		   glBindTexture(GL_TEXTURE_2D, texName);
		   glBegin(GL_QUADS);
		   glTexCoord2f(0f, 0f); glVertex3f(-2f, -1f, 0f);
		   glTexCoord2f(0f, 1f); glVertex3f(-2f, 1f, 0f);
		   glTexCoord2f(1f, 1f); glVertex3f(0f, 1f, 0f);
		   glTexCoord2f(1f, 0f); glVertex3f(0f, -1f, 0f);

		   glTexCoord2f(0f, 0f); glVertex3f(1f, -1f, 0f);
		   glTexCoord2f(0f, 1f); glVertex3f(1f, 1f, 0f);
		   glTexCoord2f(1f, 1f); glVertex3f(2.41421f, 1f, -1.41421f);
		   glTexCoord2f(1f, 0f); glVertex3f(2.41421f, -1f, -1.41421);
		   glEnd();
		   glFlush();
		   glDisable(GL_TEXTURE_2D);
		
		/*glEnable(GL_TEXTURE_RECTANGLE_ARB);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		
		animation.bind(GL_TEXTURE_RECTANGLE_ARB, 0);
		
		glBegin(GL_QUADS);
		glTexCoord2f(x1, y1);	//TOP LEFT
		glVertex2f(-1, 1);
		glTexCoord2f(x1, y2);	//BOTTOM LEFT
		glVertex2f(-1, -1);
		glTexCoord2f(x2, y2);	//TOP LEFT
		glVertex2f(1, -1);
		glTexCoord2f(x2, y1);	//TOP LEFT
		glVertex2f(1, 1);
		glEnd();
		
		animation.bind(GL_TEXTURE_RECTANGLE_ARB, 0);
		
		animation.bind(0);
		
		glDisable(GL_CULL_FACE);
		glDisable(GL_TEXTURE_RECTANGLE_ARB);
	}

	@Override
	protected void finalize() throws Throwable {
		animation.finalize();
		super.finalize();
	}
}*/
