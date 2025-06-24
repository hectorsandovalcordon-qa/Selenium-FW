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
            logger.info("Creando una nueva instancia de Page para el contexto de prueba.");
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
            logger.info("Cerrando la instancia de Page del contexto de prueba.");
            page.context().close();
            page.browser().close();
            page = null;
        } else {
            logger.warn("Se intentó cerrar un Page, pero la instancia ya era nula.");
        }
    }
}
