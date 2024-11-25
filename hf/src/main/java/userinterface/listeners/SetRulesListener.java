package userinterface.listeners;

import java.awt.event.ActionEvent;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JSpinner;

import logic.Simulation;

public class SetRulesListener extends Listener {
    JSpinner minLiveSpinner;
    JSpinner maxLiveSpinner;
    JSpinner spawnSpinner;
    JButton applyButton;


    public SetRulesListener(Simulation simulation, JSpinner min, JSpinner max, JSpinner spawn, JButton set){
        super(simulation);
        minLiveSpinner = min;
        maxLiveSpinner = max;
        spawnSpinner = spawn;
        applyButton = set;
        applyButton.addActionListener(this);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        Map<String, Integer> newRules = simulation.getRuleset().getRules();
        newRules.put("minLive", (int) minLiveSpinner.getValue());
        newRules.put("maxLive", (int) maxLiveSpinner.getValue());
        newRules.put("spawn", (int) spawnSpinner.getValue());
        simulation.getRuleset().setRules(newRules);
    }
    
}
