package enservio.testautomation.testscripts.webapp.contentexpress.scenario2;

import org.junit.Test;

import enservio.framework.globalfunctions.TestScript;
import enservio.framework.testflowsetup.*;

public class TC15 extends TestScript {

	@Test
	public void runTC15() {
		testParameters.setIterationMode(IterationOptions.RunAllIterations);
		driveTestExecution();
	}

	@Override
	protected void executeTestCase() throws Exception {
		System.out.println("Selenium Demo WEBAPP - TC15");

	}
}