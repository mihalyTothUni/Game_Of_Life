package userinterface.listeners;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JSpinner;

import logic.SessionRules;
import logic.Simulation;
import userinterface.FileSelector;
import io.RulesJSON;

/**
 * Listener for loading rules from a file
 */
public class RuleLoadListener extends Listener{
    private FileSelector fileSelector; // For selecting the rules file
    private JSpinner minSpinner;        // For setting the minimum number of live neighbors
    private JSpinner maxSpinner;        // For setting the maximum number of live neighbors
    private JSpinner spawnSpinner;   // For setting the number of neighbors required for spawn
    private JButton loadButton;     // For loading the selected file into the spinners
    private String directory;       // The directory where the rules are stored

    /**
     * Constructor
     * @param simulation         The simulation to be modified
     * @param fileSelector       The file selector for the rules
     * @param minSpinner         The spinner for the minimum number of live neighbors
     * @param maxSpinner         The spinner for the maximum number of live neighbors
     * @param spawnSpinner       The spinner for the number of neighbors required for spawn
     * @param loadButton         The button for loading the selected file
     * @param directory          The directory where the rules are stored
     */
    public RuleLoadListener(Simulation simulation, FileSelector fileSelector, JSpinner minSpinner, JSpinner maxSpinner, JSpinner spawnSpinner, JButton loadButton, String directory){
        super(simulation);
        this.minSpinner = minSpinner;
        this.maxSpinner = maxSpinner;
        this.spawnSpinner = spawnSpinner;
        this.fileSelector = fileSelector;
        this.loadButton = loadButton;
        this.directory = directory;
        loadButton.addActionListener(this);

    }

    /**
     * Loads the rules from the selected file into the spinners
     * @param e the event that triggered the listener (clicking the button)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String selectedFile = directory;
        selectedFile = selectedFile.concat((String) fileSelector.getSelectedItem());
        
        if(!selectedFile.isEmpty()){
            RulesJSON ser = new RulesJSON();
            try {
                // File has entire ruleset, currently only the numerical values are used
                SessionRules readRules = ser.deserialize(selectedFile);
                minSpinner.setValue(readRules.getRule("minLive"));
                maxSpinner.setValue(readRules.getRule("maxLive"));
                spawnSpinner.setValue(readRules.getRule("spawn"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        
    }
    
}
