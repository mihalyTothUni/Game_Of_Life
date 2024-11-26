package userinterface.listeners;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JSpinner;

import logic.GameField;
import logic.Simulation;

/**
 * Listener for the populate button
 */
public class PopulateListener extends Listener{
    private JSpinner popSpinner; // Spinner for the desired population density
    private JButton setButton;   // Button to set the population density

    /**
     * Constructor
     * @param simulation the simulation to be populated
     * @param popSpinner the spinner for the desired population density
     * @param setButton the button the listener is attached to
     */
    public PopulateListener(Simulation simulation, JSpinner popSpinner, JButton setButton){
        super(simulation);
        this.popSpinner = popSpinner;
        this.setButton = setButton;
        setButton.addActionListener(this);
    }

    /**
     * Populates the simulation when the button is pressed
     * @param e the event that triggered the listener (clicking the button)
     * Simulation gets cleared first
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        simulation.clear();
        simulation.setCurrentField(new GameField(simulation.getRuleset().getDimX(), simulation.getRuleset().getDimY(), (int) popSpinner.getValue()));
            
    }

    
}
