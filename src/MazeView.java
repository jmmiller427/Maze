import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Random;

class MazeView extends JPanel{

    Cell grid[][];
    private Cell cell, currentCell, finalCell, neighborCell, temp;
    ArrayList<Cell> neighbors;
    LinkedList<Cell> cellStack = new LinkedList<>();
    int row, col;
    boolean visited[][];
    boolean genRunning;
    int visit = 0;
    int total;
    Timer mazeGenTimer;

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

        // Loop through to create a single i, j cell
        // Add it to the grid at i, j
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                cell = new Cell(i, j);
                grid[i][j] = cell;
                visited[i][j] = false;
                super.add(grid[i][j]);
            }
        }
    }

    void doGenMaze(){

        cellStack.push(currentCell);
        visited[currentCell.get_i()][currentCell.get_j()] = true;

        getNeighbors(currentCell, visited);

        if (neighbors.isEmpty()) {
            while (!cellStack.isEmpty()) {
                temp = cellStack.pop();
                temp.setBackground(Color.GRAY);
                getNeighbors(temp, visited);
                if (!neighbors.isEmpty()) {
                    currentCell = temp;
                    break;
                }
                if (temp == null) {
                    genRunning = false;
                    break;
                }
            }
        }

        neighborCell = neighbors.get(new Random().nextInt(neighbors.size()));
        currentCell.setBackground(Color.GRAY);

        breakWall(currentCell, neighborCell);
        currentCell.drawWalls();
        neighborCell.drawWalls();

        currentCell = neighborCell;
    }

    void generateMaze(Cell[][] grid, boolean[][] visited){

        currentCell = grid[0][0];
        genRunning = true;

        while(genRunning){
            doGenMaze();
        }
    }

    void showGenerateMaze(Cell[][] grid, boolean[][] visited, int speed){

        currentCell = grid[0][0];
        genRunning = true;

        ActionListener sec = e -> {

            if(genRunning) {
                doGenMaze();
            }
            else{
                mazeGenTimer.stop();
            }
        };

        mazeGenTimer = new Timer(speed, sec);
        mazeGenTimer.start();
    }

    void breakWall(Cell currentCell, Cell neighborCell){

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

    void getNeighbors(Cell currentCell, boolean[][] visited){

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

    void setRows(int rows){ row = rows; }
    void setCols(int cols){ col = cols; }
    int getRow(){ return row; }
    int getCol(){ return col; }
}
