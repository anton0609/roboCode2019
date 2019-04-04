
package group03;

import java.awt.geom.Point2D;

import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;
import robocode.WinEvent;
import robocode.util.Utils;

public class MovementSystem {
		private double absBearing;
		private double latVel;
		private TeamRobot dagge;
		private double moveDirection = 1;
		long timeStamp;
		long timeLast;
		
		public MovementSystem(TeamRobot dagge) {
			this.dagge = dagge;
		}
		
		public void setData(double absBearing, double latVel) {
			this.absBearing = absBearing;
			this.latVel = latVel;
		}
		public void move(ScannedRobotEvent e) {
			
		if(Math.random()>.9){
				dagge.setMaxVelocity((12*Math.random())+24);																	 				 //randomly change speed
		}
			
		if(e.getDistance() > 150) {

			dagge.setTurnRightRadians(robocode.util.Utils.normalRelativeAngle(absBearing-dagge.getHeadingRadians()+latVel/dagge.getVelocity())); //drive towards the enemies predicted future location
			dagge.setAhead((e.getDistance() - 140)*(moveDirection/Math.abs(moveDirection))); 																				 //move forward
		} else {
			dagge.setTurnLeft(-90-e.getBearing()); 																								 //turn perpendicular to the enemy
			dagge.setAhead(Math.max((e.getDistance() - 140), 20)*(moveDirection/Math.abs(moveDirection)));															     //move forward
		}
			moveDirection ++;
			
	}
		public void collision(HitRobotEvent e) {																								 // Avoid collision
			moveDirection = -5;
		}
		public void wallHit(HitWallEvent e) {																									 // Avoid walls	
			moveDirection = -5;

	}
		public void win(WinEvent e) {																											 // Dance
			for (int i = 0; i < 50; i++) {
				dagge.turnRight(30);
				dagge.turnLeft(30);
		}
	}
}
