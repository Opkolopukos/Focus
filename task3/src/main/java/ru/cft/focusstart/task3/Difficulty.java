package ru.cft.focusstart.task3;

public enum Difficulty {
    EASY(9, 9, 10),
    NORMAL(16, 16, 40),
    HARD(30, 16, 99);

    private final int width;
    private final int height;
    private final int mines;

    Difficulty(int width, int height, int mines) {
        this.width = width;
        this.height = height;
        this.mines = mines;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMines() {
        return mines;
    }
}
