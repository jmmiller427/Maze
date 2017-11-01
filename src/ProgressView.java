import javax.swing.*;
import java.awt.*;

class ProgressView extends JPanel{

    JLabel progressLabel, currentLabel;

    ProgressView(){

        progressLabel = new JLabel("Progress: 0%");
        currentLabel = new JLabel("Currently: Doing Nothing");

        super.add(progressLabel);
        super.add(currentLabel);

        super.setLayout(new GridLayout(1, 2, 0,0));
    }

    void generateMessageFinish(){ currentLabel.setText("Currently: Generating Maze Finished"); super.repaint(); }
    void generateMessageCurrent(){ currentLabel.setText("Currently: Generating Maze..."); super.repaint(); }
    void progress(int progress){ progressLabel.setText("Progress: " + progress + "%"); super.repaint(); }
}
