package com.practice.algorithms.models;

import lombok.Getter;

/**
 * Board that represents the finite space where the game takes place.
 */
public class Board {

    @Getter
    private final int width;
    @Getter
    private final int height;
    @Getter
    private final int[][] space;
    @Getter
    private int generation;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        space = new int[height][width];
    }

    public int getCellState(int cellXCoordinate, int cellYCoordinate) {
        return space[cellYCoordinate][cellXCoordinate];
    }

    public void setCellState(int cellState, int cellXCoordinate, int cellYCoordinate) {
        space[cellYCoordinate][cellXCoordinate] = cellState;
    }

    public void incrementGeneration() {
        generation++;
    }

    public enum Position {
        CENTER
    }
}
