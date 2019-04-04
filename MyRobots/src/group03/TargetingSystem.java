
package group03;

import java.awt.geom.Point2D;

import robocode.util.Utils;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;

/**
 * A simple class handling aiming and firing. It simply shoots at the closest
 * target based on the last known positions of all enemies.
 */
public class TargetingSystem {

	private TeamRobot dagge;
	private double absBearing;
	private double latVel;

	public TargetingSystem(TeamRobot dagge) {
		this.dagge = dagge;
	}
/**
 * Method for locking onto a prey with the radar 
 * and predicting its movement in order to stalk the prey and later effectively fire at it
	
*/
	public void track(ScannedRobotEvent e) {

		if (dagge.isTeammate(e.getName())) {
			return;
		}
		
		absBearing = e.getBearingRadians() + dagge.getHeadingRadians();         								// enemies absolute bearing
		latVel = e.getVelocity() * Math.sin(e.getHeadingRadians() - absBearing);								// enemies later velocity
		double gunTurnAmt;															   								// amount to turn our gun
		dagge.setTurnRadarLeftRadians(dagge.getRadarTurnRemainingRadians());		   								// lock on the radar
		
	if(e.getDistance() > 150) {
		gunTurnAmt = robocode.util.Utils.normalRelativeAngle(absBearing- dagge.getGunHeadingRadians()+latVel/13);   //amount to turn our gun, lead just a little bit
		dagge.setTurnGunRightRadians(gunTurnAmt); 																	//turn our gun
		
	} else {
		gunTurnAmt = robocode.util.Utils.normalRelativeAngle(absBearing- dagge.getGunHeadingRadians()+latVel/8);	//amount to turn our gun, lead just a little bit
		dagge.setTurnGunRightRadians(gunTurnAmt);																	//turn our gun
		}
	}
	public void fire(ScannedRobotEvent e) {
		
		if(e.getDistance() > 150) {
			dagge.setFire(Math.min(Math.min(dagge.getEnergy()/10, 400/e.getDistance()), e.getEnergy()/4));			// Fire
		} else {
			dagge.setFire(Math.min(Math.min(dagge.getEnergy()/10, 3), e.getEnergy()/4));							// Fire
		}
	}
	public double getAbsBearing() {
		return absBearing;
	}
	public double getLatVel() {
		return latVel;
	}
}
