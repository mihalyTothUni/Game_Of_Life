package userinterface.listeners;

import java.io.IOException;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JTextField;

import io.RulesJSON;
import logic.Simulation;

public class RuleSaveListener extends Listener {
    JTextField nameField;
    JButton saveButton;
    String directory;


    public RuleSaveListener(Simulation simulation, JTextField nameField, JButton saveButton, String directory){
        super(simulation);
        this.nameField = nameField;
        this.saveButton = saveButton;
        this.directory = directory;
        saveButton.addActionListener(this);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        RulesJSON ser = new RulesJSON();
        String fileName = directory;
        fileName = fileName.concat(nameField.getText());
        try {
            // Saves complete ruleset, current implementation only uses the math values
            ser.serialize(simulation.getRuleset(), fileName);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
    

