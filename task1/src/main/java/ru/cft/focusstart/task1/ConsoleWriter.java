package ru.cft.focusstart.task1;

import java.io.PrintStream;

class ConsoleWriter implements Output {
    private final PrintStream printStream;

    ConsoleWriter() {
        this.printStream = new PrintStream(System.out);
    }

    public void write(String message) {
        printStream.println(message);
    }
}
