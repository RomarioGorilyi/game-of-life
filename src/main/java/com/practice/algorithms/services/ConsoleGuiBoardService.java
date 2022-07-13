package com.practice.algorithms.services;

import com.practice.algorithms.models.Board;

public class ConsoleGuiBoardService implements GuiBoardService {

    private static final String LIVE_CELL_SYMBOL = "*";
    private static final String DEAD_CELL_SYMBOL = ".";

    @Override
    public void displayBoardState(Board board) {
        System.out.printf("Generation %d:%n", board.getGeneration());
        int[][] boardSpace = board.getSpace();
        for (int yCoordinateIndex = 0; yCoordinateIndex < board.getHeight(); yCoordinateIndex++) {
            for (int xCoordinateIndex = 0; xCoordinateIndex < board.getWidth(); xCoordinateIndex++) {
                if (boardSpace[yCoordinateIndex][xCoordinateIndex] == 1) {
                    System.out.print(LIVE_CELL_SYMBOL);
                } else {
                    System.out.print(DEAD_CELL_SYMBOL);
                }
            }
            System.out.println(); // move to the next line
        }
    }
}
