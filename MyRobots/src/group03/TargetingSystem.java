
package group03;

import java.awt.geom.Point2D;

import robocode.util.Utils;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;

public class TargetingSystem {

	private TeamRobot dagge;
	private double absBearing; // Short for absolute bearing.
	private double latVel;     // Short for later velocity

	/**
	 * Constructor - links operating robot to this TargetingSystem
	 * 
	 * @param dagge - The operating robot
	 */

	public TargetingSystem(TeamRobot dagge) {
		this.dagge = dagge;
	}

	/**
	 * Provides the ability to lock onto a detected enemy robot. In tandem with
	 * radar tracking also controls gun for predictive targeting.
	 * 
	 * @param e - The ScannedRobotEvent
	 */

	public void track(ScannedRobotEvent e) {

		if (dagge.isTeammate(e.getName())) {
			return;
		}

		absBearing = e.getBearingRadians() + dagge.getHeadingRadians();
		latVel = e.getVelocity() * Math.sin(e.getHeadingRadians() - absBearing);
		double gunTurnAmt;
		dagge.setTurnRadarLeftRadians(dagge.getRadarTurnRemainingRadians());

		if (e.getDistance() > 150) {
			gunTurnAmt = robocode.util.Utils
					.normalRelativeAngle(absBearing - dagge.getGunHeadingRadians() + latVel / 13);
			dagge.setTurnGunRightRadians(gunTurnAmt);

		} else {
			gunTurnAmt = robocode.util.Utils
					.normalRelativeAngle(absBearing - dagge.getGunHeadingRadians() + latVel / 8);
			dagge.setTurnGunRightRadians(gunTurnAmt);
		}
	}

	/**
	 * Calibrates firing power to make sure shots are more efficient.
	 * 
	 * @param e - The ScannedRobotEvent
	 */

	public void fire(ScannedRobotEvent e) {

		if (e.getDistance() > 150) {
			dagge.setFire(Math.min(Math.min(dagge.getEnergy() / 10, 400 / e.getDistance()), e.getEnergy() / 4));
		} else {
			dagge.setFire(Math.min(Math.min(dagge.getEnergy() / 10, 3), e.getEnergy() / 4));
		}
	}

	/**
	 * Simply enables easy access to this key variable for other methods.
	 * 
	 * @return absBearing - useful value for the MovementSystem.
	 */

	public double getAbsBearing() {
		return absBearing;
	}

	/**
	 * Simply enables easy access to this key variable for other methods.
	 * 
	 * @return latVel - useful value for the MovementSystem.
	 */

	public double getLatVel() {
		return latVel;
	}
}
