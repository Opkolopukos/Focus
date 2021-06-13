package ru.cft.focusstart.task3.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.focusstart.task3.controller.IController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InfoMiddlePanel {
    private final JPanel middlePanel;

    private final JButton start;
    private final JLabel mines;
    private final JLabel timer;
    private static final Logger logger = LoggerFactory.getLogger(InfoMiddlePanel.class);

    protected InfoMiddlePanel(IController controller) {
        middlePanel = new JPanel(new GridLayout(1, 5));
        Border border = new EmptyBorder(10, 10, 10, 10);
        mines = new JLabel();
        timer = new JLabel("Timer: " + 0);
        start = new JButton(new ImageIcon(MainButtonImage.SMILEY.getImage()));
        start.addActionListener(e -> controller.startGame());
        start.setBackground(Color.GRAY);
        middlePanel.setBackground(Color.lightGray);
        middlePanel.add(mines, BorderLayout.EAST);
        mines.setBorder(border);
        timer.setBorder(border);
        timer.setBounds(10, 10, 10, 10);
        mines.setBounds(10, 10, 10, 10);
        mines.setFont(Font.getFont(Font.MONOSPACED));
        timer.setFont(Font.getFont(Font.MONOSPACED));
        mines.setForeground(Color.BLACK);
        timer.setForeground(Color.BLACK);
        middlePanel.add(start, BorderLayout.CENTER);
        middlePanel.add(timer, BorderLayout.WEST);
        logger.info("Middle Panel initialized");
    }

    public void changeMood(MainButtonImage mainButtonImage) {
        start.setIcon(new ImageIcon(mainButtonImage.getImage()));

    }

    public void updateTime(long value) {
        timer.setText("Time: " + value);
    }

    public void updateFlags(int value) {
        mines.setText("Mines:" + value);
    }

    public JPanel getMiddlePanel() {
        return middlePanel;
    }
}
