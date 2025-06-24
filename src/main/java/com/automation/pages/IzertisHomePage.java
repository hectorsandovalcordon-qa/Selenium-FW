package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page Object para la página principal de Izertis adaptado a Playwright
 */
public class IzertisHomePage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(IzertisHomePage.class);

    // Selectores
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
        this.passionForTechnologyTitle = page.locator(
                "xpath=//h1[contains(text(),'Passion') or contains(text(),'Technology') or contains(text(),'Pasión')] | //h2[contains(text(),'Passion') or contains(text(),'Technology')]");
        this.cookieBanner = page.locator("#gdpr-cookie-message");
        this.acceptCookiesButton = page.locator("#gdpr-cookie-accept-all");
        this.footer = page.locator("footer, .footer");
    }

    @Override
    public boolean isPageLoaded() {
        try {
            return logo.isVisible() ||
                    page.title().toLowerCase().contains("izertis") ||
                    page.url().contains("izertis.com");
        } catch (Exception e) {
            logger.error("Error verificando si la página está cargada: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public String getPageName() {
        return "Izertis Home Page";
    }

    public void clickLogo() {
        logger.info("Clickeando en el logo de Izertis");
        logo.click();
    }

    public String getMainTitle() {
        logger.info("Obteniendo el título principal");
        if (mainTitle.isVisible()) {
            return mainTitle.textContent().trim();
        } else if (passionForTechnologyTitle.isVisible()) {
            return passionForTechnologyTitle.textContent().trim();
        }
        return "Título no encontrado";
    }

    public void acceptCookiesIfPresent() {
        logger.info("Verificando y aceptando cookies si es necesario");
        if (cookieBanner.isVisible() && acceptCookiesButton.isVisible()) {
            acceptCookiesButton.click();
            logger.info("Cookies aceptadas");
        } else {
            logger.info("No hay banner de cookies presente o ya fue aceptado");
        }
    }

    public void scrollToFooter() {
        logger.info("Haciendo scroll hacia el footer");
        if (footer.isVisible()) {
            footer.scrollIntoViewIfNeeded();
        }
    }

    public boolean isLogoVisible() {
        return logo.isVisible();
    }

    public boolean isMainNavigationVisible() {
        return mainNavigation.isVisible();
    }
}
