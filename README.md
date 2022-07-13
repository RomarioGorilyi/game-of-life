# Game of Life

## Description
Game of Life is an application where we run a simulation of cells evolution.<br>
The universe of the Game of Life is an infinite two-dimensional orthogonal grid of square cells,
each of which is in one of 2 possible states, alive or dead.
Every cell interacts with its 8 neighbors, which are the cells that are horizontally, vertically, or diagonally 
adjacent.

The initial pattern constitutes the seed of the system.<br>
The 1st generation is created by applying the game rules simultaneously to every cell in the seed, live or dead.<br>
Births and deaths occur simultaneously.<br>
Each generation is a pure function of the preceding one.
The rules continue to be applied repeatedly to create further generations.

## Rules

### Basic Rule
1. Any live cell with fewer than 2 live neighbors dies as if caused by underpopulation.
2. Any live cell with 2 or 3 live neighbors lives on to the next generation.
3. Any live cell with more than 3 live neighbors dies, as if by overcrowding.
4. Any dead cell with exactly 3 live neighbors becomes a live cell, as if by reproduction.

### TBA

## How to run
1. Build executable .jar file via command: <code>./gradlew build</code>
2. Go to the directory where .jar file should be created (e.g. ./build/libs/)
3. Run in console: <code>java -jar game-of-life-0.1.0.jar</code>
4. There are 2 modes of app running available:
   1. **auto**:
      * type in the console: <code>auto</code>
      * new generations are executed by default with 1 sec period (for now, this can be changed only in <code>Main.
        java</code> class, configurability may be added in future)
      * no graceful stop of the simulation is available :(, please use forceful console termination (e.g. <code>Ctrl + 
        C</code>)) 
   2. **manual**:
      * type in the console: <code>next</code>
      * a new generation is executed manually per each <code>next</code> command
      * to exit the application type in the console: <code>q</code>

To start the simulation an initial configuration and observing how it evolves.
