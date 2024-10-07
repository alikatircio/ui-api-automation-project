package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverManager;
import utils.SeleniumHelper;

import java.time.Duration;
import java.util.List;

public class FindJob {

    protected Logger logger = LogManager.getLogger(this.getClass());

    WebDriver driver;
    public String jobTitle = "";
    @FindBy(xpath = "//a[text() = 'See all QA jobs']")
    WebElement seeAllJobs;
    @FindBy(id = "filter-by-location")
    WebElement filterLoction;
    @FindBy(css = "div[class*='position-list-item'][data-location]")
    List<WebElement> jobList;
    @FindBy(id = "career-position-list")
    WebElement careerPositonList;
    @FindBy (css = "p[class*='position-title']")
    List<WebElement> positionTitle;
    @FindBy (css = "div[class*='position-list'] span[class*='position-department']")
    List<WebElement> positionDepartment;
    @FindBy (css = "[class*='position-location']")
    List<WebElement> positionLocation;
    @FindBy (id = "wt-cli-accept-all-btn")
    WebElement cookiesAcceptBtn;
    @FindBy (css = "span[id*='filter-by-location']")
    WebElement locationCombobox;
    @FindBy(css = "#select2-filter-by-location-results li")
    List<WebElement> locationValueList;
    @FindBy(xpath = "//a[text()='View Role']")
    List<WebElement> viewRoleButton;

    public FindJob(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void seeAllJobs() {
        SeleniumHelper.waitForElementToBeClickable(driver, cookiesAcceptBtn);
        cookiesAcceptBtn.click();
        seeAllJobs.click();
    }

    public void selectLocationAndDepartment(String location) {

        SeleniumHelper.scrollToElement(driver, careerPositonList);
        SeleniumHelper.waitForElementToBeClickable(driver, filterLoction);
        SeleniumHelper.waitForTextToBePresent(driver, filterLoction, location);
        locationCombobox.click();
        for (WebElement element : locationValueList) {

            if (element.getText().equals(location)) {
                element.click();
                break;
            }
        }
    }

    public boolean isJobListLoaded() {

        Capabilities capabilities = ((RemoteWebDriver) driver).getCapabilities();
        String browserName = capabilities.getBrowserName();
        WebDriverWait wait;
        if (browserName.equalsIgnoreCase("chrome")) {

            wait = new WebDriverWait(driver, Duration.ofMillis(2000));
            wait.until(ExpectedConditions.invisibilityOfAllElements(jobList));
        }
        wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        wait.until(ExpectedConditions.visibilityOfAllElements(jobList));
        return !jobList.isEmpty();
    }

    public boolean verifyJobDetails(String titleAndDepartment, String location) {

        if (!jobList.isEmpty()) {

            for (int i = 0; i < jobList.size(); i++) {

                if (positionTitle.get(i).getText().contains(titleAndDepartment) &&
                        positionDepartment.get(i).getText().equals(titleAndDepartment) &&
                        positionLocation.get(i).getText().equals(location)) {

                    jobTitle = positionTitle.get(i).getText();
                    Actions action = new Actions(driver);
                    action.moveToElement(positionLocation.get(i)).build().perform();
                    viewRoleButton.get(i).click();
                    return true;
                }
            }
        }
        return false;

    }
}
