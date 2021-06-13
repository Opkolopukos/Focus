package ru.cft.focusstart.task2.shapes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.focusstart.task2.utils.Messages;

import static ru.cft.focusstart.task2.utils.Constants.*;
import static ru.cft.focusstart.task2.utils.RoundingFormat.TWO_DECIMALS_FORMAT;

public class Rectangle extends Shape {
    private static final Logger logger = LoggerFactory.getLogger(Rectangle.class.getName());
    private final double width;
    private final double length;

    public Rectangle(double sideA, double sideB) {
        if (!isPossibleToCreate(sideA, sideB)) {
            logger.error("Side length should be positive. Current - {}_{}", sideA, sideB);
            throw new IllegalArgumentException(Messages.INVALID_ARGUMENT);
        }
        super.shapeType = ShapeType.RECTANGLE;
        super.name = shapeType.getName();
        this.width = Math.min(sideA, sideB);
        this.length = Math.max(sideA, sideB);
        logger.info("Rectangle shaped figure is created");
    }

    private boolean isPossibleToCreate(double sideA, double sideB) {
        return (sideA > 0 && sideB > 0);
    }

    private double getWidth() {
        return width;
    }

    private double getLength() {
        return length;
    }

    @Override
    public double calculateArea() {
        return TWO_DECIMALS_FORMAT.format(width * length);
    }

    @Override
    public double calculatePerimeter() {
        return TWO_DECIMALS_FORMAT.format(2 * (width + length));
    }

    public double calculateDiagonal() {
        return TWO_DECIMALS_FORMAT.format(Math.sqrt((width * width) + (length * length)));
    }

    @Override
    public String getShapeInfo() {
        return getShapeName() + NEXT_LINE +
                AREA + calculateArea() + MEASURE_UNITS_SQR_MM + NEXT_LINE +
                PERIMETER + calculatePerimeter() + MEASURE_UNITS_MM + NEXT_LINE +
                DIAGONAL + calculateDiagonal() + MEASURE_UNITS_MM + NEXT_LINE +
                LENGTH + getLength() + MEASURE_UNITS_MM + NEXT_LINE +
                WIDTH + getWidth() + MEASURE_UNITS_MM;
    }
}
