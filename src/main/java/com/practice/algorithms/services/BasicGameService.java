package com.practice.algorithms.services;

import com.practice.algorithms.exceptions.CoordinateException;
import com.practice.algorithms.models.Board;
import com.practice.algorithms.models.CellDiff;
import com.practice.algorithms.models.CellsPattern;
import com.practice.algorithms.models.Coordinates;
import com.practice.algorithms.rules.CellEvolutionRule;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BasicGameService implements GameService {

    private final Board board;
    private final CellEvolutionRule cellEvolutionRule;

    private final List<CellDiff> cellStatesDiff = new ArrayList<>();

    public BasicGameService(Board board, CellEvolutionRule cellEvolutionRule) {
        this.board = board;
        this.cellEvolutionRule = cellEvolutionRule;
    }

    @Override
    public BasicGameService addLiveCell(int cellXCoordinate, int cellYCoordinate) throws CoordinateException {
        validateCellXCoordinate(cellXCoordinate);
        validateCellYCoordinate(cellYCoordinate);

        board.setCellState(1, cellXCoordinate, cellYCoordinate);
        return this;
    }

    private void validateCellXCoordinate(int cellCoordinate) throws CoordinateException {
        validateCellCoordinate(cellCoordinate, "X", board.getWidth() - 1);
    }

    private void validateCellYCoordinate(int cellCoordinate) throws CoordinateException {
        validateCellCoordinate(cellCoordinate, "Y", board.getHeight() - 1);
    }

    private void validateCellCoordinate(int cellCoordinate, String coordinateType, int coordinateMaxLimit)
            throws CoordinateException {
        if (cellCoordinate < 0) {
            throw new CoordinateException(String.format("Target %s coordinate [%d] can't be negative.",
                    coordinateType, cellCoordinate));
        } else if (cellCoordinate > coordinateMaxLimit) {
            throw new CoordinateException(String.format("Target %s coordinate [%d] can't be out of board boundaries [%d].",
                    coordinateType, cellCoordinate, coordinateMaxLimit));
        }
    }

    @Override
    public BasicGameService addLivePattern(CellsPattern pattern, Coordinates startPatternCoordinates) {
        for (int patternYCoordinateIndex = 0; patternYCoordinateIndex < pattern.getHeight(); patternYCoordinateIndex++) {
            for (int patternXCoordinateIndex = 0; patternXCoordinateIndex < pattern.getWidth(); patternXCoordinateIndex++) {
                board.setCellState(pattern.getCells()[patternYCoordinateIndex][patternXCoordinateIndex],
                        patternXCoordinateIndex + startPatternCoordinates.xCoordinate(),
                        patternYCoordinateIndex + startPatternCoordinates.yCoordinate());
            }
        }
        return this;
    }

    @Override
    public BasicGameService addLivePattern(CellsPattern pattern, Board.Position positionOnBoard) {
        return addLivePattern(pattern, calculateStartPatternCoordinates(pattern, positionOnBoard));
    }

    /**
     * Pattern start coordinates should be offset by the size of the pattern
     * so that the actual center of the pattern is located at the specified position on the board.
     */
    private Coordinates calculateStartPatternCoordinates(CellsPattern pattern, Board.Position positionOnBoard) {
        Coordinates positionCoordinates = switch (positionOnBoard) {
            // for even numbers we select the cell that is to the right and down from the actual center
            case CENTER -> new Coordinates(board.getWidth() / 2, board.getHeight() / 2);
            default -> new Coordinates(0, 0);
        };
        return new Coordinates(positionCoordinates.xCoordinate() - pattern.getWidth() / 2,
                positionCoordinates.yCoordinate() - pattern.getHeight() / 2);
    }

    @Override
    public void nextGeneration() {
        for (int yCoordinateIndex = 0; yCoordinateIndex < board.getHeight(); yCoordinateIndex++) {
            for (int xCoordinateIndex = 0; xCoordinateIndex < board.getWidth(); xCoordinateIndex++) {
                int currentCellState = board.getCellState(xCoordinateIndex, yCoordinateIndex);
                int newCellState = cellEvolutionRule.evolveCell(currentCellState, getLiveNeighboursNumber(
                        xCoordinateIndex, yCoordinateIndex));
                if (newCellState != currentCellState) {
                    cellStatesDiff.add(new CellDiff(xCoordinateIndex, yCoordinateIndex, newCellState));
                }
            }
        }

        // apply diff to the actual board
        cellStatesDiff.forEach(cellDiff -> board.setCellState(cellDiff.newCellState(), cellDiff.cellXCoordinate(),
                cellDiff.cellYCoordinate()));
        cellStatesDiff.clear();
        board.incrementGeneration();
    }

    private int getLiveNeighboursNumber(int cellXCoordinate, int cellYCoordinate) {
        return getNeighboursCoordinates(cellXCoordinate, cellYCoordinate).stream()
                .map(neighbourCoordinates ->
                        board.getCellState(neighbourCoordinates.xCoordinate(), neighbourCoordinates.yCoordinate()))
                .reduce(Integer::sum)
                .orElse(0);
    }

    private List<Coordinates> getNeighboursCoordinates(int cellXCoordinate, int cellYCoordinate) {
        int xNeighbourCoordinateStart = cellXCoordinate == 0 ? cellXCoordinate : cellXCoordinate - 1;
        int xNeighbourCoordinateEnd = cellXCoordinate == board.getWidth() - 1 ? cellXCoordinate : cellXCoordinate + 1;
        int yNeighbourCoordinateStart = cellYCoordinate == 0 ? cellYCoordinate : cellYCoordinate - 1;
        int yNeighbourCoordinateEnd = cellYCoordinate == board.getHeight() - 1 ? cellYCoordinate : cellYCoordinate + 1;

        List<Coordinates> neighboursCoordinates = IntStream.rangeClosed(
                xNeighbourCoordinateStart, xNeighbourCoordinateEnd)
                .mapToObj(xNeighbourCoordinate ->
                        IntStream.rangeClosed(yNeighbourCoordinateStart, yNeighbourCoordinateEnd).mapToObj(
                                yNeighbourCoordinate -> new Coordinates(xNeighbourCoordinate, yNeighbourCoordinate)))
                .flatMap(Function.identity()).collect(Collectors.toList());
        // remove coordinates of the target cell
        neighboursCoordinates.remove(new Coordinates(cellXCoordinate, cellYCoordinate));
        return neighboursCoordinates;
    }

}
