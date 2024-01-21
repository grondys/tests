package com.gitlab.rmarzec.task;
import com.gitlab.rmarzec.framework.utils.DriverFactory;
import com.gitlab.rmarzec.model.YTTile;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;


public class Task4Test {
    public static final String TARGET_URL = "https://youtube.com";
    public static final String XPATH_YOUTUBE_ACCEPT_COOKIES = "//button[contains(@aria-label,'Accept')]";
    public static final String XPATH_GRID_ROW = "//ytd-rich-grid-row";
    public static final String XPATH_LIST_ELEMENTS= "//ytd-rich-item-renderer[@class='style-scope ytd-rich-grid-row']";
    public static final String XPATH_TITLE = "//yt-formatted-string[@id='video-title']";
    public static final String CHANNEL_NAME_ID = "text";
    public static final String XPATH_TIME = "//span[@id='text']";
    public static final String LIVE_CHANNEL = "LIVE";
    @Test
    public void Task4Test(){
        DriverFactory driverFactory = new DriverFactory();
        WebDriver webDriver = driverFactory.initDriver();
        webDriver.get(TARGET_URL);
        //accept cookies
        WebDriverWait wait = new WebDriverWait(webDriver, 5);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(XPATH_YOUTUBE_ACCEPT_COOKIES))).click();
        //read all rows with tiles and get all tiles from each particular row
        List<WebElement> gridRows = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(XPATH_GRID_ROW)));
        List<WebElement> tilesList = new ArrayList<>();
        for(WebElement we: gridRows) {
            List<WebElement> listElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(XPATH_LIST_ELEMENTS)));
            tilesList.addAll(listElements);
        }

        //result tiles list
        List<YTTile> ytTileList = new ArrayList<YTTile>();
        //if there is at least 12 tiles iterate through them and pass the values to ytTileList
        int tileListCount = Math.min(tilesList.size(), 12);
        for(WebElement tile : tilesList.subList(0,tileListCount)) {
            WebElement title = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_TITLE)));
            WebElement channelName = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(CHANNEL_NAME_ID)));
            WebElement time = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_TIME)));

            YTTile ytTile = new YTTile();
            ytTile.setTitle(title.getText());
            ytTile.setChannel(channelName.getText());
            ytTile.setLength(time.getText());
            ytTileList.add(ytTile);
        }
        //iterate through results from ytTile and print only those are not LIVE
        for(YTTile ytTile : ytTileList)
            if(!ytTile.getLength().equals(LIVE_CHANNEL))
                System.out.println(ytTile.getTitle()+" "+ytTile.getLength());

    }
}
