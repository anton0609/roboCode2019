package se.lth.cs.etsa02.unitTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.lth.cs.etsa02.dagge.MovementSystem;
import se.lth.cs.etsa02.dagge.ScanSystem;

public class ScanSystemTest {
	private ScanSystem scanUT;
	private MockBot mockBot;
	private MovementSystem moveSystem;
	@Before
	public void setUp() {
		mockBot = new MockBot(null, 0, 0, 0, 0);
		scanUT = new ScanSystem(mockBot, moveSystem);
	}

	@After
	public void tearDown() {
		mockBot = null;
		scanUT = null;
	}

	@Test
	public void testScanReset() {
		scanUT.reset();
		assertTrue("Target not reset", scanUT.getCurrentTarget()==null );
	}

}
