package test.integration;

import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import de.metas.adempiere.ait.test.IntegrationTestSuite;

@RunWith(IntegrationTestSuite.class)
@SuiteClasses({
		// Tests within ad_it proper
		test.integration.mf15.system.SysConfigTests.class,
		
		// Dependencies:
		test.integration.swat.AllTests.class,
		test.integration.contracts.AllTests.class
})
public class AllTests
{
}
