package enservio.driverscripts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
//import java.io.IOException;
import java.io.PrintStream;
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import org.openqa.selenium.Platform;

import enservio.framework.globalfunctions.*;
import enservio.framework.testdatafunctions.*;
import enservio.framework.testflowsetup.*;
import enservio.framework.reports.ReportThemeFactory.Theme;
import enservio.framework.reports.*;

public class triggerscript {
	
	private static SeleniumReport report;
	private static ReportSettings reportSettings;
	private static String reportPath;

	private static ArrayList<TestParameters> testInstancesToRun;
	private static Properties properties;
	private static pathsetup pathSetup = pathsetup.getInstance();

	private static Date startTime, endTime;
	private static String timeStamp;

	public static void main(String[] args) throws FileNotFoundException {

		setRelativePath();
		initializeTestBatch();
		initializeSummaryReport();
		setupErrorLog();
		driveBatchExecution();
		wrapUp();
	}

	private static void setRelativePath() {

		String relativePath = new File(System.getProperty("user.dir"))
				.getAbsolutePath();

		if (relativePath.contains("src")) {

			relativePath = new File(System.getProperty("user.dir")).getParent();
		}

		pathSetup.setRelativePath(relativePath);

	}

	private static void initializeTestBatch() {
		startTime = utilities.getCurrentTime();
		properties = settings.getInstance();

		testInstancesToRun = getRunInfo(properties
				.getProperty("RunConfiguration"));

	}

	private static ArrayList<TestParameters> getRunInfo(String sheetName) {
		System.out.println(sheetName);

		ExcelDataAccess runManagerAccess = new ExcelDataAccess(
				pathSetup.getRelativePath() + utilities.getFileSeparator()
						+ "datarepository" + utilities.getFileSeparator()
						+ "masterdata" + utilities.getFileSeparator(),
				"mastersheet");
		runManagerAccess.setDatasheetName(sheetName);

		int nTestInstances = runManagerAccess.getLastRowNum();
		ArrayList<TestParameters> testInstancesToRun = new ArrayList<TestParameters>();

		for (int currentTestInstance = 1; currentTestInstance <= nTestInstances; currentTestInstance++) {
			String executeFlag = runManagerAccess.getValue(currentTestInstance,
					"Execute");

			if (executeFlag.equalsIgnoreCase("Yes")) {
				TestParameters testParameters = new TestParameters();

				testParameters.setCurrentSuite(runManagerAccess.getValue(
						currentTestInstance, "Test_Suite"));
				testParameters.setCurrentTestcase(runManagerAccess.getValue(
						currentTestInstance, "Test_Case_ID"));

				testParameters.setCurrentFunctionality(runManagerAccess
						.getValue(currentTestInstance, "Functionality"));
				testParameters.setCurrentTestDescription(runManagerAccess
						.getValue(currentTestInstance, "Description"));

				testParameters.setIterationMode(IterationOptions
						.valueOf(runManagerAccess.getValue(currentTestInstance,
								"Iteration_Mode")));
				String startIteration = runManagerAccess.getValue(
						currentTestInstance, "Start_Iteration");
				if (!startIteration.equals("")) {
					testParameters.setStartIteration(Integer
							.parseInt(startIteration));
				}
				String endIteration = runManagerAccess.getValue(
						currentTestInstance, "End_Iteration");
				if (!endIteration.equals("")) {
					testParameters.setEndIteration(Integer
							.parseInt(endIteration));
				}

				testParameters.setCurrentPriority(runManagerAccess.getValue(
						currentTestInstance, "Priority"));

				testParameters.setCurrentRiskFactor(runManagerAccess.getValue(
						currentTestInstance, "Risk_Factor"));
				testParameters.setLanguage(runManagerAccess.getValue(
						currentTestInstance, "Language"));

				String platform = runManagerAccess.getValue(
						currentTestInstance, "Platform");
				if (!platform.equals("")) {
					testParameters.setPlatform(PlatformFactory
							.getPlatform(platform));
				}

				String browser, browserVersion;

				if (sheetName.equals("restfulapi")) {

					testParameters.setCurrentApplication(runManagerAccess
							.getValue(currentTestInstance, "Method"));
					testParameters.setBrowser(Browser.NA);
					testParameters.setBrowserVersion("NA");

				} else {

					testParameters.setCurrentApplication(runManagerAccess
							.getValue(currentTestInstance, "Application"));
					browser = runManagerAccess.getValue(currentTestInstance,
							"Browser");
					if (!browser.equals("")) {
						testParameters.setBrowser(Browser.valueOf(browser));
					}

					browserVersion = runManagerAccess.getValue(
							currentTestInstance, "Browser_Version");
					if (!browserVersion.equals("")) {
						testParameters.setBrowserVersion(browserVersion);
					}
				}

				testInstancesToRun.add(testParameters);

			}
		}

		return testInstancesToRun;

	}

	private static void initializeSummaryReport() {
		pathSetup.setRunConfiguration(properties
				.getProperty("RunConfiguration"));
		timeStamp = TimeStamp.getInstance();
		reportSettings = initializeReportSettings();

		ReportTheme reportTheme = ReportThemeFactory.getReportsTheme(Theme
				.valueOf(properties.getProperty("ReportsTheme")));

		report = new SeleniumReport(reportSettings, reportTheme);

		report.initializeReportTypes();

		report.initializeResultSummary();
		report.addResultSummaryHeading(reportSettings.getProjectName() + " - "
				+ " Automation Execution Result Summary");
		report.addResultSummarySubHeading(
				"Date & Time",
				": "
						+ utilities.getCurrentFormattedTime(properties
								.getProperty("DateFormatString")), "OnError",
				": " + properties.getProperty("OnError"));

		report.addResultSummaryTableHeadings();

	}

	private static ReportSettings initializeReportSettings() {
		reportPath = pathSetup.getRelativePath() + utilities.getFileSeparator()
				+ "Results" + utilities.getFileSeparator() + timeStamp;
		ReportSettings reportSettings = new ReportSettings(reportPath, "");

		reportSettings.setDateFormatString(properties
				.getProperty("DateFormatString"));
		reportSettings.setProjectName(properties.getProperty("ProjectName"));
		reportSettings.generateExcelReports = Boolean.parseBoolean(properties
				.getProperty("ExcelReport"));
		reportSettings.generateHtmlReports = Boolean.parseBoolean(properties
				.getProperty("HtmlReport"));
		return reportSettings;
	}

	private static void setupErrorLog() throws FileNotFoundException {
		String errorLogFile = reportPath + utilities.getFileSeparator()
				+ "ErrorLog.txt";

		System.setErr(new PrintStream(new FileOutputStream(errorLogFile)));
	}

	private static void driveBatchExecution() {
		int nThreads = Integer.parseInt(properties
				.getProperty("NumberOfThreads"));
		ExecutorService parallelExecutor = Executors
				.newFixedThreadPool(nThreads);

		for (int currentTestInstance = 0; currentTestInstance < testInstancesToRun
				.size(); currentTestInstance++) {
			ParallelRunner testRunner = new ParallelRunner(
					testInstancesToRun.get(currentTestInstance), report,
					properties.getProperty("RunConfiguration"));
			parallelExecutor.execute(testRunner);

			if (pathSetup.getStopExecution()) {
				break;
			}
		}

		parallelExecutor.shutdown();
		while (!parallelExecutor.isTerminated()) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static void wrapUp() {
		endTime = utilities.getCurrentTime();

		closeSummaryReport();

		/*
		 * if (reportSettings.generateHtmlReports) { try {
		 * Runtime.getRuntime().exec
		 * ("RunDLL32.EXE shell32.dll,ShellExec_RunDLL " + reportPath +
		 * "\\HTML Results\\Summary.Html"); } catch (IOException e) {
		 * e.printStackTrace(); } } else if
		 * (reportSettings.generateExcelReports) { try {
		 * Runtime.getRuntime().exec
		 * ("RunDLL32.EXE shell32.dll,ShellExec_RunDLL " + reportPath +
		 * "\\Excel Results\\Summary.xls"); } catch (IOException e) {
		 * e.printStackTrace(); } }
		 */
	}

	private static void closeSummaryReport() {
		String totalExecutionTime = utilities.getTimeDifference(startTime,
				endTime);
		report.addResultSummaryFooter(totalExecutionTime);
	}

}