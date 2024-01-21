package com.gitlab.rmarzec.task;

import com.gitlab.rmarzec.framework.utils.DriverFactory;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import java.time.Duration;

public class Task3Test {
    public static final String TARGET_URL = "https://google.com";
    public static final String GOOGLE_ACCEPT_COOKIES_ID = "L2AGLb";
    public static final String SEARCH_PHRASE = "HTML select tag - W3Schools";
    public static final String SEARCHBOX_NAME = "q";
    public static final String LUCKYTRY_BUTTON_NAME = "btnI";
    public static final String EXPECTED_URL = "https://www.w3schools.com/tags/tag_select.asp";
    public static final String W3SCHOOLS_ACCEPT_COOKIES_ID = "accept-choices";
    public static final String XPATH_TRYIT_BUTTON = "//a[text()='Try it Yourself »']";
    public static final String FRAME_CONTENT = "iframeResult";
    public static final String HEADER_TAG = "h1";
    public static final String SELECT_CARS = "cars";
    public static final String CAR_BRAND= "opel";

    //Method handles click action of element which triggers new tab
    private void ChangeTabAfterElementClick(WebElement elementToClick, WebDriver webDriver)
    {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        //Store the ID of the original window
        String originalTab = webDriver.getWindowHandle();

        //Check we don't have other windows open already
        assert webDriver.getWindowHandles().size() == 1;

        //Click the link which opens in a new window
        elementToClick.click();

        //Wait for the new window or tab
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        //Loop through until we find a new window handle
        for (String tabHandle : webDriver.getWindowHandles()) {
            if(!originalTab.contentEquals(tabHandle)) {
                webDriver.switchTo().window(tabHandle);
                break;
            }
        }
    }
    @Test
    public void Task3Test() {
        DriverFactory driverFactory = new DriverFactory();
        WebDriver webDriver = driverFactory.initDriver();
        webDriver.get(TARGET_URL);
        //accept cookies
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(By.id(GOOGLE_ACCEPT_COOKIES_ID))).click();
        //type search phrase
        WebElement searchBox = webDriver.findElement(By.name(SEARCHBOX_NAME));
        searchBox.sendKeys(SEARCH_PHRASE);
        //click lucky try and verify the result
        wait.until(ExpectedConditions.elementToBeClickable(By.name(LUCKYTRY_BUTTON_NAME))).click();
        String expectedUrl = EXPECTED_URL ;
        if (!webDriver.getCurrentUrl().equals(expectedUrl)) {
            System.out.println(webDriver.getCurrentUrl());
            webDriver.get(expectedUrl);
        }
        //accept cookies
        wait.until(ExpectedConditions.elementToBeClickable(By.id(W3SCHOOLS_ACCEPT_COOKIES_ID))).click();
        //Try it yourself which redirects to new tab
        WebElement tryItYourselfButton = webDriver.findElement(By.xpath(XPATH_TRYIT_BUTTON));
        ChangeTabAfterElementClick(tryItYourselfButton,webDriver);
        //switch to frame with H1 section
        WebElement frame = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(FRAME_CONTENT)));
        webDriver.switchTo().frame(frame);
        WebElement selectElement = webDriver.findElement(By.tagName(HEADER_TAG));
        System.out.println(selectElement.getText());
        //choose opel from cars dropdown
        WebElement carsSelect = webDriver.findElement(By.id(SELECT_CARS));
        Select select = new Select(carsSelect);
        select.selectByValue(CAR_BRAND);
        WebElement selectedOption = select.getFirstSelectedOption();
        System.out.println(selectedOption.getText()+","+selectedOption.getAttribute("value"));

    }
}
