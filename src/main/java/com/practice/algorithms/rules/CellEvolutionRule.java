package com.practice.algorithms.rules;

@FunctionalInterface
public interface CellEvolutionRule {

    /**
     * Perform evolution of the specified cell to the next generation.
     *
     * @param cellState current state of the cell where {@code 1} - live and {@code 0} - dead
     * @param liveNeighboursNumber number of live neighbours of the cell
     * @return new state of the cell where {@code 1} - live and {@code 0} - dead
     */
    int evolveCell(int cellState, int liveNeighboursNumber);
}
