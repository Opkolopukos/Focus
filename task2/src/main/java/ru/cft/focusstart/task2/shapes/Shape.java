package ru.cft.focusstart.task2.shapes;

public abstract class Shape {
    protected String name;
    protected ShapeType shapeType;

    protected abstract double calculateArea();

    protected abstract double calculatePerimeter();

    protected String getShapeName() {
        return "Тип фигуры: " + name;
    }

    public abstract String getShapeInfo();
}
