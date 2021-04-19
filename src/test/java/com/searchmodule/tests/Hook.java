package com.mm.api_demo_project.stepdefinitions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mm.FrameworkUtilities.Reporter;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

	private static Logger log = LogManager.getLogger(Hooks.class);
	Reporter reporter= new Reporter();

	public Hooks() {

	}

	/**
	 * This Before Hook reports start of the scenario in Extent report
	 *
	 * @author Santosh Kundurthi
	 * @param scenario
	 *            - Scenario object
	 */
	@Before
	public void reportScenarioStart(Scenario scenario) {
		// Reporter.logEvent("info", "Scenario " + scenario.getName() + "
		// execution started!");
		log.info("Scenario " + scenario.getName() + " execution started!");
		reporter.startTest(scenario.getName());
	}

	/**
	 * This After Hook reports end of the scenario in Extent report
	 *
	 * @author Santosh Kundurthi
	 * @param scenario
	 *            - Scenario object
	 */
	@After
	public void reportScenarioEnd(Scenario scenario) {
		// Reporter.logEvent("info", "Scenario " + scenario.getName() + "
		// execution ended!");
		log.info("Scenario " + scenario.getName() + " execution ended!");
		reporter.closeTest();
	}

}
