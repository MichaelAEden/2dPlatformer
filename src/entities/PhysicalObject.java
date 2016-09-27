package entities;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.RevoluteJointDef;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import render.Camera;
import render.Model;
import render.Shader;
import render.Texture;
import tileMap.TileMap;

public class PhysicalObject {
	
	private Model model;
	private Texture texture;
	private Body body;

	public PhysicalObject(float width, float height, Texture texture, Vec2 position, World world) {
		this.texture = texture;
		createModel(width, height);
		createBody(width, height, world, position);
	}
	
	protected void createModel(float width, float height) {
		float w = 0.5f * width;
		float h = 0.5f * height;
		float[] vertices = new float[] {
				-w, h, 0, 	//TOP LEFT		0
				w, h, 0,		//TOP RIGHT		1
				w, -h, 0,		//BOTTOM RIGHT	2
				-w, -h, 0,	//BOTTOM LEFT	3
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
	
	protected void createBody(float width, float height, World world, Vec2 position) {
		BodyDef boxDef = new BodyDef();
		boxDef.type = BodyType.DYNAMIC;
		boxDef.position.set(position);
		
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(width / 2f, height / 2f);
		
		FixtureDef boxFixture = new FixtureDef();
		boxFixture.density = 5;
		boxFixture.shape = boxShape;
		boxFixture.filter.categoryBits = 0x0002;
		boxFixture.filter.maskBits = 0x0001;
		
		body = world.createBody(boxDef);
		body.createFixture(boxFixture);
	}
	
	public void createJoint(PhysicalObject body2, World world, Vec2 anchor) {
		RevoluteJointDef jointDef = new RevoluteJointDef();
		jointDef.enableMotor = true;
		jointDef.motorSpeed = 0.0f;
		jointDef.maxMotorTorque = 500;
		jointDef.initialize(body, body2.getBody(), anchor);
				
		world.createJoint(jointDef);
	}
	
	public void render(Shader shader, Camera camera, TileMap tileMap, EntityMap entityMap) {
		Vec2 position = body.getPosition();
		
		shader.bind();
		texture.bind(0);
		
		Matrix4f objectPosition = new Matrix4f().translate(new Vector3f(position.x, position.y, 0));
		Matrix4f objectRotation = new Matrix4f().rotate(body.getAngle(), new Vector3f(0, 0, 1));
		Matrix4f target = new Matrix4f();
		
		camera.getProjection().mul(tileMap.getMatrix(), target);
		target.mul(objectPosition);
		target.mul(objectRotation);
		
		shader.setUniform("sampler", 0);
		shader.setUniform("projection", target);
		
		model.render();
	}
	
	public void destroy(World world) {
		world.destroyBody(body);
		try {
			texture.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public Body getBody() { return body; }
}
