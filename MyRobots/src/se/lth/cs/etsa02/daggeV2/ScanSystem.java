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
	 * Initially picks a target closest to DaggeV2. Then makes sure that DaggeV2
	 * stays on this target.
	 * 
	 * @param e - the ScannedRobotEvent
	 * @return the proper ScannedRobotEvent
	 */

	public boolean Scan(ScannedRobotEvent e) {
		
		if(currentTarget == null && !dagge.isTeammate(e.getName())) {
		currentTarget = e.getName();
		}
		
		if (!e.getName().equals(currentTarget)) {
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

	public String getCurrentTaret() {
		return currentTarget;
	}
}
