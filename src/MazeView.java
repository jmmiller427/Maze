/* Name: John Miller
 * Date: 31 October 2017
 * Class: CS 335 - 001
 * Project: Maze
 *
 * Structure: Create a new grid by taking a row and column number. Initialize all the Cells and put them in
 *            a grid array. When the generate button is clicked, generate the maze. Solve the maze if the solve
 *            button is clicked.
 */

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Random;

class MazeView extends JPanel{

    Cell grid[][];
    Cell currentSolveCell, finalSolveCell;
    boolean visited[][];
    private Cell currentGenCell, neighborGenCell;
    private ArrayList<Cell> neighbors, solveNeighbors;
    private LinkedList<Cell> cellStack = new LinkedList<>();
    private LinkedList<Cell> solveCellStack = new LinkedList<>();
    private int row, col;
    private boolean visitedSolve[][];
    boolean genRunning;
    private Timer mazeGenTimer, mazeSolveTimer;
    private int total, visitedCells;

    // Create a maze view JPanel that is rows tall and cols wide
    MazeView(int rows, int cols){
        newGrid(rows, cols);
    }

    // Create the grid
    void newGrid(int rows, int cols){

        // Set the rows to the number of rows and columns entered
        setRows(rows);
        setCols(cols);

        total = rows * cols;

        // Set the grid to the number of rows and columns
        super.removeAll();
        super.setLayout(new GridLayout(rows, cols));

        // Grid is a new Cell array of rows and cols, create visited arrays of rows and cols size
        grid = new Cell[rows][cols];
        visited = new boolean[rows][cols];
        visitedSolve = new boolean[rows][cols];

        // Loop through to create a single i, j cell
        // Add it to the grid at i, j
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                Cell cell = new Cell(i, j);
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

        // Push the current cell onto the stack and set it as visited
        cellStack.push(currentGenCell);
        visited[currentGenCell.get_i()][currentGenCell.get_j()] = true;

        // Get the neighbors of the current cell
        getNeighbors(currentGenCell, visited);

        // If there are no neighbors
        if (neighbors.isEmpty()) {

            // While there are Cells on the stack
            while (!cellStack.isEmpty()) {

                // Pop off the last item and set it as a temp Cell and get the neighbors
                Cell temp = cellStack.pop();
                temp.setBackground(Color.GRAY);
                getNeighbors(temp, visited);

                // If there are neighbors of the temp cell
                if (!neighbors.isEmpty()) {

                    // Set the current cell as the temp cell and break out of the loop
                    currentGenCell = temp;
                    break;
                }
            }
        }

        // Set the neighbor cell as a random neighbor with a try/catch, if the neighbors array is empty then
        // the every cell has been visited, so set genRunning to false to break out of the loop
        try { neighborGenCell = neighbors.get(new Random().nextInt(neighbors.size())); }
        catch(IllegalArgumentException e) { genRunning = false; }

        currentGenCell.setBackground(Color.GRAY);

        // Break the wall between the current cell and the neighbor cell
        breakWall(currentGenCell, neighborGenCell);
        currentGenCell.drawWalls();
        neighborGenCell.drawWalls();

        // Set the current cell as the neighbor cell
        currentGenCell = neighborGenCell;
    }

    void generateMaze(Cell[][] grid, boolean[][] visited){

        // Set the first cell as position 0, 0
        currentGenCell = grid[0][0];
        genRunning = true;

        // Loop until all cells have been visited
        while(genRunning){
            doGenMaze(visited);
            Maze.progressView.currentLabel.setText("Currently: Generate Finished");
            Maze.progressView.currentLabel.repaint();
            Maze.progressView.progressLabel.setText("Progress: 0%");
            Maze.progressView.progressLabel.repaint();
        }
    }

    void showGenerateMaze(Cell[][] grid, boolean[][] visited, int speed){

        // Set the first cell as 0, 0
        currentGenCell = grid[0][0];
        genRunning = true;

        // Create an action listener for a timer to animate the generation
        ActionListener sec = e -> {

            // If the loop should still be running, generate one cell of the maze
            if(genRunning) {
                doGenMaze(visited);

                // Update labels
                Maze.progressView.currentLabel.setText("Currently: Generating...");
                Maze.progressView.currentLabel.repaint();
                Maze.progressView.progressLabel.setText("Progress: 0%");
                Maze.progressView.progressLabel.repaint();
            }

            else{
                mazeGenTimer.stop();

                // Update labels and set solve buttons to enabled after the maze is generated
                Maze.progressView.currentLabel.setText("Currently: Generating Finished");
                Maze.progressView.currentLabel.repaint();
                Maze.controlView.showSolve.setEnabled(true);
                Maze.controlView.solve.setEnabled(true);
            }
        };

        // Create a new timer from the speed of the slider and start the timer
        mazeGenTimer = new Timer(speed, sec);
        mazeGenTimer.start();
    }

    private void breakWall(Cell currentCell, Cell neighborCell){

        // Get the i and j of the neighbor cell and the current cell
        int n_i = neighborCell.get_i();
        int n_j = neighborCell.get_j();
        int c_i = currentCell.get_i();
        int c_j = currentCell.get_j();

        // If the neighbor i or j positions are the same, find if neighbor is above,
        // below, right or left of the current cell
        if (c_i == n_i && n_j > c_j){
            // Delete current right, neighbor left
            currentCell.walls[3] = 0;
            neighborCell.walls[1] = 0;
        }
        if (c_i == n_i && n_j < c_j){
            // Current left, neighbor right
            currentCell.walls[1] = 0;
            neighborCell.walls[3] = 0;
        }
        if (c_j == n_j && n_i > c_i){
            // Current top, neighbor bottom
            currentCell.walls[2] = 0;
            neighborCell.walls[0] = 0;
        }
        if (c_j == n_j && n_i < c_i){
            // Current bottom, neighbor top
            currentCell.walls[0] = 0;
            neighborCell.walls[2] = 0;
        }
    }

    private void getNeighbors(Cell currentCell, boolean[][] visited){

        // Create an empty neighbor array
        neighbors = new ArrayList<>();

        // Get the i and j position of the current cell
        int i = currentCell.get_i();
        int j = currentCell.get_j();

        // If i or j are in bounds and the neighbor in the direction is not visited
        // add it to the neighbors array
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
        // Set the start and final cells
        currentSolveCell = grid[0][0];
        finalSolveCell = grid[getRow() - 1][getCol() - 1];
    }

    private void doSolveMaze(){

        visitedCells++;

        // Push the current cell on the stack and mark it as visited
        solveCellStack.push(currentSolveCell);
        visitedSolve[currentSolveCell.get_i()][currentSolveCell.get_j()] = true;

        // Get the neighbors of the current cell
        getSolveNeighbors(currentSolveCell, visitedSolve);

        // If there are no neighbors of the current cell
        if (solveNeighbors.isEmpty()){

            // While there are cells on the stack
            while (!solveCellStack.isEmpty()){

                // Set a temp cell to the last cell on the stack
                Cell tempSolve = solveCellStack.pop();
                tempSolve.setBackground(Color.BLACK);

                // Get the temp cells neighbors
                getSolveNeighbors(tempSolve, visitedSolve);

                // If there are neighbors of the temp cell
                if (!solveNeighbors.isEmpty()){

                    // Set the current cell to the solve cell
                    currentSolveCell = tempSolve;
                    break;
                }
            }
        }

        // Set the neighbor cell as the first available neighbor. Neighbors are chosen
        // checking if there is a right, bottom, left and then top neighbor. The order matters to
        // try and get to the final cell as quickly as possible
        Cell neighborSolveCell = solveNeighbors.get(0);

        neighborSolveCell.setBackground(Color.ORANGE);
        currentSolveCell.setBackground(Color.ORANGE);

        // Set the current cell as the neighbor cell
        currentSolveCell = neighborSolveCell;
    }

    void solveMaze(){

        visitedCells = 0;

        // Set the start and final cells
        setStartFinalCell();

        // While the final cell and current cell are not equal, solve the maze
        while (finalSolveCell != currentSolveCell){
            doSolveMaze();
        }
        Maze.progressView.currentLabel.setText("Currently: Solve Finished");
        Maze.progressView.currentLabel.repaint();
        Maze.progressView.progressLabel.setText("Progress: " + visitedCells * 100 / total + "%");
        Maze.progressView.progressLabel.repaint();
    }

    void showSolveMaze(int speed){

        visitedCells = 0;

        // Set the current and final cells
        setStartFinalCell();

        // Create an action listener to run the solve maze DFS
        ActionListener sec = e -> {

            // If the current cell and final cell aren't equal, solve the maze
            if(finalSolveCell != currentSolveCell) {
                doSolveMaze();
                Maze.progressView.currentLabel.setText("Currently: Solving Maze...");
                Maze.progressView.currentLabel.repaint();
                Maze.progressView.progressLabel.setText("Progress: " + visitedCells * 100 / total + "%");
                Maze.progressView.progressLabel.repaint();
            }

            // Once it is solved stop the timer
            else{
                mazeSolveTimer.stop();
                Maze.progressView.currentLabel.setText("Currently: Solve Finished");
                Maze.progressView.currentLabel.repaint();
                Maze.controlView.generate.setEnabled(true);
                Maze.controlView.showGenerate.setEnabled(true);
            }
        };

        // Create a timer to animate the solve
        mazeSolveTimer = new Timer(speed, sec);
        mazeSolveTimer.start();
    }

    private void getSolveNeighbors(Cell currentSolveCell, boolean[][] visitedSolve){

        // Create a neighbor array and set i and j of the current cell
        solveNeighbors = new ArrayList<>();
        int i = currentSolveCell.get_i();
        int j = currentSolveCell.get_j();

        // Check if there is a wall to the right, then bottom, then left, then top.
        // Add the neighbor if there is no wall and it has not been visited
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

    // Set the rows and cols and get the rows and cols
    private void setRows(int rows){ row = rows; }
    private void setCols(int cols){ col = cols; }
    private int getRow(){ return row; }
    private int getCol(){ return col; }
}
