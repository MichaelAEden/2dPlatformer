package tileMap;

import io.Window;
import render.Camera;
import render.Model;

public class TileLongBlock extends Tile {

	public TileLongBlock(String name) {
		super(name);
	}

	protected void createModel() {
		float[] vertices = new float[] {
				-0.5f, 0.5f, 0, 	//TOP LEFT		0
				15.5f, 0.5f, 0,		//TOP RIGHT		1
				15.5f, -0.5f, 0,		//BOTTOM RIGHT	2
				-0.5f, -0.5f, 0,	//BOTTOM LEFT	3
		};
		
		float[] texCoords = new float[] {
				200, 0, 	//TOP LEFT
				201, 0,	//TOP RIGHT
				201, 1,	//BOTTOM RIGHT
				200, 1,	//BOTTOM LEFT
		};
		
		int[] indicies = new int[] {
				0, 1, 2,
				2, 3, 0
		};
		
		//model = new Model(vertices, texCoords, indicies);
	}
	
	public boolean shouldRender(int x, int y, Camera camera, Window window, TileMap world) {
		return true;
	}
}
