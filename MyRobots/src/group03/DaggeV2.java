package group03;

import java.awt.Color;

import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.MessageEvent;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;
import robocode.WinEvent;

public class DaggeV2 extends TeamRobot {

	private TargetingSystem targetingSystem = new TargetingSystem(this);
	private MovementSystem movementSystem = new MovementSystem(this);
	
	/**
	 * Specifies basic configurations for DaggeV2 during the game.
	 * 
	 * Configures radar and gun movement for more efficient while turning.
	 */
	
	public void run() {

		setAdjustRadarForRobotTurn(true); 
		setAdjustGunForRobotTurn(true); 
		turnRadarRightRadians(Double.POSITIVE_INFINITY); 
	}

	/**
	 * Calls upon the set of actions involved with tracking, targeting, moving and shooting.
	 * 
	 * Makes sure DaggeV2 lock onto a target, then moves into optimal distance, 
	 * then circle strafes around its target while firing continuously with adaptive power.
	 * Also involves countermeasures for predicted incoming fire.
	 */
	
	public void onScannedRobot(ScannedRobotEvent e) {
		movementSystem.setData(targetingSystem.getAbsBearing(), targetingSystem.getLatVel()); 																	
		targetingSystem.track(e); 
		movementSystem.move(e); 
		targetingSystem.fire(e); 
	}
	
	/**
	 * Specifies the actions to be made when collision with a wall occurs.
	 * Makes sure DaggeV2 turns around and moves away from wall.
	 */
	
	public void onHitWall(HitWallEvent e) { 
		movementSystem.wallHit(e);
	}

	/**
	 * Specifies the actions to be made when collision with another robot occurs.
	 * Makes sure DaggeV2 moves out of melee range.
	 */
	
	public void onHitRobot(HitRobotEvent e) { 
		movementSystem.collision(e);
	}

	/**
	 * Specifies what celebration DaggeV2 does when he wins.
	 * Makes a neat dance.
	 */
	
	public void onWin(WinEvent e) { 
		movementSystem.win(e);	
	}
	
	/**
	 * Specifies how DaggeV2 receives colouring specification from a Leader.
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
