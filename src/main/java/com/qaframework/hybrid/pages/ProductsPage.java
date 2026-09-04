package com.qaframework.hybrid.pages;

import com.qaframework.hybrid.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class ProductsPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By searchInput = By.id("search_product");
    private final By searchBtn = By.id("submit_search");
    private final By productNames = By.xpath("//div[@class='productinfo text-center']/p");

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void navigateToProducts() {
        // Direct URL navigation avoids third-party redirect ad popups
        driver.get(ConfigReader.getProperty("base.url") + "/products");
        // Dismiss Google ads iframe if present
        dismissAdIfPresent();
    }

    private void dismissAdIfPresent() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("const ads = document.querySelectorAll('iframe[id^=\"aswift\"], .grippy-host'); ads.forEach(e => e.remove());");
        } catch (Exception ignored) {}
    }

    public void searchProduct(String productName) {
        dismissAdIfPresent();
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(searchInput));
        input.clear();
        input.sendKeys(productName);
        driver.findElement(searchBtn).click();
    }

    public List<String> getAllVisibleProductNames() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(productNames));
        return driver.findElements(productNames).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
}