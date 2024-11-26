package userinterface.listeners;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

import logic.Simulation;

/**
 * Listener for the start/pause button
 */
public class StartPauseListener extends Listener {
    private JButton startPauseButton; // Button to start/pause the simulation

    /**
     * Constructor
     * @param simulation the simulation to be started/paused
     * @param startPauseButton the button the listener is attached to
     */
    public StartPauseListener(Simulation simulation, JButton startPauseButton) {
        super(simulation);
        this.startPauseButton = startPauseButton;
        startPauseButton.addActionListener(this);
    }

    /**
     * Starts or pauses the simulation when the button is pressed
     * @param e the event that triggered the listener (clicking the button)
     * Button text changes based on the state of the simulation
     */
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
