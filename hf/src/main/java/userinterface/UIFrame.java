package userinterface;

import javax.swing.*;

import logic.Simulation;
import logic.SessionRules.shapeList;

import java.awt.*;

public class UIFrame {

    Simulation simulation;

    JButton startPauseButton;
    JButton clearButton;
    JButton saveButton;
    JButton setShapeButton;
    JButton populateButton;
    JButton applyRulesButton;
    JButton saveRulesButton;

    JTextField fileNameField;
    JTextField presetNameField;

    JComboBox<shapeList> shapeDropdown;
    JComboBox<String> presetDropdown;
    
    JSpinner popDensitySpinner;
    JSpinner minLiveSpinner;
    JSpinner maxLiveSpinner;
    JSpinner spawnSpinner;
    
    
    


    public UIFrame(int width, int height, Simulation simulation){

        this.simulation = simulation;
        // Create the main frame
        JFrame frame = new JFrame("Game of Life");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLayout(new BorderLayout());

        // Create a panel for the controls
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(2, 1)); // Divide into 2 rows for organization

        // Create the first row of controls
        JPanel topRow = new JPanel();
        topRow.setLayout(new FlowLayout(FlowLayout.CENTER));

        startPauseButton = new JButton("Start");
        new StartPauseListener(simulation, startPauseButton);
        clearButton = new JButton("Clear");
        new ClearListener(simulation, clearButton);
        fileNameField = new JTextField(10); // 10 columns for width
        saveButton = new JButton("Save");
        shapeList[] cellShape = {shapeList.SQUARE, shapeList.TRIANGLE, shapeList.HEXAGON};
        shapeDropdown = new JComboBox<>(cellShape);
        setShapeButton = new JButton("Set");
        new SetShapeListener(simulation, setShapeButton, shapeDropdown);
        populateButton = new JButton("Populate");
        popDensitySpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 5));
        

        topRow.add(startPauseButton);
        topRow.add(clearButton);
        topRow.add(new JLabel("File Name:"));
        topRow.add(fileNameField);
        topRow.add(saveButton);
        topRow.add(new JLabel("Cell shape:"));
        topRow.add(shapeDropdown);
        topRow.add(setShapeButton);
        topRow.add(new JLabel("Density:"));
        topRow.add(popDensitySpinner);
        topRow.add(populateButton);
        new PopulateListener(simulation, popDensitySpinner, populateButton);

        // Create the second row of controls
        JPanel bottomRow = new JPanel();
        bottomRow.setLayout(new FlowLayout(FlowLayout.CENTER));

        presetDropdown = new JComboBox<>(new String[]{"Preset 1", "Preset 2", "Preset 3"});
        minLiveSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 10, 1)); // min=0, max=10, step=1
        maxLiveSpinner = new JSpinner(new SpinnerNumberModel(3, 0, 10, 1));
        spawnSpinner = new JSpinner(new SpinnerNumberModel(2, 0, 10, 1));
        applyRulesButton = new JButton("Apply");
        presetNameField = new JTextField(10);
        saveRulesButton = new JButton("Save preset");

        bottomRow.add(new JLabel("Preset:"));
        bottomRow.add(presetDropdown);
        bottomRow.add(new JLabel("Min Live:"));
        bottomRow.add(minLiveSpinner);
        bottomRow.add(new JLabel("Max Live:"));
        bottomRow.add(maxLiveSpinner);
        bottomRow.add(new JLabel("Spawn:"));
        bottomRow.add(spawnSpinner);
        bottomRow.add(applyRulesButton);
        new SetRulesListener(simulation, minLiveSpinner, maxLiveSpinner, spawnSpinner, applyRulesButton);
        bottomRow.add(presetNameField);
        bottomRow.add(saveRulesButton);

        // Add the rows to the control panel
        controlPanel.add(topRow);
        controlPanel.add(bottomRow);

        // Create a panel for the game board
        JPanel gameBoard = new GameUI(simulation, 10);
        gameBoard.setBackground(Color.BLACK);
        gameBoard.setLayout(new BorderLayout());


        // Add the panels to the frame
        frame.add(controlPanel, BorderLayout.NORTH);
        frame.add(gameBoard, BorderLayout.CENTER);

        // Show the frame
        frame.setVisible(true);
    }

    //defaults to 1000x650 pixels
    public UIFrame(Simulation simulation) {
        this(1000, 650, simulation);
    }
}

