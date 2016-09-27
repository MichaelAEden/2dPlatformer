package tileMap;

import java.util.HashMap;

import org.jbox2d.collision.shapes.PolygonShape;
import org.joml.Vector3f;

import io.Window;
import render.Camera;
import render.Model;
import render.Texture;

public class Tile {
	protected static Tile[] tiles = new Tile[16];
	protected static int numberOfTiles = 1;
	
	private HashMap<String, Texture> tileTextures = new HashMap<String, Texture>();
	
	protected String name;
	protected String texture;
	protected int id;
	protected Model model;
	
	protected PolygonShape collisionBounds;
		
	//Creates all tiles
	public static final Tile testTile = new Tile("testTile");
	public static final Tile dirt = new Tile("dirt");
	public static final Tile stone = new Tile("stone");
	public static final Tile bedrock = new Tile("bedrock");
	public static final Tile grass = new Tile("grass");
	public static final Tile gold = new Tile("gold");
	public static final Tile brick = new Tile("brick");
	
	public static final Tile fire = new TileLongBlock("fire");

	public Tile(String name) {
		this.name = name;
		this.texture = name;
		id = numberOfTiles;

		tiles[id] = this;
		
		numberOfTiles++;
		
		if (!tileTextures.containsKey(texture)) {
			tileTextures.put(texture, new Texture("tiles/" + texture + ".png"));
		}
		
		createModel();
		createCollisionBox();
	}

	protected void createModel() {
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
	
	protected void createCollisionBox() {
		collisionBounds = new PolygonShape();
		collisionBounds.setAsBox(0.5f, 0.5f);
	}

	public static Tile[] getTiles() { return tiles; }
	public static Tile getTileAtID(int id) {
		if (id == 0) return null;
		return tiles[id]; 
	}
	public String getName() { return name; }
	public String getTextureString() { return texture; }
	public Texture getTexture() { return tileTextures.get(texture); }
	public int getID() { return id; }
	public Model getModel() { return model; }
	public PolygonShape getCollisionBox() { return collisionBounds; }
	
	public boolean shouldRender(int x, int y, Camera camera, Window window, TileMap world) {
		Vector3f cameraPos = camera.getPosition();
		if ((-cameraPos.x + Window.WIDTH / 2) > ((x - 0.5) * world.getScale()) &&
			(-cameraPos.x - Window.WIDTH / 2) < ((x + 0.5) * world.getScale()) &&
			(-cameraPos.y + Window.HEIGHT / 2) > ((y - 0.5) * world.getScale()) &&
			(-cameraPos.y - Window.HEIGHT / 2) < ((y + 0.5) * world.getScale())) {
			return true;
		}
		return false;
	}
}