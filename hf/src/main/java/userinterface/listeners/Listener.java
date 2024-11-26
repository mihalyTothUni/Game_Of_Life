package userinterface.listeners;

import java.awt.event.ActionListener;

import logic.Simulation;

/**
 * Abstract class for all listeners to inherit from
 */
public abstract class Listener implements ActionListener {
    protected Simulation simulation;
    
    /**
     * Constructor for the Listener class
     * @param simulation the simulation to work on
     */
    protected Listener(Simulation simulation) {
        this.simulation = simulation;
        
    }

    
}
