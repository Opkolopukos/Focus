package ru.cft.focusstart.task2.shapes;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RectangleTest {
    Rectangle rectangleTest;

    @BeforeAll
    public void Init() {
        rectangleTest = new Rectangle(5, 10);
    }


    @Test
    @DisplayName("Should not create a circle with negative param")
    void shouldFailInit() {
        assertThrows(IllegalArgumentException.class, () -> new Rectangle(12, 0));
        assertThrows(IllegalArgumentException.class, () -> new Rectangle(0, 12));
    }

    @Test
    void checkCalculateAreaMethod() {
        assertEquals(50, rectangleTest.calculateArea());
    }

    @Test
    void checkCalculatePerimeterMethod() {
        assertEquals(30, rectangleTest.calculatePerimeter());
    }

    @Test
    void checkCalculateDiameterMethod() {
        assertEquals(11.18, rectangleTest.calculateDiagonal());
    }
}
