package com.practice.algorithms.models;

import lombok.Getter;

import java.util.List;

public enum CellsPattern {

    GLIDER(3, 3, List.of(new Coordinates(0, 2), new Coordinates(1, 0),
            new Coordinates(1, 2), new Coordinates(2, 1), new Coordinates(2, 2)));

    @Getter
    private final int width;
    @Getter
    private final int height;
    @Getter
    private final int[][] cells;

    CellsPattern(int width, int height, List<Coordinates> liveCellsCoordinates) {
        this.width = width;
        this.height = height;
        cells = new int[height][width];
        liveCellsCoordinates.forEach(liveCellCoordinates ->
                cells[liveCellCoordinates.yCoordinate()][liveCellCoordinates.xCoordinate()] = 1);
    }
}
