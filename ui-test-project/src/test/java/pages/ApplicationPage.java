package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utils.SeleniumHelper;

public class ApplicationPage {

    WebDriver driver;

    @FindBy(css = ".posting-headline h2")
    WebElement positionTitle;
    @FindBy(css = ".posting-headline div[class*='location']")
    WebElement positionLocation;
    @FindBy(css = ".posting-headline div[class*='department']")
    WebElement positionDepartment;

    public ApplicationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void viewRoleOnApplicationPage (String title, String department, String location) {

        Object[] windowHandles=driver.getWindowHandles().toArray();
        driver.switchTo().window((String) windowHandles[1]);
        try {
            SeleniumHelper.waitForElementToBeVisible(driver, positionTitle);
            Assert.assertTrue(driver.getCurrentUrl().contains("lever.co"), "url: "+driver.getCurrentUrl() +" exp: lever.co");
            Assert.assertEquals(positionTitle.getText(), title);
            Assert.assertTrue(positionDepartment.getText().contains(department), "expected: "+positionDepartment.getText() +" contains: "+ department);
            Assert.assertEquals(positionLocation.getText(), location);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
