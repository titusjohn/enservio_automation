package enservio.testautomation.commonfunctions;

public class ApiTestArtifacts {

	String sApiTestSheetPath;
	
	String sApiShtInputData;
	String sApiShtInpQueryLkup;
	String sApiShtInpDataLkup;
	
	String sApiShtExpData;
	String sApiShtExpValidate;
	String sApiShtExpQueryLkup;
	String sApiShtExpDataLkup;
	String sApiExpRespPath;
	String sApiExpResFileName;
	
	String sApiActRespPath;
	String sApiActResFileName;

	public ApiTestArtifacts() {

		this.SetApiShtInputData();
		this.SetApiShtInpQueryLkup();
		this.SetApiShtInpDataLkup();
		
		this.SetApiShtExpData();
		this.SetApiShtExpValidate();
		//this.SetApiShtExpAppMastrData();
		//this.SetApiShtExpAppLkup();
		this.SetApiShtExpQueryLkup();
		this.SetApiShtExpDataLkup();

	}

	public void SetApiTestSheetPath(String sPath) {
		this.sApiTestSheetPath = sPath;
	}

	public void SetApiShtInputData() {
		this.sApiShtInputData = "InputData";
	}

	public void SetApiShtInpQueryLkup() {
		this.sApiShtInpQueryLkup = "InpQuery_LookUp";
	}

	public void SetApiShtInpDataLkup() {
		this.sApiShtInpDataLkup = "InpData_LookUp";
	}

	public void SetApiShtExpData() {
		this.sApiShtExpData = "ExpectedData";
	}

	public void SetApiShtExpValidate() {
		this.sApiShtExpValidate = "Exp_Validate";
	}
	
	public void SetApiShtExpQueryLkup() {
		this.sApiShtExpQueryLkup = "ExpQuery_Lookup";
	}

	public void SetApiShtExpDataLkup() {
		this.sApiShtExpDataLkup = "ExpData_Lookup";
	}

	public void SetApiExpRespPath(String sPath) {
		this.sApiExpRespPath = sPath;
	}

	public void SetApiExpRespFileName(String sFileName) {
		this.sApiExpResFileName = sFileName;
	}

	public void SetApiActRespPath(String sPath) {
		this.sApiActRespPath = sPath;
	}

	public void SetApiActRespFileName(String sFileName) {
		this.sApiActResFileName = sFileName;
	}

	public String GetApiTestSheetPath() {
		return this.sApiTestSheetPath;
	}

	public String GetApiShtInputData() {
		return this.sApiShtInputData;
	}

	public String GetApiShtInpQueryLkup() {
		return this.sApiShtInpQueryLkup;
	}

	public String GetApiShtInpDataLkup() {
		return this.sApiShtInpDataLkup;
	}

	public String GetApiShtExpData() {
		return this.sApiShtExpData;
	}

	public String GetApiShtExpValidate() {
		return this.sApiShtExpValidate;
	}
	
	public String GetApiShtExpQueryLkup() {
		return this.sApiShtExpQueryLkup;
	}

	public String GetApiShtExpDataLkup() {
		return this.sApiShtExpDataLkup;
	}

	public String GetApiExpRespPath() {
		return this.sApiExpRespPath;
	}

	public String GetApiExpResFileName() {
		return this.sApiExpResFileName;
	}

	public String GetApiActRespPath() {
		return this.sApiActRespPath;
	}

	public String GetApiActResFileName() {
		return this.sApiActResFileName;
	}
		
}
