package enservio.testautomation.commonfunctions;

public class ApiInputBaseData {

	protected String sTestcaseID;
	protected int iIteration;
	protected int iSubIteration;

	public void SetTestcaseID(String sTestcaseID) {

		this.sTestcaseID = sTestcaseID;
	}

	public void SetIteration(int iIteration) {

		this.iIteration = iIteration;
	}

	public void SetSubIteration(int iSubIteration) {

		this.iSubIteration = iSubIteration;
	}

	public String GetTestcaseID() {

		return this.sTestcaseID;
	}

	public int GetIteration() {

		return this.iIteration;
	}

	public int GetSubIteration() {

		return this.iSubIteration;
	}

}
