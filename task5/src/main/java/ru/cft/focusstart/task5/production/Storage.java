package ru.cft.focusstart.task5.production;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.focusstart.task5.properties.ConfigProperties;

import java.util.LinkedList;

public class Storage {
    private static final Logger logger = LoggerFactory.getLogger(Storage.class.getName());
    private final LinkedList<Resource> resources = new LinkedList<>();

    public synchronized Resource get() throws InterruptedException {
        while (resources.isEmpty()) {
            wait();
            logger.warn("{} is waiting due to empty storage", Thread.currentThread());
        }
        logger.info("{} id is back to work", Thread.currentThread());
        Resource resource = resources.removeFirst();
        logger.info("Resource with {} id was consumed. Storage size {} ", resource.getId(), resources.size());
        notifyAll();
        logger.info("{} notifying other threads", Thread.currentThread().getName());
        return resource;
    }


    public synchronized void put(Resource resource) throws InterruptedException {
        while (resources.size() == ConfigProperties.STORAGE_SIZE_LIMIT) {
            wait();
            logger.warn("{} is waiting due to max storage capacity", Thread.currentThread());
        }
        resources.add(resource);
        logger.info("New resource added. Id - {}. Storage size {} ", resource.getId(), resources.size());
        notifyAll();
        logger.info("{} notifying other threads", Thread.currentThread().getName());
    }
}