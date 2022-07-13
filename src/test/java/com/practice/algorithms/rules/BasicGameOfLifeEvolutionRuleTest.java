package com.practice.algorithms.rules;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BasicGameOfLifeEvolutionRuleTest {

    private final CellEvolutionRule evolutionRule = new BasicGameOfLifeEvolutionRule();

    private static final int LIVE_STATE = 1;
    private static final int DEAD_STATE = 0;

    @ParameterizedTest(name = "Cell should not change its LIVE state when liveNeighboursNumber={0}")
    @ValueSource(ints = {2, 3})
    void testEvolveCellWhenCellIsLiveAndShouldNotChangeState(int liveNeighboursNumber) {
        int newCellState = evolutionRule.evolveCell(LIVE_STATE, liveNeighboursNumber);
        assertEquals(LIVE_STATE, newCellState);
    }

    @ParameterizedTest(name = "Cell state should become DEAD when liveNeighboursNumber={0}")
    @ValueSource(ints = {0, 1, 4, 5, 6, 7, 8, 9, Integer.MAX_VALUE})
    void testEvolveCellWhenCellIsLiveAndShouldBecomeDead(int liveNeighboursNumber) {
        int newCellState = evolutionRule.evolveCell(LIVE_STATE, liveNeighboursNumber);
        assertEquals(DEAD_STATE, newCellState);
    }

    @ParameterizedTest(name = "Cell state should become LIVE when liveNeighboursNumber={0}")
    @ValueSource(ints = {3})
    void testEvolveCellWhenCellIsDeadAndShouldBecomeLive(int liveNeighboursNumber) {
        int newCellState = evolutionRule.evolveCell(DEAD_STATE, liveNeighboursNumber);
        assertEquals(LIVE_STATE, newCellState);
    }

    @ParameterizedTest(name = "Cell should not change its DEAD state when liveNeighboursNumber={0}")
    @ValueSource(ints = {0, 1, 2, 4, 5, 6, 7, 8, 9, Integer.MAX_VALUE})
    void testEvolveCellWhenCellIsDeadAndShouldNotChangeState(int liveNeighboursNumber) {
        int newCellState = evolutionRule.evolveCell(DEAD_STATE, liveNeighboursNumber);
        assertEquals(DEAD_STATE, newCellState);
    }
}