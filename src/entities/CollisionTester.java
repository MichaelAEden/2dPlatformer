package entities;

import java.awt.Shape;
import java.awt.geom.Area;

import tileMap.TileMap;

public class CollisionTester {

	public CollisionTester() {
		// TODO Auto-generated constructor stub
	}
	
	//Tests for the possibility of a collision, not a collision itself
	//Uses Axis-Aligned Bounding Boxes to achieve this
	private boolean isCollisionPossible(Shape shapeA, Shape shapeB) {
		Area areaA = new Area(shapeA);
		areaA.intersect(new Area(shapeB));
		
		return !areaA.isEmpty();
	}
	
	public boolean doesEntityCollideAt(TileMap tileMap, Entity entity, int x, int y) {
		//return isCollisionPossible(entity.getAABB(), tileMap.getTileAt(x, y).get);
		return true;
	}
}
