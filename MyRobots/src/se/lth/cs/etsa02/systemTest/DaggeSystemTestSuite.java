package se.lth.cs.etsa02.systemTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AntiCollision_STS.class, DodgeBullets_STS.class, GoodAimEasy_STS.class, GoodAimHard_STS.class, })
public class DaggeSystemTestSuite {

}
