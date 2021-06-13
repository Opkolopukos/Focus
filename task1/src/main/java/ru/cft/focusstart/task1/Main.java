package ru.cft.focusstart.task1;


import java.util.InputMismatchException;

public class Main {
    public static void main(String[] args) {
        try {
            int size = InputArgumentUtils.getTableSizeFromConsole();
            MultiplicationTable multiplicationTable = new MultiplicationTable(size);
            MultiplicationTablePrinter tablePrinter = new MultiplicationTablePrinter(multiplicationTable, new ConsoleWriter());
            tablePrinter.printTableWithFormat();
        } catch (InputMismatchException e) {
            System.out.println("Should be a number");
        } catch (ArgumentOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }
}
