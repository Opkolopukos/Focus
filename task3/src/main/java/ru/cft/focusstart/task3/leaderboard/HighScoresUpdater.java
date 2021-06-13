package ru.cft.focusstart.task3.leaderboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.focusstart.task3.Difficulty;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.cft.focusstart.task3.leaderboard.DefaultScores.*;

public class HighScoresUpdater {
    private static final Logger logger = LoggerFactory.getLogger(HighScoresUpdater.class);
    private final Path highScoresFilePath;

    protected HighScoresUpdater() throws IOException {
        highScoresFilePath = Paths.get("HighScores.txt");
        File file = highScoresFilePath.toFile().exists() ?
                highScoresFilePath.toFile() :
                newFileWithDefaultScores();
        logger.info("{} is being read successfully", file.getName());
    }

    protected Map<String, Integer> getCurrentChampionByDifficulty(Difficulty difficulty) {
        try (Stream<String> allLinesAsStream = Files.lines(highScoresFilePath, StandardCharsets.UTF_8)) {
            Map<String, Integer> result = new HashMap<>(1);
            List<String> collect = allLinesAsStream.filter(line -> line.contains(difficulty.name()))
                    .flatMap(line -> Stream.of(line.split(" ")))
                    .collect(Collectors.toList());
            result.put(collect.get(1), Integer.valueOf(collect.get(2)));
            return result;
        } catch (IOException e) {
            logger.error("Reading lines has stopped, possible caused problem - {}", e.getMessage());
        }
        return Collections.emptyMap();
    }

    protected void updateCurrentChampion(String name, Integer score, Difficulty difficulty) {
        String updatedLine = String.format(SCORE_FORMAT, difficulty.name(), name, score);
        try (Stream<String> allLinesAsStream = Files.lines(highScoresFilePath, StandardCharsets.UTF_8)) {
            List<String> collect = allLinesAsStream.collect(Collectors.toList());
            for (int i = 0; i < collect.size(); i++) {
                if (collect.get(i).contains(difficulty.name())) {
                    collect.set(i, updatedLine);
                }
            }
            Files.write(highScoresFilePath, collect);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File newFileWithDefaultScores() throws IOException {
        File file = Files.createFile(highScoresFilePath).toFile();
        List<String> defaultScores = new ArrayList<>();
        Arrays.stream(Difficulty.values()).forEach(difficulty -> defaultScores.add(String.format(SCORE_FORMAT, difficulty, DEFAULT_USER, DEFAULT_SCORE)));
        Files.write(highScoresFilePath, defaultScores);
        return file;
    }
}
