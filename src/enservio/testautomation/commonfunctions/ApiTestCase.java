package enservio.testautomation.commonfunctions;

import java.util.Properties;

import org.json.JSONArray;

import enservio.framework.errorhandling.Status;
import enservio.framework.globalfunctions.TestParameters;
import enservio.framework.globalfunctions.pathsetup;
import enservio.framework.reports.Report;
import enservio.framework.testdatafunctions.ExcelDataAccess;

public abstract class ApiTestCase {
	
	protected static int currentIteration;
	protected static String timeStamp;
	protected static String datatablePath;
	protected static Properties properties;
	protected static TestParameters testParameters;
	protected static pathsetup pathSetup;
	protected static Report report;

	protected static ApiTestArtifacts apiTestArtifacts;
	protected static ApiTestDefinition apiTestDefinition;
	
	protected static int iResponseCode;
	protected static String sContentType;
	protected static JSONArray jsonArray;
	
	protected static ApiExpDataDefinition apiExpDataDefinition;

	protected void SetTestArtifacts(ApiTestArtifacts apiTestArtifacts) {
	
		ApiTestCase.apiTestArtifacts = new ApiTestArtifacts();
		ApiTestCase.apiTestArtifacts = apiTestArtifacts;
		
	}
	
	protected void SetTestDefinition(ApiTestDefinition apiTestDefinition) {
	
		ApiTestCase.apiTestDefinition = new ApiTestDefinition();
		ApiTestCase.apiTestDefinition = apiTestDefinition;
		
	}

	protected void SetExpDataDefinition(ApiExpDataDefinition apiExpDataDefinition) {
	
		ApiTestCase.apiExpDataDefinition = new ApiExpDataDefinition();
		ApiTestCase.apiExpDataDefinition = apiExpDataDefinition;
		
	}
	
	protected void SetResponseCode(int sResponseCode) {
		
		ApiTestCase.iResponseCode = sResponseCode;
		
	}
	
	protected void SetContentType(String sContentType) {
		
		ApiTestCase.sContentType = sContentType;
	}
	
	protected void SetJSONArray(JSONArray jsonArray) {
		
		ApiTestCase.jsonArray = jsonArray;
		
	}
	
	protected ExcelDataAccess RetrieveDataObject(String sSheetName) {

		try {

			ExcelDataAccess testData = new ExcelDataAccess(datatablePath,
					testParameters.getCurrentSuite());

			testData.setDatasheetName(sSheetName);

			return testData;

		} catch (Exception e) {

			report.updateTestLog(
					testParameters.getCurrentFunctionality(),
					"Failed in retrieving data from the test data sheet : "
							+ sSheetName + "for testcase (Iteration - "
							+ ApiTestCase.currentIteration + " ) :"
							+ testParameters.getCurrentTestcase(), Status.FAIL);

			return null;

		}

	}
	
	protected String Getthekey(String sValue) {

		try {
						
			int endIndex = sValue.indexOf("_");

			if (endIndex != -1) {

				sValue = sValue.substring(0, endIndex);
							
			}
		
			
			return sValue;
						

		} catch (Exception e) {

			report.updateTestLog(
					testParameters.getCurrentFunctionality(),
					"Failed in getting the data source name (Iteration - "
							+ ApiTestCase.currentIteration + " ) :"
							+ testParameters.getCurrentTestcase(), Status.FAIL);
			return "NONE";
		}

	}
	
 	protected int getTCRecordNo(ExcelDataAccess testInputData) {
 		
 		int iRowSize = testInputData.getLastRowNum();
 		int iIterNo  = 0;

		for (int i = 1; i <= iRowSize; i++) {

			String sTestCaseID = testInputData.getValue(i, "TC_ID");
			
			int iIteration = Integer.parseInt(testInputData.getValue(i,
					"Iteration"));

			if ((sTestCaseID.equalsIgnoreCase(testParameters
					.getCurrentTestcase()))
					&& (iIteration == ApiTestCase.currentIteration)) {
				iIterNo = i;
				break;
			}
			
		}
		
		return iIterNo;
		
 	}
	
}
