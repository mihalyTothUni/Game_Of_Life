package userinterface.listeners;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JTextField;

import io.SimulationJSON;
import logic.Simulation;

/**
 * Listener for the save button
 */
public class SimSaveListener extends Listener{
    private JTextField nameField;   // Text field for the name of the simulation
    private JButton saveButton;     // Button to save the simulation
    private String directory;       // Directory where the simulation will be saved

    /**
     * Constructor
     * @param simulation    Simulation object we are working with
     * @param nameField     Text field for inputing the name of the simulation
     * @param saveButton    Button to save the simulation
     * @param directory     Directory where the simulation will be saved
     */
    public SimSaveListener(Simulation simulation, JTextField nameField, JButton saveButton, String directory){
        super(simulation);
        this.nameField = nameField;
        this.saveButton = saveButton;
        this.directory = directory;
        saveButton.addActionListener(this);
    }

    /**
     * Saves the simulation when the button is pressed
     * @param e the event that triggered the listener (clicking the button)
     */
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
