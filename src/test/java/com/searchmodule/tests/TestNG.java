package com.mm.api_demo_project.runners;

import java.io.IOException;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.mm.FrameworkUtilities.FrameWorkException;
import com.mm.FrameworkUtilities.Reporter;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/java/com/mm/api_demo_project/features", glue = {
		"com.mm.api_demo_project.stepdefinitions" }, plugin = { "pretty", "json:target/cucumber.json" })

public class CucumberRunnerTestNG extends AbstractTestNGCucumberTests {

	Reporter reporter = new Reporter();

	/**
	 * This BeforeClass initializes Extent report
	 *
	 * @author Santosh Kundurthi
	 * @throws FrameWorkException
	 * @throws IOException
	 */
	@BeforeSuite
	public void initializeExtentReport() throws FrameWorkException, IOException {
		System.out.println("****** Intializing Test Suite *******");
		reporter.intializeReports();
		System.out.println("****** Test Suite is intialized sucessfully*******");
	}

	/**
	 * This AfterClass closes and flushes Extent report
	 *
	 * @author Santosh Kundurthi
	 * 
	 */
	@AfterSuite
	public void closeAndFlushExtentReport() {
		reporter.flushReport();
	}
}
