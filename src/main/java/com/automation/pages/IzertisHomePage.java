package com.automation.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

/**
 * Page Object para la página principal de Izertis
 */
public class IzertisHomePage extends BasePage {

    private final Locator logo;
    private final Locator mainNavigation;
    private final Locator mainTitle;
    private final Locator passionForTechnologyTitle;
    private final Locator cookieBanner;
    private final Locator acceptCookiesButton;
    private final Locator footer;

    public IzertisHomePage(Page page) {
        super(page);
        this.logo = page.locator("img[alt*='Izertis'], .logo, [data-testid='logo']");
        this.mainNavigation = page.locator("nav, .navbar, .main-navigation");
        this.mainTitle = page.locator("h1, h2, .main-title, .hero-title");
        this.passionForTechnologyTitle = page.locator("//h1[contains(text(),'Passion') or contains(text(),'Technology') or contains(text(),'Pasión')] | //h2[contains(text(),'Passion') or contains(text(),'Technology')]");
        this.cookieBanner = page.locator("#gdpr-cookie-message");
        this.acceptCookiesButton = page.locator("#gdpr-cookie-accept-all");
        this.footer = page.locator("footer, .footer");
    }

    // Métodos específicos de la página principal

    /**
     * Verifica si la página está cargada correctamente
     */
    @Override
    public boolean isPageLoaded() {
        try {
            return logo.isVisible() || 
                   page.title().toLowerCase().contains("izertis") ||
                   page.url().contains("izertis.com");
        } catch (Exception e) {
            logger.error("Error verificando si la página está cargada: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene el nombre de la página
     */
    @Override
    public String getPageName() {
        return "Izertis Home Page";
    }

    /**
     * Clickea en el logo de Izertis
     */
    public void clickLogo() {
        logger.info("Clickeando en el logo de Izertis");
        logo.click();
    }

    /**
     * Obtiene el texto del título principal
     */
    public String getMainTitle() {
        logger.info("Obteniendo el título principal");
        if (mainTitle.isVisible()) {
            return mainTitle.textContent();
        } else if (passionForTechnologyTitle.isVisible()) {
            return passionForTechnologyTitle.textContent();
        }
        return "Título no encontrado";
    }

    /**
     * Acepta las cookies si aparece el banner
     */
    public void acceptCookiesIfPresent() {
        logger.info("Verificando y aceptando cookies si es necesario");
        try {
            if (cookieBanner.isVisible() && acceptCookiesButton.isVisible()) {
                acceptCookiesButton.click();
                logger.info("Cookies aceptadas");
            }
        } catch (Exception e) {
            logger.info("No hay banner de cookies presente o ya fue aceptado");
        }
    }

    /**
     * Hace scroll hacia el footer
     */
    public void scrollToFooter() {
        logger.info("Haciendo scroll hacia el footer");
        if (footer.isVisible()) {
            footer.scrollIntoViewIfNeeded();
        }
    }

    /**
     * Verifica si el logo es visible
     */
    public boolean isLogoVisible() {
        return