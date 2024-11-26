package userinterface.listeners;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

import logic.Simulation;

/**
 * Listener for the clear button
 */
public class ClearListener extends Listener {
    private JButton clearPauseButton;
    /**
     * Constructor
     * @param simulation the simulation to be cleared
     * @param clearPauseButton the button the listener is attached to
     */
    public ClearListener(Simulation simulation, JButton clearPauseButton) {
        super(simulation);
        this.clearPauseButton = clearPauseButton;
        clearPauseButton.addActionListener(this);
    }

    /**
     * Clears the simulation when the button is pressed
     * @param e the event that triggered the listener (clicking the button)
     * Simulation gets paused first if it is running
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (simulation.isRunning()) {
            simulation.pause();
        }
        simulation.clear();
    }

   
    
}
