package se.lth.cs.etsa02.unitTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;
import se.lth.cs.etsa02.daggeV2.MovementSystem;
import se.lth.cs.etsa02.daggeV2.ScanSystem;

public class MovementSystemTest {

	private MovementSystem movementUT;
	private MockBot mockBot;
	private ScanSystem scanSystem;
	private ScannedRobotEvent sre;

	@Before
	public void setUp() {
		mockBot = new MockBot("DaggeV2", 100, Math.PI, 100, 100);
		movementUT = new MovementSystem(mockBot, (double) 1200, (double) 1200);
	}

	@After
	public void tearDown() {
		mockBot = null;
		movementUT = null;
	}

	@Test
	public void testWallHitEventHandling() {
		movementUT.wallHit(new HitWallEvent(0));
		assertTrue("Wrong Movementdirection, exptected 1, got " + movementUT.getMoveDirection(),
				-1 == movementUT.getMoveDirection());

	}

	@Test
	public void testCollision() {
		movementUT.collision(new HitRobotEvent(null, 0, 0, false));
		assertTrue("Wrong Movementdirection, exptected 1, got " + movementUT.getMoveDirection(),
				-1 == movementUT.getMoveDirection());
	}

	@Test
	public void testGetMoveDirection() {
		assertTrue("Wrong Movement direction", movementUT.getMoveDirection() == 1);
	}

	@Test
	public void testGetAndSetData() {
		movementUT.setData(10, 20);
		Double[] data = movementUT.getData();
		assertTrue("wrong absolute bearing data", 10 == data[0]);
		assertTrue("wrong later velocity", 20 == data[1]);
	}

}
