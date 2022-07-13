package com.practice.algorithms.services;

import com.practice.algorithms.exceptions.CoordinateException;
import com.practice.algorithms.models.Board;
import com.practice.algorithms.models.CellsPattern;
import com.practice.algorithms.models.Coordinates;

public interface GameService {

    BasicGameService addLiveCell(int cellXCoordinate, int cellYCoordinate) throws CoordinateException;

    BasicGameService addLivePattern(CellsPattern pattern, Coordinates startPatternCoordinates);

    BasicGameService addLivePattern(CellsPattern pattern, Board.Position positionOnBoard);

    void nextGeneration();
}
