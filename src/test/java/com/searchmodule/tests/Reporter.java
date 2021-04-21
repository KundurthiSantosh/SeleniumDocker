package com.mm.FrameworkUtilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mm.TestConfig.TestCaseHelper;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Reporter extends TestCaseHelper
{	
	public static ExtentReports extent;
	public static ExtentTest test;
	public static String resultfile;

	public String sResultFolderPath= null;

	PropertiesFileConfiguration properties= PropertiesFileConfiguration.getInstance();

	//==================================================================	
	public void intializeReports() throws FrameWorkException, IOException {
		sResultFolderPath = createResultFolder();
		resultfile = sResultFolderPath + RESULTS_FILE;
		extent = new ExtentReports(resultfile);
		
		properties.loadPropertiesFile();
		
		String sProjectName= properties.getProperty("ProjectName").trim();
		String sEnvironment= properties.getProperty("Environment").trim();
		String sReleaseName= properties.getProperty("ReleaseName").trim();

		extent.addSystemInfo("Project Name", sProjectName)
		.addSystemInfo("Environment", sEnvironment)
		.addSystemInfo("Release Name",sReleaseName);

		//extent.loadConfig(new File("extent.xml"));		
	}
	//==================================================================
	public void startTest(String testcasename)
	{		
		test = extent.startTest(testcasename);
	}	
	//==================================================================
	public void closeTest()
	{
		extent.endTest(test);		
	}
	//==================================================================
	public void flushReport()
	{
		extent.flush();
	}
	//==================================================================	
	public void logEvent(StepStatus stepStatus, String stepDescription)
	{		
		switch (stepStatus)
		{
		case Pass:
			test.log(LogStatus.PASS, stepDescription);
			break;

		case PassWithScreenShot:
			test.log(LogStatus.PASS, stepDescription+ test.addScreenCapture(getScreenShot()));
			break;

		case Fail:
			test.log(LogStatus.FAIL, stepDescription);
			break;

		case FailWithScreenShot:
			test.log(LogStatus.FAIL, stepDescription+ test.addScreenCapture(getScreenShot()));
			break;

		case Warning:
			test.log(LogStatus.WARNING, stepDescription);
			break;

		case WarningWithScreenShot:
			test.log(LogStatus.WARNING, stepDescription + test.addScreenCapture(getScreenShot()));
			break;

		case Info:
			test.log(LogStatus.INFO, stepDescription);
			break;

		case InfoWithScreenShot:
			test.log(LogStatus.INFO, stepDescription +test.addScreenCapture(getScreenShot()));
			break;

		default:
			System.out.println("Invalid reporting status");
			break;
		}		
	}
	//==================================================================
	//public static void log(boolean status, String passMessage, String failMessage)
	//{
	//if(status)
	//logEvent("pass", passMessage);
	//else
	//logEvent("fail", failMessage);
	//}
	//==================================================================	
	public static String getScreenShot()
	{	
		TakesScreenshot sht = (TakesScreenshot) driver;       

		String screenshotfile = sht.getScreenshotAs(OutputType.BASE64);

		return "data:image/png;base64,"+ screenshotfile;
	}
	//==================================================================	

	public static String createResultFolder()
	{		
		Date date = new Date();

		//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());		
		File file = new File(RESULTS_PATH + timeStamp.replace(".", "-"));

		if(!file.exists())		
			file.mkdirs();

		String folderpath = RESULTS_PATH + timeStamp.replace(".", "-");

		return folderpath;	
	}
	//==================================================================
	public void log(StepStatus stepStatus, String stepDescription){
		logEvent(stepStatus, stepDescription);
	}

	/**
	 * 
	 * @author ot02123
	 * @return extents-report folder path
	 */
	public String getReportPath(){
		return sResultFolderPath;
	}

	/**
	 * @author ot02123
	 * @Modified: 
	 * @throws IOException
	 * @throws URISyntaxException
	 * 
	 */
	public void openReportinDefaultBrowser() throws IOException, URISyntaxException{
		File reportPath= new File(getReportPath()+"\\SC_SummaryReport.html");
		Desktop.getDesktop().browse(reportPath.toURI());
	}
}
