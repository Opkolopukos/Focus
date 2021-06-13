package ru.cft.focusstart.task5.production;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.focusstart.task5.properties.ConfigProperties;

public class ResourceConsumer implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ResourceConsumer.class.getName());
    private final Storage storage;
    private final int id;

    public ResourceConsumer(int id, Storage storage) {
        this.id = id;
        this.storage = storage;
        logger.info("Resource consumer with {} created", id);
    }

    @Override
    public void run() {
        logger.info("Starting {}!", Thread.currentThread().getName());
        while (true) {
            try {
                Resource resource = storage.get();
                logger.info("Resource with {} id was consumed.", resource.getId());
                logger.info("{} with {} id going to sleep for {} milliseconds", Thread.currentThread(), id, ConfigProperties.INTERVAL_OF_CONSUMING);
                Thread.sleep(ConfigProperties.INTERVAL_OF_CONSUMING);
            } catch (InterruptedException e) {
                logger.warn("{} was interrupted, possible cause - {}", Thread.currentThread().getName(), e.getCause());
            }
        }
    }
}



