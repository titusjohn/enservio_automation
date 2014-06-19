package enservio.framework.globalfunctions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import enservio.framework.errorhandling.setupexceptions;

public class settings {
	private static Properties properties;

	private int logLevel = 4;
	public Boolean generateExcelReports = Boolean.valueOf(true);
	public Boolean generateHtmlReports = Boolean.valueOf(true);
	public Boolean takeScreenshotFailedStep = Boolean.valueOf(true);
	public Boolean takeScreenshotPassedStep = Boolean.valueOf(false);
	private String dateFormatString = "dd-MMM-yyyy hh:mm:ss a";

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

	public static synchronized Properties getInstance() {
		if (properties == null) {
			loadFromPropertiesFile();
		}
		return properties;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	private static void loadFromPropertiesFile() {
		pathsetup pathSetup = pathsetup.getInstance();
		if (pathSetup.getRelativePath() == null) {
			throw new setupexceptions("TestParameters.relativePath is not set!");
		}
		properties = new Properties();
		try {
			properties.load(new FileInputStream(pathSetup.getRelativePath()
					+ utilities.getFileSeparator() + "datarepository"
					+ utilities.getFileSeparator() + "masterdata"
					+ utilities.getFileSeparator()
					+ "Global Settings.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new setupexceptions(
					"FileNotFoundException while loading the Global Settings file");
		} catch (IOException e) {
			e.printStackTrace();
			throw new setupexceptions(
					"IOException while loading the Global Settings file");
		}
	}

}
