package se.lth.cs.etsa02.systemTest;

import static org.junit.Assert.assertTrue;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.RoundEndedEvent;
import robocode.control.events.RoundStartedEvent;
import robocode.control.events.TurnEndedEvent;
import robocode.control.snapshot.IRobotSnapshot;
import se.lth.cs.etsa02.robocode.control.testing.RobotTestBed;


@RunWith(JUnit4.class)
public class AntiCollision_STS extends RobotTestBed {

	// constants used to configure this system test case
		private String ROBOT_UNDER_TEST = "se.lth.cs.etsa02.daggeV2.DaggeV2";
		private String ENEMY_ROBOTS = "sample.Crazy";
		private int NBR_ROUNDS = 100;
		private double collisions = 0;// number of "collisions" as defined by being arbitrarily close to enemies or walls
		private double turns = 0;
		private int SIZE_X = 1200;
		private int SIZE_Y = 1200;
		
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
			return null;
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
			System.out.println(collisions/turns);
			assertTrue("Not good enough avoidance of robots " + collisions/turns, collisions/turns < 0.03);
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
			IRobotSnapshot robot = event.getTurnSnapshot().getRobots()[0];
			if(robot.getState().isHitRobot() || robot.getState().isHitWall()) {
				collisions++;
			}
			turns++;
		}

}
