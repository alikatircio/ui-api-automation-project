package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverManager {

   private static WebDriver driver;
   private static final Logger logger = LogManager.getLogger(DriverManager.class);

    public static WebDriver getDriver() {

        String browser = System.getProperty("browser", Config.getProperty("browser"));
        if (driver == null) {
           switch (browser.toLowerCase()) {

               case "chrome":
                   WebDriverManager.chromedriver().setup();
                   driver = new ChromeDriver();
                   logger.info("Chrome tarayıcısı başlatıldı.");

                   break;
               case "firefox":
                   WebDriverManager.firefoxdriver().setup();
                   driver = new FirefoxDriver();
                   logger.info("Firefox tarayıcısı başlatıldı.");

                   break;
               default:
                   throw new IllegalArgumentException("Chrome veya Firefox tarayıcılarını kullanınız.");
           }
       }
       return driver;
   }

   public static void quit() {

       if (driver != null) {
           driver.quit();
           driver = null;
           logger.info("Tarayıcı kapatıldı ve WebDriver sıfırlandı.");
       }
   }

}
