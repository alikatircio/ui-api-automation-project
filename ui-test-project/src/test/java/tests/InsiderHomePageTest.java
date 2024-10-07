package tests;

import base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Config;

public class InsiderHomePageTest extends BaseTest {

    private  final Logger logger = LogManager.getLogger(this.getClass());

    @Test
    public void testHomePage() {

        driver.get(Config.getProperty("url"));
        logger.info("Insider anasayfasına gidildi.");
        Assert.assertTrue(driver.getTitle().contains("Insider"), "Insider anasayfa açılmadı.");
    }
}
