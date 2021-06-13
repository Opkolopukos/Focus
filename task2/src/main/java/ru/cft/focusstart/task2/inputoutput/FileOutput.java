package ru.cft.focusstart.task2.inputoutput;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;

public class FileOutput implements Output {
    private static final Logger logger = LoggerFactory.getLogger(FileOutput.class.getName());
    private PrintWriter printWriter;

    public FileOutput(String outputFileName) {
        try {
            this.printWriter = new PrintWriter(outputFileName);
        } catch (IOException e) {
            logger.error(e.getMessage() + "can't create new file: " + outputFileName);
        }
        logger.info("Output file is ready");
    }

    @Override
    public void write(String message) {
        printWriter.println(message);
        logger.info("Writing to file is finished");
    }

    @Override
    public void close() {
        printWriter.close();
        logger.info("Resource closed");
    }
}
