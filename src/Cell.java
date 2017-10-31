/* Name: John Miller
 * Date: 23 October 2017
 * Class: CS 335 - 001
 * Project: Maze
 *
 * Structure: - Create a class for each individual Cell. The Cells will change based on how many rows and columns
 *              there are.
 *            - Each cell will also have a visited flag on it.
 *            - Each cell needs 4 lines to draw the walls around it. A line goes on top, bottom, left and right.
 *            - The lines will have an array of booleans, so the wall will be drawn if it is true, and not drawn if
 *              it is false.
 *            - Each cell will have an i and j value, to represent where it is on the grid. Each i, j value will
 *              represent the top left corner of the cell and lines (walls) are drawn from here
 */

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

class Cell extends JPanel{

    private boolean visited = false;
    int[] walls = {1, 1, 1, 1};

    int i, j;

    // The cell object gets an i, j, rows and cols
    Cell(int i, int j){
        this.i = i;
        this.j = j;

        super.setBackground(Color.BLACK);
        super.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
    }

    void drawWalls(){
        super.setBorder(BorderFactory.createMatteBorder(walls[0], walls[1], walls[2], walls[3], Color.WHITE));
    }

//
//    // Set a cell as visited or check if it has been visited or not
//    public void setVisited(boolean visited) { this.visited = visited; }
//    public boolean isVisited(){ return visited; }

    int get_i(){ return i; }
    int get_j(){ return j; }
}