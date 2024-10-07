package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.SeleniumHelper;

public class HomaPage {

    WebDriver driver;

    @FindBy(xpath = "//a[contains(text(),'Company')]")
    WebElement elementCompany;

    @FindBy(xpath = " //a[text()='Careers']")
    WebElement elementCareers;

    public HomaPage(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void navToCareers() {

        SeleniumHelper.waitForElementToBeVisible(driver, elementCompany);
        elementCompany.click();
        SeleniumHelper.waitForElementToBeVisible(driver, elementCareers);
        elementCareers.click();
    }
}
