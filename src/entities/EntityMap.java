package entities;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import game.Game;
import io.Window;
import render.Camera;
import render.Shader;
import tileMap.Tile;
import tileMap.TileMap;

public class EntityMap {
	
	//World takes arguments in standard units, **i.e. metres and seconds
	private World world = new World(new Vec2(0, -9.8f), false);
	private Set<Entity> entities = new HashSet<Entity>();
	
	private EntityPlayer player;

	public EntityMap() {
		player = new EntityPlayer(world, new Vec2(6.3f, 6));
		
		spawnEntity(player);
		spawnEntity(new EntityTile(world, new Vec2(5, 6), Tile.testTile.getID(), true));
		spawnEntity(new EntityTile(world, new Vec2(5.4f, 7), Tile.testTile.getID(), true));
		spawnEntity(new EntityTile(world, new Vec2(5.8f, 8), Tile.testTile.getID(), true));
		spawnEntity(new EntityTile(world, new Vec2(6.3f, 9), Tile.testTile.getID(), true));
		spawnEntity(new EntityTile(world, new Vec2(6.8f, 8), Tile.testTile.getID(), true));
		spawnEntity(new EntityTile(world, new Vec2(7.2f, 7), Tile.testTile.getID(), true));
		spawnEntity(new EntityTile(world, new Vec2(7.6f, 6), Tile.testTile.getID(), true));	
	}
	
	public void spawnEntity(Entity entity) {
		entities.add(entity);
	}
	
	public void update(Camera camera, Window window, TileMap tileMap) {
		updateWorld();
		updateEntities(window, tileMap);
	}
	
	private void updateWorld() {
		//velocityIterations and positionIterations are used during collision testing
		//Higher numbers are used for more accuracy but use more processing power, and vice versa
		world.step(1f/(float)Game.FPS, 8, 3);
	}
	
	private void updateEntities(Window window, TileMap tileMap) {
		EntityOperation update = (Entity entity, Window win, TileMap tMap, EntityMap entityMap) -> entity.update(win, tMap, entityMap, world);
		EntityOperation killStaticTiles = (Entity entity, Window win, TileMap tMap, EntityMap entityMap) -> {
			if (entity instanceof EntityTile && !((EntityTile) entity).isDynamic() && !tileMap.isTileActiveAt((int)entity.getX(), (int)entity.getY())) {
				entity.kill(world);
			}
		};
		
		entities = processEntities(update, window, tileMap, this);
		entities = processEntities(killStaticTiles, window, tileMap, this);
		
		Iterator<Entity> iterator = entities.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().isDead()) iterator.remove();
		}
	}
	
	public void render(Shader shader, Camera camera, Window window, TileMap tileMap, EntityMap entityMap) {
		for (Entity entity : entities) {
			entity.render(shader, camera, tileMap, entityMap);
		}
	}
	
	
	
	/*public Set<Entity> getEntitiesAtPoint(Vec2 point) {
		Set<Entity> entitiesAtPoint = new HashSet<Entity>();
		Iterator<Entity> iterator = entities.iterator();
		Entity entity;
		
		while (iterator.hasNext()) {
			entity = iterator.next();
			Vec2 localPoint = entity.getBody().getLocalPoint(point);
			if (Math.abs(localPoint.x) < entity.getWidth() && Math.abs(localPoint.y) < entity.getHeight()) entitiesAtPoint.add(entity);
		}
		return entitiesAtPoint;
	}*/
	
	public static Set<Entity> processEntities(EntityOperation entityOperation, Window window, TileMap tileMap, EntityMap entityMap) {
		Set<Entity> processed = new HashSet<Entity>();
		Set<Entity> todo = new HashSet<Entity>(entityMap.getAllEntities());
				
		Entity entity;
			
		while (!todo.isEmpty()) {
			entity = todo.iterator().next();
			
			entityOperation.process(entity, window, tileMap, entityMap);
			
			processed.add(entity);
			todo = new HashSet<Entity>(entityMap.getAllEntities());
			todo.removeAll(processed);
		}
		
		return processed;
	}
	
	public interface EntityOperation {
		void process(Entity entity, Window window, TileMap tileMap, EntityMap entityMap);
	}
	
	public Set<Entity> getAllEntities() { return entities; }
	public Entity getPlayer() { return player; }
	
	//Not used
	/*	
	public Set<Entity> getNearbyEntities(Vec2 point, float radius) {
		Set<Entity> nearbyEntities = new HashSet<Entity>();
		Iterator<Entity> iterator = entities.iterator();
		Entity entity;
				
		while (iterator.hasNext()) {
			entity = iterator.next();
			if (Math.pow(radius, 2) > Math.pow(entity.getX() - point.x, 2) + Math.pow(entity.getY() - point.y, 2)) nearbyEntities.add(entity);
		}
		return nearbyEntities;
	}*/
}
