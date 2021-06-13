package ru.cft.focusstart.task2.inputoutput;

public interface Output extends AutoCloseable {
    void write(String message);
}
