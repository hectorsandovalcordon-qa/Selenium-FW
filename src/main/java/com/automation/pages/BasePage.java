package com.automation.pages;

import com.automation.config.Configuration;
import com.microsoft.playwright.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase base para todas las páginas con funcionalidad común usando Playwright
 */
public abstract class PlaywrightBasePage {
    protected static final Logger logger = LoggerFactory.getLogger(PlaywrightBasePage.class);
    protected Page page;
    protected Configuration config;

    public PlaywrightBasePage(Page page) {
        this.page = page;
        this.config = Configuration.getInstance();
    }

    /**
     * Navega a una URL específica
     */
    public void navigateTo(String url) {
        logger.info("Navegando a: {}", url);
        page.navigate(url);
    }

    /**
     * Navega a la URL base configurada
     */
    public void navigateToBase() {
        navigateTo(config.getBaseUrl());
    }

    /**
     * Obtiene el título de la página actual
     */
    public String getPageTitle() {
        String title = page.title();
        logger.info("Título de página obtenido: {}", title);
        return title;
    }

    /**
     * Obtiene la URL actual
     */
    public String getCurrentUrl() {
        String url = page.url();
        logger.info("URL actual: {}", url);
        return url;
    }

    /**
     * Verifica si un elemento está presente en la página
     */
    protected boolean isElementPresent(String locator) {
        return page.locator(locator).count() > 0;
    }

    /**
     * Verifica si un elemento está visible
     */
    protected boolean isElementVisible(String locator) {
        return page.locator(locator).isVisible();
    }

    /**
     * Clickea un elemento
     */
    protected void clickElement(String locator) {
        page.locator(locator).click();
    }

    /**
     * Envía texto a un elemento
     */
    protected void sendKeys(String locator, String text) {
        page.locator(locator).fill(text);
    }

    /**
     * Obtiene texto de un elemento
     */
    protected String getText(String locator) {
        return page.locator(locator).innerText();
    }

    /**
     * Hace scroll hacia un elemento
     */
    protected void scrollToElement(String locator) {
        page.locator(locator).scrollIntoViewIfNeeded();
    }

    /**
     * Hace hover sobre un elemento
     */
    protected void hoverOnElement(String locator) {
        page.locator(locator).hover();
    }

    /**
     * Espera a que un elemento sea visible
     */
    protected void waitForElementToBeVisible(String locator) {
        page.locator(locator).waitFor();
    }

    /**
     * Espera a que un elemento sea clickeable
     */
    protected void waitForElementToBeClickable(String locator) {
        page.locator(locator).waitFor();
    }

    /**
     * Verifica si la página actual es la esperada
     */
    public abstract boolean isPageLoaded();

    /**
     * Obtiene el nombre de la página
     */
    public abstract String getPageName();
}
