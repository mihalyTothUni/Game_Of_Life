package userinterface.listeners;

import java.util.List;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JButton;

import logic.Simulation;
import logic.SimulationObserver;
import userinterface.FileSelector;
import userinterface.GameUI;
import io.SimulationJSON;

public class SimLoadListener extends Listener{

    FileSelector fileSelector;
    JButton loadButton;
    String directory;
    Simulation loadedSimulation;
    GameUI ui;

    public SimLoadListener(Simulation simulation, FileSelector fileSelector, JButton loadButton, String directory){
        super(simulation);
        this.fileSelector = fileSelector;
        this.loadButton = loadButton;
        this.directory = directory;
        loadButton.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String selectedFile = directory;
        selectedFile = selectedFile.concat((String) fileSelector.getSelectedItem());
        
        
        if(!selectedFile.isEmpty()){
            SimulationJSON ser = new SimulationJSON();
            try {
                loadedSimulation = ser.deserialize(selectedFile);
                
                // In current implementation, all simulations are the same size but check to make sure
                if(loadedSimulation.getCurrentField().getDimX() == simulation.getCurrentField().getDimX() && loadedSimulation.getCurrentField().getDimY() == simulation.getCurrentField().getDimY()){
                    simulation.setCurrentField(loadedSimulation.getCurrentField());
                }

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        
    }
    
}
