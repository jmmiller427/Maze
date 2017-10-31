import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class ProgressView extends JPanel{
    ProgressView(ActionListener AL){

        JLabel progressLabel = new JLabel("Progress: 0%");
        JLabel timeLabel = new JLabel("Time: 0s");

        super.add(progressLabel);
        super.add(timeLabel);

        super.setLayout(new GridLayout(1, 2, 0,0));
    }
}
