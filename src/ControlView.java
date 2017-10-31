import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class ControlView extends JPanel{

    private int rows = 10;
    private int cols = 10;
    private int speed = 25;

    JSlider rowSlider, columnSlider, speedSlider;
    JLabel rowsLabel, columnsLabel, speedLabel;
    JButton stop, solve, generate;
    JCheckBox showSolve, showGenerate;

    ControlView(ActionListener AL){

        stop = new JButton("Stop");
        solve = new JButton("Solve");
        generate = new JButton("Generate");
        showSolve = new JCheckBox("Show Solve");
        showGenerate = new JCheckBox("Show Generation");
        speedSlider = new JSlider(SwingConstants.HORIZONTAL, 10, 150, 70);
        rowSlider = new JSlider(SwingConstants.HORIZONTAL, 10, 50, 10);
        columnSlider = new JSlider(SwingConstants.HORIZONTAL, 10, 50, 10);
        speedLabel = new JLabel("  Speed: Medium");
        rowsLabel = new JLabel("  Rows: 10");
        columnsLabel = new JLabel("  Columns: 10");

        stop.addActionListener(AL);
        solve.addActionListener(AL);
        generate.addActionListener(AL);

        stop.setEnabled(false);
        solve.setEnabled(false);
        showSolve.setEnabled(false);

        super.add(generate);
        super.add(showGenerate);
        super.add(solve);
        super.add(showSolve);
        super.add(speedLabel);
        super.add(speedSlider);
        super.add(rowsLabel);
        super.add(rowSlider);
        super.add(columnsLabel);
        super.add(columnSlider);
        super.add(stop);

        super.setLayout(new GridLayout(11, 1, 0, 0));
    }

    int returnRow(){

        rowSlider.addChangeListener(e -> {
            rows = rowSlider.getValue();
            rowsLabel.setText("  Rows: " + rows);
            repaint();
        });
        return rows;
    }

    int returnCol(){

        columnSlider.addChangeListener(e -> {
            cols = columnSlider.getValue();
            columnsLabel.setText("  Columns: " + cols);
            repaint();
        });
        return cols;
    }

    int returnSpeed(){
        speedSlider.addChangeListener(e -> {
            speed = speedSlider.getValue();
            if (speed >= 100 && speed <= 150){
                speedLabel.setText("  Speed: Slow");
            }else if (speed >= 70 && speed < 100){
                speedLabel.setText("  Speed: Medium");
            }else{
                speedLabel.setText("  Speed: Fast");
            }
            repaint();
        });
        return speed;
    }
}
