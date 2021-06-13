package ru.cft.focusstart.task1;

import java.util.Scanner;

public class InputArgumentUtils {
    private static final int MIN_TABLE_SIZE = 1;
    private static final int MAX_TABLE_SIZE = 32;

    private InputArgumentUtils() {
    }

    public static int getTableSizeFromConsole() throws ArgumentOutOfBoundsException {
        System.out.printf("Set the size of multiplication table - %1d to %2s possible" + System.lineSeparator(),
                MIN_TABLE_SIZE, MAX_TABLE_SIZE);
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        if (number < MIN_TABLE_SIZE || number > MAX_TABLE_SIZE) {
            throw new ArgumentOutOfBoundsException("inappropriate size");
        }
        return number;
    }
}
