package ru.cft.focusstart.task5;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.focusstart.task5.production.ResourceConsumer;
import ru.cft.focusstart.task5.production.ResourceProducer;
import ru.cft.focusstart.task5.production.Storage;
import ru.cft.focusstart.task5.properties.ConfigProperties;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class.getName());
    private static final Storage shared_storage = new Storage();

    public static void main(String[] args) {
        logger.info("Program started");
        List<ResourceProducer> resourceProducers = buildProducers();
        List<ResourceConsumer> resourceConsumers = buildConsumers();

        startConsumption(resourceConsumers);
        startProduction(resourceProducers);
    }

    private static void startProduction(List<ResourceProducer> producers) {
        producers.forEach(producer -> new Thread(producer).start());
    }

    private static void startConsumption(List<ResourceConsumer> consumers) {
        consumers.forEach(consumer -> new Thread(consumer).start());
    }

    private static List<ResourceConsumer> buildConsumers() {
        List<ResourceConsumer> resourceConsumers = new ArrayList<>(ConfigProperties.NUMBER_OF_CONSUMERS);
        for (int i = 1; i < ConfigProperties.NUMBER_OF_CONSUMERS + 1; i++) {
            resourceConsumers.add(new ResourceConsumer(i, shared_storage));
        }
        return resourceConsumers;
    }

    private static List<ResourceProducer> buildProducers() {
        List<ResourceProducer> resourceProducers = new ArrayList<>(ConfigProperties.NUMBER_OF_PRODUCERS);
        for (int i = 1; i < ConfigProperties.NUMBER_OF_PRODUCERS + 1; i++) {
            resourceProducers.add(new ResourceProducer(i, shared_storage));
        }
        return resourceProducers;
    }
}
