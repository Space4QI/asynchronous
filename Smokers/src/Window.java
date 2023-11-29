import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    private Panel[] panels;

    public Window() {
        setTitle("Smoker Problem Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        panels = new Panel[3];
        for (int i = 0; i < 3; i++) {
            panels[i] = new Panel();
            add(panels[i]);
        }

        pack();
        setVisible(true);
    }

    public Panel[] getPanels() {
        return panels;
    }

    public void log(String message) {
        System.out.println(message);
    }
}