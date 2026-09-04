package com.qaframework.hybrid.hooks;

import com.qaframework.hybrid.config.ConfigReader;
import com.qaframework.hybrid.driver.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {

    @Before("@ui or @hybrid")
    public void setupDriver() {
        DriverManager.initDriver();
        DriverManager.getDriver().get(ConfigReader.getProperty("base.url"));
    }

    @After("@ui or @hybrid")
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed() && DriverManager.getDriver() != null) {
            byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Failure_Screenshot");
        }
        DriverManager.quitDriver();
    }
}