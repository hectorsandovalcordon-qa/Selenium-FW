package com.automation.config;

import com.microsoft.playwright.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * Gestor de Playwright para inicializar diferentes navegadores
 */
public class DriverManager {
    private static final Logger logger = LoggerFactory.getLogger(DriverManager.class);
    private static final Configuration config = Configuration.getInstance();
    private static Browser browser;
    private static BrowserContext context;
    private static Page page;

    private DriverManager() {
        // Constructor privado para evitar instanciación
    }

    /**
     * Crea una nueva instancia de Playwright según la configuración
     */
    public static Page createDriver() {
        Playwright playwright = Playwright.create();
        String browserName = config.getBrowser().toLowerCase();

        // Sobrescribir con propiedades del sistema si están definidas
        if (System.getProperty("browser") != null) {
            browserName = System.getProperty("browser").toLowerCase();
        }

        logger.info("Creando una nueva instancia del navegador: {}", browserName);
        logger.info("Modo headless: {}", isHeadlessMode());

        browser = switch (browserName) {
            case "chrome" -> playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(isHeadlessMode()));
            case "firefox" -> playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(isHeadlessMode()));
            case "edge" -> playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("msedge").setHeadless(isHeadlessMode()));
            default -> {
                logger.warn("Navegador no soportado: {}. Usando Chrome por defecto", browserName);
                yield playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(isHeadlessMode()));
            }
        };

        context = browser.newContext();
        page = context.newPage();

        // Configurar timeouts
        page.setDefaultTimeout(config.getImplicitWait() * 1000); // Convertir segundos a milisegundos
        page.setDefaultNavigationTimeout(config.getPageLoadTimeout() * 1000); // Convertir segundos a milisegundos

        // Maximizar ventana si está configurado y no es headless
        if (config.shouldMaximizeWindow() && !isHeadlessMode()) {
            page.setViewportSize(1920, 1080);
        }

        logger.info("Instancia de Playwright creada correctamente");
        return page;
    }

    /**
     * Verifica si debe ejecutarse en modo headless
     */
    private static boolean isHeadlessMode() {
        // Primero verificar propiedades del sistema
        String headlessProperty = System.getProperty("headless");
        if (headlessProperty != null) {
            return Boolean.parseBoolean(headlessProperty);
        }

        // Luego verificar configuración
        return config.isHeadless();
    }

    /**
     * Cierra el navegador y limpia los recursos
     */
    public static void tearDown() {
        if (context != null) {
            context.close();
        }
        if (browser != null) {
            browser.close();
        }
    }
}
