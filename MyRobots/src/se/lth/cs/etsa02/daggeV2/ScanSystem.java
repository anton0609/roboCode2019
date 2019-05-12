package se.lth.cs.etsa02.daggeV2;

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
	 * Checks if the robot is 
	 * 
	 * @param e
	 *            - the ScannedRobotEvent
	 * @return true if the scanned robot is an enemy and the current target if there is one
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
	 * Resets the target for DaggeV2.
	 */

	public void reset() {
		currentTarget = null;
		movementSystem.setStuck();
	}
	
	
	/**
	 * Returns DaggeV2's current target.
	 * 
	 * @return currentTarget
	 */

	public String getCurrentTarget() {
		return currentTarget;
	}

}
