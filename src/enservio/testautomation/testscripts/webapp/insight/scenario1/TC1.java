package enservio.testautomation.testscripts.webapp.insight.scenario1;

import org.junit.Test;

import enservio.framework.globalfunctions.TestScript;
import enservio.framework.testflowsetup.*;


public class TC1 extends TestScript {

	@Test
	public void runTC1() {
		
		testParameters.setIterationMode(IterationOptions.RunAllIterations);
		driveTestExecution();
		
	}

	@Override
	protected void executeTestCase() throws Exception {
		
		System.out.println("Selenium Demo WEBAPP - TC1");
		
	}
}