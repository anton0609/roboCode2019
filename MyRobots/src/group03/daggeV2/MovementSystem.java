
package group03.daggeV2;

import java.awt.geom.Point2D;

import group03.PositioningSystem;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;
import robocode.WinEvent;
import robocode.util.Utils;
//TODO implementera ett gravitationsmovement så att vi håller oss undan väggarna
//TODO implementera ett bättra oscillerande mönster då vi rör oss mot fiendern
public class MovementSystem {
	private PositioningSystem positioningSystem;
	private double absBearing;
	private double latVel;
	private TeamRobot dagge;
	private boolean stuck = false;
	private double moveDirection = 1; // Used to provide the ability to easily invert move direction.
	private double prevEnergy; 		  // Used to keep track of targets energy level.
	
	
	/**
	 * Constructor - links operating robot to this MovementSystem
	 * 
	 * @param dagge - The operating robot
	 * @param positioningSystem - the positioningSystem
	 */

	public MovementSystem(TeamRobot dagge, PositioningSystem positioningSystem) {
		this.dagge = dagge;
		this.positioningSystem = positioningSystem;
	}

	/**
	 * Provides ability to input values for parameters absBearing and latVel.
	 * 
	 * @param absBearing - Short for absolute bearing.
	 * @param latVel     - Short for later velocity.
	 */

	public void setData(double absBearing, double latVel) {
		this.absBearing = absBearing;
		this.latVel = latVel;
	}

	/**
	 * Specifies movement related action to be made in the event of detecting
	 * another robot with the radar. Moves into optimal range if farther away. Makes
	 * movement slightly less predictable by randomly changing speed. Strafes
	 * circularly and with perpendicular bearing around the target when in range.
	 * Also enables countermeasures for collisions and predicted incoming enemy
	 * fire.
	 * 
	 * @param e - the ScannedRobotEvent
	 */

	public void move(ScannedRobotEvent e) {

		if (Math.random() > .8) {
			dagge.setMaxVelocity((12 * Math.random()) + 36);
		}

		if (e.getDistance() > 220) {
			if(!stuck) {
			moveDirection = 1;
			}
			dagge.setTurnRightRadians(robocode.util.Utils
					.normalRelativeAngle(absBearing - dagge.getHeadingRadians() + latVel / dagge.getVelocity()));
			dagge.setAhead((e.getDistance() - 140) * (moveDirection / Math.abs(moveDirection)));
			stuck = false;
		} else {
			if (prevEnergy - e.getEnergy() >= 0.1 && e.getDistance() > 200) {
				moveDirection = -moveDirection;
			}
			dagge.setTurnLeft(-90 - e.getBearing());
			dagge.setAhead(Math.max((e.getDistance() - 140), 20) * (moveDirection / Math.abs(moveDirection)));
			stuck = false;
		}
		prevEnergy = e.getEnergy();
		stuck = false;
	}

	/**
	 * Changes move direction for a few turns which allows for moving out of melee
	 * range.
	 * 
	 * @param e - The HitRobotEvent
	 */

	public void collision(HitRobotEvent e) { // Avoid collision
		moveDirection = -moveDirection;
		stuck = true;
	}

	/**
	 * Changes move direction for a few turns which allows for moving away from
	 * wall.
	 * 
	 * @param e - The HitWallEvent
	 */

	public void wallHit(HitWallEvent e) { // Avoid walls
		moveDirection = -moveDirection;
		stuck = true;
	}

	/**
	 * Makes a series of quick turns which simulates a dance.
	 * 
	 * @param e - The WinEvent
	 */
	public void win(WinEvent e) {
		for (int i = 0; i < 50; i++) {
			dagge.turnRight(30);
			dagge.turnLeft(30);
		}
	}
}
