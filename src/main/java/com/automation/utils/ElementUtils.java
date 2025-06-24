package com.automation.utils;

import com.automation.config.Configuration;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Utilidades para operaciones comunes con Locator
 */
public class ElementUtils {
    private final Logger logger = LoggerFactory.getLogger(ElementUtils.class);
    private final Page page;

    public ElementUtils(Page page) {
        this.page = page;
    }

    /**
     * Espera a que un elemento sea clickeable y lo clickea
     */
    public void clickElement(Locator locator) {
        try {
            locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED));
            locator.click();
            logger.info("Elemento clickeado exitosamente");
        } catch (Exception e) {
            logger.error("Error al clickear elemento: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Espera a que un elemento sea visible y envía texto
     */
    public void sendKeys(Locator locator, String text) {
        try {
            locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            locator.fill(text);
            logger.info("Texto enviado exitosamente: {}", text);
        } catch (Exception e) {
            logger.error("Error al enviar texto: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Espera a que un elemento sea visible y obtiene su texto
     */
    public String getText(Locator locator) {
        try {
            locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            String text = locator.textContent();
            logger.info("Texto obtenido: {}", text);
            return text;
        } catch (Exception e) {
            logger.error("Error al obtener texto: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Verifica si un elemento está presente
     */
    public boolean isElementPresent(String selector) {
        return page.locator(selector).count() > 0;
    }

    /**
     * Verifica si un elemento está visible
     */
    public boolean isElementVisible(Locator locator) {
        try {
            return locator.isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Espera a que un elemento sea visible
     */
    public Locator waitForElementToBeVisible(String selector) {
        return page.locator(selector).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
    }

    /**
     * Espera a que un elemento sea clickeable
     */
    public Locator waitForElementToBeClickable(String selector) {
        return page.locator(selector).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED));
    }

    /**
     * Scroll hacia un elemento
     */
    public void scrollToElement(Locator locator) {
        try {
            locator.scrollIntoViewIfNeeded();
            logger.info("Scroll realizado hacia el elemento");
        } catch (Exception e) {
            logger.error("Error al hacer scroll: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Hover sobre un elemento
     */
    public void hoverOnElement(Locator locator) {
        try {
            locator.hover();
            logger.info("Hover realizado sobre el elemento");
        } catch (Exception e) {
            logger.error("Error al hacer hover: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Selecciona una opción de un dropdown por texto visible
     */
    public void selectByVisibleText(Locator locator, String text) {
        try {
            locator.selectOption(text);
            logger.info("Opción seleccionada: {}", text);
        } catch (Exception e) {
            logger.error("Error al seleccionar opción: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Obtiene todos los elementos que coinciden con el selector
     */
    public List<Locator> findElements(String selector) {
        return page.locator(selector).all();
    }
}
