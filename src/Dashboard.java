import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Dashboard extends JPanel {
    private JPanel Panel;
    private JTextField restaurantTextField;
    private JButton startSimulationButton;
    private JButton addCustomersToSimulatuionButton;
    private JSlider slider1;
    private JTextArea NamesField;
    private JLabel NLabel;
    private Restaurant ourResturant;
    private SimulationBoard SimulationBoard;

    public Dashboard(int N) {
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
                        SimulationBoard = new SimulationBoard(slider1.getValue());
                        SimulationBoard.display(restaurantTextField.getText());
                        ourResturant = new Restaurant(slider1.getValue(),restaurantTextField.getText(), SimulationBoard);
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

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame jFrame = new JFrame("Restaurant Simulation");
                jFrame.setContentPane(new Dashboard(10).Panel);
                jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
                jFrame.pack();
                jFrame.setVisible(true);
            }
        });
    }
}

