package ru.cft.focusstart.task2.shapes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.focusstart.task2.utils.Messages;

import static java.lang.Math.PI;
import static ru.cft.focusstart.task2.utils.Constants.*;
import static ru.cft.focusstart.task2.utils.RoundingFormat.TWO_DECIMALS_FORMAT;

public class Circle extends Shape {
    private static final Logger logger = LoggerFactory.getLogger(Circle.class.getName());
    private final double radius;

    public Circle(double radius) {
        if (!isPossibleToCreate(radius)) {
            logger.error("Radius should be positive. Current - {}", radius);
            throw new IllegalArgumentException(Messages.INVALID_ARGUMENT);
        }
        super.shapeType = ShapeType.CIRCLE;
        super.name = shapeType.getName();
        this.radius = radius;
        logger.info("Circle shaped figure is created");
    }

    private boolean isPossibleToCreate(double radius) {
        return radius > 0;
    }

    @Override
    public double calculateArea() {
        return TWO_DECIMALS_FORMAT.format(PI * (radius * radius));
    }

    @Override
    public double calculatePerimeter() {
        return TWO_DECIMALS_FORMAT.format((PI + PI) * radius);
    }

    public double calculateDiameter() {
        return TWO_DECIMALS_FORMAT.format(radius * 2);
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public String getShapeInfo() {
        return getShapeName() + NEXT_LINE +
                AREA + calculateArea() + MEASURE_UNITS_SQR_MM + NEXT_LINE +
                PERIMETER + calculatePerimeter() + MEASURE_UNITS_MM + NEXT_LINE +
                RADIUS + getRadius() + MEASURE_UNITS_MM + NEXT_LINE +
                DIAMETER + calculateDiameter() + MEASURE_UNITS_MM;
    }
}
