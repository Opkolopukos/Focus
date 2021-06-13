package ru.cft.focusstart.task2.shapes;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TriangleTest {
    Triangle triagleTest;

    @BeforeAll
    void init() {
        triagleTest = new Triangle(4, 7, 5);
    }

    @Test
    void cantInitWithIncorrectSideCorrelation() {
        assertThrows(IllegalArgumentException.class, () -> new Triangle(15, 4, 7));
        assertThrows(IllegalArgumentException.class, () -> new Triangle(1, 3, 8));
        assertThrows(IllegalArgumentException.class, () -> new Triangle(2, 42, 9));
        assertThrows(IllegalArgumentException.class, () -> new Triangle(15, 4, 7));
    }

    @Test
    void cantInitWithNegativeArguments() {
        assertThrows(IllegalArgumentException.class, () -> new Triangle(0, 4, 7));
        assertThrows(IllegalArgumentException.class, () -> new Triangle(1, 0, 8));
        assertThrows(IllegalArgumentException.class, () -> new Triangle(2, 42, 0));

    }

    @Test
    void checkcalculateAreaMethod() {
        assertEquals(9.8, triagleTest.calculateArea());
    }

    @Test
    void checkcalculatePerimeterMethod() {
        assertEquals(16, triagleTest.calculatePerimeter());
    }

    @Test
    void checkGetAngleMethods() {
        assertEquals(34.05, triagleTest.getAAngle());
        assertEquals(44.42, triagleTest.getBAngle());
        assertEquals(101.54, triagleTest.getCAngle());
    }

}
