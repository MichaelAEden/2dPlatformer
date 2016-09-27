package tileMap;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import entities.EntityMap;
import entities.EntityTile;
import io.Window;
import render.Camera;
import render.Shader;

public class TileMap {	
	//y = 0 is the bottom of the map;
	//As y increases, height increases
	private int[] tileMap;
	private int[] metadata;
	private boolean[] activeTiles;
	private boolean[] previouslyActiveTiles;
	
	private int width;
	private int height;
	private int scale;
	private Matrix4f worldMatrix;
	
	public TileMap(int width, int height) {
		this.width = width;
		this.height = height;
		scale = 64;
		
		tileMap = new int[width * height];
		metadata = new int[width * height];
		activeTiles = new boolean[width * height];
				
		worldMatrix = new Matrix4f();
		worldMatrix.scale(scale);
		
		createWorld();
	}

	private void createWorld() {
		int bedrockHeight = 2;
		int stoneHeight = 3;
		int dirtHeight = 5;
		
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				setTileAt(x, y, 
						y < bedrockHeight ? Tile.bedrock.getID() : 
						y < stoneHeight ? Tile.stone.getID() : 
						y < dirtHeight ? Tile.dirt.getID() : 
						y == dirtHeight ? Tile.grass.getID() : 0	
						);
				if (x == 0) {
					setTileAt(x, y, Tile.gold.getID());
				}
				if (x == width - 1) {
					setTileAt(x, y, Tile.gold.getID());
				}
				if (y == height - 1) {
					setTileAt(x, y, Tile.gold.getID());
				}
				setTileActiveAt(x, y, false, null, null);
				setTileMetadataAt(x, y, 0);
			}
		}
		
		setTileAt(10, 10, Tile.dirt.getID());
	}
	
	public void update(Camera camera, Window window, EntityMap entityMap) {
		resetTiles();
		correctCamera(camera, window, entityMap);
	}
	

	private void resetTiles() {
		previouslyActiveTiles = activeTiles.clone();
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				setTileActiveAt(x, y, false, null, null);
			}
		}
	}
	
	private void correctCamera(Camera camera, Window window, EntityMap entityMap) {
		camera.getPosition().lerp(new Vector3f(entityMap.getPlayer().getX(), entityMap.getPlayer().getY(), 0).mul(-(float)scale, new Vector3f()), 0.1f);
		
		Vector3f cameraPos = camera.getPosition();
		int visualWidth = -width * scale;
		int visualHeight = height * scale;
		
		if (cameraPos.x > -(Window.WIDTH / 2) + (scale / 2)) {
			cameraPos.x = -(Window.WIDTH / 2) + (scale / 2);
		}
		else if (cameraPos.x < visualWidth + (Window.WIDTH / 2) + (scale / 2)) {
			cameraPos.x = visualWidth + (Window.WIDTH / 2) + (scale / 2);
		}
		
		if (cameraPos.y < -visualHeight + (Window.HEIGHT / 2) + (scale / 2)) {
			cameraPos.y = -visualHeight + (Window.HEIGHT / 2) + (scale / 2);
		}
		else if (cameraPos.y > -(Window.HEIGHT / 2) + (scale / 2)) {
			cameraPos.y = -(Window.HEIGHT / 2) + (scale / 2);
		}
	}

	public void render(Shader shader, Camera camera, Window window, EntityMap entityMap) {
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				if (getTileAt(x, y) != null && getTileAt(x, y).shouldRender(x, y, camera, window, this)) {
					TileRenderer.renderTile(getTileIDAt(x, y), x, y, shader, camera, this);
				}
			}
		}
	}
	
	//Sets whether or not the tile is in the JBox2d world
	public void setTileActiveAt(int x, int y, boolean active, EntityMap entityMap, World world) {
		if(active && !wasTileLastActiveAt(x, y)) entityMap.spawnEntity(new EntityTile(world, new Vec2((float)x, (float)y), getTileIDAt(x, y), false));
		activeTiles[y * width + x] = active;
	}
	public boolean isTileActiveAt(int x, int y) {
		return activeTiles[y * width + x];
	}
	public boolean wasTileLastActiveAt(int x, int y) {
		return previouslyActiveTiles[y * width + x];
	}
	public void setTileAt(int x, int y, int id) {
		if (isInBounds(x, y)) tileMap[y * width + x] = id;
	}
	public int getTileIDAt(int x, int y) {
		if (isInBounds(x, y)) return tileMap[y * width + x];
		return 0;
	}
	public void setTileMetadataAt(int x, int y, int metadata) {
		if (isInBounds(x, y)) this.metadata[y * width + x] = metadata;
	}
	public int getTileMetadataAt(int x, int y) {
		if (isInBounds(x, y)) return this.metadata[y * width + x];
		return 0;
	}
	public Tile getTileAt(int x, int y) {
		if (isInBounds(x, y)) return Tile.getTileAtID(tileMap[y * width + x]);
		return null;
	}
	public boolean isInBounds(int x, int y) {
		return (x >= 0 && x < width && y >= 0 && y < height);
	}
	
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int getScale() { return scale; }
	public Matrix4f getMatrix() { return worldMatrix; }
}
