package ru.cft.focusstart.task3.view;


import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public enum CellTypeImage {
    ZERO("zero", ".png"),
    ONE("one", ".png"),
    TWO("two", ".png"),
    THREE("three", ".png"),
    FOUR("four", ".png"),
    FIVE("five", ".png"),
    SIX("six", ".png"),
    SEVEN("seven", ".png"),
    EIGHT("eight", ".png"),
    MINE("mine", ".png"),
    MINE_BLOWN("mine_blown", ".png"),
    CLOSED("closed", ".png"),
    FLAG("flag", ".png");

    private Image image;

    CellTypeImage(String name, String extension) {
        try {
            Image rawImage = ImageIO.read(ClassLoader.getSystemResource(name + extension));
            int iconSize = 25;
            this.image = rawImage.getScaledInstance(iconSize, iconSize, Image.SCALE_FAST);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Image getImage() {
        return image;
    }
}