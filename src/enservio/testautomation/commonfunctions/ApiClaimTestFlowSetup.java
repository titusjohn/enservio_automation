package enservio.testautomation.commonfunctions;

import java.io.File;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.HashMap;

import enservio.framework.errorhandling.Status;
import enservio.framework.globalfunctions.DataTable;
import enservio.framework.globalfunctions.TestParameters;
import enservio.framework.globalfunctions.pathsetup;
import enservio.framework.globalfunctions.settings;
import enservio.framework.globalfunctions.utilities;
import enservio.framework.reports.Report;
import enservio.framework.testdatafunctions.ExcelDataAccess;

public class ApiClaimTestFlowSetup extends ApiTestCase implements ApiTestFlowSetup {
	
	private ApiInputClaimData apiInputClaimData;
	HashMap<String, Object> hMap = new HashMap<String, Object>();
	
	public ApiClaimTestFlowSetup(TestParameters testParameters,
			String datatablePath, pathsetup pathSetup, int currentIteration,
			String timeStamp, Report report) {

		ApiTestCase.testParameters = testParameters;
		ApiTestCase.datatablePath = datatablePath;
		ApiTestCase.currentIteration = currentIteration;
		ApiTestCase.timeStamp = timeStamp;
		ApiTestCase.properties = settings.getInstance();
		ApiTestCase.pathSetup = pathSetup;
		ApiTestCase.report = report;
		
	}

	public void initializeTestArtifacts() {

		try {
			
			apiTestArtifacts = new ApiTestArtifacts();

			apiTestArtifacts.SetApiTestSheetPath(datatablePath);

			String sActPath = pathSetup.getRelativePath()
					+ utilities.getFileSeparator() + "Results"
					+ utilities.getFileSeparator() + timeStamp
					+ utilities.getFileSeparator() + "Datatables"
					+ utilities.getFileSeparator() + "API-Responses";

			new File(sActPath).mkdir();

			apiTestArtifacts.SetApiActRespPath(sActPath);

			String sActFileName = "Act-Resp_"
					+ testParameters.getCurrentTestcase().toLowerCase() + "_iter-"
					+ currentIteration + ".json";
			// +"_"+ utilities
			// .getCurrentFormattedTime(
			// properties.getProperty("DateFormatString"))
			// .replace(" ", "_").replace(":", "-");

			apiTestArtifacts.SetApiActRespFileName(sActFileName);
			
			String sExpPath = pathSetup.getRelativePath()
					+ utilities.getFileSeparator() + "datarepository"
					+ utilities.getFileSeparator() + properties.getProperty("RunConfiguration")
					+ utilities.getFileSeparator() + testParameters.getCurrentApplication()
					+ utilities.getFileSeparator() + "Exp-Response";
					
								
			apiTestArtifacts.SetApiExpRespPath(sExpPath);
			
			String sExpFileName = "Exp-Resp_"
					+ testParameters.getCurrentTestcase().toLowerCase() + "_iter-"
					+ currentIteration + ".json";
			
			apiTestArtifacts.SetApiExpRespFileName(sExpFileName);

			report.updateTestLog("Test-Artifacts",
					"Gathered all test artifacts required for the testcase",
					Status.DONE);

		} catch (Exception e) {

			report.updateTestLog(
					testParameters.getCurrentFunctionality(),
					"Failed in initializing test artifacts for the testcase (Iteration - "
							+ ApiTestCase.currentIteration + " ) :"
							+ testParameters.getCurrentTestcase(), Status.FAIL);
		}
		
		this.SetTestArtifacts(apiTestArtifacts);
			

	}

	public void initializeTestDefinition(DataTable dataTable) {

		try {

			String sSheetName = properties.getProperty("DefaultDataSheet");
			apiTestDefinition = new ApiTestDefinition();

			ExcelDataAccess testInputData = RetrieveDataObject(sSheetName);
			
			int iVal = getTCRecordNo(testInputData);
			
			apiTestDefinition.SetTestCaseID(testInputData.getValue(iVal,
					"TC_ID"));
			apiTestDefinition.SetIteration(Integer
					.parseInt(testInputData.getValue(iVal, "Iteration")));
			apiTestDefinition.SetToken(dataTable.getData(sSheetName,
					"Token"));
			apiTestDefinition.SetDataToken(dataTable.getData(
					sSheetName, "DataToken"));
			apiTestDefinition.SetSecurityToken(dataTable.getData(
					sSheetName, "SecurityToken"));
			apiTestDefinition.SetAppURL(dataTable.getData(sSheetName,
					"App_URL"));
			apiTestDefinition.SetUserName(dataTable.getData(sSheetName,
					"UserName"));
			apiTestDefinition.SetPassword(dataTable.getData(sSheetName,
					"Password"));
			apiTestDefinition.SetURLParamData(testInputData.getValue(iVal,
					"URLParam"));
			apiTestDefinition.SetJSONData(testInputData.getValue(iVal,
					"JsonData"));
			apiTestDefinition.SetCompositeAPI(testInputData.getValue(iVal,
					"CompositeAPI"));
			
			report.updateTestLog(
					"Test Definitions",
					"Initialized all test definitions required to run the testcase",
					Status.DONE);

		} catch (Exception e) {

			report.updateTestLog(
					testParameters.getCurrentFunctionality(),
					"Failed in initializing test definitions for the testcase (Iteration - "
							+ ApiTestCase.currentIteration + " ) :"
							+ testParameters.getCurrentTestcase(), Status.FAIL);
		}
		
		this.SetTestDefinition(apiTestDefinition);
		
	}

	public void initializeInputData(ArrayList<String> aArrInpToken) {

		try {

			apiInputClaimData = new ApiInputClaimData();
			String sSheetName = apiTestArtifacts.GetApiShtInputData();

			ExcelDataAccess testInputData = RetrieveDataObject(sSheetName);

			int iVal = getTCRecordNo(testInputData);
				
			apiInputClaimData.SetTestcaseID(testInputData.getValue(iVal,
					"TC_ID"));
			apiInputClaimData.SetIteration(Integer
					.parseInt(testInputData.getValue(iVal, "Iteration")));
			apiInputClaimData
					.SetSubIteration(Integer.parseInt(testInputData
							.getValue(iVal, "SubIteration")));

			SetClaimURLParams(testInputData, iVal, aArrInpToken);

			report.updateTestLog(
					"Input Data",
					"Gathered the test data sources (DB, DataSheet, JSON files etc) for executing the testcase",
					Status.DONE);

		} catch (Exception e) {

			report.updateTestLog(
					testParameters.getCurrentFunctionality(),
					"Failed in initializing test input data for the testcase (Iteration - "
							+ ApiTestCase.currentIteration + " ) :"
							+ testParameters.getCurrentTestcase(), Status.FAIL);
		}
	

	}

	public HashMap<String, Object> consolidateInputData(ArrayList<String> aArrInpToken) {

		try {

			for (String sClm : aArrInpToken) {

				String sClmMethod = "Get" + sClm;

				Method ClmMeth = apiInputClaimData.getClass().getMethod(
						sClmMethod);
				ClmMeth.invoke(apiInputClaimData);
				hMap.put(sClm, ClmMeth.invoke(apiInputClaimData));

			}

			hMap = mapInpTestData(hMap, aArrInpToken);

			report.updateTestLog(
					"Input Parameters",
					"Consolidated input parameters for the testcase : "+aArrInpToken,
					Status.DONE);

		} catch (Exception e) {

			report.updateTestLog(
					testParameters.getCurrentFunctionality(),
					"Failed in creating getmethod dynamically (Iteration - "
							+ ApiTestCase.currentIteration + " ) :"
							+ testParameters.getCurrentTestcase(), Status.FAIL);
		}
		
		
		return hMap;
		
	}

	public HashMap<String, Object> mapInpTestData(HashMap<String, Object> hMap,
			ArrayList<String> aArrInpToken) {

		HashMap<String, Object> hQmap = new HashMap<String, Object>();

		for (String sClm : aArrInpToken) {

			String sValue = (String) hMap.get(sClm);

			switch (Getthekey(sValue)) {

			case "QUERY":

				if (hQmap.size() == 0) {

					hQmap = getInpQueryData(aArrInpToken, sValue);

				}

				hMap.put(sClm, hQmap.get(sClm));

				break;

			case "DATA":

				String sData = getInpTestData(sValue);

				hMap.put(sClm, sData);

				break;

			case "NONE":

				hMap.put(sClm, "NONE");

				break;

			default:

				hMap.put(sClm, "");

				break;

			}

		}

		return hMap;

	}

	public HashMap<String, Object> getInpQueryData(
			ArrayList<String> aArrInpToken, String sValue) {

		HashMap<String, Object> hQMap = new HashMap<String, Object>();

		int i = 0;

		ArrayList<String> arrValue = getClmQueryResults(sValue, aArrInpToken);

		for (String sClm : aArrInpToken) {

			hQMap.put(sClm, arrValue.get(i));
			i++;

		}

		return hQMap;

	}

	public String getInpTestData(String sValue) {

		String sData = "";
		ExcelDataAccess testInpLkData = RetrieveDataObject(apiTestArtifacts.sApiShtInpDataLkup);

		int iRowSize = testInpLkData.getLastRowNum();

		for (int i = 1; i <= iRowSize; i++) {

			if (testInpLkData.getValue(i, "DATA_ID").equals(sValue)) {

				sData = testInpLkData.getValue(i, "DATA");

				break;

			}

		}

		return sData;
	}

	public HashMap<String, Object> getInpNoneData(HashMap<String, Object> hMap) {

		return hMap;
	}

	public void SetClaimURLParams(ExcelDataAccess testInputData, int iTr,
			ArrayList<String> aArrInpToken) {

		try {

			for (String sClm : aArrInpToken) {

				switch (sClm) {

				case "ClaimID":

					apiInputClaimData.SetClaimID(testInputData.getValue(iTr,
							sClm));
					break;

				case "AddressID":

					apiInputClaimData.SetAddressID(testInputData.getValue(iTr,
							sClm));
					break;

				case "BuildingID":

					apiInputClaimData.SetBuildingID(testInputData.getValue(iTr,
							sClm));
					break;

				case "EmailAddress":

					apiInputClaimData.SetEmailAddress(testInputData.getValue(
							iTr, sClm));
					break;

				case "FormID":

					apiInputClaimData.SetFormID(testInputData.getValue(iTr,
							sClm));
					break;

				case "LimitID":

					apiInputClaimData.SetLimitID(testInputData.getValue(iTr,
							sClm));
					break;

				case "PhoneID":

					apiInputClaimData.SetPhoneID(testInputData.getValue(iTr,
							sClm));
					break;

				case "RoomID":

					apiInputClaimData.SetRoomID(testInputData.getValue(iTr,
							sClm));
					break;

				default:

					report.updateTestLog(
							testParameters.getCurrentFunctionality(),
							"Invalid URL Parameter mentioned, please verify!",
							Status.DEBUG);
					break;

				}

			}

		} catch (Exception e) {

			report.updateTestLog(
					testParameters.getCurrentFunctionality(),
					"Failed assigning URL parameters for testcase (Iteration - "
							+ ApiTestCase.currentIteration + " ) :"
							+ testParameters.getCurrentTestcase(), Status.FAIL);

		}

	}

	public ArrayList<String> getClmQueryResults(String sVal,
			ArrayList<String> aArrInpToken) {

		ArrayList<String> aArrInpQVal = new ArrayList<String>();
		String sQuery = "";

		ExcelDataAccess testQueryData = RetrieveDataObject(apiTestArtifacts.sApiShtInpQueryLkup);

		int iRowSize = testQueryData.getLastRowNum();

		for (int i = 1; i <= iRowSize; i++) {

			if (testQueryData.getValue(i, "QUERY_ID").equals(sVal)) {

				sQuery = testQueryData.getValue(i, "QUERY");

				break;

			}

		}

		System.out.println(sQuery);

		// aArrInpQVal = ExecuteQuery(sQuery);

		aArrInpQVal.add("Claim - Query");
		aArrInpQVal.add("Address - Query");

		return aArrInpQVal;

	}

}
