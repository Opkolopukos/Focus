package ru.cft.focusstart.task5.production;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class Resource {
    private static final Logger logger = LoggerFactory.getLogger(Resource.class.getName());
    private final UUID id;

    public Resource() {
        id = UUID.randomUUID();
        logger.info("Resource with {} created", id);
    }

    public UUID getId() {
        return id;
    }

}
