package enservio.framework.globalfunctions;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;

import enservio.framework.testflowsetup.Browser;
import enservio.framework.testflowsetup.Language;
import enservio.framework.testflowsetup.IterationOptions;

public class TestParameters {
	private String currentSuite;
	private String currentTestcase;
	private String currentApplication;
	private String currentFunctionality;
	private String currentTestDescription;
	private IterationOptions iterationMode;

	private String currentTestPriority;
	private String currentTestRiskFactor;

	private Language language;
	private Platform platform;
	private Browser browser;
	private String browserVersion;

	private WebDriver driver;
	private String testCycleId = "";

	public String getCurrentSuite() {
		return this.currentSuite;
	}

	public void setCurrentSuite(String currentSuite) {
		this.currentSuite = currentSuite;
	}

	public String getCurrentTestcase() {
		return this.currentTestcase;
	}

	public void setCurrentTestcase(String currentTestcase) {
		this.currentTestcase = currentTestcase;
	}

	public String getCurrentApplication() {
		return this.currentApplication;
	}

	public void setCurrentApplication(String currentApplication) {
		this.currentApplication = currentApplication;
	}

	public String getCurrentFunctionality() {
		return this.currentFunctionality;
	}

	public void setCurrentFunctionality(String currentFunctionality) {
		this.currentFunctionality = currentFunctionality;
	}

	public void setCurrentTestDescription(String currentTestDescription) {
		this.currentTestDescription = currentTestDescription;
	}

	public String getCurrentTestDescription() {
		return this.currentTestDescription;
	}

	public IterationOptions getIterationMode() {
		return iterationMode;
	}

	public void setIterationMode(IterationOptions iterationMode) {
		this.iterationMode = iterationMode;
	}

	private int startIteration = 1;

	public int getStartIteration() {
		return startIteration;
	}

	public void setStartIteration(int startIteration) {
		if (startIteration > 0) {
			this.startIteration = startIteration;
		}
	}

	private int endIteration = 1;

	public int getEndIteration() {
		return endIteration;
	}

	public void setEndIteration(int endIteration) {
		if (endIteration > 0) {
			this.endIteration = endIteration;
		}
	}

	public String getCurrentPriority() {
		return this.currentTestPriority;
	}

	public void setCurrentPriority(String currentTestPriority) {
		this.currentTestPriority = currentTestPriority;
	}

	public String getCurrentRiskFactor() {
		return this.currentTestRiskFactor;
	}

	public void setCurrentRiskFactor(String currentTestRiskFactor) {
		this.currentTestRiskFactor = currentTestRiskFactor;
	}

	public Browser getBrowser() {
		return browser;
	}

	public void setBrowser(Browser browser) {
		this.browser = browser;
	}

	public String getBrowserVersion() {
		return browserVersion;
	}

	public void setBrowserVersion(String version) {
		this.browserVersion = version;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setLanguage(String language) {
		if (!language.equals(""))
			this.language = Language.valueOf(language);
		else
			this.language = Language.getDefault();
	}

	public Language getLanguage() {
		return language;
	}

	public String getTestCycleId() {
		return testCycleId;
	}

	public void setTestCycleId(String testCycleId) {
		this.testCycleId = testCycleId;
	}

	/*
	 * public String toString(){ StringBuilder sb = new StringBuilder();
	 * sb.append("currentSuite:        " + this.currentSuite + "\n");
	 * sb.append("currentTestcase:        " + this.currentTestcase + "\n");
	 * sb.append("currentApplication:        " + this.currentApplication +
	 * "\n"); sb.append("currentFunctionality:        " +
	 * this.currentFunctionality + "\n");
	 * sb.append("currentTestDescription:        " + this.currentTestDescription
	 * + "\n"); sb.append("currentTestPriority:        " +
	 * this.currentTestPriority + "\n");
	 * sb.append("currentTestRiskFactor:        " + this.currentTestRiskFactor +
	 * "\n");
	 * 
	 * sb.append("Language:        " + this.language + "\n");
	 * sb.append("Platform:        " + this.getPlatform() + "\n");
	 * sb.append("Browser:         " + this.getBrowser() + "\n");
	 * sb.append("Browser Version: " + this.getBrowserVersion() + "\n");
	 * sb.append("Cycle Id:        " + this.getTestCycleId() + "\n");
	 * 
	 * return sb.toString(); }
	 */
}
