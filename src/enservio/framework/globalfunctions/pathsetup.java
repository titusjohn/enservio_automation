package enservio.framework.globalfunctions;

import enservio.framework.errorhandling.setupexceptions;

public class pathsetup {
	private String relativePath;
	private String reportPath;
	private String runConfiguration;
	private String projectName;
	private String resultPath;

	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		if (projectName != null && projectName.trim() != "")
			this.projectName = projectName;
		else
			throw new setupexceptions("Project Name Not Set!!");
	}

	public String getRelativePath() {
		return this.relativePath;
	}

	public void setRelativePath(String relativePath) {
		if (relativePath != null && relativePath.trim() != "")
			this.relativePath = relativePath;
		else
			throw new setupexceptions("Relative Path Not Set!!");
	}

	public String getReportPath() {
		return reportPath;
	}

	public void setReportPath(String reportPath) {
		if (reportPath != null && reportPath.trim() != "")
			this.reportPath = reportPath;
		else
			throw new setupexceptions("Report Path Not Set!!");
	}

	public String getRunConfiguration() {
		return this.runConfiguration;
	}

	public void setRunConfiguration(String runConfiguration) {
		if (runConfiguration != null && runConfiguration.trim() != "")
			this.runConfiguration = runConfiguration;
		else
			throw new setupexceptions("Run Configuration Not Set!!");
	}

	public void setTestResultPath(String resultPath) {
		if (!resultPath.equals(""))
			this.resultPath = resultPath;
	}

	public String getTestResultPath() {
		return resultPath;
	}

	private Boolean stopExecution = Boolean.valueOf(false);
	private static pathsetup pathSetup;

	public Boolean getStopExecution() {
		return this.stopExecution;
	}

	public void setStopExecution(Boolean stopExecution) {
		this.stopExecution = stopExecution;
	}

	public static synchronized pathsetup getInstance() {
		if (pathSetup == null) {
			pathSetup = new pathsetup();
		}
		return pathSetup;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

}
