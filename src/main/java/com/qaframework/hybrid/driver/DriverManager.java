package com.qaframework.hybrid.driver;

import com.qaframework.hybrid.config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

public class DriverManager {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initDriver() {
        if (driver.get() == null) {
            String browser = System.getProperty("browser") != null 
                    ? System.getProperty("browser") 
                    : ConfigReader.getProperty("browser");

            boolean headless = Boolean.parseBoolean(
                    System.getProperty("headless") != null 
                    ? System.getProperty("headless") 
                    : ConfigReader.getProperty("headless"));

            switch (browser.toLowerCase()) {
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if (headless) firefoxOptions.addArguments("-headless");
                    driver.set(new FirefoxDriver(firefoxOptions));
                    break;
                case "chrome":
                default:
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    if (headless) {
                        chromeOptions.addArguments("--headless=new");
                        chromeOptions.addArguments("--no-sandbox");
                        chromeOptions.addArguments("--disable-dev-shm-usage");
                    }
                    chromeOptions.addArguments("--start-maximized");
                    driver.set(new ChromeDriver(chromeOptions));
                    break;
            }

            int waitSeconds = Integer.parseInt(ConfigReader.getProperty("implicit.wait"));
            getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(waitSeconds));
        }
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}