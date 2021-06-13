package ru.cft.focusstart.task5.properties;

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
        logger.info("PropertyReader created");
    }

    private void initProperty() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            InputStream stream = loader.getResourceAsStream("task5.properties");
            if (stream != null) {
                properties.load(stream);
                logger.info("PropertyReader created with config from file");
                return;
            }
        } catch (Exception e) {
            logger.warn("Setting default properties due to loading from file exception. {}", e.getMessage());
        }
        setDefault();
        logger.info("PropertyReader created with default config");
    }


    private void setDefault() {
        properties.setProperty("N", DefaultProperties.NUMBER_OF_PRODUCERS);
        properties.setProperty("M", DefaultProperties.NUMBER_OF_CONSUMERS);
        properties.setProperty("tN", DefaultProperties.INTERVAL_OF_PRODUCING);
        properties.setProperty("tM", DefaultProperties.INTERVAL_OF_CONSUMING);
        properties.setProperty("S", DefaultProperties.STORAGE_SIZE_LIMIT);
    }

    public int getNumberOfProducers() {
        return Integer.parseInt(properties.getProperty("N"));
    }

    public int getNumberOfConsumers() {
        return Integer.parseInt(properties.getProperty("M"));
    }

    public int getStorageSizeLimit() {
        return Integer.parseInt(properties.getProperty("S"));
    }

    public int getIntervalOfProducing() {
        return Integer.parseInt(properties.getProperty("tN"));
    }

    public int getIntervalOfConsuming() {
        return Integer.parseInt(properties.getProperty("tM"));
    }
}
