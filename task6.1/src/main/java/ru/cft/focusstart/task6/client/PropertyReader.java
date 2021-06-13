package ru.cft.focusstart.task6.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    private static final Logger logger = LoggerFactory.getLogger(PropertyReader.class.getName());
    private final Properties properties;

    public PropertyReader() {
        properties = new Properties();
        initProperty();
        logger.info("Client PropertyReader created");
    }

    private void initProperty() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            InputStream stream = loader.getResourceAsStream("Client.properties");
            if (stream != null) {
                properties.load(stream);
                logger.info("Client PropertyReader created with config from file");
                return;
            }
        } catch (Exception e) {
            logger.warn("Setting default properties due to loading from file exception. {}", e.getMessage());
        }
        setDefault();
        logger.info("Client PropertyReader created with default config");
    }


    private void setDefault() {
        properties.setProperty("host", "127.0.0.1");
        properties.setProperty("port", "8080");
    }

    public int getPort() {
        return Integer.parseInt(properties.getProperty("port"));
    }

    public String getHost() {
        return properties.getProperty("host");
    }
}
