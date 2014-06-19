package enservio.framework.globalfunctions;

import java.util.Properties;
import org.openqa.selenium.WebDriver;
import enservio.framework.reports.*;

public abstract class ReusableLibrary {
	/**
	 * The {@link DataTable} object (passed from the test script)
	 */
	protected DataTable dataTable;
	/**
	 * The {@link SeleniumReport} object (passed from the test script)
	 */
	protected SeleniumReport report;
	/**
	 * The {@link WebDriver} object
	 */
	protected WebDriver driver;
	/**
	 * The {@link ScriptHelper} object (required for calling one reusable
	 * library from another)
	 */
	protected ScriptHelper scriptHelper;

	/**
	 * The {@link Properties} object with settings loaded from the framework
	 * properties file
	 */
	protected Properties properties = settings.getInstance();
	/**
	 * The {@link FrameworkParameters} object
	 */
	protected pathsetup pathSetup = pathsetup.getInstance();

	/**
	 * Constructor to initialize the {@link ScriptHelper} object and in turn the
	 * objects wrapped by it
	 * 
	 * @param scriptHelper
	 *            The {@link ScriptHelper} object
	 * @param driver
	 *            The {@link WebDriver} object
	 */
	public ReusableLibrary(ScriptHelper scriptHelper) {
		this.scriptHelper = scriptHelper;

		this.dataTable = scriptHelper.getDataTable();
		this.report = scriptHelper.getReport();
		this.driver = scriptHelper.getDriver();
	}
}