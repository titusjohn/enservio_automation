package enservio.testautomation.commonfunctions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;

import enservio.framework.errorhandling.Status;

public class ApiClaimTestExecute extends ApiTestCase {

	private HashMap<String, Object> hMap;
	private HashMap<String, Object> hEndPoint;
	
	public ApiClaimTestExecute(HashMap<String, Object> hMap) {

		 this.hMap			   = hMap;
	}
	
	private void SetEndPoint(HashMap<String, Object> hEndPoint) {
		
		this.hEndPoint = hEndPoint;
	}
	
	private HashMap<String, Object> GetEndPoint() {
		
		return this.hEndPoint;
	}
	
	public void constructEndPointURL(
			ArrayList<String> aArrInpToken) {

		HashMap<String, Object> hEnd = new HashMap<String, Object>();

		String sDataToken = MsgDataToken(aArrInpToken,
				ApiTestCase.apiTestDefinition.GetDataToken());

		String sBaseURL = ApiTestCase.properties.getProperty("ApplicationUrl")
				+ ApiTestCase.apiTestDefinition.GetToken() + sDataToken;

		hEnd.put("URL", sBaseURL);
		hEnd.put("COOKIE", ApiTestCase.apiTestDefinition.GetSecurityToken());

		report.updateTestLog(
				"End Point", sBaseURL.replace(ApiTestCase.properties.getProperty("ApplicationUrl"), ""),
				Status.DONE);

		report.updateTestLog("Cookie","Successfully set security token for the test case",
				Status.DONE);

		this.SetEndPoint(hEnd);

	}
	
	public void PushtoClaimAPI () {

		try {
			
			//JSONObject jsonObject;
			JSONArray jsonArray;
			String sContentType = "";
			String sMethod = ApiTestCase.testParameters.getCurrentApplication().toUpperCase();
			
			HashMap<String, Object> hParam1 = this.GetEndPoint();

			URL url = new URL(hParam1.get("URL").toString());

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(sMethod);
			conn.addRequestProperty("Cookie", hParam1.get("COOKIE").toString());
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() == 200) {
				
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
				StringBuilder responseStrBuilder = new StringBuilder();

			    String inputStr;
			    
			    while ((inputStr = br.readLine()) != null) {
			    	
			    	//inputStr= inputStr.replace("[", "");
			    	//inputStr= inputStr.replace("]", "");
			    	
			    	responseStrBuilder.append(inputStr);
			    }
			    
			    if (responseStrBuilder.length() >0) {
			    	
			    	sContentType = conn.getContentType();
			    	jsonArray  = new JSONArray(responseStrBuilder.toString());			    			    	
			    	//jsonObject = new JSONObject(responseStrBuilder.toString());
			    				    	
			    					    
			      } else {
			    	 
			    	  jsonArray = null;
			      }    	 
			    		    
			} else {
				
				jsonArray = null;
				
			}
	
			this.SetContentType(sContentType);
			this.SetJSONArray(jsonArray);
			this.SetResponseCode(conn.getResponseCode());
				
			conn.disconnect();
			
			report.updateTestLog("Call API","Able to successfully call "+testParameters.getCurrentFunctionality()+ " : '"+testParameters.getCurrentApplication().toUpperCase()+ "' API without any issues!",
					Status.DONE);
							

		} catch (MalformedURLException e) {

			e.printStackTrace();
			report.updateTestLog("Call API","Error occured while calling "+testParameters.getCurrentFunctionality()+ " : '"+testParameters.getCurrentApplication().toUpperCase()+ "' API",
					Status.FAIL);

		} catch (IOException e) {

			e.printStackTrace();
			report.updateTestLog("Call API","Error occured while calling "+testParameters.getCurrentFunctionality()+ " : '"+testParameters.getCurrentApplication().toUpperCase()+ "' API",
					Status.FAIL);

		} catch (JSONException e) {
			
			e.printStackTrace();
			report.updateTestLog("Call API","Error occured while calling "+testParameters.getCurrentFunctionality()+ " : '"+testParameters.getCurrentApplication().toUpperCase()+ "' API",
					Status.FAIL);
		}

	}

	public String MsgDataToken(ArrayList<String> aArrInpToken, String sDToken) {

		for (String sClm : aArrInpToken) {

			if (hMap.get(sClm).equals("NONE")) {

				sDToken = sDToken.replace("{" + sClm + "}", "");

			} else {

				sDToken = sDToken.replace("{" + sClm + "}", hMap.get(sClm)
						.toString());

			}
		}
		return sDToken;

	}

	
}
