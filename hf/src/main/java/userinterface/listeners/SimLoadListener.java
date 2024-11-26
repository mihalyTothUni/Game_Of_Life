package userinterface.listeners;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JButton;

import logic.Simulation;
import userinterface.FileSelector;
import io.SimulationJSON;

/**
 * Listener for the simulation load button
 */
public class SimLoadListener extends Listener{
    private FileSelector fileSelector;      // File selector for the simulation
    private JButton loadButton;             // Button to load the simulation
    private String directory;               // Directory where the simulations are stored
    private Simulation loadedSimulation;    // The simulation that was loaded

    /**
     * Constructor
     * @param simulation Simulation object we are working with
     * @param fileSelector File selector for the simulation
     * @param loadButton Button to load the simulation
     * @param directory Directory where the simulations are stored
     */
    public SimLoadListener(Simulation simulation, FileSelector fileSelector, JButton loadButton, String directory){
        super(simulation);
        this.fileSelector = fileSelector;
        this.loadButton = loadButton;
        this.directory = directory;
        loadButton.addActionListener(this);
    }

    /**
     * Loads the selected simulation when the button is pressed
     * Simulation data is loaded into the current simulation object
     * Current implementation assumes all simulations are the same size
     * If the loaded simulation is a different size, the current simulation is not changed
     * @param e the event that triggered the listener (clicking the button)
     */
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
