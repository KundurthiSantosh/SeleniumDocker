package com.mm.api_demo_project.stepdefinitions;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonObject;
import com.mm.FrameworkUtilities.APIGlobals;
import com.mm.FrameworkUtilities.CommonUtils;
import com.mm.FrameworkUtilities.FrameWorkException;
import com.mm.FrameworkUtilities.PropertiesFileConfiguration;
import com.mm.FrameworkUtilities.RESTLib;
import com.mm.FrameworkUtilities.Reporter;
import com.mm.FrameworkUtilities.StepStatus;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitions {

	private static Logger log = LogManager.getLogger(StepDefinitions.class);
	Reporter reporter= new Reporter();
	RESTLib rest = new RESTLib();
	PropertiesFileConfiguration prop = PropertiesFileConfiguration.getInstance();
	
	@Given("^I want to test API \"([^\"]*)\" for test case \"([^\"]*)\"$")
	public void setAPIEndpointURL(String API, String testCaseName) throws FrameWorkException {
		APIGlobals.userData = CommonUtils.loadYAML(testCaseName.trim(), "TestData.yml");
		if (APIGlobals.userData.get("pathParameters") != null) {
			for (Map.Entry<String, Object> entry : APIGlobals.userData.get("pathParameters").entrySet())
				API += "/" + entry.getValue().toString();
		}
		if (APIGlobals.userData.get("queryParameters") != null) {
			API += "?";
			for (Map.Entry<String, Object> entry : APIGlobals.userData.get("queryParameters").entrySet())
				API += entry.getKey() + "=" + entry.getValue().toString();
		}
		APIGlobals.apiEndPointUri = String.format("%s%s", prop.getProperty("API_URL"), API);
		APIGlobals.testName = testCaseName;
		System.out.println("URL " + APIGlobals.apiEndPointUri);
		reporter.logEvent(StepStatus.Pass, "API URL is set to " + APIGlobals.apiEndPointUri);
		log.info("Cucumber Hostname URL is :: " + APIGlobals.apiEndPointUri);
		log.info("Cucumber Test case name is :: " + APIGlobals.testName);
	}

	@When("^I set header content type as \"([^\"]*)\"$")
	public void setHeader(String contentType) {
		if (contentType != null && !contentType.isEmpty()) {
			APIGlobals.CONTENT_TYPE = contentType;
			reporter.logEvent(StepStatus.Pass, "Content type is set to " + APIGlobals.CONTENT_TYPE);
			log.info("Content type is :: " + APIGlobals.CONTENT_TYPE);
		} else {
			reporter.logEvent(StepStatus.Fail, "Content type cannot be null or empty!");
			log.info("Content type cannot be null or empty!");
		}
	}

	@When("^I hit the API \"([^\"]*)\" of request type \"([^\"]*)\"$")
	public void submitRequest(String API, String requestType) {
		if (APIGlobals.userData.get("requestBody") != null) {
			JsonObject jsonObject = new JsonObject();
			for (Map.Entry<String, Object> entry : APIGlobals.userData.get("requestBody").entrySet())
				jsonObject.addProperty(entry.getKey(), (String) entry.getValue());
			APIGlobals.REQUESTBODY = jsonObject.toString();
		}
		switch (requestType) {
		case "GET":
			APIGlobals.response = rest.GETRequest();
			reporter.logEvent(StepStatus.Pass,
					"Response of " + requestType + " request is: " + APIGlobals.response.getBody().asPrettyString());
			break;

		case "POST":
			APIGlobals.response = rest.POSTRequest();
			reporter.logEvent(StepStatus.Pass,
					"Response of " + requestType + " request is: " + APIGlobals.response.getBody().asPrettyString());
			break;

		case "PUT":
			APIGlobals.response = rest.PUTRequest();
			reporter.logEvent(StepStatus.Pass,
					"Response of " + requestType + " request is: " + APIGlobals.response.getBody().asPrettyString());
			break;

		case "DELETE":
			APIGlobals.response = rest.DELETERequest();
			reporter.logEvent(StepStatus.Pass,
					"Response of " + requestType + " request is: " + APIGlobals.response.getBody().asPrettyString());
			break;

		default:
			reporter.logEvent(StepStatus.Fail, "Invalid request type!");
			log.info("Invalid request type!");
			break;
		}
	}

	@Then("^I verify that the status code is \"([^\"]*)\"$")
	public void verifyStatusCode(String expectedResponseCode) {
		if (expectedResponseCode.equals(String.valueOf(RESTLib.getResponseCode()))) {
			reporter.logEvent(StepStatus.Pass, "The actual response code of API matches to the expected response code");
			log.info("Status Code is " + expectedResponseCode);
		} else {
			reporter.logEvent(StepStatus.Fail, "The actual response code of API does not match the expected response code");
			log.info("Status Code is " + expectedResponseCode);
		}
	}

	@And("^I validated response against schema \"([^\"]*)\"$")
	public void verifySchema(String schemaFileName) {
		rest.validateSchema(schemaFileName);		
	}

	@And("^I validated the response values with \"([^\"]*)\"$")
	public void verifyValues(String POJOClass) {
		rest.verifyValues(POJOClass);		
	}
}
