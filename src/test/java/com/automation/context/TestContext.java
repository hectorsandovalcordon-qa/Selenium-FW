package com.automation.context;

import com.automation.config.DriverManager;
import com.microsoft.playwright.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestContext {
    private Page page;
    private PageObjectManager pageObjectManager;
    private static final Logger logger = LoggerFactory.getLogger(TestContext.class);

    public Page getPage() {
        if (page == null) {
            logger.info("Obteniendo instancia de Page desde DriverManager.");
            page = DriverManager.createDriver();
        }
        return page;
    }

    public PageObjectManager getPageObjectManager() {
        if (pageObjectManager == null) {
            pageObjectManager = new PageObjectManager(getPage());
        }
        return pageObjectManager;
    }

    public void quitPage() {
        if (page != null) {
            logger.info("Limpiando recursos con DriverManager tearDown.");
            DriverManager.tearDown();
            page = null;
            pageObjectManager = null;
        } else {
            logger.warn("Se intentó cerrar un Page, pero la instancia ya era nula.");
        }
    }
}
