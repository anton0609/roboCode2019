package group03;

import robocode.*;

import java.awt.*;

public class DaggeV1 extends TeamRobot {


	/**
	 * SuperTracker - a Super Sample Robot by CrazyBassoonist based on the robot Tracker by Mathew Nelson and maintained by Flemming N. Larsen
	 * <p/>
	 * Locks onto a robot, moves close, fires when close.
	 */
		int moveDirection=1;
		
		//which way to move
		/**
		 * run:  Tracker's main run function
		 */
		public void run() {
			setAdjustRadarForRobotTurn(true);//keep the radar still while we turn
			setBodyColor(new Color(153, 0, 0));
			setGunColor(new Color(51, 51, 51));
			setRadarColor(new Color(255, 0, 0));
			setScanColor(Color.white);
			setBulletColor(Color.red);
			setAdjustGunForRobotTurn(true); // Keep the gun still when we turn
			turnRadarRightRadians(Double.POSITIVE_INFINITY);//keep turning radar right
		}
	 
		/**
		 * onScannedRobot:  Here's the good stuff
		 */
		public void onScannedRobot(ScannedRobotEvent e) {
			if(isTeammate(e.getName() + " " + getBotNumber(e.getName()))) {
				return;
			}
			double absBearing=e.getBearingRadians()+getHeadingRadians();//enemies absolute bearing
			double latVel=e.getVelocity() * Math.sin(e.getHeadingRadians() -absBearing);//enemies later velocity
			double gunTurnAmt;//amount to turn our gun
			setTurnRadarLeftRadians(getRadarTurnRemainingRadians());//lock on the radar
			if(Math.random()>.9){
				setMaxVelocity((12*Math.random())+12);//randomly change speed
			}
			if (e.getDistance() > 150) {//if distance is greater than 150
				gunTurnAmt = robocode.util.Utils.normalRelativeAngle(absBearing- getGunHeadingRadians()+latVel/13);//amount to turn our gun, lead just a little bit
				setTurnGunRightRadians(gunTurnAmt); //turn our gun
				setTurnRightRadians(robocode.util.Utils.normalRelativeAngle(absBearing-getHeadingRadians()+latVel/getVelocity()));//drive towards the enemies predicted future location
				setAhead((e.getDistance() - 140)*moveDirection);//move forward
				setFire(Math.min(400 / e.getDistance(), 3)); //fire
			}
			else{//if we are close enough...
				gunTurnAmt = robocode.util.Utils.normalRelativeAngle(absBearing- getGunHeadingRadians()+latVel/8);//amount to turn our gun, lead just a little bit
				setTurnGunRightRadians(gunTurnAmt);//turn our gun
				setTurnLeft(-90-e.getBearing()); //turn perpendicular to the enemy
				setAhead(Math.max((e.getDistance() - 140), 5)*moveDirection);//move forward
				setFire(Math.min(400 / e.getDistance(), 3));//fire
			}	
		}
	 
		public void onHitWall(HitWallEvent e){
			moveDirection=-moveDirection;//reverse direction upon hitting a wall
		}
	 
		/**
		 * onWin:  Do a victory dance
		 */
		public void onWin(WinEvent e) {
			for (int i = 0; i < 50; i++) {
				turnRight(30);
				turnLeft(30);
			}
		}
		static public int getBotNumber(String name) {
			 	String n = "0";
			 	int low = name.indexOf("(")+1; int hi = name.lastIndexOf(")");
			 	if (low >= 0 && hi >=0) { n = name.substring(low, hi); }
			 	return Integer.parseInt(n);
			
		}
	}
