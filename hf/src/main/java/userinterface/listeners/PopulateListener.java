package userinterface.listeners;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JSpinner;

import logic.GameField;
import logic.Simulation;

public class PopulateListener extends Listener{
    JSpinner popSpinner;
    JButton setButton;

    public PopulateListener(Simulation simulation, JSpinner popSpinner, JButton setButton){
        super(simulation);
        this.popSpinner = popSpinner;
        this.setButton = setButton;
        setButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        simulation.clear();
        simulation.setCurrentField(new GameField(simulation.getRuleset().getDimX(), simulation.getRuleset().getDimY(), (int) popSpinner.getValue()));
            
    }

    
}
