/* Name: John Miller
 * Date: 23 October 2017
 * Class: CS 335 - 001
 * Project: Maze
 *
 * Structure: Create a Cell object. Each object is an individual JPanel with 4 walls.
 *            Each Cell has an i and j to know the position of each Cell. The walls can be deleted
 *            but changing a wall from a 1 to a 0.
 */

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Color;

class Cell extends JPanel{

    // Create an array for each Wall of the cell.
    // 0: Top, 1: Left, 2: Bottom, 3: Right
    int[] walls = {1, 1, 1, 1};
    private int i, j;

    // The cell object gets an i, j, rows and cols
    Cell(int i, int j){
        this.i = i;
        this.j = j;

        // Set the background as Black for each cell and 4 white walls
        super.setBackground(Color.BLACK);
        super.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
    }

    // Draw or delete walls depending on which walls get removed while generating
    void drawWalls(){
        super.setBorder(BorderFactory.createMatteBorder(walls[0], walls[1], walls[2], walls[3], Color.WHITE));
    }

    // Return the i and j position of a cell
    int get_i(){ return i; }
    int get_j(){ return j; }
}