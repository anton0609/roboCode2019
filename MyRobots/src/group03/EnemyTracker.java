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

package group03;

import java.awt.geom.Point2D;

import robocode.Robot;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;

/**
 * A class to help with the management of enemies.
 */
public class EnemyTracker {
	
	// ETSA02 Lab2: Add attributes according to the provided UML class diagram.
	private Point2D.Double[] enemyPositions;
	private String[] enemyNames;
	private int enemyCount;
	private TeamRobot robot;
	private Robot[] enemyRobots;

	/**
	 * Construct an object to help with the management of enemies.
	 * @param robot the robot we are working on.
	 */
	public EnemyTracker(TeamRobot robot) {
		// ETSA02 Lab2: Implement this constructor to initiate the attributes.
		// Make room for 10 enemies when you create the arrays.
		enemyPositions = new Point2D.Double[10];
		enemyCount = 0;
		enemyNames = new String[10];
		this.robot=robot;
	}
	
	/** 
	 * To be called when an enemy is scanned.
	 * @param e The ScannedRobotEvent received from the onScannedRobot method.
	 */ 
	public void addEnemy(ScannedRobotEvent e) {
		// ETSA02 Lab2: Add an enemy to the database. This behavior was previously in onScannedRobot()
		// You get the first line of code for free - uncomment it and add the rest
		
		Point2D.Double enemyPosition = MathUtils.calcEnemyPosition(robot, e);
		int index = findEnemyByName(e.getName());
		if (index >= 0) {
			enemyPositions[index] = enemyPosition;
		} else {
			enemyNames[enemyCount] = e.getName();
			enemyPositions[enemyCount] = enemyPosition;
			enemyCount++;
		}
	}
	
	/**
	 * Stop tracking an enemy, i.e., when that enemy is dead.
	 * @param name the name of enemy to stop tracking.
	 */
	public void removeEnemy(String name) {
		// ETSA02 Lab2: Remove an enemy from the database. This behavior was previously in onRobotDeath()
		int index = findEnemyByName(name);
		if (index >= 0) {
			for (int i = index + 1; i < enemyCount; i++) {
				enemyNames[i - 1] = enemyNames[i];
				enemyPositions[i-1] = enemyPositions[i];
			}
			enemyCount--;
		}
		
	}
	
	/**
	 * Returns the last known positions of the enemies.
	 * @return An array of the last known enemy positions.
	 */
	public Point2D.Double[] getEnemyPositions() {
		// ETSA02 Lab2: You get this method for free. Uncomment the lines below.
		
		Point2D.Double[] positions = new Point2D.Double[enemyCount];
		for (int i = 0; i < enemyCount; i++) {
			positions[i] = enemyPositions[i];
		}
		return positions;
		
	}
	
	/**
	 * @return the number of enemies currently being tracked.
	 */
	public int getEnemyCount() {
		// ETSA02 Lab2: Return the enemy count.
		return enemyCount;
	}
	
	private int findEnemyByName(String name) {
		// ETSA02 Lab2: Copy this method from BasicMeleeBot_AntiPattern.
		for (int i = 0; i < enemyCount; i++) {
			if (enemyNames[i].equals(name)) return i;
		}
		return -1;
	}
}
