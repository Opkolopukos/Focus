package ru.cft.focusstart.task2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.focusstart.task2.cmdline.CmdLineParser;
import ru.cft.focusstart.task2.inputoutput.FileReader;
import ru.cft.focusstart.task2.inputoutput.Output;
import ru.cft.focusstart.task2.inputoutput.OutputFactory;
import ru.cft.focusstart.task2.shapes.Shape;
import ru.cft.focusstart.task2.shapes.ShapeFactory;

import java.util.List;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class.getName());

    public static void main(String[] args) {
        logger.info("Application started");
        CmdLineParser cmdParser = new CmdLineParser();
        cmdParser.parseCmdLine(args);
        try (FileReader fileReader = new FileReader(cmdParser.getInputFileName())) {
            List<String> fileContents = fileReader.readAllLines();
            Shape shape = ShapeFactory.createShape(fileContents);
            try (Output writer = OutputFactory.getOutput(cmdParser.getOutputStrategy())) {
                writer.write(shape.getShapeInfo());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.info("Application finished");
    }
}
