package ru.cft.focusstart.task1;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;


class MultiplicationTablePrinter {
    private static final String DIGIT_SEPARATOR_SYMBOL = "|";
    private static final String CROSS_SYMBOL = "+";
    private static final String DASH_SYMBOL = "-";
    private static final String SPACE_SYMBOL = " ";
    private final Output output;
    private final String lineSeparator;
    private final MultiplicationTable multiplicationTable;
    private final int firstRowCellFormat;
    private final int generalRowCellFormat;


    public MultiplicationTablePrinter(@NotNull MultiplicationTable multiplicationTable, @NotNull Output output) {
        this.output = output;
        this.multiplicationTable = multiplicationTable;
        this.firstRowCellFormat = String.valueOf(multiplicationTable.getSize()).length();
        this.generalRowCellFormat = String.valueOf(multiplicationTable.getSize() * multiplicationTable.getSize()).length();
        this.lineSeparator = setSeparator();
    }

    private String getFormattedRow(int[] row) {
        StringBuilder stringBuilder = new StringBuilder(row.length * firstRowCellFormat * generalRowCellFormat);
        System.out.println(stringBuilder.capacity());
        stringBuilder.append(String.format("%" + firstRowCellFormat + "s", row[0] != 0 ? row[0] : SPACE_SYMBOL));
        Arrays.stream(row).skip(1).
                forEach(element -> stringBuilder.append(String.format(DIGIT_SEPARATOR_SYMBOL + "%" + generalRowCellFormat + "s", element)));
        return stringBuilder.toString();
    }

    private String setSeparator() {
        return DASH_SYMBOL.repeat(firstRowCellFormat) +
                (CROSS_SYMBOL + DASH_SYMBOL.repeat(generalRowCellFormat)).repeat(multiplicationTable.getSize());
    }

    public void printTableWithFormat() {
        for (int[] row : multiplicationTable.getFilledTable()) {
            output.write(getFormattedRow(row));
            output.write(lineSeparator);
        }
    }
}
