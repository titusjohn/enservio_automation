package enservio.framework.globalfunctions;

import java.util.Properties;

import enservio.framework.testflowsetup.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.*;
import com.opera.core.systems.OperaDriver;

public class WebDriverFactory {
	private static Properties properties;

	/**
	 * Function to return the appropriate {@link RemoteWebDriver} object based
	 * on the {@link Browser} passed
	 * 
	 * @param browser
	 *            The {@link Browser} to be used for the test execution
	 * @return The {@link RemoteWebDriver} object corresponding to the
	 *         {@link Browser} specified
	 */
	public static RemoteWebDriver getDriver(Browser browser) {
		WebDriver driver = null;

		switch (browser) {
		case Chrome:
			properties = settings.getInstance();
			System.setProperty("webdriver.chrome.driver",
					properties.getProperty("ChromeDriverPath"));
			driver = new ChromeDriver();
			break;
		case Firefox:
			driver = new FirefoxDriver();
			break;
		case HtmlUnit:
			driver = new HtmlUnitDriver();
			break;
		case InternetExplorer:
			properties = settings.getInstance();
			System.setProperty("webdriver.ie.driver",
					properties.getProperty("InternetExplorerDriverPath"));
			driver = new InternetExplorerDriver();
			break;
		case Opera:
			driver = new OperaDriver();
			break;
		case NA:
			break;
		case Safari:
			break;
		default:
			properties = settings.getInstance();
			System.setProperty("webdriver.ie.driver",
					properties.getProperty("InternetExplorerDriverPath"));
			driver = new InternetExplorerDriver();
		}

		return (RemoteWebDriver) driver;
	}

}