package enservio.framework.reports;

public class ReportSettings {
	private String reportPath;
	private String reportName;
	private String projectName = "";

	private int logLevel = 4;

	public Boolean generateExcelReports = Boolean.valueOf(false);
	public Boolean generateHtmlReports = Boolean.valueOf(true);
	public Boolean includeTestDataInReport = Boolean.valueOf(true);
	public Boolean takeScreenshotFailedStep = Boolean.valueOf(true);
	public Boolean takeScreenshotPassedStep = Boolean.valueOf(false);

	private String dateFormatString = "dd-MMM-yyyy hh:mm:ss a";

	public String getReportPath() {
		return this.reportPath;
	}

	public String getReportName() {
		return this.reportName;
	}

	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public int getLogLevel() {
		return this.logLevel;
	}

	public void setLogLevel(int logLevel) {
		if (logLevel < 0) {
			logLevel = 0;
		}
		if (logLevel > 5) {
			logLevel = 5;
		}
		this.logLevel = logLevel;
	}

	public String getDateFormatString() {
		return this.dateFormatString;
	}

	public void setDateFormatString(String dateFormatString) {
		this.dateFormatString = dateFormatString;
	}

	public ReportSettings(String reportPath, String reportName) {
		this.reportPath = reportPath;
		this.reportName = reportName;
	}

}