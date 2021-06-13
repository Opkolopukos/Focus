package ru.cft.focusstart.task2.cmdline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.focusstart.task2.inputoutput.OutputStrategy;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CmdLineParser {
    private static final String WRITE_TO_FILE_PARAM = "-f";
    private static final String WRITE_TO_CONSOLE_PARAM = "-c";
    private String inputFileName;
    private static final Logger logger = LoggerFactory.getLogger(CmdLineParser.class.getName());

    private OutputStrategy outputStrategy;

    public void parseCmdLine(String[] args) {
        logger.info("Try parsing command line");
        if (args.length == 0) {
            showUsage();
            logger.info("No arguments were given. Console output strategy is set by default");
            outputStrategy = OutputStrategy.TO_CONSOLE;
        }
        boolean isWritingToFile = Arrays.stream(args).anyMatch(WRITE_TO_FILE_PARAM::equalsIgnoreCase);
        boolean isWritingToConsole = Arrays.stream(args).anyMatch(WRITE_TO_CONSOLE_PARAM::equalsIgnoreCase);
        inputFileName = Arrays.stream(args).filter(x -> x.endsWith(".txt")).collect(Collectors.joining());

        if (isWritingToFile && isWritingToConsole) {
            logger.info("Both output params were chosen {}{} Console output will be chosen by default",
                    WRITE_TO_FILE_PARAM, WRITE_TO_CONSOLE_PARAM);
            outputStrategy = OutputStrategy.TO_CONSOLE;
            logger.info("Console output strategy is set");
        }
        if (isWritingToConsole && outputStrategy == null) {
            outputStrategy = OutputStrategy.TO_CONSOLE;
            logger.info("Console output strategy is set");
        }
        if (isWritingToFile && outputStrategy == null) {
            outputStrategy = OutputStrategy.TO_FILE;
            logger.info("File output strategy is set");
        }
        if (outputStrategy == null) {
            outputStrategy = OutputStrategy.TO_CONSOLE; // default
        }
    }

    private static void showUsage() {
        logger.info("Usage: program [-f|-c] <inputFile>");
    }

    public String getInputFileName() {
        return inputFileName;
    }

    public OutputStrategy getOutputStrategy() {
        return outputStrategy;
    }
}
