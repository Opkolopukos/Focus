package ru.cft.focusstart.task2.shapes;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CircleTest {
    Circle circleTest;

    @BeforeAll
    void Init() {
        circleTest = new Circle(5);
    }


    @Test
    void shouldFailInit() {
        assertThrows(IllegalArgumentException.class, () -> new Circle(0));
    }

    @Test
    void checkCalculateAreaMethod() {
        assertEquals(78.54, circleTest.calculateArea());
    }

    @Test
    void checkCalculatePerimeterMethod() {
        assertEquals(31.42, circleTest.calculatePerimeter());
    }

    @Test
    void checkCalculateDiameterMethod() {
        assertEquals(10, circleTest.calculateDiameter());
    }

    @Test
    void checkGetRadiusMethod() {
        assertEquals(5, circleTest.getRadius());
    }
}
