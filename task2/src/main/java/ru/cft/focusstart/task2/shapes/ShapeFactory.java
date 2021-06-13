package ru.cft.focusstart.task2.shapes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.focusstart.task2.utils.Messages;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ShapeFactory {
    private static final Logger logger = LoggerFactory.getLogger(ShapeFactory.class.getName());

    private ShapeFactory() {
    }

    public static Shape createShape(List<String> rawParams) throws IllegalArgumentException {
        checkParamsSize(rawParams);
        ShapeType shapeType = ShapeType.parseStringToShapeIngoringCase(rawParams.get(0));
        List<Double> params = getParamsFromLine(rawParams.get(1));
        if (params.size() < shapeType.getParamsForAssembly()) {
            logger.error("Incorrect params size for {} shape assembly. Required appropriate params - {}",
                    shapeType.name(), shapeType.getParamsForAssembly());
            throw new IllegalArgumentException(Messages.INVALID_ARGUMENT);
        }
        logger.info("Shape factory is trying to create a shape from input params");
        return switch (shapeType) {
            case CIRCLE -> new Circle(params.get(0));
            case RECTANGLE -> new Rectangle(params.get(0), params.get(1));
            case TRIANGLE -> new Triangle(params.get(0), params.get(1), params.get(2));
        };
    }

    private static void checkParamsSize(List<String> input) {
        if (input.size() <= 1) {
            logger.error("Number of input params {}", input.size());
            throw new IllegalArgumentException(Messages.INSUFFICIENT_PARAMS_SIZE);
        }
    }

    private static List<Double> getParamsFromLine(String lineWithParams) {
        try {
            return Arrays.stream(lineWithParams.split(" ")).map(Double::parseDouble).collect(Collectors.toList());
        } catch (NumberFormatException e) {
            logger.error("Inappropriate params: " + lineWithParams);
        }
        return Collections.emptyList();
    }
}
