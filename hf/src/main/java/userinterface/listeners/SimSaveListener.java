package userinterface.listeners;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JTextField;

import io.SimulationJSON;
import logic.Simulation;

public class SimSaveListener extends Listener{

    JTextField nameField;
    JButton saveButton;
    String directory;

    public SimSaveListener(Simulation simulation, JTextField nameField, JButton saveButton, String directory){
        super(simulation);
        this.nameField = nameField;
        this.saveButton = saveButton;
        this.directory = directory;
        saveButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SimulationJSON ser = new SimulationJSON();
        String fileName = directory;
        fileName = fileName.concat(nameField.getText());
        try {
            ser.serialize(simulation, fileName);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }
    
}
