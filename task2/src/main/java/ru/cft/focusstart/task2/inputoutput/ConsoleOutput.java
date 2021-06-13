package ru.cft.focusstart.task2.inputoutput;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;

public class ConsoleOutput implements Output {
    private static final Logger logger = LoggerFactory.getLogger(ConsoleOutput.class.getName());
    private final PrintStream printStream;

    public ConsoleOutput() {
        this.printStream = new PrintStream(System.out);
        logger.info("Console output has been created");
    }

    @Override
    public void write(String message) {
        printStream.println(message);
        logger.info("Writing to console is finished");
    }

    @Override
    public void close() {
        //
    }
}
