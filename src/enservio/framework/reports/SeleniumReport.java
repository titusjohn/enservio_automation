package enservio.framework.reports;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import enservio.framework.errorhandling.*;

public class SeleniumReport extends Report {
	private WebDriver driver;

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * Constructor to initialize the Report
	 * 
	 * @param reportSettings
	 *            The {@link ReportSettings} object
	 * @param reportTheme
	 *            The {@link ReportTheme} object
	 */
	public SeleniumReport(ReportSettings reportSettings, ReportTheme reportTheme) {
		super(reportSettings, reportTheme);
	}

	@Override
	protected void takeScreenshot(String screenshotPath) {
		if (driver == null) {
			throw new setupexceptions("Report.driver is not initialized!");
		}

		File scrFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);

		try {
			FileUtils.copyFile(scrFile, new File(screenshotPath), true);
		} catch (IOException e) {
			e.printStackTrace();
			throw new setupexceptions("Error while writing screenshot to file");
		}
	}

}