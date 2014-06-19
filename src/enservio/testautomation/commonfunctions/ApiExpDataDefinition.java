package enservio.testautomation.commonfunctions;

public class ApiExpDataDefinition {
	
	public String sTestCaseID;
	public int iIteration;
	public int iResponseCode;
	public String sContentType;
	public String sValidateJSON;
	
	public void SetTestCaseID(String sTestcaseID) {

		this.sTestCaseID = sTestcaseID;
		
	}

	public void SetIteration(int iIteration) {

		this.iIteration = iIteration;
		
	}

	public void SetResponseCode(int iResCode) {

		this.iResponseCode = iResCode;
	}	
	
	public void SetContentType(String sExpContType) {

		this.sContentType = sExpContType;
	}	
	
	public void SetValidateJSON(String sJSON) {

		this.sValidateJSON = sJSON;
	}	

	public String GetTestCaseID() {

		return this.sTestCaseID;
		
	}

	public int GetIteration() {

		return this.iIteration;
		
	}

	public int GetResponseCode() {

		return this.iResponseCode;
	}	
	
	public String GetContentType() {

		return this.sContentType;
		
	}
	
	public String GetValidateJSON() {

		return this.sValidateJSON;
	}	
	
}
