package userinterface;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;

import logic.SessionRules.shapeList;
import logic.Simulation;

public class SetShapeListener extends Listener {
    JButton shapeButton;
    JComboBox<shapeList> shapeSelector;

    public SetShapeListener(Simulation simulation, JButton shapeButton, JComboBox<shapeList> shapeSelector) {
        super(simulation);
        this.shapeButton = shapeButton;
        this.shapeSelector = shapeSelector;
        shapeButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        simulation.setShape(shapeSelector.getItemAt(shapeSelector.getSelectedIndex()));
    }

    
    
}
