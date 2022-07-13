package com.practice.algorithms.rules;

/**
 * Basic rule of cells evolution.
 */
public class BasicGameOfLifeEvolutionRule implements CellEvolutionRule {

    private static final int CELL_DEAD_STATE = 0;
    private static final int CELL_LIVE_STATE = 1;

    /**
     * 1. Any live cell with fewer than 2 live neighbors dies as if caused by underpopulation.
     * 2. Any live cell with 2 or 3 live neighbors lives on to the next generation.
     * 3. Any live cell with more than 3 live neighbors dies, as if by overcrowding.
     * 4. Any dead cell with exactly 3 live neighbors becomes a live cell, as if by reproduction.
     *
     * @param cellState            current state of the specified cell represented by O or 1
     * @param liveNeighboursNumber number of live neighbours of the specified cell
     * @return new state of the specified cell represented by O or 1
     */
    @Override
    public int evolveCell(int cellState, int liveNeighboursNumber) {
        int newCellState = cellState;

        if (cellState == CELL_LIVE_STATE) {
            if (liveNeighboursNumber != 2 && liveNeighboursNumber != 3) {
                newCellState = CELL_DEAD_STATE;
            }
        } else { // cell is dead, exceptional cases when cell state is neither 0, nor 1 is ignored
            if (liveNeighboursNumber == 3) {
                newCellState = CELL_LIVE_STATE;
            }
        }
        return newCellState;
    }
}
