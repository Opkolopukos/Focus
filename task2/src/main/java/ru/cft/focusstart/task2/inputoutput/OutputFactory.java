package ru.cft.focusstart.task2.inputoutput;


public class OutputFactory {

    private OutputFactory() {
    }

    public static Output getOutput(OutputStrategy outputStrategy) {
        return switch (outputStrategy) {
            case TO_CONSOLE -> new ConsoleOutput();
            case TO_FILE -> new FileOutput("output.txt");
        };
    }
}
