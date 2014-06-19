package enservio.testautomation.commonfunctions;


public class ApiTestDefinition {

	public String sTestCaseID;
	public int iIteration;
	public String sToken;
	public String sDataToken;
	public String sSecurityToken;
	public String sApp_URL;
	public String sUserName;
	public String sPassword;
	public String sURLParam;
	public String sJsonData;
	public String sCompositeAPI;

	public void SetTestCaseID(String sTestcaseID) {

		this.sTestCaseID = sTestcaseID;
		
	}

	public void SetIteration(int iIteration) {

		this.iIteration = iIteration;
		
	}

	public void SetToken(String sToken) {

		this.sToken = sToken;
	}	
	
	public void SetDataToken(String sDataToken) {

		this.sDataToken = sDataToken;
	}	

	public void SetSecurityToken(String sSecurityToken) {

		this.sSecurityToken = sSecurityToken;
	}
	
	public void SetAppURL(String sApp_URL) {

		this.sApp_URL = sApp_URL;
	}
	
	public void SetUserName(String sUserName) {

		this.sUserName = sUserName;
	}
	
	public void SetPassword(String sPassword) {

		this.sPassword = sPassword;
		
	}
	
	public void SetURLParamData(String sURLParam ) {

		this.sURLParam = sURLParam;
	}
	
	public void SetJSONData(String sJsonData ) {

		this.sJsonData = sJsonData;
	}
	
	public void SetCompositeAPI(String sCompositeAPI) {
		
		this.sCompositeAPI = sCompositeAPI;
	}
	
	public String GetTestCaseID() {

		return this.sTestCaseID;
	}

	public int GetIteration() {

		return this.iIteration;
	}
	
	public String GetToken() {

		return this.sToken;
	}

	public String GetDataToken() {

		return this.sDataToken;
	}
	
	public String GetSecurityToken() {

		return this.sSecurityToken;

	}

	public String GetAppUrl() {

		return this.sApp_URL;

	}

	public String GetUserName() {

		return this.sUserName;
	}

	public String GetPassword() {

		return this.sPassword;

	}

	public String GetURLParamData() {

		return this.sURLParam;

	}

	public String GetJSONData() {

		return this.sJsonData;

	}

	public String GetCompositeAPI() {

		return this.sCompositeAPI;

	}

		
}
