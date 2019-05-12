package se.lth.cs.etsa02.dagge;

import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.MessageEvent;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;
import robocode.WinEvent;
import se.lth.cs.etsa02.RobotColors;

public class Dagge extends TeamRobot {

	private TargetingSystem targetingSystem;
	private MovementSystem movementSystem;
	private ScanSystem scanSystem;

	/**
	 * Specifies basic configurations for Dagge during the game.
	 * 
	 * Configures radar and gun movement for more efficient while turning.
	 */
	@Override
	public void run() {
		targetingSystem = new TargetingSystem(this);
		movementSystem = new MovementSystem(this, this.getBattleFieldWidth(), this.getBattleFieldHeight());
		scanSystem = new ScanSystem(this, movementSystem);
		setAdjustRadarForRobotTurn(true);
		setAdjustGunForRobotTurn(true);

		turnRadarRightRadians(Double.POSITIVE_INFINITY);
		
	}

	/**
	 * Provides all systems with information about the scanned robot. Calls upon
	 * the set of actions involved with tracking, targeting, moving and
	 * shooting.
	 * 
	 * @param e
	 *            - the ScannedRobotEvent
	 */
	@Override
	public void onScannedRobot(ScannedRobotEvent e) {
		if (scanSystem.scan(e)) {
			movementSystem.setData(targetingSystem.getAbsBearing(), targetingSystem.getLatVel());
			targetingSystem.track(e);
			movementSystem.move(e);
			targetingSystem.fire(e);
		}
	}

	/**
	 * Specifies the actions to be made when collision with a wall occurs
	 * 
	 * @param e
	 *            - the HitWallEvent
	 */
	@Override
	public void onHitWall(HitWallEvent e) {
		movementSystem.wallHit(e);
	}

	/**
	 * Specifies the actions to be made when collision with another robot
	 * occurs. Makes sure Dagge move away from the robot collided with.
	 * 
	 * @param e
	 *            - the HitRobotEvent
	 */
	@Override
	public void onHitRobot(HitRobotEvent e) {
		movementSystem.collision(e);
	}

	/**
	 * Specifies what celebration Dagge does when he wins. Makes a neat dance.
	 * 
	 * @param e
	 *            - the WinEvent
	 */
	@Override
	public void onWin(WinEvent e) {
		movementSystem.win(e);
	}

	/**
	 * Specifies how Dagge receives coloring specification from a Leader.
	 * 
	 * @param e
	 *            - the MessageEvent
	 */
	@Override
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
	 * Allows Dagge to change target after making a kill.
	 * 
	 * @param e
	 *            - the RobotDeathEvent
	 */
	@Override
	public void onRobotDeath(RobotDeathEvent e) {
		if (e.getName().equals(scanSystem.getCurrentTarget())) {
			scanSystem.reset();
		}
	}
}
