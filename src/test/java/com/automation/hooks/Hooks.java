package com.automation.hooks;

import com.automation.context.TestContext;
import com.microsoft.playwright.Page;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;

public class Hooks {

    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);
    private final TestContext testContext;

    public Hooks(TestContext context) {
        this.testContext = context;
    }

    @Before
    public void setUp(Scenario scenario) {
        logger.info("=======================================================================");
        logger.info("INICIANDO ESCENARIO: {}", scenario.getName());
        logger.info("=======================================================================");
    }

    @After
    public void tearDown(Scenario scenario) {
        logger.info("=======================================================================");
        logger.info("FINALIZANDO ESCENARIO: {} - Estado: {}", scenario.getName(), scenario.getStatus());
        logger.info("=======================================================================");
        testContext.quitPage();
    }

    @AfterStep
    public void takeScreenshotOnFailure(Scenario scenario) {
        if (scenario.isFailed()) {
            logger.error("El paso ha fallado. Tomando captura de pantalla...");
            Page page = testContext.getPage();
            byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
            Allure.addAttachment("Screenshot on Failure", new ByteArrayInputStream(screenshot));
        }
    }
}
