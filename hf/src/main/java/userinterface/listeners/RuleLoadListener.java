package userinterface.listeners;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JSpinner;

import logic.SessionRules;
import logic.Simulation;
import userinterface.FileSelector;
import io.RulesJSON;

public class RuleLoadListener extends Listener{

    FileSelector fileSelector;
    JSpinner minSpinner;
    JSpinner maxSpinner;
    JSpinner spawnSpinner;

    JButton loadButton;
    String directory;

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

    @Override
    public void actionPerformed(ActionEvent e) {

        String selectedFile = directory;
        selectedFile = selectedFile.concat((String) fileSelector.getSelectedItem());
         
        
        if(!selectedFile.isEmpty()){
            RulesJSON ser = new RulesJSON();
            try {
                // File has entire ruleset, currently only math values are used
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
