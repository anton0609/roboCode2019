
package se.lth.cs.etsa02.dagge;

import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;
import robocode.WinEvent;

public class MovementSystem {
 
	private double absBearing;
	private double latVel;
	private TeamRobot dagge;
	private int stuck = 0;
	private double moveDirection = 1; // Used to provide the ability to easily invert move direction.
	private double prevEnergy; // Used to keep track of targets energy level.
	private double width;
	private double height;
	private int turn;

	/**
	 * Constructor - links operating robot to this MovementSystem
	 * 
	 * @param dagge             - The operating robot
	 * @param positioningSystem - the positioningSystem
	 */

	public MovementSystem(TeamRobot dagge, Double width, Double height) {
		this.dagge = dagge;
		this.width = width;
		this.height = height;
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
		if (stuck < 0) {
			stuck = 0;
		}
		if (Math.random() > .8) {
			dagge.setMaxVelocity((12 * Math.random()) + 36);
		}

		if (e.getDistance() > 270) {

			if (stuck < 5 && e.getDistance() > 310) {

				moveDirection = 1;
			}

			if (dagge.getY() <= 50 && dagge.getHeadingRadians() < 3 * Math.PI / 2
					&& dagge.getHeadingRadians() >= Math.PI) {
				turn = 1;
				stuck++;
			}
			if (dagge.getY() >= height - 50 && dagge.getHeadingRadians() < Math.PI / 2) {
				turn = 1;
				stuck++;
			}
			if (dagge.getX() <= 50 && dagge.getHeadingRadians() >= 3 * Math.PI / 2) {
				turn = 1;
				stuck++;
			}
			if (dagge.getX() >= width - 50 && dagge.getHeadingRadians() >= Math.PI / 2
					&& dagge.getHeadingRadians() < Math.PI) {
				turn = 1;
				stuck++;
			}

			dagge.setTurnRightRadians(
					robocode.util.Utils.normalRelativeAngle((1 - turn) * (Math.PI / 10 - Math.random() * Math.PI / 3)
							+ turn * (-Math.PI / 10 + Math.random() * Math.PI / 3) + absBearing
							- dagge.getHeadingRadians() + latVel / dagge.getVelocity()));
			dagge.setAhead((e.getDistance() - 140) * moveDirection);
			stuck--;

		} else if (e.getDistance() < 100) {

			dagge.setTurnRightRadians(robocode.util.Utils.normalRelativeAngle(
					Math.PI / 2 * (e.getDistance() / 50) + absBearing - dagge.getHeadingRadians()));
			dagge.setBack(50 - (e.getDistance()) * moveDirection);

		} else {
			if (prevEnergy - e.getEnergy() >= 0.1 && prevEnergy - e.getEnergy() <= 3 && e.getDistance() > 120
					&& Math.random() > 0.8) {
				moveDirection = -moveDirection;
			}

			dagge.setTurnLeft(-90 - e.getBearing());
			dagge.setAhead(Math.max((e.getDistance() - 140) / 2, 20) * moveDirection);
			stuck--;
		}

		prevEnergy = e.getEnergy();

	}

	/**
	 * Changes move direction for a few turns which allows for moving out of melee
	 * range.
	 * 
	 * @param e - The HitRobotEvent
	 */

	public void collision(HitRobotEvent e) { // Avoid collision
		moveDirection = -moveDirection;
		stuck += 12;
	}

	/**
	 * Changes move direction for a few turns which allows for moving away from
	 * wall.
	 * 
	 * @param e - The HitWallEvent
	 */

	public void wallHit(HitWallEvent e) { // Avoid walls
		moveDirection = -moveDirection;
		stuck += 8;
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

	/**
	 * Resets the stuck parameter.
	 */
	public void setStuck() {
		stuck = 0;
	}
	/**
	 * 
	 * @return the current movement direction
	 */
	public double getMoveDirection() {
		return moveDirection;
	}

	public Double[] getData() {
		return new Double[] {absBearing, latVel};
	}

}
