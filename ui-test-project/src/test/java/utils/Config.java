package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Config {

    private static Properties properties;
    private static final Logger logger = LogManager.getLogger(Config.class);


    static {
        try {
            FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
            properties = new Properties();
            properties.load(fis);
            logger.info("config.properties dosyası yüklendi.");
        } catch (IOException e) {
            logger.error("config.properties dosyası yüklenirken hata oluştu: " + e.getMessage());
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
