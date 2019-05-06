package group03.daggeV2;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javafx.util.Pair;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;

public class ScanSystem {
	private TeamRobot dagge;
	private String currentTarget = null;
	private Map<String, Double> targets = new HashMap<String, Double>();
	private boolean alive = false;
	private Point2D.Double prevPos;
	private int count = 0;

	public ScanSystem(TeamRobot dagge) {
		this.dagge = dagge;
		prevPos = new Point2D.Double(dagge.getX(), dagge.getY());
	}

	/**
	 * Initially picks a target closest to DaggeV2. Then makes sure that DaggeV2
	 * stays on this target.
	 * 
	 * @param e - the ScannedRobotEvent
	 * @return the proper ScannedRobotEvent
	 */

	public boolean Scan(ScannedRobotEvent e) {
		
		if(currentTarget == null) {
		currentTarget = e.getName();
		}
		
		if (!e.getName().equals(currentTarget) && e.getEnergy() > 0) {
			dagge.setTurnRadarLeftRadians(Double.POSITIVE_INFINITY);
			return false;
		}
		return true;
		/*if (!alive) {
			if (!dagge.isTeammate(e.getName())) {
				targets.put(e.getName(), e.getDistance());
			}
			Double minDist = Double.POSITIVE_INFINITY;
			for (Entry<String, Double> entry : targets.entrySet()) {
				if (entry.getValue() < minDist) {
					minDist = entry.getValue();
					currentTarget = entry.getKey();
				}
			}
			alive = true;
		
		return e; */
	}

	/**
	 * Resets the target for DaggeV2.
	 */

	public void reset() {
		currentTarget = null;
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
