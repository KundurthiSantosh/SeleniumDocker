package com.mm.FrameworkUtilities;

import java.io.File;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.mm.TestConfig.TestCaseHelper;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RESTLib {

	private static Logger log = LogManager.getLogger(RESTLib.class);
	Reporter reporter = new Reporter();
	JSONObjectIterator jsonObjectIterator = new JSONObjectIterator();

	/**
	 * This function returns a Response of the GET request.
	 *
	 * @author Santosh Kundurthi
	 * @param uri
	 *            - uri of request
	 * @return Response of GET request
	 */
	public Response GETRequest() {
		RequestSpecification requestSpecification = RestAssured.given();
		requestSpecification.header("Content-Type", APIGlobals.CONTENT_TYPE);
		return requestSpecification.get(APIGlobals.apiEndPointUri);
		
	}

	/**
	 * This function returns a Response of the PUT request.
	 * 
	 * @author Santosh Kundurthi
	 * @param uri
	 *            - uri of request
	 * @param strJSONPayload
	 *            - payload for PUT request in string format
	 * @return Response of PUT request
	 */
	public Response PUTRequest() {
		RequestSpecification requestSpecification = RestAssured.given();
		requestSpecification.header("Content-Type", APIGlobals.CONTENT_TYPE);
		if (APIGlobals.REQUESTBODY.length() > 0) {
			requestSpecification.body(APIGlobals.REQUESTBODY);
			return requestSpecification.put(APIGlobals.apiEndPointUri);
		} else {
			reporter.logEvent(StepStatus.Fail, "Request Body cannot be null or empty!");
			log.info(" Request Body cannot be null or empty!");
			return null;
		}
	}

	/**
	 * This function returns a Response of the DELETE request.
	 * 
	 * @author Santosh Kundurthi
	 * @param uri
	 *            - uri of request
	 * @param strJSONPayload
	 *            - payload for DELETE request in string format
	 * @return Response of DELETE request
	 */
	public Response DELETERequest() {
		RequestSpecification requestSpecification = RestAssured.given();
		requestSpecification.header("Content-Type", APIGlobals.CONTENT_TYPE);
		if (APIGlobals.REQUESTBODY.length() > 0) {
			requestSpecification.body(APIGlobals.REQUESTBODY);
			return requestSpecification.delete(APIGlobals.apiEndPointUri);
		} else {
			reporter.logEvent(StepStatus.Fail, "Request Body cannot be null or empty!");
			log.info(" Request Body cannot be null or empty!");
			return null;
		}
	}

	/**
	 * This function returns a Response of the POST request.
	 *
	 * @author Santosh Kundurthi
	 * @param uri
	 *            - uri of request
	 * @param strJSONPayload
	 *            - payload for POST request in string format
	 * @return Response of POST request
	 */
	public Response POSTRequest() {
		RequestSpecification requestSpecification = RestAssured.given();
		requestSpecification.header("Content-Type", APIGlobals.CONTENT_TYPE);
		if (APIGlobals.REQUESTBODY.length() > 0) {
			requestSpecification.body(APIGlobals.REQUESTBODY);
			return requestSpecification.post(APIGlobals.apiEndPointUri);
		} else {
			reporter.logEvent(StepStatus.Fail, "Request Body cannot be null or empty!");
			log.info(" Request Body cannot be null or empty!");
			return null;
		}
	}

	/**
	 * This function takes schema file and validates response against given
	 * schema file.
	 *
	 * @author Santosh Kundurthi
	 * @param schemaFile
	 *            - file name of Schema
	 * 
	 */
	public void validateSchema(String schemaFile) {

		try {
			JsonSchemaValidator validator = JsonSchemaValidator
					.matchesJsonSchema(new File(TestCaseHelper.TESTDATA_PATH + "Schema\\" + schemaFile));
			APIGlobals.response.then().body(validator);
			log.debug("Json schema validation is ok");
			reporter.logEvent(StepStatus.Pass, "Schema validation matches with Schema file");
		} catch (Exception oEx) {
			log.error("json schema validation failed");
			reporter.logEvent(StepStatus.Fail, "Schema validation failed with exception: " + oEx.getLocalizedMessage());
		}
	}

	/**
	 * This function takes POJO Class and converts response object to POJO
	 * object
	 *
	 * @author Santosh Kundurthi
	 * @param POJOClass
	 * 
	 * 
	 */
	public void verifyValues(String POJOClass) {
		try {
			if (APIGlobals.userData.get("responseValues") != null) {
				jsonObjectIterator.handleJSONObject(new JSONObject(new Gson().toJson(
						APIGlobals.response.getBody().as(Class.forName("com.mm.api_demo_project.responses." + POJOClass)))));
				for (Map.Entry<String, Object> entry : APIGlobals.userData.get("responseValues").entrySet())
					if (entry.getValue().toString().equals(jsonObjectIterator.getValueFromKey(entry.getKey())))
						reporter.logEvent(StepStatus.Pass, "Value " + entry.getKey() + " from response is as expected "
								+ entry.getValue().toString());
					else
						reporter.logEvent(StepStatus.Fail,
								"Actual value for " + entry.getKey() + " from response "
										+ jsonObjectIterator.getValueFromKey(entry.getKey())
										+ " did not match with expected value " + entry.getValue().toString());

			} else
				reporter.log(StepStatus.Fail, "No Response values provided to validate for " + APIGlobals.testName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This function returns response code
	 *
	 * @author Santosh Kundurthi
	 * 
	 * @return Response Code
	 * 
	 */
	public static int getResponseCode() {
		return APIGlobals.response.getStatusCode();
	}
}
