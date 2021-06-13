package ru.cft.focusstart.task2.inputoutput;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class FileReader implements AutoCloseable {
    private static final Logger logger = LoggerFactory.getLogger(FileReader.class);
    private final String fileName;

    public FileReader(String filename) throws FileNotFoundException {
        this.fileName = "task2/" + filename;
        fileCheck();
        logger.info("{} is being read successfully", filename);
    }

    public List<String> readAllLines() {
        Path path = Paths.get(fileName).toAbsolutePath();
        try {
            return Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.error("Reading lines has stopped, possible caused problem - {}", e.getMessage());
        }
        return Collections.emptyList();
    }

    private void fileCheck() throws FileNotFoundException {
        File file = new File(fileName);
        if (!file.exists() || !file.isFile()) {
            logger.error("File {} does not exist", fileName);
            throw new FileNotFoundException("Can't read from file - " + fileName);
        }
    }

    @Override
    public void close() throws Exception {
        // no resources to close
    }
}
