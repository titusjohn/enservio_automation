package enservio.testautomation.testscripts.webapp.contentexpress.scenario1;

import org.junit.Test;
import org.openqa.selenium.By;

import enservio.framework.errorhandling.Status;
import enservio.framework.globalfunctions.TestScript;
import enservio.framework.testflowsetup.*;

public class TC1 extends TestScript {

	@Test
	public void runTC1() {
		testParameters.setIterationMode(IterationOptions.RunAllIterations);
		driveTestExecution();
	}

	@Override
	protected void executeTestCase() throws Exception {

		driver.get("http://dev.contentsexpress.lan/via2020webserver/qa/Login.aspx?LOGOFF=");

		if (driver.findElement(By.name("txtUserName")).isDisplayed()) {

			report.updateTestLog("Load URL",
					"URL for content express app loaded", Status.DONE);

			driver.findElement(By.name("txtUserName")).sendKeys(
					"tjohn@enservio.com");
			report.updateTestLog("UserName",
					"Entered the UserName successfully", Status.DONE);
			driver.findElement(By.name("txtPassword")).sendKeys(
					"tjohn@enservio.com");
			report.updateTestLog("Password",
					"Entered the Password successfully", Status.DONE);
			driver.findElement(By.name("btnSubmit")).click();
			report.updateTestLog("Login",
					"Clicked the Login button successfully", Status.DONE);

		}

		while (!driver.findElement(
				By.id("ctl00_ContentPlaceHolder1_btnNewClaim")).isDisplayed()) {

			driver.wait(5);
		}

		if (driver.findElement(By.id("ctl00_ContentPlaceHolder1_btnNewClaim"))
				.isDisplayed()) {

			report.updateTestLog("Success Login", "Logged in successfully",
					Status.DONE);

		} else {

			report.updateTestLog("Fail Login", "Failed to Login", Status.FAIL);
		}

	}

}