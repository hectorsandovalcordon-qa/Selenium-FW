package com.automation.utils;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Utilidades para operaciones comunes con Locator en Playwright
 */
public class ElementUtils {

    private final Logger logger = LoggerFactory.getLogger(ElementUtils.class);
    private final Page page;
    private final int timeout; // en milisegundos

    public ElementUtils(Page page, int timeoutInSeconds) {
        this.page = page;
        this.timeout = timeoutInSeconds * 1000;
    }

    public void clickElement(Locator locator) {
        try {
            locator.waitFor(new Locator.WaitForOptions().setTimeout((double) timeout));
            locator.click(new Locator.ClickOptions().setTimeout((double) timeout));
            logger.info("Elemento clickeado exitosamente");
        } catch (Exception e) {
            logger.error("Error al clickear elemento: {}", e.getMessage());
            throw e;
        }
    }

    public void sendKeys(Locator locator, String text) {
        try {
            locator.waitFor(new Locator.WaitForOptions().setTimeout((double) timeout));
            locator.fill(""); // clear
            locator.type(text, new Locator.TypeOptions().setTimeout((double) timeout));
            logger.info("Texto enviado exitosamente: {}", text);
        } catch (Exception e) {
            logger.error("Error al enviar texto: {}", e.getMessage());
            throw e;
        }
    }

    public String getText(Locator locator) {
        try {
            locator.waitFor(new Locator.WaitForOptions().setTimeout((double) timeout));
            String text = locator.textContent();
            logger.info("Texto obtenido: {}", text);
            return text != null ? text.trim() : "";
        } catch (Exception e) {
            logger.error("Error al obtener texto: {}", e.getMessage());
            throw e;
        }
    }

    public boolean isElementPresent(String selector) {
        try {
            return page.locator(selector).count() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isElementVisible(Locator locator) {
        try {
            return locator.isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    public Locator waitForElementToBeVisible(String selector) {
        Locator locator = page.locator(selector);
        locator.waitFor(new Locator.WaitForOptions().setTimeout((double) timeout));
        return locator;
    }

    public Locator waitForElementToBeClickable(String selector) {
        Locator locator = page.locator(selector);
        locator.waitFor(new Locator.WaitForOptions().setTimeout((double) timeout));
        return locator;
    }

    public void scrollToElement(Locator locator) {
        try {
            locator.scrollIntoViewIfNeeded();
            page.waitForTimeout(500);
            logger.info("Scroll realizado hacia el elemento");
        } catch (Exception e) {
            logger.error("Error al hacer scroll: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void hoverOnElement(Locator locator) {
        try {
            locator.hover(new Locator.HoverOptions().setTimeout((double) timeout));
            logger.info("Hover realizado sobre el elemento");
        } catch (Exception e) {
            logger.error("Error al hacer hover: {}", e.getMessage());
            throw e;
        }
    }

    public void selectByVisibleText(Locator selectLocator, String visibleText) {
        try {
            selectLocator.selectOption(new String[] { visibleText });
            logger.info("Opción seleccionada: {}", visibleText);
        } catch (Exception e) {
            logger.error("Error al seleccionar opción: {}", e.getMessage());
            throw e;
        }
    }

    public List<ElementHandle> findElements(String selector) {
        return page.querySelectorAll(selector);
    }
}
