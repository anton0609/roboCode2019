package se.lth.cs.etsa02.unitTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.lth.cs.etsa02.daggeV2.TargetingSystem;

public class TargetingSystemTest {
	private MockBot mockBot;
	private TargetingSystem targetingUT;
	private MockScannedRobotEvent mockSRE;
	@Before
	public void setUp(){
		mockBot = new MockBot("DaggeV2", 100, Math.PI, 0, 0);
		targetingUT = new TargetingSystem(mockBot);
		
	}

	@After
	public void tearDown() {
		mockBot = null;
		targetingUT = null;
		mockSRE = null;
	}

	@Test
	public void testFireAtEnemyFarAway() {
		mockSRE = new MockScannedRobotEvent(null, 100, 0, 450, 0, 20, false );
		assertTrue("Fired when the target was to far away",!targetingUT.fire(mockSRE));
	}
	@Test
	public void testFireAtEnemyMidDistance() {
		mockSRE = new MockScannedRobotEvent(null, 100, 0, 250, 0, 20, false );
		assertTrue("Did not fire when supposed to",targetingUT.fire(mockSRE));
	}
	
	@Test
	public void testFireAtEnemyCloseDistance() {
		mockSRE = new MockScannedRobotEvent(null, 100, 0, 100, 0, 20, false );
		assertTrue("Did not fire when supposed to",targetingUT.fire(mockSRE));
	}
	

}
