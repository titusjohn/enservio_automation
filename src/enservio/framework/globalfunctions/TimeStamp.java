package enservio.framework.globalfunctions;

import java.io.File;
import java.util.Properties;

import enservio.framework.globalfunctions.utilities;

public class TimeStamp {
	private static String timeStamp;

	public static synchronized String getInstance() {
		if (timeStamp == null) {
			pathsetup pathSetup = pathsetup.getInstance();
			Properties properties = settings.getInstance();

			timeStamp = pathSetup.getRunConfiguration()
					+ utilities.getFileSeparator()
					+ "Run_"
					+ utilities
							.getCurrentFormattedTime(
									properties.getProperty("DateFormatString"))
							.replace(" ", "_").replace(":", "-");

			String reportPathWithTimeStamp = pathSetup.getRelativePath()
					+ utilities.getFileSeparator() + "Results"
					+ utilities.getFileSeparator() + timeStamp;

			new File(reportPathWithTimeStamp).mkdirs();
			new File(reportPathWithTimeStamp + utilities.getFileSeparator()
					+ "Screenshots").mkdir();
		}

		return timeStamp;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

}