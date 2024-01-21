package com.gitlab.rmarzec.task;

import com.gitlab.rmarzec.framework.utils.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import java.util.List;
import java.time.Duration;


public class Task2Test {

    public static final String TARGET_URL = "https://pl.wikipedia.org/wiki/Wiki";
    public static final String LANG_BUTTON_ID = "p-lang-btn-checkbox";
    public static final String CSS_LANG_LIST_CONTAINER = ".row.uls-language-list.uls-lcd";
    public static final String LI_TAG = "li";
    public static final String LANGUAGE = "English";
    public static final String A_TAG = "a";
    public static final String HREF_TAG = "href";

    @Test
    public void Task2Test(){
        DriverFactory driverFactory = new DriverFactory();
        WebDriver webDriver = driverFactory.initDriver();
        webDriver.get(TARGET_URL);
        //clicking the button which opens the list of languages
        WebElement languages = webDriver.findElement(By.id(LANG_BUTTON_ID));
        languages.click();
        //downloading languages to List<WebElement> and print all elements
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(1));
        WebElement languageListContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CSS_LANG_LIST_CONTAINER)));
        List<WebElement> languagesList = languageListContainer.findElements(By.tagName(LI_TAG));
        for(WebElement language : languagesList) {
            System.out.println(language.getText());
            //printing additionally url address for "English" language
            if(language.getText().equals(LANGUAGE))
                System.out.println(language.findElement(By.tagName(A_TAG)).getAttribute(HREF_TAG));

        }
    }
}
