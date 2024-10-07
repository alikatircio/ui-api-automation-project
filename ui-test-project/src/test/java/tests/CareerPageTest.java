package tests;

import base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CareerPage;
import pages.HomaPage;
import utils.Config;


public class CareerPageTest extends BaseTest {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Test
    public void testNavigationCareerPage() {

        driver.get(Config.getProperty("url"));
        logger.info("Insider anasayfasına gidildi.");
        HomaPage homaPage = new HomaPage(driver);
        homaPage.navToCareers();
        CareerPage careerPage = new CareerPage(driver);
        Assert.assertTrue(careerPage.careerSectionDisplayed(), "Career sayfasındaki bölümler görüntülenemedi.");
    }
}
