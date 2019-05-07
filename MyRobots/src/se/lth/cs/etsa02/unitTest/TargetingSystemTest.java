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
	public void setUp() throws Exception {
		mockBot = new MockBot("DaggeV2", 100, Math.PI, 100, 100);
		targetingUT = new TargetingSystem(mockBot);
		mockSRE = new MockScannedRobotEvent("enemy", 100, 0, 100, 0, 20, false );
	}

	@After
	public void tearDown() throws Exception {
		mockBot = null;
		targetingUT = null;
		mockSRE = null;
	}

	@Test
	public void testAbsBearing() {
		targetingUT.track(mockSRE);
		double calculatedAbsBearing = mockSRE.getBearingRadians() + mockBot.getHeadingRadians();
		assertTrue("absBearing is not correct "+ targetingUT.getAbsBearing(), calculatedAbsBearing==targetingUT.getAbsBearing() );
	}

}
