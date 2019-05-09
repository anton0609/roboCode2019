package se.lth.cs.etsa02.unitTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.lth.cs.etsa02.daggeV2.ScanSystem;

public class ScanSystemTest {
	private ScanSystem scanUT;
	private MockBot mockBot;
	@Before
	public void setUp() {
		mockBot = new MockBot(null, 0, 0, 0, 0);
		scanUT = new ScanSystem(mockBot);
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
