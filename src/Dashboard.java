import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Dashboard extends JPanel {

    private static final int SIZE = 75;
    Restaurant ourResturant;
    Dashboard dashboard;

    private JPanel Panel;
    private JTextField restaurantTextField;
    private JButton startSimulationButton;
    private JButton addCustomersToSimulatuionButton;
    private JSlider slider1;
    private JTextArea NamesField;
    private JLabel NLabel;

    public Dashboard(int N) {
        super(new GridLayout((int) Math.sqrt(N), (int) Math.sqrt(N)));
        this.setPreferredSize(new Dimension((int) Math.sqrt(N) * SIZE, (int) Math.sqrt(N) * SIZE));
        for (int i = 0; i < N; i++) {
            this.add(new ChessButton(i, N));
        }
        slider1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                NLabel.setText(String.valueOf(slider1.getValue()));
            }
        });
        startSimulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                slider1.setEnabled(false);
                startSimulationButton.setEnabled(false);
                restaurantTextField.setEnabled(false);
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        dashboard = new Dashboard(slider1.getValue());
                        dashboard.display(restaurantTextField.getText());
                        ourResturant = new Restaurant(slider1.getValue(),restaurantTextField.getText(), dashboard);
                    }
                });
            }
        });
        addCustomersToSimulatuionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ourResturant.AddCustomersToSim(NamesField.getText().split("\n"));
            }
        });
    }

    public static class ChessButton extends JButton {
        public ChessButton(int i, int N) {
            super(i / N + "," + i % N);
            this.setOpaque(true);
            this.setBorderPainted(true);
            this.setText(String.valueOf(i));
        }
    }

    private void display(String Title) {
        JFrame f = new JFrame(Title);
        f.setMinimumSize(new Dimension(300,300));
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(this);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame jFrame = new JFrame("Restaurant Simulation");
                jFrame.setContentPane(new Dashboard(10).Panel);
                jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
                jFrame.pack();
                jFrame.setVisible(true);
                //new Dashboard().display();
            }
        });
    }
}

