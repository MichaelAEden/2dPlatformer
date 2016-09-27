package entities;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import render.Camera;
import render.Shader;
import tileMap.Tile;
import tileMap.TileMap;

public abstract class EntityLiving extends Entity {

	//Rendered in order
	protected final int LEFTARM = 0;
	protected final int LEFTFOREARM = 1;
	protected final int LEFTTHIGH = 2;
	protected final int LEFTLEG = 3;
	
	protected final int NECK = 4;
	protected final int TORSO = 5;
	protected final int HEAD = 6;
	
	protected final int RIGHTARM = 7;
	protected final int RIGHTFOREARM = 8;
	protected final int RIGHTTHIGH = 9;
	protected final int RIGHTLEG = 10;
	
	protected final int PARTS = 11;
	
	protected float scale;
	
	protected PhysicalObject[] bodyParts;
	
	//Body part dimensions
	protected float armWidth;
	protected float armLength;
	protected float forearmWidth;
	protected float forearmLength;
	
	protected float thighWidth;
	protected float thighLength;
	protected float legWidth;
	protected float legLength;
	
	protected float torsoWidth;
	protected float torsoLength;
	
	protected float neckWidth;
	protected float neckLength;
	protected float headRadius;
	
	//Body part positions
	protected Vec2 armPos;
	protected Vec2 forearmPos;
	protected Vec2 thighPos = new Vec2(0, 0);
	protected Vec2 legPos = new Vec2(0, 0);
	protected Vec2 neckPos;
	protected Vec2 headPos = new Vec2(0, 0);
	
	//Joint positions
	protected Vec2 shoulderPos;
	protected Vec2 elbowPos = new Vec2(0, 0);
	protected Vec2 pelvisPos = new Vec2(0, 0);
	protected Vec2 kneePos = new Vec2(0, 0);
	protected Vec2 neckToTorso = new Vec2(0, 0);
	protected Vec2 neckHead = new Vec2(0, 0);
	
	public EntityLiving(World world, Vec2 position) {
		super(world, position);
		
		bodyParts = new PhysicalObject[PARTS];
		
		
		//bodyParts[TORSO].createJoint(bodyParts[RIGHTARM], world, new Vec2(0, 0));
	}
	
	@Override
	protected void createBody(World world, Vec2 position) {
		//Every body part is positioned relative to the centre of the torso
		bodyParts[TORSO] = new PhysicalObject(torsoWidth, torsoLength, Tile.gold.getTexture(), position, world);
		bodyParts[LEFTARM] = new PhysicalObject(armWidth, armLength, Tile.gold.getTexture(), position.add(armPos), world);
		bodyParts[RIGHTARM] = new PhysicalObject(armWidth, armLength, Tile.gold.getTexture(), position.add(armPos), world);
		bodyParts[LEFTFOREARM] = new PhysicalObject(forearmWidth, forearmLength, Tile.gold.getTexture(), position.add(forearmPos), world);
		bodyParts[RIGHTFOREARM] = new PhysicalObject(forearmWidth, forearmLength, Tile.gold.getTexture(), position.add(forearmPos), world);
		bodyParts[LEFTLEG] = new PhysicalObject(legWidth, legLength, Tile.dirt.getTexture(), position.add(legPos), world);
		bodyParts[RIGHTLEG] = new PhysicalObject(legWidth, legLength, Tile.dirt.getTexture(), position.add(thighPos), world);
		bodyParts[LEFTTHIGH] = new PhysicalObject(thighWidth, thighLength, Tile.gold.getTexture(), position.add(thighPos), world);
		bodyParts[RIGHTTHIGH] = new PhysicalObject(thighWidth, thighLength, Tile.gold.getTexture(), position.add(thighPos), world);
		bodyParts[NECK] = new PhysicalObject(neckWidth, neckLength, Tile.gold.getTexture(), position.add(neckPos), world);
		bodyParts[HEAD] = new PhysicalObject(headRadius, headRadius, Tile.gold.getTexture(), position.add(headPos), world);
		
		bodyParts[TORSO].createJoint(bodyParts[LEFTARM], world, position.add(shoulderPos));
		bodyParts[TORSO].createJoint(bodyParts[RIGHTARM], world, position.add(shoulderPos));
		bodyParts[TORSO].createJoint(bodyParts[LEFTLEG], world, bodyParts[LEFTLEG].getBody().getWorldCenter().add(new Vec2(0, -0.2f)));
		bodyParts[TORSO].createJoint(bodyParts[RIGHTLEG], world, bodyParts[RIGHTLEG].getBody().getWorldCenter().add(new Vec2(0, -0.2f)));
	}

	@Override
	public void render(Shader shader, Camera camera, TileMap tileMap, EntityMap entityMap) {
		for(int i = 0; i < bodyParts.length; i++) {
			bodyParts[i].render(shader, camera, tileMap, entityMap);
		}
	}
	
	@Override
	public void applyForce(Vec2 force) {
		Body torso = bodyParts[TORSO].getBody();
		torso.applyForce(force, torso.getWorldCenter());
	}
	
	@Override
	public void kill(World world) {
		super.kill(world);
		for(PhysicalObject bodyPart : bodyParts) {
			bodyPart.destroy(world);
		}
	}
	
	@Override
	public Vec2 getLinearVelocity() { return bodyParts[TORSO].getBody().getLinearVelocity(); }
	@Override
	public Vec2 getPosition() { return bodyParts[TORSO].getBody().getPosition(); }
	@Override
	public float getX() { return bodyParts[TORSO].getBody().getPosition().x; }
	@Override
	public float getY() { return bodyParts[TORSO].getBody().getPosition().y; }
}
