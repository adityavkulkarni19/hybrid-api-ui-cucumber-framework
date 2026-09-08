# Hybrid API + UI Cucumber Framework

![Status](https://img.shields.io/badge/status-in%20progress-yellow)
![Java](https://img.shields.io/badge/Java-11-orange)
![Selenium](https://img.shields.io/badge/Selenium-4.44-green)
![Cucumber](https://img.shields.io/badge/Cucumber-7.18-brightgreen)
![REST Assured](https://img.shields.io/badge/REST%20Assured-5.5-blue)

A BDD test automation framework combining REST API testing (REST Assured) and UI testing (Selenium WebDriver) under Cucumber. Work in progress — this README documents the planned architecture and current build status.

## Goal

Most portfolio automation projects test UI only. This framework is being built to demonstrate the pattern used in real QA teams: verifying the same feature from both the API layer and the UI layer in a single suite, driven by Gherkin feature files.

## Planned tech stack

| Concern            | Tool                        |
|--------------------|------------------------------|
| Language           | Java 11                     |
| Browser automation | Selenium WebDriver 4        |
| API testing        | REST Assured                |
| BDD framework      | Cucumber (Gherkin)          |
| Test execution     | TestNG (via cucumber-testng)|
| Build tool         | Maven                       |
| Reporting          | ExtentReports                |
| Logging            | Log4j2                      |
| Design pattern     | Page Object Model            |

## Planned architecture

src/main/java/com/qaframework/hybrid/
├── config/ ConfigReader — loads config.properties
├── driver/ DriverManager — thread-safe WebDriver factory
├── api/ ApiClient — REST Assured request/response wrapper
├── pages/ Page objects for UI flows
├── utils/ WaitUtils, ScreenshotUtil, ExtentReportManager
└── hooks/ Hooks — Cucumber @Before/@After lifecycle

src/test/java/com/qaframework/hybrid/
├── runners/ TestRunner — Cucumber-TestNG entry point
├── steps/api/ API step definitions
└── steps/ui/ UI step definitions

src/test/resources/
├── features/api/ Gherkin scenarios for API tests
└── features/ui/ Gherkin scenarios for UI tests


## Current status

- [x] Maven project scaffolded
- [x] Dependencies configured (Selenium, Cucumber, REST Assured, ExtentReports, Log4j2)
- [ ] Package structure
- [ ] Page objects
- [ ] API client wrapper
- [ ] Cucumber step definitions
- [ ] Feature files
- [ ] Test execution and reporting
- [ ] CI/CD pipeline

## Related work

A completed UI-only framework using the same architectural principles (Page Object Model, config-driven execution, CI/CD) is available here: [selenium-saucedemo-framework](https://github.com/adityavkulkarni19/selenium-saucedemo-framework).

## Author

[Aditya Kulkarni](https://github.com/adityavkulkarni19)
