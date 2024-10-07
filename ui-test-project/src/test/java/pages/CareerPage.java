package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.SeleniumHelper;

public class CareerPage {

    WebDriver driver;

    @FindBy(id = "career-find-our-calling")
    WebElement teamSection;
    @FindBy(id = "career-our-location")
    WebElement locationSection;
    @FindBy(css = "section[data-id = 'a8e7b90'] h2")
    WebElement lifeInsiderSection;

    public CareerPage (WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    public boolean careerSectionDisplayed() {

        SeleniumHelper.waitForElementToBeVisible(driver, lifeInsiderSection);
        return teamSection.isDisplayed() && locationSection.isDisplayed() &&
                lifeInsiderSection.isDisplayed() && lifeInsiderSection.getText().equals("Life at Insider");
    }

}
