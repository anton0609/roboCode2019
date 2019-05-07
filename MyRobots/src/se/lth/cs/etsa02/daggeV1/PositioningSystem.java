/**	
Copyright (c) 2018 David Phung

Building on work by Mathew A. Nelson and Robocode contributors.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package se.lth.cs.etsa02.daggeV1;

import java.awt.geom.Point2D;

/** 
 * A class providing support for calculating positions related to the battle field.
 */
public class PositioningSystem {
	private double width;
	private double height;
	public enum Wall {TOP, LEFT, RIGHT, BOTTOM};
	
	
	/**
	 * Construct an object to help with battle field related calculations.
	 * @param width the width of the battle field.
	 * @param height the height of the battle field.
	 */
	public PositioningSystem(double width, double height) {
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Check whether a certain point is close to one of the walls. If so returns that wall.
	 * @param point the point to check.
	 * @param nearWallDistance the distance from the wall.
	 * @return if the distance from the point to one of the walls is less than nearWallDistance, return that wall. Otherwise, return null.
	 */
	public Wall checkCloseToWall(Point2D.Double point, double nearWallDistance) {
		double x = point.x;
		double y = point.y;
		if((height-y) < nearWallDistance) {
			return Wall.TOP;
		} else if(x < nearWallDistance) {
			return Wall.LEFT;
		} else if(y < nearWallDistance) {
			return Wall.BOTTOM;
		} else if((width-x) < nearWallDistance) {
			return Wall.RIGHT;
		}
		return null;
	}
	
	/**
	 * Compute the projection of a point on a wall.
	 * @param point the point to compute.
	 * @param wall the wall to compute. 
	 * @return the projection of the point on the wall.
	 */
	public Point2D.Double getProjectionOnWall(Point2D.Double point, Wall wall) {
		switch(wall) {
		case TOP : return new Point2D.Double(point.x, height);
		case LEFT : return new Point2D.Double(0, point.y);
		case BOTTOM : return new Point2D.Double(point.x, 0);
		case RIGHT : return new Point2D.Double(width, point.y);
		default : return point;
		}
	}	
}