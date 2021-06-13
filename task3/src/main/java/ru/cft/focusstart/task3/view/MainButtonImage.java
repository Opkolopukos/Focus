package ru.cft.focusstart.task3.view;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public enum MainButtonImage {
    SMILEY("smiley", ".png"),
    DEFEAT("defeat", ".png"),
    VICTORY("victory", ".png");

    private Image image;

    MainButtonImage(String name, String extension) {
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
