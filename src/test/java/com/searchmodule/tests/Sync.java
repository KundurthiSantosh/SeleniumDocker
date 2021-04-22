package com.mm.WebUtilities;

import com.mm.TestConfig.TestCaseHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * This class contains element based synchronization methods
 * @author mm02123
 * @since 03-14-2021(1.0)
 * @version 1.0
 * @ModifiedBy
 * @ModifiedDate 
 */
public class Sync extends TestCaseHelper {

	/**
	 * This method waits till specified amount of time element is displayed on UI
	 * @param path :Element path
	 * @param waitTime :Waiting time
	 * @return True- if element is displayed on UI
	 * @author mm02123
	 * @since 03-14-2021
	 * @ModifiedBy
	 * @ModifiedDate
	 */
	public boolean isElementDisplayed(By path, int waitTime){
		nullifyImplicitWait();
		boolean bFlag= false;

		Wait wait= new WebDriverWait(driver, waitTime);
		wait.until(ExpectedConditions.visibilityOfElementLocated(path));

		WebElement ele= driver.findElement(path);
		bFlag =ele.isDisplayed();

		return bFlag;
	}

	/**
	 * This method waits till specified amount of time element is displayed on DOM
	 * @param path :Element path
	 * @param waitTime :Waiting time
	 * @return True- if element is present on DOM
	 * @author mm02123
	 * @since 03-14-2021
	 * @ModifiedBy
	 * @ModifiedDate
	 */
	public boolean isElementPresent(By path, int waitTime){
		nullifyImplicitWait();
		boolean bFlag= false;

		Wait wait= new WebDriverWait(driver, waitTime);
		wait.until(ExpectedConditions.presenceOfElementLocated(path));

		WebElement ele= driver.findElement(path);
		int iSize= driver.findElements(path).size();
		if (iSize> 0)
			bFlag= true;
		else
			bFlag= false;

		return bFlag;
	}

	/**
	 * This method waits till element is enabled
	 * @param path :Element path
	 * @param waitTime :Waiting time
	 * @return True- Element is enabled. False- Element is disabled
	 * @author mm02123
	 * @since 03-14-2021
	 * @ModifiedBy
	 * @ModifiedDate
	 */
	public boolean isEnabled(By path, int waitTime){
		boolean bElementDisplayFlag= isElementDisplayed(path, waitTime);
		boolean bEnableFlag= false;
		WebElement ele= null;
		if(bElementDisplayFlag){
			ele= driver.findElement(path);
			bEnableFlag= ele.isDisplayed();
		}
		return bEnableFlag;
	}

	/**
	 * This method waits till specified amount of time element is displayed on UI
	 * @param path :Element path
	 * @param waitTime :Waitig time
	 * @return Web Element
	 * @author mm02123
	 * @since 03-14-2021
	 * @ModifiedBy
	 * @ModifiedDate
	 */
	public WebElement getWebElement(By path, int waitTime){
		WebElement ele = null;
		if(isElementPresent(path, waitTime)){
			ele= driver.findElement(path);
		}
		return ele;
	}

	/**
	 * This method is to nullify implicit wait
	 * @author mm02123
	 * @since 03/14/2021
	 * @ModifeidBy
	 * @ModifiedDate
	 */
	private void nullifyImplicitWait(){
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
	}


}
