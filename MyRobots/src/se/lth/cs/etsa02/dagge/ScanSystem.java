package se.lth.cs.etsa02.dagge;

import robocode.ScannedRobotEvent;
import robocode.TeamRobot;

public class ScanSystem {

	private TeamRobot dagge;
	private String currentTarget;
	private MovementSystem movementSystem;

	public ScanSystem(TeamRobot dagge, MovementSystem movementSystem) {
		this.dagge = dagge;
		this.movementSystem = movementSystem;
	}

	/**
	 * Checks if the robot is eligible for targeting.
	 * 
	 * @param e
	 *            - the ScannedRobotEvent
	 * @return true if the scanned robot is an enemy and also the chosen current target if there is one.
	 */

	public boolean scan(ScannedRobotEvent e) {

		if (currentTarget == null && !dagge.isTeammate(e.getName())) {
			currentTarget = e.getName();
			return true;
		} else if (!e.getName().equals(currentTarget)) {
			return false;
		}
		return true;
	}

	/**
	 * Resets the target for Dagge.
	 */

	public void reset() {
		currentTarget = null;
		movementSystem.setStuck();
	}
	
	
	/**
	 * Returns Dagge's current target.
	 * 
	 * @return currentTarget
	 */

	public String getCurrentTarget() {
		return currentTarget;
	}

}
