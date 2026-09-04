package com.qaframework.hybrid.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.qaframework.hybrid.steps", "com.qaframework.hybrid.hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber-report.html",
                "json:target/cucumber-reports/cucumber.json"
        },
        tags = "@hybrid or @api or @ui"
)
public class TestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}