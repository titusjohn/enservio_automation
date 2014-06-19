package enservio.testautomation.commonfunctions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.*;

import enservio.framework.errorhandling.Status;
import enservio.framework.globalfunctions.utilities;
import enservio.framework.testdatafunctions.ExcelDataAccess;

public class ApiClaimTestValidate extends ApiTestCase {

	protected ApiExpClaimData apiExpClaimData = new ApiExpClaimData();
	private ArrayList<HashMap<String, Object>> arrHmap = new ArrayList<HashMap<String, Object>>();

	public void saveJsonToFile() {

		try {

			if ((ApiTestCase.jsonArray != null) && (ApiTestCase.jsonArray.length()>0)) {

				String sFileName = ApiTestCase.apiTestArtifacts
						.GetApiActRespPath()
						+ utilities.getFileSeparator()
						+ ApiTestCase.apiTestArtifacts.GetApiActResFileName();

				FileWriter file = new FileWriter(sFileName);

				ObjectMapper mapper = new ObjectMapper();
				mapper.enable(SerializationFeature.INDENT_OUTPUT);
				mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
				Object json = mapper.readValue(
						ApiTestCase.jsonArray.toString(), Object.class);

				file.write(mapper.writerWithDefaultPrettyPrinter()
						.writeValueAsString(json));

				ApiTestCase.report.updateTestLog(
						"Response Message",
						"Saved the response JSON file got from API '<b>"
								+ ApiTestCase.apiTestArtifacts
										.GetApiExpResFileName()
								+ "</b>' in results folder", Status.DONE);

				file.flush();
				file.close();

			} else {

				ApiTestCase.report.updateTestLog("Response Message",
						"No JSON file was created for this testcase",
						Status.DONE);
			}

		} catch (IOException e) {

			ApiTestCase.report
					.updateTestLog(
							"Response Message",
							"Error occurred while copying the JSON file to Results repository ",
							Status.FAIL);

		}

	}

	public void verifyResponseCode() {

		int iExpRespCode = ApiTestCase.apiExpDataDefinition.GetResponseCode();
		int iActRespCode = ApiTestCase.iResponseCode;

		if (iExpRespCode == iActRespCode) {

			ApiTestCase.report.updateTestLog("Response Code",
					"Expected Response Code : '<b>" + iExpRespCode
							+ "</b>' & Actual Response Code : '<b>"
							+ iActRespCode + "</b>' MATCH", Status.DONE);

		} else {

			ApiTestCase.report.updateTestLog("Response Code",
					"Expected Response Code : '<b>" + iExpRespCode
							+ "</b>' & Actual Response Code : '<b>"
							+ iActRespCode + "</b>' DOES NOT MATCH",
					Status.FAIL);
		}

	}

	public boolean toValidateResponseFile() {

		if (ApiTestCase.apiExpDataDefinition.GetValidateJSON().toUpperCase()
				.equals("YES")) {

			ApiTestCase.report.updateTestLog("JSON File",
					"JSON file values WILL be validated for this testcase",
					Status.DONE);

			return true;

		} else {

			ApiTestCase.report
					.updateTestLog(
							"JSON File",
							"No JSON response file will be generated for this testcase",
							Status.PASS);

			return false;
		}

	}

	public void isJSONFileValid() {

		String sExpContentType = ApiTestCase.apiExpDataDefinition
				.GetContentType();
		String sActContentType = ApiTestCase.sContentType;

		if (sExpContentType.equals(sActContentType)) {

			ApiTestCase.report.updateTestLog("JSON Content Type",
					"Expected Content Type : '<b>" + sExpContentType
							+ "</b>' & Actual Content Type : '<b>"
							+ sActContentType + "</b>' MATCH", Status.DONE);

		} else {

			ApiTestCase.report.updateTestLog("JSON Content Type",
					"Expected Content Type : '<b>" + sExpContentType
							+ "</b>' & Actual Content Type : '<b>"
							+ sActContentType + "</b>' DOES NOT MATCH",
					Status.FAIL);
		}

	}

	public void groupValiddata() {

		try {
		HashMap<String, Object> hClaimMap = apiExpClaimData.GetClaimAddData();
		HashMap<String, Object> hClaimYESVal = new HashMap<String, Object>();
		HashMap<String, Object> hClaimNOVal = new HashMap<String, Object>();

		Iterator<String> keyIterator = hClaimMap.keySet().iterator();

		while (keyIterator.hasNext()) {

			String sKey = keyIterator.next();

			if (hClaimMap.get(sKey).toString().trim().toUpperCase()
					.equals("YES")) {

				hClaimYESVal.put(sKey, "YES");

			} else {

				hClaimNOVal.put(sKey, "NO");

			}

		}

		arrHmap.add(hClaimYESVal);
		arrHmap.add(hClaimNOVal);
		} catch (Exception e) {
			
			e.printStackTrace();
			report.updateTestLog("Group Data", "Error occured while grouping required data to be considered for validation for "
					+ testParameters.getCurrentFunctionality() + " : '"
					+ testParameters.getCurrentApplication().toUpperCase()
					+ "' API", Status.FAIL);
		}

	}

	public boolean isJSONDataMassage() {

		groupValiddata();

		HashMap<String, Object> hYesMap = arrHmap.get(0);
		HashMap<String, Object> hNoMap = arrHmap.get(1);

		if ((hNoMap.size() > 0) && (hYesMap.size()>0)) {

			return true;

		} else {

			return false;
		}
		

	}

	public String saveMassageFile(String sRespValPath,String sFileName, JSONArray jArrNewobj) {
		
		String sPath = sRespValPath + utilities.getFileSeparator() + sFileName;
		
		try {
		
		FileWriter file = new FileWriter(sPath);
        
        ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		Object jsObj = mapper.readValue(jArrNewobj.toString(), Object.class);	
											
		file.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsObj));
        
		file.close();
		
		} catch (IOException e) {
			
			e.printStackTrace();
			report.updateTestLog("Saving JSON File", "Error occured while saving the JSON file with required data to be validated for "
					+ testParameters.getCurrentFunctionality() + " : '"
					+ testParameters.getCurrentApplication().toUpperCase()
					+ "' API", Status.FAIL);
			
		}	
	
		return sPath;
		
	}

	public JSONArray getTheMassagedJSONObj(HashMap<String, Object> hNoMap, String sFile ) {
		
		JSONArray jArrNewobj = new JSONArray();

		try {

			BufferedReader in = new BufferedReader(new FileReader(sFile) );
            StringBuilder responseStrBuilder = new StringBuilder();

		    String inputStr;
	
            while ((inputStr = in.readLine()) != null) {
            	
            	responseStrBuilder.append(inputStr);
             }
 
            in.close();
     
			JSONArray jArrobj = new JSONArray(responseStrBuilder.toString());
	
			for (int i = 0; i < jArrobj.length(); i++) {

				JSONObject jsonobj = (JSONObject) jArrobj.get(i);

				Iterator<String> keyIterator = hNoMap.keySet().iterator();

				while (keyIterator.hasNext()) {

					String sKey = keyIterator.next();
					jsonobj.remove(sKey);

				}

				jArrNewobj.put(jsonobj);
			}

			
		} catch (IOException e) {
			
			e.printStackTrace();
			report.updateTestLog("JSON Object", "IO exception error occured while creating JSON Object with required validation data for "
					+ testParameters.getCurrentFunctionality() + " : '"
					+ testParameters.getCurrentApplication().toUpperCase()
					+ "' API", Status.FAIL);
			
		}catch (JSONException e) {

			e.printStackTrace();
			report.updateTestLog("JSON Object", "JSON exception error occured while creating JSON Object with required validation data for "
					+ testParameters.getCurrentFunctionality() + " : '"
					+ testParameters.getCurrentApplication().toUpperCase()
					+ "' API", Status.FAIL);
	
		}

		return jArrNewobj;
	}
		
	public ArrayList<String> massageJSONData(String sExpFile, String sActFile) {
				
		ArrayList<String> arrPath= new ArrayList<String>(); 

		try {
		
		HashMap<String, Object> hNoMap = arrHmap.get(1);

		String sRespValPath = pathSetup.getRelativePath()
				+ utilities.getFileSeparator() + "Results"
				+ utilities.getFileSeparator() + timeStamp
				+ utilities.getFileSeparator() + "Datatables"
				+ utilities.getFileSeparator() + "API-RespValidation";

		String sExpFName = ApiTestCase.apiTestArtifacts.GetApiExpResFileName();
		String sActFName = ApiTestCase.apiTestArtifacts.GetApiActResFileName();
		
		new File(sRespValPath).mkdir();

		JSONArray jArrNewActobj = getTheMassagedJSONObj(hNoMap,sActFile);
		JSONArray jArrNewExpobj = getTheMassagedJSONObj(hNoMap,sExpFile);
	
		arrPath.add(saveMassageFile(sRespValPath,sActFName,jArrNewActobj));
		arrPath.add(saveMassageFile(sRespValPath,sExpFName,jArrNewExpobj));
		
		} catch (Exception e) {
			
			e.printStackTrace();
			report.updateTestLog("Massage Data",
					"Error occured while massaging the data "
							+ testParameters.getCurrentFunctionality()
							+ " : '"
							+ testParameters.getCurrentApplication()
									.toUpperCase() + "' API ", Status.FAIL);
		}
		
		return arrPath;
	}

	public void compareJSONFiles(boolean bMassage) {

		try {

			File expFile;
			File actFile;

			String sExpFile = ApiTestCase.apiTestArtifacts.GetApiExpRespPath()
					+ utilities.getFileSeparator()
					+ ApiTestCase.apiTestArtifacts.GetApiExpResFileName();
			String sActFile = ApiTestCase.apiTestArtifacts.GetApiActRespPath()
					+ utilities.getFileSeparator()
					+ ApiTestCase.apiTestArtifacts.GetApiActResFileName();

			if (bMassage) {

				ArrayList <String> arrTempPath = massageJSONData(sExpFile, sActFile);
				
				actFile = new File(arrTempPath.get(0));
				expFile = new File(arrTempPath.get(1));				

			} else {

				expFile = new File(sExpFile);
				actFile = new File(sActFile);

			}

			ObjectMapper mapper = new ObjectMapper();
			JsonNode tExpFileTree = mapper.readTree(expFile);
			JsonNode tActFileTree = mapper.readTree(actFile);

			boolean bAreEqual = tExpFileTree.equals(tActFileTree);

			if (bAreEqual) {

				report.updateTestLog("Compare JSON Files",
						"Actual and Expected Response files from the "
								+ testParameters.getCurrentFunctionality()
								+ " : '"
								+ testParameters.getCurrentApplication()
										.toUpperCase() + "' API MATCH",
						Status.PASS);

			} else {

				report.updateTestLog(
						"Compare JSON Files",
						"Actual and Expected Response files from the "
								+ testParameters.getCurrentFunctionality()
								+ " : '"
								+ testParameters.getCurrentApplication()
										.toUpperCase() + "' API DOES NOT MATCH",
						Status.FAIL);

			}

		} catch (IOException e) {

			e.printStackTrace();
			report.updateTestLog("Compare JSON Files",
					"Error occured while comparing JSON files for "
							+ testParameters.getCurrentFunctionality()
							+ " : '"
							+ testParameters.getCurrentApplication()
									.toUpperCase() + "' API ", Status.FAIL);

		}

	}

	public void getExpDataDefinition() {

		try {

			ApiExpDataDefinition apiExpDataDefinition = new ApiExpDataDefinition();

			String sSheetName = ApiTestCase.apiTestArtifacts.GetApiShtExpData();

			ExcelDataAccess testInputData = RetrieveDataObject(sSheetName);

			int iVal = getTCRecordNo(testInputData);

			apiExpDataDefinition.SetTestCaseID(testInputData.getValue(iVal,
					"TC_ID"));
			apiExpDataDefinition.SetIteration(Integer.parseInt(testInputData
					.getValue(iVal, "Iteration")));
			apiExpDataDefinition.SetContentType(testInputData.getValue(iVal,
					"ContentType"));
			apiExpDataDefinition.SetResponseCode(Integer.parseInt(testInputData
					.getValue(iVal, "ResponseCode")));
			apiExpDataDefinition.SetValidateJSON(testInputData.getValue(iVal,
					"ValidateJSON"));

			this.SetExpDataDefinition(apiExpDataDefinition);

		} catch (Exception e) {

			report.updateTestLog(
					"Exp Data Definition",
					"Error occurred while defining the expected results definition",
					Status.FAIL);
		}

	}

	public void mapExpDataToValidate() {

		try {

			String sSheetName = ApiTestCase.apiTestArtifacts
					.GetApiShtExpValidate();

			ExcelDataAccess testInputData = RetrieveDataObject(sSheetName);

			int iVal = getTCRecordNo(testInputData);

			apiExpClaimData
					.SetTestCaseID(testInputData.getValue(iVal, "TC_ID"));
			apiExpClaimData.SetIteration(Integer.parseInt(testInputData
					.getValue(iVal, "Iteration")));
			apiExpClaimData.SetValidate(testInputData
					.getValue(iVal, "Validate"));
			HashMap<String, Object> hClaimAddMap = apiExpClaimData
					.ClaimAddressHMap();

			Iterator<String> keyIterator = hClaimAddMap.keySet().iterator();

			while (keyIterator.hasNext()) {

				String sKey = keyIterator.next().trim();

				hClaimAddMap.put(sKey, testInputData.getValue(iVal, sKey));

			}

			apiExpClaimData.SetClaimAddData(hClaimAddMap);

		} catch (Exception e) {

			report.updateTestLog(
					"Exp Data to Validate",
					"Error occurred while identifying the data required to validate",
					Status.FAIL);
		}

	}

	public void getExpectedTestData() {
		
		String sSheetName = ApiTestCase.apiTestArtifacts.GetApiShtExpDataLkup();
		
	}

	public void compareExpectedData() {

	}

	public void getExpectedQueryData() {

	}

	public void consolidateQueryData() {

	}

	public void mapQueryData() {

	}

	public void verifyJSONResponse() {

		boolean bMassage = isJSONDataMassage();

		String sKey = this.Getthekey(apiExpClaimData.GetValidate());

		switch (sKey) {

		case "JSONFILE":

			compareJSONFiles(bMassage);

			break;

		case "DATA":

			getExpectedTestData();
			compareExpectedData();

			break;

		case "QUERY":

			getExpectedQueryData();
			consolidateQueryData();
			mapQueryData();
			compareExpectedData();

			break;

		default:

			break;
		}

	}

	public void verifyData() {

		verifyResponseCode();

		if (toValidateResponseFile()) {

			isJSONFileValid();
			mapExpDataToValidate();
			verifyJSONResponse();

		}

	}

}
