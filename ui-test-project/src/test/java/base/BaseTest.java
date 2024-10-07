package base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.DriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import utils.SeleniumHelper;

import java.nio.file.Paths;

public class BaseTest {

    protected WebDriver driver;
    protected Logger logger = LogManager.getLogger(this.getClass());


    @BeforeSuite
    public void setUpSuite() {
    }

    @BeforeMethod
    public void setup() {

        driver = DriverManager.getDriver();
        driver.manage().window().maximize();
        logger.info("Tarayıcı başlatıldı ve pencere maksimize edildi.");
        Allure.label("TestCase",this.getClass().getSimpleName());
    }

    @AfterMethod
    public void tearDown(ITestResult testResult) {

        if (ITestResult.FAILURE == testResult.getStatus()) {
            logger.error("Test başarısız oldu: " + testResult.getName());
            SeleniumHelper.takeScreenshot(driver, testResult.getName());
        }
        DriverManager.quit();
    }

}
