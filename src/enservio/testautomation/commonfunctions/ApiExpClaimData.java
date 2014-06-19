package enservio.testautomation.commonfunctions;

import java.util.HashMap;

public class ApiExpClaimData {
	
	public String sTestCaseID;
	public int iIteration;
	public int iSubIteration;
	public String sValidate;
	public HashMap<String, Object> hClaimAddMap;
		
	public void SetTestCaseID(String sTestcaseID) {
		
		this.sTestCaseID = sTestcaseID;
		
	}
	
	public void SetIteration(int iTr ) {
		
		this.iIteration = iTr;
	}

	public void SetSubIteration(int iSubTr) {
		
		this.iSubIteration = iSubTr;
	}
	
	public void SetValidate(String sVal) {
		
		this.sValidate = sVal;
		
	}
	
	public HashMap<String, Object> ClaimAddressHMap() {
		
		HashMap<String, Object> hMap = new HashMap<String, Object>();
	    String[] sToValidate = {"AuditRowId","AuditId","ValidationMessage","AddressTypeName","StateName","Validated","EditedDate","EditedBy","CreatedDate","CreatedBy","TsEdit","ContactName","PostalCode","StateProv","City","Address1","Address2","AddressType","ClaimId","AddressId"};
				
	    for (int i=0;i<sToValidate.length;i++)
	    {
	    	
	    	hMap.put(sToValidate[i],"");
	    	
	    }
			
		
		return hMap;
	}

	public void SetClaimAddData(HashMap<String, Object> hMap) {
		
		hClaimAddMap = new HashMap<String, Object>();
		hClaimAddMap = hMap;
				
	}
		
	public String GetValidate() {
		
		return this.sValidate;
	}
	
	public String GetTestCaseID() {
		
		return this.sTestCaseID;
		
	}
	
	public int GetIteration() {
		
		return this.iIteration;
		
	}
	
	public HashMap<String, Object> GetClaimAddData () {
		
		return this.hClaimAddMap;
	}
	
}
