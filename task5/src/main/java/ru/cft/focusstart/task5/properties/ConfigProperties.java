package ru.cft.focusstart.task5.properties;

public class ConfigProperties {
    private ConfigProperties() {
    }

    private static final PropertyReader propertyReader = new PropertyReader();
    public static final int NUMBER_OF_PRODUCERS = propertyReader.getNumberOfProducers();
    public static final int NUMBER_OF_CONSUMERS = propertyReader.getNumberOfConsumers();
    public static final int INTERVAL_OF_PRODUCING = propertyReader.getIntervalOfProducing();
    public static final int INTERVAL_OF_CONSUMING = propertyReader.getIntervalOfConsuming();
    public static final int STORAGE_SIZE_LIMIT = propertyReader.getStorageSizeLimit();
}
