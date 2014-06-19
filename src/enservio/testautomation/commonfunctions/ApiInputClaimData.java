package enservio.testautomation.commonfunctions;

public class ApiInputClaimData extends ApiInputBaseData {

	public String sClaimID;
	public String sAddressID;
	public String sBuildingID;
	public String sEmailAddress;
	public String sFormID;
	public String sLimitID;
	public String sPhoneID;
	public String sRoomID;

	public ApiInputClaimData() {
		super();

	}

	public void SetClaimID(String sClaimID) {

		this.sClaimID = sClaimID;
	}

	public void SetAddressID(String sAddressID) {

		this.sAddressID = sAddressID;
	}

	public void SetBuildingID(String sBuildingID) {

		this.sBuildingID = sBuildingID;
	}

	public void SetEmailAddress(String sEmailAddress) {

		this.sEmailAddress = sEmailAddress;
	}

	public void SetFormID(String sFormID) {

		this.sFormID = sFormID;
	}

	public void SetLimitID(String sLimitID) {

		this.sLimitID = sLimitID;
	}

	public void SetPhoneID(String sPhoneID) {

		this.sPhoneID = sPhoneID;
	}

	public void SetRoomID(String sRoomID) {

		this.sRoomID = sRoomID;
	}

	public String GetClaimID() {
		return this.sClaimID;
	}

	public String GetAddressID() {
		return this.sAddressID;
	}

	public String GetBuildingID() {
		return this.sBuildingID;
	}

	public String GetEmailAddress() {
		return this.sEmailAddress;
	}

	public String GetFormID() {
		return this.sFormID;
	}

	public String GetLimitID() {
		return this.sLimitID;
	}

	public String GetPhoneID() {
		return this.sPhoneID;
	}

	public String GetRoomID() {
		return this.sRoomID;
	}

}
