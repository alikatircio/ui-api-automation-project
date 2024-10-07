package utils;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class SeleniumHelper {

    private static final Logger logger = LogManager.getLogger(SeleniumHelper.class);

    public static void takeScreenshot(WebDriver driver, String testName) {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(srcFile, new File("screenshots/" + testName + ".png"));
            logger.info("Ekran görüntüsü alındı: " + testName);
        } catch (IOException e) {
            logger.error("Ekran görüntüsü alınırken hata oluştu: " + e.getMessage());
        }
    }

    public static void waitForElementToBeVisible(WebDriver driver, WebElement element) {

        Wait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(60))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class)
                .ignoring(ElementNotInteractableException.class);
        fluentWait.until(ExpectedConditions.visibilityOf(element));
        logger.info("Element görünür oldu: " + element.toString());
    }

    public static void waitForListElementToBeVisible(WebDriver driver, List<WebElement> element) {

        Wait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(60))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class)
                .ignoring(ElementNotInteractableException.class);
        fluentWait.until(ExpectedConditions.visibilityOfAllElements(element));
        logger.info("Element görünür oldu: " + element.toString());
    }

    public static void waitForElementToBeClickable(WebDriver driver, WebElement element) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        logger.info("Element tıklanılabilir oldu: " + element.toString());
    }

    public static void scrollToElement(WebDriver driver, WebElement element) {

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void documentReady(WebDriver driver) {

        Wait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class);

        fluentWait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    public static void waitForTextToBePresent(WebDriver driver, WebElement element, String text) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.textToBePresentInElement(element, text));
    }
}
