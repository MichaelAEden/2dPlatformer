package entities;

import static org.lwjgl.glfw.GLFW.*;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import io.Window;
import tileMap.TileMap;

public class EntityPlayer extends EntityLiving {
	
	private float jump = 100.0f;
	private float walkSpeed = 100.0f;
	
	public EntityPlayer(World world, Vec2 position) {
		super(world, position);
		
		name = "player";
		
		//Body proportions
		armWidth = 0.15f;
		armLength = 0.41f;
		forearmWidth = 0.12f;
		forearmLength = 0.59f;
		
		thighWidth = 0.22f;
		thighLength = 0.55f;
		legWidth = 0.14f;
		legLength = 0.58f;
		
		torsoWidth = 0.31f;
		torsoLength = 0.77f;
		
		neckWidth = 0.15f;
		neckLength = 0.16f;
		headRadius = 0.15f;
		
		armPos = new Vec2(-0.067f, 0.148f);
		forearmPos = new Vec2(-0.067f, 0.235f);
		neckPos = new Vec2(-0.023f, 0.419f);
		
		shoulderPos = new Vec2(-0.090f, 0.270f);
		
		scale = 1f;
		
		createBody(world, position);
	}
	
	@Override
	public void update(Window window, TileMap tileMap, EntityMap entityMap, World world) {
		super.update(window, tileMap, entityMap, world);
						
		float xForce = 0;
		float yForce = 0; 
		
		if (window.getInput().isKeyDown(GLFW_KEY_UP)) {
			yForce += jump;
		}
		if (window.getInput().isKeyDown(GLFW_KEY_LEFT)) {
			xForce -= walkSpeed;
		}
		else if (window.getInput().isKeyDown(GLFW_KEY_RIGHT)) {
			xForce += walkSpeed;
		}
		
		/*if (window.getInput().isKeyPressed(GLFW_KEY_SPACE)) {
			EntityOperation useForce = (Entity entity, Window win, TileMap tMap, EntityMap eMap) -> {
				if (!(entity instanceof EntityPlayer) && !(entity instanceof EntityTile && !((EntityTile)entity).isDynamic()))
				{
					Vec2 direction = entity.getPosition().sub(getPosition());
					float distance = direction.normalize();
					float strength = 500f;
					entity.applyForce(direction.mul(strength / distance));
				}
			};
			EntityMap.processEntities(useForce, window, tileMap, entityMap);
		}*/
			
		applyForce(new Vec2(xForce, yForce));
	}
}
