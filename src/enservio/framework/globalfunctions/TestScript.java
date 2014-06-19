package enservio.framework.globalfunctions;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import enservio.framework.reports.*;
import enservio.framework.testflowsetup.*;
import enservio.framework.errorhandling.*;
import enservio.framework.testdatafunctions.*;
import enservio.framework.reports.ReportThemeFactory.Theme;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public abstract class TestScript {

	private Date startTime, endTime;
	private String reportPath;
	protected int currentIteration;
	protected String timeStamp;
	
	protected DataTable dataTable;
	protected ReportSettings reportSettings;
	protected SeleniumReport report;
	protected RemoteWebDriver driver;
	protected ScriptHelper scriptHelper;

	private Properties properties;
	private String gridMode;
	protected pathsetup pathSetup = pathsetup.getInstance();

	public String sAppType;
	protected TestParameters testParameters = new TestParameters();

	public TestScript() {
		
		setRelativePath();
		properties = settings.getInstance();
		sAppType = properties.getProperty("RunConfiguration");
		setDefaultTestParameters();
	}

	private void setRelativePath() {
	
		String relativePath = new File(System.getProperty("user.dir"))
				.getAbsolutePath();
		if (relativePath.contains("driverscripts")) {
			relativePath = new File(System.getProperty("user.dir")).getParent();
		}
		pathSetup.setRelativePath(relativePath);
	}

	protected abstract void executeTestCase() throws Exception;

	private void setDefaultTestParameters() {

		testParameters.setCurrentTestcase(this.getClass().getSimpleName());
		testParameters.setCurrentSuite(this.getClass().getPackage().getName()
				.substring(12));
		testParameters.setIterationMode(IterationOptions.RunAllIterations);

		testParameters.setBrowser(Browser.valueOf(properties
				.getProperty("DefaultBrowser")));
		testParameters.setBrowserVersion(properties
				.getProperty("DefaultBrowserVersion"));
		testParameters.setPlatform(PlatformFactory.getPlatform(properties
				.getProperty("DefaultPlatform")));

		if (sAppType.toLowerCase().equals("webapp")) {

			testParameters.setCurrentApplication("insight");
			testParameters.setCurrentFunctionality("multisearch");

		} else {

			testParameters.setCurrentApplication("get");
			testParameters.setCurrentFunctionality("claim");
		}

	}

	public void driveTestExecution() {

		startTime = utilities.getCurrentTime();

		if (sAppType.toLowerCase().equals("webapp")) {

			initializeWebDriver();

		}

		initializeTestReport();
		initializeDatatable();
		initializeTestScript();
		initializeTestIterations();
		setUp();
		executeTestIterations();
		tearDown();
		wrapUp();
	}

	private void initializeWebDriver() {

		gridMode = properties.getProperty("GridMode");

		if (gridMode.equalsIgnoreCase("on")) {

			DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
			desiredCapabilities.setBrowserName(testParameters.getBrowser()
					.toString());
			desiredCapabilities.setVersion(testParameters.getBrowserVersion());
			desiredCapabilities.setPlatform(testParameters.getPlatform());

			URL gridHubUrl;

			try {
				gridHubUrl = new URL(properties.getProperty("GridHubUrl"));
			} catch (MalformedURLException e) {
				e.printStackTrace();
				throw new setupexceptions(
						"The specified Selenium Grid Hub URL is malformed");
			}

			driver = new RemoteWebDriver(gridHubUrl, desiredCapabilities);
		} else {

			driver = WebDriverFactory.getDriver(testParameters.getBrowser());

		}
	}

	private void initializeTestReport() {

		pathSetup.setRunConfiguration(properties
				.getProperty("RunConfiguration"));

		timeStamp = TimeStamp.getInstance();

		initializeReportSettings();

		ReportTheme reportTheme = ReportThemeFactory.getReportsTheme(Theme
				.valueOf(properties.getProperty("ReportsTheme")));

		report = new SeleniumReport(reportSettings, reportTheme);

		report.initializeReportTypes();

		report.setDriver(driver);

		report.initializeTestLog();

		report.addTestLogHeading(reportSettings.getProjectName() + " - "
				+ reportSettings.getReportName()
				+ " Automation Execution Results");

		report.addTestLogSubHeading(
				"Date & Time",
				": "
						+ utilities.getCurrentFormattedTime(properties
								.getProperty("DateFormatString")),
				"Iteration Mode", ": " + testParameters.getIterationMode());

		report.addTestLogSubHeading("Start Iteration",
				": " + testParameters.getStartIteration(), "End Iteration",
				": " + testParameters.getEndIteration());

		if (sAppType.toLowerCase().equals("webapp")) {

			if (gridMode.equalsIgnoreCase("on")) {
				report.addTestLogSubHeading("Browser",
						": " + testParameters.getBrowser(), "Version", ": "
								+ testParameters.getBrowserVersion());
				report.addTestLogSubHeading("Platform", ": "
						+ testParameters.getPlatform().toString(),
						"Application URL",
						": " + properties.getProperty("ApplicationUrl"));
			} else {
				report.addTestLogSubHeading("Browser",
						": " + testParameters.getBrowser(), "Application URL",
						": " + properties.getProperty("ApplicationUrl"));
			}

			report.addTestLogSubHeading("Application", ": "
					+ testParameters.getCurrentApplication().toUpperCase(),
					"Functionality",
					": " + testParameters.getCurrentFunctionality());

		} else {

			report.addTestLogSubHeading("Method", ": "
					+ testParameters.getCurrentApplication().toUpperCase(),
					"API Name", ": " + testParameters.getCurrentFunctionality());

		}

		report.addTestLogTableHeadings();

	}

	private void initializeReportSettings() {

		reportPath = pathSetup.getRelativePath() + utilities.getFileSeparator()
				+ "Results" + utilities.getFileSeparator() + timeStamp;

		reportSettings = new ReportSettings(reportPath,
				testParameters.getCurrentSuite() + "_"
						+ testParameters.getCurrentTestcase());

		reportSettings.setDateFormatString(properties
				.getProperty("DateFormatString"));
		reportSettings.setLogLevel(Integer.parseInt(properties
				.getProperty("LogLevel")));
		reportSettings.setProjectName(properties.getProperty("ProjectName"));
		reportSettings.generateExcelReports = Boolean.parseBoolean(properties
				.getProperty("ExcelReport"));
		reportSettings.generateHtmlReports = Boolean.parseBoolean(properties
				.getProperty("HtmlReport"));
		reportSettings.includeTestDataInReport = Boolean
				.parseBoolean(properties.getProperty("IncludeTestDataInReport"));
		reportSettings.takeScreenshotFailedStep = Boolean
				.parseBoolean(properties
						.getProperty("TakeScreenshotFailedStep"));
		reportSettings.takeScreenshotPassedStep = Boolean
				.parseBoolean(properties
						.getProperty("TakeScreenshotPassedStep"));
	}

	private void initializeDatatable() {

		String datatablePath = pathSetup.getRelativePath()
				+ utilities.getFileSeparator() + "datarepository"
				+ utilities.getFileSeparator() + sAppType
				+ utilities.getFileSeparator()
				+ testParameters.getCurrentApplication().toLowerCase();

		// String comdataPath = pathSetup.getRelativePath()
		// + utilities.getFileSeparator() + "datarepository"
		// + utilities.getFileSeparator() + sAppType;

		String runTimeDatatablePath;

		if (reportSettings.includeTestDataInReport) {
			runTimeDatatablePath = reportPath + utilities.getFileSeparator()
					+ "Datatables";

			System.out.println("reportPath  :" + reportPath);
			System.out
					.println("runTimeDatatablePath  :" + runTimeDatatablePath);

			File runTimeDatatable = new File(runTimeDatatablePath
					+ utilities.getFileSeparator()
					+ testParameters.getCurrentSuite() + ".xls");
			if (!runTimeDatatable.exists()) {
				File datatable = new File(datatablePath
						+ utilities.getFileSeparator()
						+ testParameters.getCurrentSuite() + ".xls");

				try {
					FileUtils.copyFile(datatable, runTimeDatatable);

				} catch (IOException e) {
					e.printStackTrace();
					throw new setupexceptions(
							"Error in creating run-time datatable: Copying the datatable failed...");
				}
			}

			File runTimeCommonDatatable = new File(runTimeDatatablePath
					+ utilities.getFileSeparator() + "Common Testdata.xls");

			if (!runTimeCommonDatatable.exists()) {
				File commonDatatable = new File(datatablePath
						+ utilities.getFileSeparator() + "Common Testdata.xls");

				try {
					FileUtils.copyFile(commonDatatable, runTimeCommonDatatable);
				} catch (IOException e) {
					e.printStackTrace();
					throw new setupexceptions(
							"Error in creating run-time datatable: Copying the common datatable failed...");
				}
			}
		} else {
			runTimeDatatablePath = datatablePath;
		}

		dataTable = new DataTable(runTimeDatatablePath,
				testParameters.getCurrentSuite());
	}

	private void initializeTestScript() {
		scriptHelper = new ScriptHelper(dataTable, report, driver);
	}

	private synchronized void initializeTestIterations() {

		switch (testParameters.getIterationMode()) {
		case RunAllIterations:
			String datatablePath = pathSetup.getRelativePath()
					+ utilities.getFileSeparator() + "datarepository"
					+ utilities.getFileSeparator() + sAppType
					+ utilities.getFileSeparator()
					+ testParameters.getCurrentApplication();
			ExcelDataAccess testDataAccess = new ExcelDataAccess(datatablePath,
					testParameters.getCurrentSuite());
			testDataAccess.setDatasheetName(properties
					.getProperty("DefaultDataSheet"));

			int startRowNum = testDataAccess.getRowNum(
					testParameters.getCurrentTestcase(), 0);
			int nTestcaseRows = testDataAccess.getRowCount(
					testParameters.getCurrentTestcase(), 0, startRowNum);
			int nSubIterations = testDataAccess
					.getRowCount("1", 1, startRowNum); // Assumption: Every test
														// case will have at
			// least one iteration

			int nIterations = nTestcaseRows / nSubIterations;

			testParameters.setEndIteration(nIterations);

			currentIteration = 1;
			break;

		case RunOneIterationOnly:
			currentIteration = 1;
			break;

		case RunRangeOfIterations:
			if (testParameters.getStartIteration() > testParameters
					.getEndIteration()) {
				throw new setupexceptions("Error",
						"StartIteration cannot be greater than EndIteration!");
			}
			currentIteration = testParameters.getStartIteration();
			break;
		}
	}

	protected void setUp() {

		if (sAppType.toLowerCase().equals("webapp")) {

			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.get(properties.getProperty("ApplicationUrl"));
		}
	}

	private void executeTestIterations() {

		while (currentIteration <= testParameters.getEndIteration()) {
			dataTable.setCurrentRow(testParameters.getCurrentTestcase(),
					currentIteration);
			report.addTestLogSection("Iteration: "
					+ Integer.toString(currentIteration));

			try {

				executeTestCase();

			} catch (setupexceptions fx) {
				exceptionHandler(fx, fx.errorName);
			} catch (Exception ex) {
				exceptionHandler(ex, "Error");
			}

			currentIteration++;
		}
	}

	private void exceptionHandler(Exception ex, String exceptionName) {
		// Error reporting
		String exceptionDescription = ex.getMessage();
		if (exceptionDescription == null) {
			exceptionDescription = ex.toString();
		}

		if (ex.getCause() != null) {
			report.updateTestLog(exceptionName, exceptionDescription
					+ " <b>Caused by: </b>" + ex.getCause(), Status.FAIL);
		} else {
			report.updateTestLog(exceptionName, exceptionDescription,
					Status.FAIL);
		}
		ex.printStackTrace();

		// Error response
		if (pathSetup.getStopExecution()) {
			report.updateTestLog(
					"Info",
					"Test execution terminated by user! All subsequent tests aborted...",
					Status.DONE);
		} else {
			OnError onError = OnError
					.valueOf(properties.getProperty("OnError"));
			switch (onError) {
			case NextIteration:
				report.updateTestLog(
						"Info",
						"Test case iteration terminated by user! Proceeding to next iteration (if applicable)...",
						Status.DONE);
				currentIteration++;
				executeTestIterations();
				break;

			case NextTestCase:
				report.updateTestLog(
						"Info",
						"Test case terminated by user! Proceeding to next test case (if applicable)...",
						Status.DONE);
				break;

			case Stop:
				pathSetup.setStopExecution(true);
				break;
			}
		}
		// Wrap up execution
		tearDown();
		wrapUp();
	}

	protected void tearDown() {

		if (sAppType.toLowerCase().equals("webapp")) {
			driver.quit();
		}
	}

	private void wrapUp() {

		endTime = utilities.getCurrentTime();
		closeTestReport();

		if (report.getTestStatus().equalsIgnoreCase("Failed")) {
			Assert.fail(report.getFailureDescription());
		}
	}

	private void closeTestReport() {
		String executionTime = utilities.getTimeDifference(startTime, endTime);
		report.addTestLogFooter(executionTime);
	}

}