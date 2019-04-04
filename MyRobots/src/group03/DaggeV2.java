package group03;

import java.awt.Color;

import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;
import robocode.WinEvent;

public class DaggeV2 extends TeamRobot {
			
	
			TargetingSystem targetingSystem = new TargetingSystem(this);
			double absBearing;
			double latVel;
			MovementSystem movementSystem = new MovementSystem(this);
			
			public void run() {
				
				setAdjustRadarForRobotTurn(true);			// Gör radar självständig
				setAdjustGunForRobotTurn(true);				// Gör kanonen självständig
				setBodyColor(new Color(153, 0, 0));			// Färger ( fixa så att ledaren sätter detta )
				setGunColor(new Color(51, 51, 51));
				setRadarColor(new Color(255, 0, 0));
				setScanColor(Color.blue);
				setBulletColor(Color.red);					
				
				turnRadarRightRadians(Double.POSITIVE_INFINITY);	// Scanna kontinuerligt
			}
		@Override
			public void onScannedRobot(ScannedRobotEvent e) {	
				movementSystem.setData(targetingSystem.getAbsBearing(), targetingSystem.getLatVel());	// Ge data om fienden till movementSystem
				targetingSystem.track(e);																// Tracka fienden samt sikta
				movementSystem.move(e);																	// Utför akuell rörelse
				targetingSystem.fire(e);																// Skjut
				
			}
			public void onHitWall(HitWallEvent e){														// Undvik väggar
				movementSystem.wallHit(e);
		}
		
			public void onHitRobot(HitRobotEvent e) { 													// Undvik gridlock
				movementSystem.collision(e);
		}
			public void onWin(WinEvent e) {																// Dansa vid vinst
				movementSystem.win(e);
	}
}
