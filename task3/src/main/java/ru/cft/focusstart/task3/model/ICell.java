package ru.cft.focusstart.task3.model;


public interface ICell {

    boolean isOpened();

    boolean isMine();

    boolean hasFlag();

    int getX();

    int getY();

    int getAroundMinesCount();
}
