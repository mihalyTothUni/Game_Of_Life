package userinterface;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

import logic.Simulation;

public class ClearListener extends Listener {
    JButton clearPauseButton;

    public ClearListener(Simulation simulation, JButton clearPauseButton) {
        super(simulation);
        this.clearPauseButton = clearPauseButton;
        clearPauseButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (simulation.isRunning()) {
            simulation.pause();
        }
        simulation.clear();
    }

   
    
}
