
package se.lth.cs.etsa02.daggeV2;




import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;
import robocode.WinEvent;


//TODO implementera ett gravitationsmovement så att vi håller oss undan väggarna
//TODO implementera ett bättra oscillerande mönster då vi rör oss mot fiendern
public class MovementSystem {
	
	private double absBearing;
	private double latVel;
	private TeamRobot dagge;
	private int stuck = 0;
	private double moveDirection = 1; // Used to provide the ability to easily invert move direction.
	private double prevEnergy; // Used to keep track of targets energy level.
	private double width;
	private double height;
	private double up;
	private double down;
	private double right;
	private double left;
	
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

		if (e.getDistance() > 220) {
			if (stuck < 5 && e.getDistance() > 270) {
				moveDirection = 1;
			}

			up = 0;
			down = 0;
			right = 0;
			left = 0;

			if (dagge.getY() <= 20) {
				up = 1;
			}
			if (dagge.getY() >= height - 20) {
				down = 1;
			}
			if (dagge.getX() <= 20) {
				right = 1;
			}
			if (dagge.getX() >= width - 20) {
				left = 1;
			}
			if (left == 1 || right == 1 || up == 1 || down == 1) {
				stuck ++;
				
				if (dagge.getHeadingRadians() < Math.PI / 2) {
					dagge.setTurnRightRadians(right * (Math.PI / 2 - dagge.getHeadingRadians()));
					dagge.setTurnLeftRadians(up * (dagge.getHeadingRadians()));
					dagge.setTurnRightRadians(down * (Math.PI / 2 - dagge.getHeadingRadians()));
					dagge.setTurnLeftRadians(left * (dagge.getHeadingRadians() + Math.PI / 2));
				} else
				if (dagge.getHeadingRadians() >= 3 * Math.PI / 2) {
					dagge.setTurnRightRadians(right * (2 * Math.PI - dagge.getHeadingRadians() + Math.PI / 2));
					dagge.setTurnLeftRadians(left * (Math.PI / 2 - (dagge.getHeadingRadians() - 2 * Math.PI)));
					dagge.setTurnRightRadians(up * (2 * Math.PI - dagge.getHeadingRadians()));
					dagge.setTurnLeftRadians(down * (dagge.getHeadingRadians() - Math.PI));
				} else
				if (dagge.getHeadingRadians() >= Math.PI / 2 && dagge.getHeadingRadians() < Math.PI) {
					dagge.setTurnLeftRadians(right * (dagge.getHeadingRadians() - Math.PI / 2));
					dagge.setTurnRightRadians(left * (Math.PI - dagge.getHeadingRadians() + Math.PI / 2));
					dagge.setTurnLeftRadians(up * (dagge.getHeadingRadians()));
					dagge.setTurnRightRadians(down * (Math.PI - dagge.getHeadingRadians()));
				} else
				if (dagge.getHeadingRadians() < 3 * Math.PI / 2 && dagge.getHeadingRadians() >= Math.PI) {
					dagge.setTurnLeftRadians(right * (dagge.getHeadingRadians() - Math.PI / 2));
					dagge.setTurnRightRadians(left * (Math.PI - dagge.getHeadingRadians() + Math.PI / 2));
					dagge.setTurnRightRadians(up * (2 * Math.PI - dagge.getHeadingRadians()));
					dagge.setTurnLeftRadians(down * (dagge.getHeadingRadians() - Math.PI));
				}
				dagge.ahead(20*moveDirection);
				dagge.execute();
				
			} else {
				
				dagge.setTurnRightRadians(robocode.util.Utils
						.normalRelativeAngle(absBearing - dagge.getHeadingRadians() + latVel / dagge.getVelocity()));
				dagge.setAhead((e.getDistance() - 140) * moveDirection);
				stuck--;
			}
		} else {
		
			//if (prevEnergy - e.getEnergy() >= 0.1 && e.getDistance() > 100 && Math.random() > 0.7) {
			//	moveDirection = -moveDirection;
			//}
			dagge.turnLeft(-90 - e.getBearing());
			dagge.setAhead(Math.max((e.getDistance() - 140) / 2, 10) * moveDirection);
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
		stuck++;
	}

	/**
	 * Changes move direction for a few turns which allows for moving away from
	 * wall.
	 * 
	 * @param e - The HitWallEvent
	 */

	public void wallHit(HitWallEvent e) { // Avoid walls
		moveDirection = -moveDirection;
		stuck++;
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
