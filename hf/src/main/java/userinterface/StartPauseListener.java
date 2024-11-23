package userinterface;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

import logic.Simulation;

public class StartPauseListener extends Listener {
    private JButton startPauseButton;

    public StartPauseListener(Simulation simulation, JButton startPauseButton) {
        super(simulation);
        this.startPauseButton = startPauseButton;
        startPauseButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (simulation.isRunning()) {
            simulation.pause();
            startPauseButton.setText("Start");
        } else {
            simulation.start();
            startPauseButton.setText("Pause");
        }
    }

   
    
}
