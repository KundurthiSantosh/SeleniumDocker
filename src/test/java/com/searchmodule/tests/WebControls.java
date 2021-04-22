package com.mm.WebUtilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.mm.FrameworkUtilities.PropertiesFileConfiguration;
import com.mm.TestConfig.TestCaseHelper;

/**
 * This class contains all generic methods to perform actions on elements and to verify elements status
 * @author 
 * @since 03-14-2021(1.0)
 * @version 1.0
 * @ModifiedBy
 * @ModifiedDate 
 */
public class WebControls extends com.mm.TestConfig.TestCaseHelper {
	public static Logger log = LogManager.getLogger(WebControls.class);// Logger Object
	PropertiesFileConfiguration properties= PropertiesFileConfiguration.getInstance();
	Sync sync= new Sync();

	//Sai
	public static boolean clickElement(WebElement element) {
		boolean bFlag= false;
			element.click();
			bFlag= true;
			return bFlag=true;
	}

	//Sai
	public boolean safeClickElement(By path, int waitTime){
		boolean bFlag= false;
		if(sync.isElementDisplayed(path, waitTime) && sync.isEnabled(path, waitTime)){
			WebElement ele= driver.findElement(path);
			ele.click();
		}
		return bFlag= true;
	}

	//Sai
	public boolean safeJavaScriptClick(By path, int waitTime){
		boolean bFlag= false;
		if(sync.isElementDisplayed(path, waitTime) && sync.isEnabled(path, waitTime)){
			WebElement ele= driver.findElement(path);
			((JavascriptExecutor) driver).executeScript("arguments[0].click()", ele);
			bFlag= true;
		}
		return bFlag= true;
	}

	//Sai
	public boolean safeEnterValue(By path,String sValue, int waitTime){
		boolean bFlag= false;
		if(sync.isElementDisplayed(path, waitTime) && sync.isEnabled(path, waitTime)){
			WebElement ele= driver.findElement(path);
			ele.sendKeys(sValue);
		}
		return bFlag=true;
	}

	//Sai
	public boolean safeClearandEnterValue(By path,String sValue, int waitTime){
		boolean bFlag= false;
		if(sync.isElementDisplayed(path, waitTime) && sync.isEnabled(path, waitTime)){
			WebElement ele= driver.findElement(path);
			ele.clear();
			ele.sendKeys(sValue);
		}
		return bFlag=true;
	}

	//Sai
	public String safeGetAttribute(By path, String sAttributeValue, int waitTime){
		String sReturnValue= null;
		if(sync.isElementDisplayed(path, waitTime)){
			WebElement ele= driver.findElement(path);
			sReturnValue= ele.getAttribute(sAttributeValue);
		}
		return sReturnValue;
	}

	//Sai
	public String safeGetText(By path, int waitTime){
		String sText= null;
		if(sync.isElementDisplayed(path, waitTime) && sync.isEnabled(path, waitTime)){
			WebElement ele= driver.findElement(path);
			sText= ele.getText().trim();
		}
		return sText;
	}

	//Sai
	public boolean safeSelectByVisibleText(By path, String sVisibleText, int waitTime){
		boolean bFlag= false;
		if(sync.isElementDisplayed(path, waitTime) && sync.isEnabled(path, waitTime)){
			WebElement ele= driver.findElement(path);
			Select select= new Select(ele);
			select.selectByVisibleText(sVisibleText);
			bFlag= true;
		}
		return bFlag;
	}

	//Sai
	public boolean safeSelectByIndex(By path, int iIndex, int waitTime){
		boolean bFlag= false;
		if(sync.isElementDisplayed(path, waitTime) && sync.isEnabled(path, waitTime)){
			WebElement ele= driver.findElement(path);
			Select select= new Select(ele);
			select.selectByIndex(iIndex);
			bFlag= true;
		}
		return bFlag;
	}

	//Sai
	public boolean safeSelecx(By path, String sValue, int waitTime){
		boolean bFlag= false;
		if(sync.isElementDisplayed(path, waitTime) && sync.isEnabled(path, waitTime)){
			WebElement ele= driver.findElement(path);
			Select select= new Select(ele);
			select.selectByValue(sValue);
			bFlag= true;
		}
		return bFlag;
	}

	//Sai
	public List<WebElement> getListofElements(By path, int waitTime){
		List<WebElement> elementList= null;
		boolean bFlag= sync.isElementPresent(path, waitTime);
		if(bFlag){
			elementList= driver.findElements(path);
		}
		return elementList;
	}

	//Sai- ScrollDown
	public void pageScrolldown(){
		JavascriptExecutor js= (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0, document.body.scrollHeight)");
	}

	public void pageScrollUntilElementIsViewed(By path, int waitTime){
		if(sync.isElementDisplayed(path, waitTime) && sync.isEnabled(path, waitTime)) {
			WebElement ele= driver.findElement(path);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView();", ele);
		}
	}

	// ---------------------------------------------------------------
	// Move from one element to another element
	public static boolean mouseHover(WebElement element) {
		boolean status = true;
		try {
			Actions actions = new Actions(driver);
			actions.moveToElement(element).build().perform();
		} catch (Exception e) {
			status = false;
		}
		return status;
	}



	// ---------------------------------------------------------------
	public static boolean verifyText(WebElement element, String text) {
		boolean status = true;
		try {
			status = scrollToElement(element);
			String appText = element.getText().trim();
			if (!appText.equals(text))
				status = false;
		} catch (Exception e) {
			status = false;
		}
		return status;
	}

	public static boolean isElementEditable(WebElement element) {
		boolean status;
		try {
			scrollToElement(element);
			status = ((element.getAttribute("disabled") != null) || (element.getAttribute("readonly") != null));
		} catch (Exception e) {
			status = false;
		}
		return status;
	}

	// ---------------------------------------------------------------
	public static String getCurrentDate() {
		String timeStamp = new SimpleDateFormat("MM.dd.yyyy").format(new Date());

		return timeStamp.replace(".", "/");
	}

	// ---------------------------------------------------------------
	public static String getPreviousDate() {
		DateFormat dateFormat = new SimpleDateFormat("MM.dd.yyyy");
		// String timeStamp = new SimpleDateFormat("MM.dd.yyyy");

		Date currentDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.add(Calendar.DAY_OF_YEAR, -1);

		Date previousDate = calendar.getTime();
		String pastDate = dateFormat.format(previousDate);
		return pastDate.replace(".", "/");
	}

	// ---------------------------------------------------------------
	public static String getFutureDate() {
		DateFormat dateFormat = new SimpleDateFormat("MM.dd.yyyy");
		// String timeStamp = new SimpleDateFormat("MM.dd.yyyy");

		Date currentDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.add(Calendar.DAY_OF_YEAR, +2);

		Date previousDate = calendar.getTime();
		String pastDate = dateFormat.format(previousDate);
		return pastDate.replace(".", "/");
	}

	// ---------------------------------------------------------------
	public static boolean scrollToElement(WebElement element) {
		boolean status = true;
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			Thread.sleep(3000);
		} catch (Exception e) {
			status = false;
		}
		return status;
	}
}
