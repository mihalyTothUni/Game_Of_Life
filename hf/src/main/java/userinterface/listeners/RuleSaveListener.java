package userinterface.listeners;

import java.io.IOException;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JTextField;

import io.RulesJSON;
import logic.Simulation;

/**
 * Listener for saving rules
 */
public class RuleSaveListener extends Listener {
    private JTextField nameField;   // Text field for the name of the ruleset
    private JButton saveButton;     // Button to save the ruleset
    private String directory;       // Directory where the ruleset will be saved


    /**
     * Constructor
     * @param simulation Simulation object we are working with
     * @param nameField Text field for the name of the ruleset
     * @param saveButton Button to save the ruleset
     * @param directory Directory where the ruleset will be saved
     */
    public RuleSaveListener(Simulation simulation, JTextField nameField, JButton saveButton, String directory){
        super(simulation);
        this.nameField = nameField;
        this.saveButton = saveButton;
        this.directory = directory;
        saveButton.addActionListener(this);
    }


    /**
     * Saves the ruleset when the button is pressed
     * @param e the event that triggered the listener (clicking the button)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        RulesJSON ser = new RulesJSON();
        String fileName = directory;
        fileName = fileName.concat(nameField.getText());
        try {
            // Saves complete ruleset, current implementation only uses the numerical values when loading
            ser.serialize(simulation.getRuleset(), fileName);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
    

