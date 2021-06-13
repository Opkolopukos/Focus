package ru.cft.focusstart.task2.shapes;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.focusstart.task2.utils.Messages;

import static ru.cft.focusstart.task2.utils.Constants.*;
import static ru.cft.focusstart.task2.utils.RoundingFormat.TWO_DECIMALS_FORMAT;

public class Triangle extends Shape {
    private static final Logger logger = LoggerFactory.getLogger(Triangle.class.getName());
    private final double sideA;
    private final double sideB;
    private final double sideC;


    public Triangle(double sideA, double sideB, double sideC) {
        if (!isPossibleToCreate(sideA, sideB, sideC)) {
            logger.error("Sides should be positive. Current {}_{}_{}", sideA, sideB, sideC);
            throw new IllegalArgumentException(Messages.INVALID_ARGUMENT);
        }
        if (!isACorrectTriangle(sideA, sideB, sideC)) {
            logger.error("Any two sides of a triangle must be greater than or equal to the length of the third side");
            throw new IllegalArgumentException(Messages.INVALID_ARGUMENT);
        }
        super.shapeType = ShapeType.TRIANGLE;
        super.name = shapeType.getName();
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
        logger.info("Triangle shaped figure is created");
    }

    private boolean isPossibleToCreate(double sideA, double sideB, double sideC) {
        return sideA > 0 && sideB > 0 && sideC > 0;
    }

    private boolean isACorrectTriangle(double sideA, double sideB, double sideC) {
        return sideA + sideB >= sideC && sideA + sideC >= sideB && sideB + sideC >= sideA;
    }

    private double getSideA() {
        return sideA;
    }

    private double getSideB() {
        return sideB;
    }

    private double getSideC() {
        return sideC;
    }

    @Override
    public double calculateArea() {
        double s = (sideA + sideB + sideC) / (double) 2;
        double x = s * (s - sideA) * (s - sideB) * (s - sideC);
        return TWO_DECIMALS_FORMAT.format(Math.sqrt(x));
    }

    @Override
    public double calculatePerimeter() {
        return TWO_DECIMALS_FORMAT.format(sideA + sideB + sideC);
    }

    public double getAAngle() {
        double a = Math.acos((Math.pow(sideB, 2) + Math.pow(sideC, 2) - Math.pow(sideA, 2)) / (2 * sideC * sideB));
        return TWO_DECIMALS_FORMAT.format(Math.toDegrees(a));
    }

    public double getBAngle() {
        double b = Math.acos((Math.pow(sideA, 2) + Math.pow(sideB, 2) - Math.pow(sideC, 2)) / (2 * sideA * sideB));
        return TWO_DECIMALS_FORMAT.format(Math.toDegrees(b));
    }

    public double getCAngle() {
        double c = Math.acos((Math.pow(sideA, 2) + Math.pow(sideC, 2) - Math.pow(sideB, 2)) / (2 * sideA * sideC));
        return TWO_DECIMALS_FORMAT.format(Math.toDegrees(c));
    }

    @Override
    public String getShapeInfo() {
        return getShapeName() + NEXT_LINE +
                AREA + calculateArea() + MEASURE_UNITS_SQR_MM + NEXT_LINE +
                PERIMETER + calculatePerimeter() + MEASURE_UNITS_MM + NEXT_LINE +
                LENGTH + "A " + getSideA() + MEASURE_UNITS_MM + OPPOSITE_ANGLE + getAAngle() + DEGREES_SYMBOL + NEXT_LINE +
                LENGTH + "B " + getSideB() + MEASURE_UNITS_MM + OPPOSITE_ANGLE + getBAngle() + DEGREES_SYMBOL + NEXT_LINE +
                LENGTH + "C " + getSideC() + MEASURE_UNITS_MM + OPPOSITE_ANGLE + getCAngle() + DEGREES_SYMBOL;
    }
}
