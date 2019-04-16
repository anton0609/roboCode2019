package SysTestGr3;

import static org.junit.Assert.assertTrue;


import java.util.HashSet;

import java.util.Set;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.RoundEndedEvent;
import robocode.control.events.RoundStartedEvent;
import robocode.control.events.TurnEndedEvent;
import robocode.control.snapshot.IBulletSnapshot;
import robocode.control.snapshot.IRobotSnapshot;
import robocode.control.testing.RobotTestBed;


@RunWith(JUnit4.class)
public class DodgeBullets_STS extends RobotTestBed {

	// constants used to configure this system test case
		private String ROBOT_UNDER_TEST = "group03.daggeV2.DaggeV2";
		private String ENEMY_ROBOTS = "sample.TrackFire";
		private int NBR_ROUNDS = 100;
		private int nbrBullets = 0;
		private int bulletHits = 0;
		private Set<Integer> bulletIDs = new HashSet<Integer>();
		private double THRESHOLD = 0;
		private String roboName;
		
		
		/**
		 * The names of the robots that want battling is specified.
		 * 
		 * @return The names of the robots we want battling.
		 */
		@Override
		public String getRobotNames() {
			return ROBOT_UNDER_TEST + "," + ENEMY_ROBOTS;
		}

		/**
		 * Pick the amount of rounds that we want our robots to battle for.
		 *
		 * @return Amount of rounds we want to battle for.
		 */
		@Override
		public int getNumRounds() {
			return NBR_ROUNDS;
		}

		/**
		 * Returns a comma or space separated list like: x1,y1,heading1,
		 * x2,y2,heading2, which are the coordinates and heading of robot #1 and #2.
		 * So "(0,0,180), (50,80,270)" means that robot #1 has position (0,0) and
		 * heading 180, and robot #2 has position (50,80) and heading 270.
		 * 
		 * Override this method to explicitly specify the initial positions for your
		 * test cases.
		 * 
		 * Defaults to null, which means that the initial positions are determined
		 * randomly. Since battles are deterministic by default, the initial
		 * positions are randomly chosen but will always be the same each time you
		 * run the test case.
		 * 
		 * @return The list of initial positions.
		 */
		@Override
		public String getInitialPositions() {
			return "(100,100,180), (200,100,270)";
		}

		/**
		 * Returns true if the battle should be deterministic and thus robots will
		 * always start in the same position each time.
		 * 
		 * Override to return false to support random initialization.
		 * 
		 * @return True if the battle will be deterministic.
		 */
		@Override
		public boolean isDeterministic() {
			return true;
		}

		/**
		 * Specifies how many errors you expect this battle to generate. Defaults to
		 * 0. Override this method to change the number of expected errors.
		 * 
		 * @return The expected number of errors.
		 */
		@Override
		protected int getExpectedErrors() {
			return errors;
		}

		/**
		 * Invoked before the test battle begins. Default behavior is to do nothing.
		 * Override this method in your test case to add behavior before the battle
		 * starts.
		 */
		@Override
		protected void runSetup() {
		}

		/**
		 * Invoked after the test battle ends. Default behavior is to do nothing.
		 * Override this method in your test case to add behavior after the battle
		 * ends.
		 */
		@Override
		protected void runTeardown() {
		}
		
		/**
		 * Called after the battle. Provided here to show that you could use this
		 * method as part of your testing.
		 * 
		 * @param event
		 *            Holds information about the battle has been completed.
		 */
		@Override
		public void onBattleCompleted(BattleCompletedEvent event) {
			assertTrue("Not good enough avoidance of bullets " + 100*bulletHits/nbrBullets + "% " + nbrBullets + " " + bulletHits + " " + roboName, 100*bulletHits/nbrBullets < THRESHOLD);
		}
		
		/**
		 * Called before each round. Provided here to show that you could use this
		 * method as part of your testing.
		 * 
		 * @param event
		 *            The RoundStartedEvent.
		 */
		@Override
		public void onRoundStarted(RoundStartedEvent event) {
		}
		
		/**
		 * Called after each round. Provided here to show that you could use this
		 * method as part of your testing.
		 * 
		 * @param event
		 *            The RoundEndedEvent.
		 */
		@Override
		public void onRoundEnded(RoundEndedEvent event) {
		}
		
		/**
		 * Called after each turn. Provided here to show that you could use this
		 * method as part of your testing.
		 * 
		 * @param event
		 *            The TurnEndedEvent.
		 */
		@Override
		public void onTurnEnded(TurnEndedEvent event) {
			IBulletSnapshot[] bullets = event.getTurnSnapshot().getBullets();
			IRobotSnapshot robot = event.getTurnSnapshot().getRobots()[1];
			roboName = robot.getName();
			
			int index = robot.getRobotIndex();
			for(IBulletSnapshot i : bullets) {
				
				bulletIDs.add(i.getBulletId());
				
				if(robot.getState().isHitRobot()) {
					bulletHits++;
				}
			}
			nbrBullets = bulletIDs.size();
			}
		
		}


