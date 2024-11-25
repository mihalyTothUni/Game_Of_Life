package userinterface.listeners;

import java.awt.event.ActionListener;

import logic.Simulation;

//abstract class for all listeners to inherit from
public abstract class Listener implements ActionListener {
    protected Simulation simulation;
    

    protected Listener(Simulation simulation) {
        this.simulation = simulation;
        
    }

    
}
