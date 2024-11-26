package userinterface.listeners;

import java.awt.event.ActionEvent;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JSpinner;

import logic.Simulation;

/**
 * Listener for setting the rules of the simulation
 */
public class SetRulesListener extends Listener {
    private JSpinner minLiveSpinner;    // JSpinner for the minimum number of live neighbors for a cell to survive
    private JSpinner maxLiveSpinner;    // JSpinner for the maximum number of live neighbors for a cell to survive
    private JSpinner spawnSpinner;      // JSpinner for the number of neighbors required for a cell to spawn
    private JButton applyButton;        // Button for applying the selected rules

    /**
     * Constructor
     * @param simulation        The simulation to be modified
     * @param min               The spinner for the minimum number of live neighbors
     * @param max               The spinner for the maximum number of live neighbors
     * @param spawn             The spinner for the number of neighbors required for spawn
     * @param set               The button for applying the selected rules
     */
    public SetRulesListener(Simulation simulation, JSpinner min, JSpinner max, JSpinner spawn, JButton set){
        super(simulation);
        minLiveSpinner = min;
        maxLiveSpinner = max;
        spawnSpinner = spawn;
        applyButton = set;
        applyButton.addActionListener(this);
    }

    /**
     * Sets the numerical rules of the simulation when the button is pressed
     * @param e the event that triggered the listener (clicking the button)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Map<String, Integer> newRules = simulation.getRuleset().getRules();
        newRules.put("minLive", (int) minLiveSpinner.getValue());
        newRules.put("maxLive", (int) maxLiveSpinner.getValue());
        newRules.put("spawn", (int) spawnSpinner.getValue());
        simulation.getRuleset().setRules(newRules);
    }
    
}
