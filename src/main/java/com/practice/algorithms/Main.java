package com.practice.algorithms;

import com.practice.algorithms.models.Board;
import com.practice.algorithms.models.CellsPattern;
import com.practice.algorithms.rules.BasicGameOfLifeEvolutionRule;
import com.practice.algorithms.services.BasicGameService;
import com.practice.algorithms.services.ConsoleGuiBoardService;
import com.practice.algorithms.services.GameService;
import com.practice.algorithms.services.GuiBoardService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private static Board board;
    private static GameService gameService;
    private static GuiBoardService guiBoardService;

    public static void main(String[] args) throws IOException, InterruptedException {
        initGame();
        navigateApplicationCli();
    }

    private static void initGame() {
        board = new Board(25, 25);
        gameService = new BasicGameService(board, new BasicGameOfLifeEvolutionRule())
                .addLivePattern(CellsPattern.GLIDER, Board.Position.CENTER);
        guiBoardService = new ConsoleGuiBoardService();
        guiBoardService.displayBoardState(board);

        // navigation options
        System.out.println("""
        
        Commands available:
        * q: quit from the application
        * next: manually simulate a new generation
        * auto: automatically simulate new generations""");
    }

    private static void nextGeneration() {
        gameService.nextGeneration();
        guiBoardService.displayBoardState(board);
    }

    private static void navigateApplicationCli() throws IOException, InterruptedException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line = "";

        while (!line.equalsIgnoreCase("q")) {
            line = in.readLine();
            if (line.equals("auto")) {
                while (true) {
                    nextGeneration();
                    Thread.sleep(1000);
                }
            } else if (line.equals("next")) {
                nextGeneration();
            }
        }

        in.close();
    }
}