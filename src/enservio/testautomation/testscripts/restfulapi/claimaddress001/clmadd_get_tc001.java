package enservio.testautomation.testscripts.restfulapi.claimaddress001;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import enservio.framework.globalfunctions.*;
import enservio.framework.testflowsetup.*;
import enservio.testautomation.commonfunctions.*;

public class clmadd_get_tc001 extends TestScript {

	public String datatablePath;
	ArrayList<String> aArrInpToken;
	HashMap<String, Object> hEndPoint;
	HashMap<String, Object> hMap;
	
	public clmadd_get_tc001() {

		aArrInpToken = new ArrayList<String>();

		datatablePath = pathSetup.getRelativePath()
				+ utilities.getFileSeparator() + "datarepository"
				+ utilities.getFileSeparator() + sAppType
				+ utilities.getFileSeparator()
				+ testParameters.getCurrentApplication();
				
	}

	public void SetApiToTest () {
			
		if (this.aArrInpToken.size()==0) {
			
			this.aArrInpToken.add("ClaimID");
			this.aArrInpToken.add("AddressID");
		
		}		
	}
	
	public void TestSetup() {
		
		ApiClaimTestFlowSetup apiClaimTest = new ApiClaimTestFlowSetup(testParameters,
							datatablePath, pathSetup, currentIteration, timeStamp, report);

		apiClaimTest.initializeTestArtifacts();
		apiClaimTest.initializeTestDefinition(dataTable);
		apiClaimTest.initializeInputData(aArrInpToken);	
		this.hMap =  apiClaimTest.consolidateInputData(aArrInpToken);
				
	}
	
	public void TestExec() {
		
		ApiClaimTestExecute apiClaimTestExecute = new ApiClaimTestExecute(this.hMap);
		
		apiClaimTestExecute.constructEndPointURL(aArrInpToken);		
		apiClaimTestExecute.PushtoClaimAPI();
		
	}
	
	public void TestVerify () {
		
		ApiClaimTestValidate apiClaimTestValidate = new ApiClaimTestValidate();
		apiClaimTestValidate.saveJsonToFile();
		apiClaimTestValidate.getExpDataDefinition();
		apiClaimTestValidate.verifyData();
		
	}
	
	public void TestCleanUp() {
		
	}
	
	@Override
	protected void executeTestCase() throws Exception {
	
		SetApiToTest();
		
	    TestSetup();
		TestExec();
		TestVerify();
		TestCleanUp();
		
	}
		
	@Test

	public void runTC1() {

		testParameters.setIterationMode(IterationOptions.RunAllIterations);
		driveTestExecution();
	}
	
	
}