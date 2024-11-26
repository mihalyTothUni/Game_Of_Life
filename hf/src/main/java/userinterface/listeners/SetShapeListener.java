package userinterface.listeners;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;

import logic.SessionRules.shapeList;
import logic.Simulation;

/**
 * Listener for the shape selection button
 */
public class SetShapeListener extends Listener {
    private JButton shapeButton;            // Button to set the shape
    private JComboBox<shapeList> shapeSelector; // Drop down menu for the shape

    /**
     * Constructor
     * @param simulation the simulation to set the shape for
     * @param shapeButton the button the listener is attached to
     * @param shapeSelector the drop down menu for the shape
     */
    public SetShapeListener(Simulation simulation, JButton shapeButton, JComboBox<shapeList> shapeSelector) {
        super(simulation);
        this.shapeButton = shapeButton;
        this.shapeSelector = shapeSelector;
        shapeButton.addActionListener(this);
    }

    /**
     * Sets the shape of the cells when the button is pressed
     * @param e the event that triggered the listener (clicking the button)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        simulation.setShape((shapeList) shapeSelector.getSelectedItem());
    }

    
    
}
