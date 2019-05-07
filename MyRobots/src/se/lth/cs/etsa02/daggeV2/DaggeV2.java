package se.lth.cs.etsa02.daggeV2;

import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.MessageEvent;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;
import robocode.WinEvent;
import se.lth.cs.etsa02.RobotColors;

public class DaggeV2 extends TeamRobot {

	private TargetingSystem targetingSystem;
	private MovementSystem movementSystem;
	private ScanSystem scanSystem;

	/**
	 * Specifies basic configurations for DaggeV2 during the game.
	 * 
	 * Configures radar and gun movement for more efficient while turning.
	 */

	public void run() {
		targetingSystem = new TargetingSystem(this);
		movementSystem = new MovementSystem(this, this.getBattleFieldWidth(), this.getBattleFieldHeight());
		scanSystem = new ScanSystem(this);
		setAdjustRadarForRobotTurn(true);
		setAdjustGunForRobotTurn(true);

		turnRadarRightRadians(Double.POSITIVE_INFINITY);

	}

	/**
	 * Calls upon the set of actions involved with tracking, targeting, moving and
	 * shooting.
	 * 
	 * Makes sure DaggeV2 lock onto a target, then moves into optimal distance, then
	 * circle strafes around its target while firing continuously with adaptive
	 * power. Also involves countermeasures for predicted incoming fire.
	 * 
	 * @param e - the ScannedRobotEvent
	 */

	public void onScannedRobot(ScannedRobotEvent e) {
		if (scanSystem.Scan(e)) {
			movementSystem.setData(targetingSystem.getAbsBearing(), targetingSystem.getLatVel());
			targetingSystem.track(e);
			movementSystem.move(e);
			targetingSystem.fire(e);
		}
	}

	/**
	 * Specifies the actions to be made when collision with a wall occurs. Makes
	 * sure DaggeV2 turns around and moves away from wall.
	 * 
	 * @param e - the HitWallEvent
	 */

	public void onHitWall(HitWallEvent e) {
		movementSystem.wallHit(e);
	}

	/**
	 * Specifies the actions to be made when collision with another robot occurs.
	 * Makes sure DaggeV2 moves out of melee range.
	 * 
	 * @param e - the HitRobotEvent
	 */

	public void onHitRobot(HitRobotEvent e) {
		movementSystem.collision(e);
	}

	/**
	 * Specifies what celebration DaggeV2 does when he wins. Makes a neat dance.
	 * 
	 * @param e - the WinEvent
	 */

	public void onWin(WinEvent e) {
		movementSystem.win(e);
	}

	/**
	 * Specifies how DaggeV2 receives coloring specification from a Leader.
	 * 
	 * @param e - the MessageEvent
	 */

	public void onMessageReceived(MessageEvent e) {
		if (e.getMessage() instanceof RobotColors) {
			RobotColors c = (RobotColors) e.getMessage();
			setBodyColor(c.bodyColor);
			setGunColor(c.gunColor);
			setRadarColor(c.radarColor);
			setScanColor(c.scanColor);
			setBulletColor(c.bulletColor);
		}
	}

	/**
	 * Allows DaggeV2 to change target after making a kill.
	 * 
	 * @param e - the RobotDeathEvent
	 */

	public void onRobotDeath(RobotDeathEvent e) {
		if (e.getName().equals(scanSystem.getCurrentTaret())) {
			scanSystem.reset();
		}
	}
}
