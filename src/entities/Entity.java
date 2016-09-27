package entities;

import java.awt.geom.Rectangle2D;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import io.Window;
import render.Camera;
import render.Shader;
import tileMap.TileMap;

public abstract class Entity {
	protected CollisionTester collisionTester;
	
	protected String name;
	
	protected Rectangle2D aabb;
	
	protected boolean isDead = false;

	public Entity(World world, Vec2 position) {}
		
	public void update(Window window, TileMap tileMap, EntityMap entityMap, World world) {
		updatePhysics(tileMap, entityMap, world);
	}
	
	abstract protected void createBody(World world, Vec2 position);

	abstract public void render(Shader shader, Camera camera, TileMap tileMap, EntityMap entityMap);
	
	private boolean isInBounds(TileMap tileMap, EntityMap entityMap) {
		if (getX() < 0 || getX() > tileMap.getWidth() || getY() < 0 || getY() > tileMap.getHeight()) {
			return false;
		}
		return true;
	}
	
	protected boolean updatePhysics(TileMap tileMap, EntityMap entityMap, World world) {
		/*
		
		Rectangle2D aabb = new Rectangle2D.Double(x, y, this.aabb.getWidth(), this.aabb.getHeight());
				
		for (int i = (int)Math.floor(aabb.getX()); i <= (int)Math.ceil(aabb.getMaxX()); i++) {
			for (int j = (int)Math.floor(aabb.getY()); j <= (int)Math.ceil(aabb.getMaxY()); j++) {
				if (tileMap.getTileAt(i, j) != null) {
					tileMap.setTileActiveAt(i, j, true, entityMap);
				}
			}
		}
		
		return true;*/
		
		if (!isInBounds(tileMap, entityMap)) kill(world);
		
		for (int i = 0; i <= 15; i++) {
			if (tileMap.getTileAt(i, 5) != null) {
				tileMap.setTileActiveAt(i, 5, true, entityMap, world);
			}
		}
		return true;
	}
	
	public void kill(World world) {
		isDead = true;
	}
	
	public String getName() { return name; }
	public boolean isDead() { return isDead; }
	
	public abstract Vec2 getPosition();
	public abstract Vec2 getLinearVelocity();
	public abstract float getX();
	public abstract float getY();
	public abstract void applyForce(Vec2 force);
	
	//Not used
	/*public void applyForce(Vec2 force, Vec2 point) { 
		body.applyForce(force, point); 
	}
	public void addLinearVelocity(Vec2 velocity) {
		body.setLinearVelocity(body.getLinearVelocity().add(velocity)); 
	}
	
			body.getLinearVelocity();
		
		float delta = 0.2f;
		
		int leftBounds;
		int rightBounds;
		int lowerBounds;
		int upperBounds;
		
		if (getLinearVelocity().x < 0) {
			leftBounds = (int)Math.floor(getX() - width / 2 - delta + (getLinearVelocity().x / (float)Game.FPS));
			rightBounds = (int)Math.ceil(getX() + width / 2 + delta);
		}
		else {
			leftBounds = (int)Math.floor(getX() - width / 2 - delta);
			rightBounds = (int)Math.ceil(getX() + width / 2 + delta + (getLinearVelocity().x / (float)Game.FPS));
		}
		if (getLinearVelocity().y < 0) {
			lowerBounds = (int)Math.floor(getY() - height / 2 - delta + (getLinearVelocity().y / (float)Game.FPS));
			upperBounds = (int)Math.ceil(getY() + height / 2 + delta);
		}
		else {
			lowerBounds = (int)Math.floor(getY() - height / 2 - delta);
			upperBounds = (int)Math.ceil(getY() + height / 2 + delta + (getLinearVelocity().y / (float)Game.FPS));
		}
		
		for (int i = leftBounds; i <= rightBounds; i++) {
			for (int j = lowerBounds; j <= upperBounds; j++) {
				if (tileMap.getTileAt(i, j) != null) {
					tileMap.setTileActiveAt(i, j, true, entityMap);
				}
			}
		}
			
		return true;
	*/
}
