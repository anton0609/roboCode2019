package group03_extra;

import robocode.HitWallEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;

public class MyFirstRobot extends Robot {
	public void run() {
        while (true) {
            ahead(300);
            
            turnGunLeft(180);
            turnGunRight(180);

        }
    }
	
	public void onScannedRobot(ScannedRobotEvent e) {
		fire(1);
	}
	
 	public void onHitWall(HitWallEvent e) {
 			turnLeft((90-e.getBearing()));
 			
 	}
}
