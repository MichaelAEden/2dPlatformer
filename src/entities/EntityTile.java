package entities;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import io.Window;
import render.Camera;
import render.Shader;
import tileMap.Tile;
import tileMap.TileMap;

public class EntityTile extends Entity {
	
	protected int tileID;
	
	protected Body body;
	
	protected float width;
	protected float height;
	
	//Used for tiles so they can be placed in the JBox2d world to interact with other entities
	
	protected boolean isDynamic;
	
	public EntityTile(World world, Vec2 position, int tileID, boolean isDynamic) {
		super(world, position);
		
		this.tileID = tileID;
		this.isDynamic = isDynamic;
		
		this.width = isDynamic ? 1.0f : 0.97f;
		this.height = isDynamic ? 1.0f : 0.97f;
		
		name = "Tile " + Tile.getTileAtID(tileID).getName();
		
		createBody(world, position);
	}

	protected void createBody(World world, Vec2 position) {
		BodyDef boxDef = new BodyDef();
		boxDef.type = isDynamic ? BodyType.DYNAMIC : BodyType.STATIC;
		boxDef.position.set(position);
		
		//Collision occurs between 'polygon skins', so there is space between colliding entities
		//Therefore, the dimensions are slightly less than 0.5f (0.5f gives a 1 x 1 tile) for static tiles
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(width / 2f, height / 2f);
		
		FixtureDef boxFixture = new FixtureDef();
		boxFixture.density = 1;
		boxFixture.shape = boxShape;
		
		body = world.createBody(boxDef);
		body.createFixture(boxFixture);
	}
	
	@Override
	public void update(Window window, TileMap tileMap, EntityMap entityMap, World world) {
		if (isDynamic) {
			super.update(window, tileMap, entityMap, world);
		}
	}
	
	@Override
	public void render(Shader shader, Camera camera, TileMap tileMap, EntityMap entityMap) {
		//if (isDynamic) {
			Vec2 bodyPosition = body.getPosition();
			
			shader.bind();
			
			if (isDynamic) {
				Tile.getTileAtID(tileID).getTexture().bind(0);
			}
			else {
				Tile.brick.getTexture().bind(0);
			}
			
			Matrix4f tilePosition = new Matrix4f().translate(new Vector3f(bodyPosition.x, bodyPosition.y, 0));
			Matrix4f tileRotation = new Matrix4f().rotate(body.getAngle(), new Vector3f(0, 0, 1));
			Matrix4f target = new Matrix4f();
			
			camera.getProjection().mul(tileMap.getMatrix(), target);
			target.mul(tilePosition);
			target.mul(tileRotation);
			
			shader.setUniform("sampler", 0);
			shader.setUniform("projection", target);
			
			Tile.getTileAtID(tileID).getModel().render();
			
		//}
	}
	
	public boolean isDynamic() { return isDynamic; }

	@Override
	public Vec2 getPosition() {	return body.getPosition(); }
	@Override
	public Vec2 getLinearVelocity() { return body.getLinearVelocity(); }
	@Override
	public float getX() { return body.getPosition().x; }
	@Override
	public float getY() { return body.getPosition().y; }
	@Override
	public void applyForce(Vec2 force) {
		body.applyForce(force, body.getWorldCenter());
	}
}
