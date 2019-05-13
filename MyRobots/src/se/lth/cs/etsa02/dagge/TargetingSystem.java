
package se.lth.cs.etsa02.dagge;

import robocode.ScannedRobotEvent;
import robocode.TeamRobot;

public class TargetingSystem {

	private TeamRobot dagge;
	private double absBearing; // Short for absolute bearing.
	private double latVel;	// Short for lateral velocity
	
	
	
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
		
		
		absBearing = e.getBearingRadians() + dagge.getHeadingRadians();
		
		latVel = e.getVelocity() * Math.sin(e.getHeadingRadians() - absBearing);
		
		double gunTurnAmt;
		
		dagge.setTurnRadarLeftRadians(dagge.getRadarTurnRemainingRadians());
				
		if (e.getDistance() >= 300) {
			gunTurnAmt = robocode.util.Utils
					.normalRelativeAngle(absBearing - dagge.getGunHeadingRadians() + latVel / (13*e.getDistance()/170));
			dagge.setTurnGunRightRadians(gunTurnAmt);
		} else if (e.getDistance() > 150 && e.getDistance() < 300) {
			gunTurnAmt = robocode.util.Utils
					.normalRelativeAngle(absBearing - dagge.getGunHeadingRadians() + latVel / 14);
			dagge.setTurnGunRightRadians(gunTurnAmt);

		} else {
			gunTurnAmt = robocode.util.Utils
					.normalRelativeAngle(absBearing - dagge.getGunHeadingRadians() + latVel / 9);
			dagge.setTurnGunRightRadians(gunTurnAmt);
		}
	}

	/**
	 * Calibrates firing power to make sure shots are more efficient.
	 * 
	 * @param e - The ScannedRobotEvent
	 * @return whether or not the robot was instructed to fire;
	 */

	public boolean fire(ScannedRobotEvent e) {
		if(e.getDistance() > 500) {
			return false;
		}
		if (e.getDistance() > 250) {
			dagge.setFire(Math.min(Math.min(dagge.getEnergy() / 10, 500 / e.getDistance()), e.getEnergy() / 4));
			return true;
		} else {
			dagge.setFire(Math.min(Math.min(dagge.getEnergy() / 10, 3), e.getEnergy() / 4));
			return true;
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

