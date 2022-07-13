package com.practice.algorithms.services;

import com.practice.algorithms.exceptions.CoordinateException;
import com.practice.algorithms.models.Board;
import com.practice.algorithms.models.Coordinates;
import com.practice.algorithms.rules.BasicGameOfLifeEvolutionRule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BasicGameServiceTest {

    private static final int DEAD_CELL = 0;
    private static final int LIVE_CELL = 1;

    private Board board;
    private GameService gameService;

    @BeforeEach
    public void setUp() {
        board = new Board(10, 15);
        gameService = new BasicGameService(board, new BasicGameOfLifeEvolutionRule());
    }

    @Test
    void testAddLiveCell() throws CoordinateException {
        // Given
        Arrays.stream(board.getSpace()).flatMapToInt(Arrays::stream)
                .forEach(cellState -> assertEquals(DEAD_CELL, cellState));

        // When
        Coordinates newLiveCellCoordinates = new Coordinates(1, 5);
        gameService.addLiveCell(newLiveCellCoordinates.xCoordinate(), newLiveCellCoordinates.yCoordinate());

        // Then
        assertEquals(LIVE_CELL, board.getCellState(newLiveCellCoordinates.xCoordinate(), newLiveCellCoordinates.yCoordinate()));
        int[][] newSpace = board.getSpace();
        for (int yCoordinateIndex = 0; yCoordinateIndex < board.getHeight(); yCoordinateIndex++) {
            for (int xCoordinateIndex = 0; xCoordinateIndex < board.getWidth(); xCoordinateIndex++) {
                if (xCoordinateIndex != newLiveCellCoordinates.xCoordinate()
                        && yCoordinateIndex != newLiveCellCoordinates.yCoordinate()) {
                    assertEquals(DEAD_CELL, newSpace[yCoordinateIndex][xCoordinateIndex]);
                }
            }
        }
    }

    @Test
    void testAddLiveCellWhenTargetXCoordinateIsOutOfBoardBoundaries() {
        int xCoordinateOutOfLimit = board.getWidth();
        Coordinates newLiveCellCoordinates = new Coordinates(xCoordinateOutOfLimit, 2);

        CoordinateException thrown = Assertions.assertThrows(CoordinateException.class, () ->
                gameService.addLiveCell(newLiveCellCoordinates.xCoordinate(), newLiveCellCoordinates.yCoordinate()),
                "CoordinateException is expected");

        assertEquals("Target X coordinate [10] can't be out of board boundaries [9].", thrown.getMessage());
    }

    @Test
    void testAddLiveCellWhenTargetYCoordinateIsOutOfBoardBoundaries() {
        int yCoordinateOutOfLimit = board.getHeight();
        Coordinates newLiveCellCoordinates = new Coordinates(1, yCoordinateOutOfLimit);

        CoordinateException thrown = Assertions.assertThrows(CoordinateException.class, () ->
                        gameService.addLiveCell(newLiveCellCoordinates.xCoordinate(), newLiveCellCoordinates.yCoordinate()),
                "CoordinateException is expected");

        assertEquals("Target Y coordinate [15] can't be out of board boundaries [14].", thrown.getMessage());
    }

    // TODO roma: add more test to cover other methods
}