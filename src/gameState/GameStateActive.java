package gameState;

import java.util.Set;

import org.jbox2d.common.Vec2;

import entities.Entity;
import entities.EntityMap;
import entities.EntityTile;
import io.Window;
import render.Background;
import render.Camera;
import render.Shader;
import tileMap.TileMap;

public class GameStateActive extends GameState {
	
	private Background background;
	
	private Entity draggedEntity;
	private Vec2 cursorOffset;
	
	public GameStateActive(GameStateManager gsm) {
		super(gsm);
		
		background = new Background("backgroundSky");
	}
	
	@Override
	public void update(Window window, Camera camera, TileMap tileMap, EntityMap entityMap) {
		super.update(window, camera, tileMap, entityMap);
		
		tileMap.update(camera, window, entityMap);
		entityMap.update(camera, window, tileMap);
		
		/*if (window.getInput().isMousePressed(0)) {
			Vec2 mouse = window.getInput().getMouseCursorOnTileMap(camera, window, tileMap);
			int x = (int)mouse.x;
			int y = (int)mouse.y;
			cursorOffset = new Vec2(x - mouse.x, y - mouse.y);
			if (tileMap.getTileAt(x, y) != null) {
				draggedEntity = new EntityTile(entityMap.getWorld(), mouse.add(cursorOffset), tileMap.getTileIDAt(x, y), true);
				tileMap.setTileAt(x, y, 0);
				entityMap.spawnEntity(draggedEntity);
			}
			else {
				Set<Entity> entities = entityMap.getEntitiesAtPoint(mouse);
				if (entities.size() > 0) draggedEntity = entities.iterator().next();
			}
		}
		
		if (window.getInput().isMouseDown(0) && draggedEntity != null) {
			Vec2 mouse = window.getInput().getMouseCursorOnTileMap(camera, window, tileMap).add(cursorOffset);
			Vec2 direction = mouse.sub(draggedEntity.getPosition());
			double distance = direction.normalize();
			double strength = 20d;
			
			draggedEntity.applyForce(direction.mul((float)(strength * Math.pow(distance, 1.5d))));
			
			//Remove effects of gravity
			draggedEntity.applyForce(new Vec2(0, draggedEntity.getBody().getMass() * 9.8f));
			//Gradually slow the entity
			float slowFactor = 3f;
			draggedEntity.applyForce(draggedEntity.getLinearVelocity().mul(-slowFactor));
			
			draggedEntity.getBody().setFixedRotation(true);
		}
		else if (window.getInput().isMouseReleased(0)) {
			if (draggedEntity != null) {
				draggedEntity.getBody().setFixedRotation(false);
			}
			draggedEntity = null;
		}*/
	}
	
	@Override
	public void render(Window window, Shader shader, Camera camera, TileMap tileMap, EntityMap entityMap) {
		background.render(shader, camera, tileMap, window);
		tileMap.render(shader, camera, window, entityMap);
		entityMap.render(shader, camera, window, tileMap, entityMap);
	}
}
