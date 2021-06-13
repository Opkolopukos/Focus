package ru.cft.focusstart.task2.shapes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ShapeFactoryTest {
    List<String> triangleParams;
    List<String> rectangleParams;
    List<String> circleParams;
    List<String> corruptedParams = List.of("asd", "2", "aa");

    @BeforeAll
    public void setup() {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        File triangleFile = new File(Objects.requireNonNull(classLoader.getResource("TriangleParamsTest.txt")).getFile());
        File rectangleFile = new File(Objects.requireNonNull(classLoader.getResource("RectangleParamsTest.txt")).getFile());
        File circleFile = new File(Objects.requireNonNull(classLoader.getResource("CircleParamsTest.txt")).getFile());
        try {
            triangleParams = Files.readAllLines(triangleFile.toPath());
            rectangleParams = Files.readAllLines(rectangleFile.toPath());
            circleParams = Files.readAllLines(circleFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    void ShapeFactoryCreateTriangleTest() {
        Assertions.assertEquals(Triangle.class, ShapeFactory.createShape(triangleParams).getClass());
    }

    @Test
    void ShapeFactoryCreateRectangleTest() {
        Assertions.assertEquals(Rectangle.class, ShapeFactory.createShape(rectangleParams).getClass());
    }

    @Test
    void ShapeFactoryCreateCircleTest() {
        Assertions.assertEquals(Circle.class, ShapeFactory.createShape(circleParams).getClass());
    }

    @Test
    void createShapeWithIncorrectInput() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> ShapeFactory.createShape(corruptedParams));
    }
}
