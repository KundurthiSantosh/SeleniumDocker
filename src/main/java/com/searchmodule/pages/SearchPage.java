package com.searchmodule.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class SearchPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(name="q")
    private WebElement searchTxt;

    @FindBy(name="btnK")
    private WebElement searchBtn;

    @FindBy(xpath = "//a[contains(text(),'Videos')]")
    private WebElement videosLink;

    @FindBy(className = "fWhgmd")
    private List<WebElement> allVideos;

    public SearchPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 30);
        PageFactory.initElements(driver, this);
    }

    public void goTo(){
        this.driver.get("https://google.com/");
    }

    public void doSearch(String keyword){
        this.wait.until(ExpectedConditions.visibilityOf(this.searchTxt));
        this.searchTxt.sendKeys(keyword);
        this.searchBtn.click();
    }

    public void goToVideos(){
        this.wait.until(ExpectedConditions.visibilityOf(this.videosLink));
        this.videosLink.click();
    }

    public int getResult(){
        By by = By.className("fWhgmd");
        this.wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(by, 0));
        System.out.println("Search Result : " + this.allVideos.size());
        return this.allVideos.size();
    }




}
