package userinterface.listeners;

import java.util.List;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JButton;

import logic.Simulation;
import logic.SimulationObserver;
import userinterface.FileSelector;
import io.SimulationJSON;

public class SimLoadListener extends Listener{

    FileSelector fileSelector;
    JButton loadButton;
    String directory;

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
        List<SimulationObserver> currentObservers = simulation.getObservers();
        
        if(!selectedFile.isEmpty()){
            SimulationJSON ser = new SimulationJSON();
            try {
                simulation = ser.deserialize(selectedFile);
                //add back the previous observers, they are needed for the UI !
                //TODO still completely screwed, GameUI was built using other simulation, uses all that shit
                //need to figure something out
                for (SimulationObserver anObserver : currentObservers) {
                    simulation.addObserver(anObserver);
                }
                

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        
    }
    
}
