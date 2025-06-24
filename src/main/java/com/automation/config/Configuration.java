package com.automation.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Clase de configuración para gestionar las propiedades del framework de automatización
 */
public class Configuration {
    private static final Logger logger = LoggerFactory.getLogger(Configuration.class);
    private static Properties properties;
    private static Configuration instance;

    private Configuration() {
        loadProperties();
    }

    public static Configuration getInstance() {
        if (instance == null) {
            synchronized (Configuration.class) {
                if (instance == null) {
                    instance = new Configuration();
                }
            }
        }
        return instance;
    }

    private void loadProperties() {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                properties.load(input);
                logger.info("Configuración cargada exitosamente");
            } else {
                logger.warn("Archivo config.properties no encontrado, usando valores por defecto");
                setDefaultProperties();
            }
        } catch (IOException e) {
            logger.error("Error al cargar la configuración: " + e.getMessage());
            setDefaultProperties();
        }
    }

    private void setDefaultProperties() {
        properties.setProperty("base.url", "https://www.izertis.com");
        properties.setProperty("browser", "chrome");
        properties.setProperty("implicit.wait", "10");
        properties.setProperty("explicit.wait", "15");
        properties.setProperty("page.load.timeout", "30");
        properties.setProperty("headless", "false");
        properties.setProperty("maximize.window", "true");
    }

    public String getBaseUrl() {
        return properties.getProperty("base.url");
    }

    public String getBrowser() {
        return properties.getProperty("browser", "chrome");
    }

    public int getImplicitWait() {
        return Integer.parseInt(properties.getProperty("implicit.wait", "10"));
    }

    public int getExplicitWait() {
        return Integer.parseInt(properties.getProperty("explicit.wait", "15"));
    }

    public int getPageLoadTimeout() {
        return Integer.parseInt(properties.getProperty("page.load.timeout", "30"));
    }

    public boolean isHeadless() {
        return Boolean.parseBoolean(properties.getProperty("headless", "false"));
    }

    public boolean shouldMaximizeWindow() {
        return Boolean.parseBoolean(properties.getProperty("maximize.window", "true"));
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
