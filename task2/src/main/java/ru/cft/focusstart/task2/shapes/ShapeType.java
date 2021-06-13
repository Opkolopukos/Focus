package ru.cft.focusstart.task2.shapes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.focusstart.task2.Main;
import ru.cft.focusstart.task2.utils.Messages;

public enum ShapeType {
    CIRCLE("Круг", 1),
    RECTANGLE("Прямоугольник", 2),
    TRIANGLE("Треугольник", 3);

    private static final Logger logger = LoggerFactory.getLogger(Main.class.getName());
    private final int paramsForAssembly;
    private final String name;

    ShapeType(String name, int paramsForAssembly) {
        this.name = name;
        this.paramsForAssembly = paramsForAssembly;
    }

    public static ShapeType parseStringToShapeIngoringCase(String value) {
        value = value.toUpperCase();
        return switch (value) {
            case "CIRCLE" -> CIRCLE;
            case "RECTANGLE" -> RECTANGLE;
            case "TRIANGLE" -> TRIANGLE;
            default -> {
                logger.error(Messages.INVALID_ARGUMENT);
                throw new IllegalArgumentException("Unexpected value: " + value);
            }
        };
    }

    public int getParamsForAssembly() {
        return paramsForAssembly;
    }

    public String getName() {
        return name;
    }
}
