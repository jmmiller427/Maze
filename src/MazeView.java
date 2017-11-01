/* Name: John Miller
 * Date: 31 October 2017
 * Class: CS 335 - 001
 * Project: Maze
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Random;

class MazeView extends JPanel{

    Cell grid[][];
    Cell currentSolveCell, finalSolveCell;
    boolean visited[][];
    private Cell cell, currentGenCell, neighborSolveCell, neighborGenCell, temp, tempSolve;
    private ArrayList<Cell> neighbors, solveNeighbors;
    private LinkedList<Cell> cellStack = new LinkedList<>();
    private LinkedList<Cell> solveCellStack = new LinkedList<>();
    private int row, col;
    private boolean visitedSolve[][];
    boolean genRunning;
    private Timer mazeGenTimer, mazeSolveTimer;

    // Create a maze view JPanel that is rows tall and cols wide
    MazeView(int rows, int cols){
        newGrid(rows, cols);
    }

    // Create the grid
    void newGrid(int rows, int cols){

        setRows(rows);
        setCols(cols);

        super.removeAll();
        super.setLayout(new GridLayout(rows, cols));

        // Grid is a new Cell array of rows and cols
        grid = new Cell[rows][cols];
        visited = new boolean[rows][cols];
        visitedSolve = new boolean[rows][cols];

        // Loop through to create a single i, j cell
        // Add it to the grid at i, j
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                cell = new Cell(i, j);
                grid[i][j] = cell;
                visited[i][j] = false;
                visitedSolve[i][j] = false;
                super.add(grid[i][j]);
            }
        }
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    /*                     START GENERATE MAZE                     */
    private void doGenMaze(boolean[][] visited){

        cellStack.push(currentGenCell);
        visited[currentGenCell.get_i()][currentGenCell.get_j()] = true;

        getNeighbors(currentGenCell, visited);

        if (neighbors.isEmpty()) {
            while (!cellStack.isEmpty()) {
                temp = cellStack.pop();
                temp.setBackground(Color.BLACK);
                getNeighbors(temp, visited);
                if (!neighbors.isEmpty()) {
                    currentGenCell = temp;
                    break;
                }
                if (temp == null) {
                    genRunning = false;
                    break;
                }
            }
        }

        neighborGenCell = neighbors.get(new Random().nextInt(neighbors.size()));
        currentGenCell.setBackground(Color.BLACK);

        breakWall(currentGenCell, neighborGenCell);
        currentGenCell.drawWalls();
        neighborGenCell.drawWalls();

        currentGenCell = neighborGenCell;
    }

    void generateMaze(Cell[][] grid, boolean[][] visited){

        currentGenCell = grid[0][0];
        genRunning = true;

        while(genRunning){
            doGenMaze(visited);
        }
    }

    void showGenerateMaze(Cell[][] grid, boolean[][] visited, int speed){

        currentGenCell = grid[0][0];
        genRunning = true;

        ActionListener sec = e -> {

            if(genRunning) {
                doGenMaze(visited);
            }
            else{
                mazeGenTimer.stop();
            }
        };


        mazeGenTimer = new Timer(speed, sec);
        mazeGenTimer.start();
    }

    private void breakWall(Cell currentCell, Cell neighborCell){

        int n_i = neighborCell.get_i();
        int n_j = neighborCell.get_j();

        int c_i = currentCell.get_i();
        int c_j = currentCell.get_j();

        if (c_i == n_i && n_j > c_j){
            // delete current right, neigh left
            currentCell.walls[3] = 0;
            neighborCell.walls[1] = 0;
        }
        if (c_i == n_i && n_j < c_j){
            // current left, neigh right
            currentCell.walls[1] = 0;
            neighborCell.walls[3] = 0;
        }
        if (c_j == n_j && n_i > c_i){
            // current top, neigh bottom
            currentCell.walls[2] = 0;
            neighborCell.walls[0] = 0;
        }
        if (c_j == n_j && n_i < c_i){
            // current bottom, neigh top
            currentCell.walls[0] = 0;
            neighborCell.walls[2] = 0;
        }
    }

    private void getNeighbors(Cell currentCell, boolean[][] visited){

        neighbors = new ArrayList<>();

        int i = currentCell.get_i();
        int j = currentCell.get_j();

        if (i > 0 && !visited[i - 1][j]) {
            neighbors.add(grid[i - 1][j]);
        }
        if (j > 0 && !visited[i][j - 1]) {
            neighbors.add(grid[i][j - 1]);
        }
        if (i < getRow() - 1 && !visited[i + 1][j]) {
            neighbors.add(grid[i + 1][j]);
        }
        if (j < getCol() - 1 && !visited[i][j + 1]) {
            neighbors.add(grid[i][j + 1]);
        }
    }
    /*                      END GENERATE MAZE                      */
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    /*                      START SOLVE MAZE                       */
    private void setStartFinalCell(){
        currentSolveCell = grid[0][0];
        finalSolveCell = grid[getRow() - 1][getCol() - 1];
    }

    private void doSolveMaze(){

        solveCellStack.push(currentSolveCell);
        visitedSolve[currentSolveCell.get_i()][currentSolveCell.get_j()] = true;

        getSolveNeighbors(currentSolveCell, visitedSolve);

        if (solveNeighbors.isEmpty()){
            while (!solveCellStack.isEmpty()){
                tempSolve = solveCellStack.pop();
                tempSolve.setBackground(Color.GRAY);
                getSolveNeighbors(tempSolve, visitedSolve);

                if (!solveNeighbors.isEmpty()){
                    currentSolveCell = tempSolve;
                    break;
                }
                if (tempSolve == null){
                    break;
                }
            }
        }

        neighborSolveCell = solveNeighbors.get(0);

        neighborSolveCell.setBackground(Color.ORANGE);
        currentSolveCell.setBackground(Color.ORANGE);

        currentSolveCell = neighborSolveCell;
    }

    void solveMaze(){
        setStartFinalCell();

        while (finalSolveCell != currentSolveCell){
            doSolveMaze();
        }
    }

    void showSolveMaze(int speed){
        setStartFinalCell();

        ActionListener sec = e -> {

            if(finalSolveCell != currentSolveCell) {
                doSolveMaze();
            }
            else{
                mazeSolveTimer.stop();
            }
        };

        mazeSolveTimer = new Timer(speed, sec);
        mazeSolveTimer.start();
    }

    private void getSolveNeighbors(Cell currentSolveCell, boolean[][] visitedSolve){

        solveNeighbors = new ArrayList<>();

        int i = currentSolveCell.get_i();
        int j = currentSolveCell.get_j();

        if (currentSolveCell.walls[3] == 0 && !visitedSolve[i][j + 1]){
            solveNeighbors.add(grid[i][j + 1]);
        }
        if (currentSolveCell.walls[2] == 0 && !visitedSolve[i + 1][j]){
            solveNeighbors.add(grid[i + 1][j]);
        }
        if (currentSolveCell.walls[1] == 0 && !visitedSolve[i][j - 1]){
            solveNeighbors.add(grid[i][j - 1]);
        }
        if (currentSolveCell.walls[0] == 0 && !visitedSolve[i - 1][j]){
            solveNeighbors.add(grid[i - 1][j]);
        }
    }
    /*                      END SOLVE MAZE                         */
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private void setRows(int rows){ row = rows; }
    private void setCols(int cols){ col = cols; }
    private int getRow(){ return row; }
    private int getCol(){ return col; }
}
