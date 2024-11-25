package userinterface;

import javax.swing.*;

import logic.Simulation;
import logic.SessionRules.shapeList;
import userinterface.listeners.*;

import java.awt.*;

public class UIFrame {

    Simulation simulation;

    JButton startPauseButton;
    JButton clearButton;

    JButton saveSimButton;
    JButton loadSimButton;

    JButton setShapeButton;
    JButton populateButton;

    JButton applyRulesButton;
    JButton saveRulesButton;
    JButton loadRulesButton;

    
    JTextField presetNameField;
    JTextField simNameField;

    JComboBox<shapeList> shapeDropdown;

    FileSelector presetDropdown;
    FileSelector simDropdown;
    
    JSpinner popDensitySpinner;
    JSpinner minLiveSpinner;
    JSpinner maxLiveSpinner;
    JSpinner spawnSpinner;
    
    
        
    static final String SIM_SAVEPATH = "hf/resources/simulations/";
    static final String RULE_SAVEPATH = "hf/resources/rules/";
    
    
    public UIFrame(int width, int height, Simulation simulation){
    
        this.simulation = simulation;
        // Create the main frame
        JFrame frame = new JFrame("Game of Life");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLayout(new BorderLayout());

        // Create a panel for the controls
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(3, 1)); // Divide into 3 rows for organization

        // Create the first row of controls
        JPanel topRow = new JPanel();
        topRow.setLayout(new FlowLayout(FlowLayout.CENTER));

        startPauseButton = new JButton("Start");
        new StartPauseListener(simulation, startPauseButton);
        clearButton = new JButton("Clear");
        new ClearListener(simulation, clearButton);

        

        shapeList[] cellShape = {shapeList.SQUARE, shapeList.TRIANGLE, shapeList.HEXAGON};
        shapeDropdown = new JComboBox<>(cellShape);
        setShapeButton = new JButton("Set");
        new SetShapeListener(simulation, setShapeButton, shapeDropdown);

        popDensitySpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 5));
        populateButton = new JButton("Populate");
        new PopulateListener(simulation, popDensitySpinner, populateButton);
        
        // Adding previously created elements to UI
        topRow.add(startPauseButton);
        topRow.add(clearButton);

        

        topRow.add(new JLabel("Cell shape:"));
        topRow.add(shapeDropdown);
        topRow.add(setShapeButton);

        topRow.add(new JLabel("Density:"));
        topRow.add(popDensitySpinner);
        topRow.add(populateButton);
        

        // Create the second row of controls
        JPanel middleRow = new JPanel();
        middleRow.setLayout(new FlowLayout(FlowLayout.CENTER));

        minLiveSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 10, 1)); // min=0, max=10, step=1
        maxLiveSpinner = new JSpinner(new SpinnerNumberModel(3, 0, 10, 1));
        spawnSpinner = new JSpinner(new SpinnerNumberModel(2, 0, 10, 1));
        applyRulesButton = new JButton("Apply");
        new SetRulesListener(simulation, minLiveSpinner, maxLiveSpinner, spawnSpinner, applyRulesButton);
        
        presetDropdown = new FileSelector(RULE_SAVEPATH);
        loadRulesButton = new JButton("Load");
        new RuleLoadListener(simulation, presetDropdown, minLiveSpinner, maxLiveSpinner, spawnSpinner, loadRulesButton, RULE_SAVEPATH);
        
        

        middleRow.add(new JLabel("Preset:"));
        middleRow.add(presetDropdown);
        middleRow.add(loadRulesButton);
        middleRow.add(new JLabel("Min Live:"));
        middleRow.add(minLiveSpinner);
        middleRow.add(new JLabel("Max Live:"));
        middleRow.add(maxLiveSpinner);
        middleRow.add(new JLabel("Spawn:"));
        middleRow.add(spawnSpinner);
        middleRow.add(applyRulesButton);
        
        

        // Create third row for save/load functionality
        JPanel bottomRow = new JPanel();
        bottomRow.setLayout(new FlowLayout(FlowLayout.CENTER));

        simNameField = new JTextField(10); // 10 columns for width
        saveSimButton = new JButton("Save");
        new SimSaveListener(simulation, simNameField, saveSimButton, SIM_SAVEPATH);

        simDropdown = new FileSelector(SIM_SAVEPATH);
        loadSimButton = new JButton("Load");
        new SimLoadListener(simulation, simDropdown, loadSimButton, SIM_SAVEPATH);

        presetNameField = new JTextField(10);
        saveRulesButton = new JButton("Save");
        new RuleSaveListener(simulation, presetNameField, saveRulesButton, RULE_SAVEPATH);

        bottomRow.add(new JLabel("Load sim:"));
        bottomRow.add(simDropdown);
        bottomRow.add(loadSimButton);

        bottomRow.add(new JLabel("Save sim:"));
        bottomRow.add(simNameField);
        bottomRow.add(saveSimButton);

        bottomRow.add(new JLabel("Save ruleset:"));
        bottomRow.add(presetNameField);
        bottomRow.add(saveRulesButton);



        // Add the rows to the control panel
        controlPanel.add(topRow);
        controlPanel.add(middleRow);
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

