package ru.cft.focusstart.task5.production;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.focusstart.task5.properties.ConfigProperties;

public class ResourceProducer implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ResourceProducer.class.getName());
    private final Storage storage;
    private final int id;

    public ResourceProducer(int id, Storage storage) {
        this.id = id;
        this.storage = storage;
        logger.info("Resource producer with {} created", id);
    }

    @Override
    public void run() {
        logger.info("Starting {}!", Thread.currentThread().getName());
        while (true) {
            try {
                Resource resource = new Resource();
                storage.put(resource);
                logger.info("Resource with {} id was added to the storage", resource.getId());
                logger.info("{} with {} id going to sleep for {} milliseconds", Thread.currentThread(), id, ConfigProperties.INTERVAL_OF_PRODUCING);
                Thread.sleep(ConfigProperties.INTERVAL_OF_PRODUCING);
            } catch (InterruptedException e) {
                logger.warn("{} was interrupted, possible cause - {}", Thread.currentThread().getName(), e.getCause());
            }
        }
    }
}

