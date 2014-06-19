package enservio.testautomation.testscripts.restfulapi.claimaddress002;

import org.junit.Test;

import enservio.framework.globalfunctions.*;
import enservio.framework.testflowsetup.*;

public class clmadd_put_tc001 extends TestScript {

	@Test
	public void runTC1() {

		testParameters.setIterationMode(IterationOptions.RunAllIterations);
		driveTestExecution();
	}

	@Override
	protected void executeTestCase() throws Exception {
		System.out.println("Selenium Demo RESTAPI- CLMADD_PUT_TC001");

	}
}