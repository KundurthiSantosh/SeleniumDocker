package com.mm.api_demo_project.runners;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.mm.FrameworkUtilities.FrameWorkException;
import com.mm.FrameworkUtilities.Reporter;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "/Workspace/GCC Selenium Framework/selenium_java_framework/src/test/java/com/mm/api_demo_project/features/",
				 glue = { "com.mm.api_demo_project.stepdefinitions" }, 
				 plugin = { "pretty", "json:target/cucumber.json" }, 
				 dryRun = false, monochrome = true)
public class CucumberRunnerJUnit {
	Reporter reporter = new Reporter();
	/**
	 * This BeforeClass initializes Extent report
	 *
	 * @author Santosh Kundurthi
	 * @throws FrameWorkException 
	 * @throws IOException 
	 */
	@BeforeClass
	public void initializeExtentReport() throws FrameWorkException, IOException {
		System.out.println("******  Intializing Test Suite *******");		
		reporter.intializeReports();
		System.out.println("******  Test Suite is intialized sucessfully *******");
		reporter.startTest("Test");
	}

	/**
	 * This AfterClass closes and flushes Extent report
	 *
	 * @author Santosh Kundurthi
	 * @param scenario - Scenario object
	 */
	@AfterClass
	public void closeAndFlushExtentReport() {
		 reporter.closeTest();
		 reporter.flushReport();
	}
}
