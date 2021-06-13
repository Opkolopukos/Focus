package ru.cft.focusstart.task1;

import java.util.Arrays;

public class MultiplicationTable {
    private final int size;
    private final int[][] filledTable;

    public MultiplicationTable(int size) {
        this.size = size;
        filledTable = fillTable(size);
    }

    private static int[][] fillTable(int size) {
        int[][] table = new int[size + 1][size + 1];
        for (int line = 0; line < table.length; line++) {
            for (int column = 0; column < table[line].length; column++) {
                table[line][column] = line * column;
            }
            table[line][0] = line;
            table[0][line] = line;
        }
        return table;
    }

    public int[][] getFilledTable() {
        return filledTable;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "MultiplicationTable" + System.lineSeparator() + Arrays.deepToString(filledTable).
                replace("], ", "]" + System.lineSeparator());
    }
}
