/* Name: John Miller
 * Date: 22 October 2017
 * Class: CS 335 - 001
 * Project: Maze
 */

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Maze extends JFrame implements ActionListener{

    ControlView controlView;
    ProgressView progressView;
    MazeView mazeView;

//    private int rows;
//    private int cols;

    private Maze(){

        super("Maze");

        controlView = new ControlView(this);
        progressView = new ProgressView();

        int rows = controlView.returnRow();
        int cols = controlView.returnCol();
        controlView.returnSpeed();

        mazeView = new MazeView(rows, cols);

        Container c = getContentPane();

        c.add(mazeView, BorderLayout.CENTER);
        c.add(controlView, BorderLayout.EAST);
        c.add(progressView, BorderLayout.SOUTH);

        setSize(800, 600);
        setResizable(true);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e){

        if (e.getActionCommand().equals("Stop")){
            mazeView.genRunning = false;
            mazeView.currentSolveCell = mazeView.finalSolveCell;
        }
        else if (e.getActionCommand().equals("Generate")){

            controlView.stop.setEnabled(true);
            controlView.showSolve.setEnabled(true);
            controlView.solve.setEnabled(true);
            controlView.showGenerate.setEnabled(false);
            controlView.generate.setEnabled(false);

            if (controlView.showGenerate.isSelected()){

                mazeView.newGrid(controlView.returnRow(), controlView.returnCol());
                mazeView.revalidate();
                mazeView.repaint();
                mazeView.showGenerateMaze(mazeView.grid, mazeView.visited, controlView.returnSpeed());
            }
            else {

                mazeView.newGrid(controlView.returnRow(), controlView.returnCol());
                mazeView.revalidate();
                mazeView.repaint();
                mazeView.generateMaze(mazeView.grid, mazeView.visited);
            }
        }
        else if (e.getActionCommand().equals("Solve")){

            controlView.solve.setEnabled(false);
            controlView.showSolve.setEnabled(false);
            controlView.generate.setEnabled(true);
            controlView.showGenerate.setEnabled(true);

            if (controlView.showSolve.isSelected()){
                mazeView.showSolveMaze(controlView.returnSpeed());
            }
            else{
                mazeView.solveMaze();
            }
        }
    }

    public static void main(String args[]){
        Maze M = new Maze();
        M.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}
