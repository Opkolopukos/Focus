package ru.cft.focusstart.task3.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.focusstart.task3.Difficulty;
import ru.cft.focusstart.task3.controller.IController;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class MinesweeperMenuBar {
    private final JMenuBar menuBar;
    private static final Logger logger = LoggerFactory.getLogger(MinesweeperMenuBar.class);

    protected MinesweeperMenuBar(IController controller) {
        this.menuBar = new JMenuBar();
        JMenu menu = new JMenu("Game");
        JMenu info = new JMenu("About");
        JMenuItem newGame = new JMenuItem("New game");
        JMenuItem highScores = new JMenuItem("HighScores");
        JMenuItem resetHighScores = new JMenuItem("HighScores reset");
        JMenu difficulty = new JMenu(Difficulty.class.getSimpleName());

        JMenuItem exit = new JMenuItem("Exit");
        JMenuItem author = new JMenuItem("Author");
        JMenuItem about = new JMenuItem("App info");
        menu.add(newGame);
        newGame.addActionListener(e -> controller.startGame());
        menu.add(difficulty);
        menu.add(highScores);
        menu.add(resetHighScores);
        menu.add(exit);
        info.add(author);
        info.add(about);
        resetHighScores.addActionListener(e -> controller.resetHighScores());
        author.addActionListener(e ->
                JOptionPane.showMessageDialog(menuBar, "Author - Azarov Dmitriy "));
        about.addActionListener(e ->
                JOptionPane.showMessageDialog(menuBar, "MineSweeper App. May 2021. "));
        highScores.addActionListener(e -> controller.showHighScores());
        exit.addActionListener(e -> controller.shutdown());

        Arrays.stream(Difficulty.values())
                .forEach(element -> difficulty.add(new JMenuItem(element.name())).addActionListener(e -> controller.changeSettings(element)));

        menuBar.setBackground(Color.GRAY);
        menuBar.add(menu);
        menuBar.add(info);
        logger.info("Menu Bar initialized");
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }
}
