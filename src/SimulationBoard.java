import javax.swing.*;
import java.awt.*;

public class SimulationBoard extends JPanel {
    private static final int SIZE = 75;

    public SimulationBoard(int N) {
        super(new GridLayout((int) Math.sqrt(N), (int) Math.sqrt(N)));
        this.setPreferredSize(new Dimension((int) Math.sqrt(N) * SIZE, (int) Math.sqrt(N) * SIZE));
        for (int i = 0; i < N; i++) {
            this.add(new SimulationBoard.ChessButton(i, N));
        }
    }

    public void display(String Title) {
        JFrame f = new JFrame(Title);
        f.setMinimumSize(new Dimension(300,300));
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(this);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    public static class ChessButton extends JButton {
        public ChessButton(int i, int N) {
            super(i / N + "," + i % N);
            this.setOpaque(true);
            this.setBorderPainted(true);
            this.setText(String.valueOf(i));
        }
    }
}


