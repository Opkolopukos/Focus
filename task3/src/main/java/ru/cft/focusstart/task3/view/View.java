package ru.cft.focusstart.task3.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.focusstart.task3.Difficulty;
import ru.cft.focusstart.task3.controller.IController;
import ru.cft.focusstart.task3.observer.HighlightEvent;
import ru.cft.focusstart.task3.observer.events.*;
import ru.cft.focusstart.task3.observer.observers.CellStateObserver;
import ru.cft.focusstart.task3.observer.observers.GameStateObserver;
import ru.cft.focusstart.task3.observer.observers.TimerObserver;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class View implements CellStateObserver, GameStateObserver, TimerObserver {
    private JFrame jframe;
    private InfoMiddlePanel middlePanel;
    private GridButtonPanel gridButtonPanel;
    private JButton[][] gameBoard;
    private static final Logger logger = LoggerFactory.getLogger(View.class);
    private final IController controller;
    private Difficulty difficulty = Difficulty.EASY; // default

    public View(IController controller) {
        this.controller = controller;
        initFrame();
        initMenu();
        initMiddlePanel();
        initButtons(difficulty);
        jframe.pack();
        logger.info("View has been created and packed. Controller {} has been injected", controller.getClass().getSimpleName());
    }

    private void initFrame() {
        jframe = new JFrame();
        jframe.setTitle("MineSweeper");
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.setBounds(250, 250, 250, 250);
        jframe.setVisible(true);
        jframe.setResizable(false);
        jframe.setBackground(Color.lightGray);
    }

    public void initButtons(Difficulty difficulty) {
        gameBoard = new JButton[difficulty.getHeight()][difficulty.getWidth()];
        gridButtonPanel = new GridButtonPanel(gameBoard, difficulty, controller);
        jframe.add(gridButtonPanel.getButtonPanel(), BorderLayout.SOUTH);
    }

    private void initMiddlePanel() {
        middlePanel = new InfoMiddlePanel(controller);
        jframe.add(middlePanel.getMiddlePanel(), BorderLayout.CENTER);
        middlePanel.updateFlags(difficulty.getMines());
    }

    private void initMenu() {
        MinesweeperMenuBar menuBar = new MinesweeperMenuBar(controller);
        jframe.setJMenuBar(menuBar.getMenuBar());
    }

    public void resize(Difficulty difficulty) {
        this.difficulty = difficulty;
        jframe.remove(gridButtonPanel.getButtonPanel());
        initButtons(difficulty);
        jframe.add(gridButtonPanel.getButtonPanel(), BorderLayout.SOUTH);
        middlePanel.updateFlags(difficulty.getMines());
        jframe.pack();
    }

    public void popMessage(String message) {
        int answer = JOptionPane.showConfirmDialog(jframe, message, "Play again?", JOptionPane.OK_CANCEL_OPTION);
        if (answer == JOptionPane.YES_OPTION) {
            controller.startGame();
        } else {
            controller.shutdown();
        }
    }

    public void popLeaderboards(String message) {
        JOptionPane.showMessageDialog(jframe, message);
    }

    public void getUserName() {
        String userName = JOptionPane.showInputDialog(jframe, "New Record!", "input your name");
        userName = userName == null ? "YOU" : userName.replace(" ", "_");
        controller.setCurrentPlayer(userName);
    }

    @Override
    public void updateCell(CellUpdate e) {
        if (e.isMine()) {
            paint(e.getRow(), e.getCol(), CellTypeImage.MINE_BLOWN);
            return;
        }
        int minesAround = e.getMinesAround();
        switch (minesAround) {
            case 0 -> paint(e.getRow(), e.getCol(), CellTypeImage.ZERO);
            case 1 -> paint(e.getRow(), e.getCol(), CellTypeImage.ONE);
            case 2 -> paint(e.getRow(), e.getCol(), CellTypeImage.TWO);
            case 3 -> paint(e.getRow(), e.getCol(), CellTypeImage.THREE);
            case 4 -> paint(e.getRow(), e.getCol(), CellTypeImage.FOUR);
            case 5 -> paint(e.getRow(), e.getCol(), CellTypeImage.FIVE);
            case 6 -> paint(e.getRow(), e.getCol(), CellTypeImage.SIX);
            case 7 -> paint(e.getRow(), e.getCol(), CellTypeImage.SEVEN);
            case 8 -> paint(e.getRow(), e.getCol(), CellTypeImage.EIGHT);
            default -> throw new IllegalArgumentException("Unknown cellTypeImage");
        }
    }


    @Override
    public void updateFlag(FlagTumbler e) {
        if (Boolean.TRUE.equals(e.getFlag())) {
            paint(e.getRow(), e.getCol(), CellTypeImage.FLAG);
        } else {
            paint(e.getRow(), e.getCol(), CellTypeImage.CLOSED);
        }
        middlePanel.updateFlags(e.getFlags());
    }

    @Override
    public void updateHighlight(HighlightEvent e) {
        if (e instanceof Highlight) {
            Highlight c = (Highlight) e;
            paint(c.getRow(), c.getCol(), CellTypeImage.ZERO);
        }
        if (e instanceof CancelHighlight) {
            CancelHighlight c = (CancelHighlight) e;
            paint(c.getRow(), c.getCol(), CellTypeImage.CLOSED);
        }
    }

    @Override
    public void updateTimer(TimerEvent e) {
        middlePanel.updateTime((e).getTime());
    }


    private void paint(int row, int col, CellTypeImage cellTypeImage) {
        gameBoard[(row)][col].setIcon(new ImageIcon(cellTypeImage.getImage()));
    }

    @Override
    public void updateVictory(Victory e) {
        middlePanel.changeMood(MainButtonImage.VICTORY);
        if ((e).isRecord()) {
            getUserName();
        }
        popMessage("Congratulations! Try again?");
    }

    @Override
    public void updateDefeat(Defeat e) {
        middlePanel.changeMood(MainButtonImage.DEFEAT);
        (e).getMinesLocation().forEach(mineCell -> paint(mineCell.getX(), mineCell.getY(), CellTypeImage.MINE));
        popMessage("You lost. Try again?");
    }

    @Override
    public void updateDifficulty(DifficultyChanged e) {
        difficulty = e.getDifficulty();
        resize(difficulty);
        Arrays.stream(gameBoard).
                flatMap(Arrays::stream).forEach(x -> x.setIcon(new ImageIcon(CellTypeImage.CLOSED.getImage())));
    }

    @Override
    public void updateNewGame(NewGame e) {
        middlePanel.changeMood(MainButtonImage.SMILEY);
        Arrays.stream(gameBoard).
                flatMap(Arrays::stream).forEach(x -> x.setIcon(new ImageIcon(CellTypeImage.CLOSED.getImage())));
        middlePanel.updateTime(0);
        middlePanel.updateFlags(difficulty.getMines());
    }

    @Override
    public void updateInfo(InfoEvent e) {
        popLeaderboards(e.getInfo());
    }
}
