package group03;

import java.awt.Color;

import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.MessageEvent;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;
import robocode.WinEvent;

public class DaggeV3 extends TeamRobot {

	private TargetingSystem targetingSystem;
	private MovementSystemV2 movementSystem;
	private PositioningSystem positioningSystem;
	private EnemyTracker enemyTracker;

	/**
	 * Specifies basic configurations for DaggeV2 during the game.
	 * 
	 * Configures radar and gun movement for more efficient while turning.
	 */

	public void run() {
		enemyTracker = new EnemyTracker(this);
		targetingSystem = new TargetingSystem(this);
		positioningSystem = new PositioningSystem(this.getBattleFieldWidth(), this.getBattleFieldHeight());
		movementSystem = new MovementSystemV2(this, enemyTracker, positioningSystem);

		setAdjustRadarForRobotTurn(true);
		setAdjustGunForRobotTurn(true);
		turnRadarRightRadians(Double.POSITIVE_INFINITY);

		while (true) {
			movementSystem.setData(targetingSystem.getAbsBearing(), targetingSystem.getLatVel());
			targetingSystem.track(e);
			movementSystem.move();
			targetingSystem.fire(e);
		}

	}

	/**
	 * Dagge tries to the scanned robot to the enemyTracker
	 * @param e
	 *            - the ScannedRobotEvent
	 */

	public void onScannedRobot(ScannedRobotEvent e) {
		enemyTracker.addEnemy(e);
	}

	/**
	 * Specifies the actions to be made when collision with a wall occurs. Makes
	 * sure DaggeV2 turns around and moves away from wall.
	 * 
	 * @param e
	 *            - the HitWallEvent
	 */

	public void onHitWall(HitWallEvent e) {
		movementSystem.wallHit(e);
	}

	/**
	 * Specifies the actions to be made when collision with another robot
	 * occurs. Makes sure DaggeV2 moves out of melee range.
	 * 
	 * @param e
	 *            - the HitRobotEvent
	 */

	public void onHitRobot(HitRobotEvent e) {
		movementSystem.collision(e);
	}

	/**
	 * Specifies what celebration DaggeV2 does when he wins. Makes a neat dance.
	 * 
	 * @param e
	 *            - the WinEvent
	 */

	public void onWin(WinEvent e) {
		movementSystem.win(e);
	}

	/**
	 * Specifies how DaggeV2 receives colouring specification from a Leader.
	 * 
	 * @param e
	 *            - the MessageEvent
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

}
