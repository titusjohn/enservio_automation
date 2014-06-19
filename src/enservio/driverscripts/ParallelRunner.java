package enservio.driverscripts;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
//import java.util.Properties;

import enservio.framework.globalfunctions.*;
import enservio.framework.reports.*;

public class ParallelRunner implements Runnable {
	private pathsetup pathSetup = pathsetup.getInstance();
	private TestParameters testParameters;
	private Date startTime, endTime;
	private String testStatus;
	private Report report;
	private String sApptype;

	public ParallelRunner(TestParameters testParameters, Report report,
			String sApptype) {
		super();

		this.testParameters = testParameters;
		this.report = report;
		this.sApptype = sApptype;
	}

	public void run() {
		
		startTime = utilities.getCurrentTime();
		testStatus = invokeTestScript();
		endTime = utilities.getCurrentTime();

		String executionTime = utilities.getTimeDifference(startTime, endTime);
		report.updateResultSummary(testParameters.getCurrentSuite(),
				testParameters.getCurrentTestcase(),
				testParameters.getCurrentTestDescription(), executionTime,
				testStatus);
	}

	private String invokeTestScript() {
		if (pathSetup.getStopExecution()) {
			testStatus = "Aborted by user";
		} else {
			try {
				Class<?> testScriptClass;

				if (sApptype.equals("webapp")) {

					testScriptClass = Class.forName("enservio."
							+ "testautomation."
							+ "testscripts."
							+ sApptype
							+ "."
							+ testParameters.getCurrentApplication()
									.toLowerCase() + "."
							+ testParameters.getCurrentSuite().toLowerCase()
							+ "." + testParameters.getCurrentTestcase());

				} else {

					testScriptClass = Class.forName("enservio."
							+ "testautomation." + "testscripts." + sApptype
							+ "."
							+ testParameters.getCurrentSuite().toLowerCase()
							+ "." + testParameters.getCurrentTestcase());

				}

				Object testScript = testScriptClass.newInstance();

				Field testParameters = testScriptClass.getSuperclass()
						.getDeclaredField("testParameters");

				testParameters.setAccessible(true);
				testParameters.set(testScript, this.testParameters);

				Method driveTestExecution = testScriptClass.getMethod(
						"driveTestExecution", (Class<?>[]) null);

				driveTestExecution.invoke(testScript, (Object[]) null);

				Field testReport = testScriptClass.getSuperclass()
						.getDeclaredField("report");

				testReport.setAccessible(true);
				Report report = (Report) testReport.get(testScript);
				testStatus = report.getTestStatus();

			} catch (ClassNotFoundException e) {
				testStatus = "Reflection Error - ClassNotFoundException";
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				testStatus = "Reflection Error - IllegalArgumentException";
				e.printStackTrace();
			} catch (InstantiationException e) {
				testStatus = "Reflection Error - InstantiationException";
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				testStatus = "Reflection Error - IllegalAccessException";
				e.printStackTrace();
			} catch (SecurityException e) {
				testStatus = "Reflection Error - SecurityException";
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				testStatus = "Reflection Error - NoSuchFieldException";
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				testStatus = "Reflection Error - NoSuchMethodException";
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				testStatus = "Failed";
				e.printStackTrace();
			}
		}

		return testStatus;
	}

}