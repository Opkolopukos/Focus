package ru.cft.focusstart.task3.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.focusstart.task3.Difficulty;
import ru.cft.focusstart.task3.controller.IController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GridButtonPanel {
    private final JPanel buttonPanel;
    private boolean isLeftPressed;
    private boolean isRightPressed;
    private static final Logger logger = LoggerFactory.getLogger(GridButtonPanel.class);

    protected GridButtonPanel(JButton[][] gameBoard, Difficulty difficulty, IController controller) {
        this.buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(difficulty.getHeight(), difficulty.getWidth()));
        for (int i = 0; i < difficulty.getHeight(); i++) {
            for (int j = 0; j < difficulty.getWidth(); j++) {
                Icon icon = new ImageIcon(CellTypeImage.CLOSED.getImage());
                JButton button = new JButton(icon);
                int row = i;
                int col = j;
                button.addActionListener(b -> controller.openCell(row, col));
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (SwingUtilities.isRightMouseButton(e)) {
                            controller.toggleFlag(row, col);
                        }
                    }
                });
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (SwingUtilities.isMiddleMouseButton(e)) {
                            controller.middleButtonHold(row, col);
                        }
                    }
                });
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if (SwingUtilities.isMiddleMouseButton(e)) {
                            controller.middleButtonRelease(row, col);
                        }
                    }
                });
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            isLeftPressed = true;
                        }
                        if (SwingUtilities.isRightMouseButton(e)) {
                            isRightPressed = true;
                        }

                        if (isLeftPressed && isRightPressed) {
                            controller.middleButtonRelease(row, col);
                        }
                    }
                });
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            isLeftPressed = false;
                        }
                        if (SwingUtilities.isRightMouseButton(e)) {
                            isRightPressed = false;
                        }

                    }
                });
                gameBoard[i][j] = button;
                button.setMargin(new Insets(5, 5, 5, 5));
                button.setBackground(Color.GRAY);
                Border border = new EmptyBorder(0, 0, 0, 0);
                button.setBorder(border);
                buttonPanel.add(button);
            }
            Border border = new EmptyBorder(0, 0, 0, 0);
            buttonPanel.setBackground(Color.GRAY);
            buttonPanel.setBorder(border);
        }
        logger.info("GridButtonPanel initialized");
    }

    public JPanel getButtonPanel() {
        return buttonPanel;
    }
}
