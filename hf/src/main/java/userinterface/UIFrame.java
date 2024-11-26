package userinterface;

import javax.swing.*;

import logic.Simulation;
import logic.SessionRules.shapeList;
import userinterface.listeners.*;

import java.awt.*;

/**
 * The main UI frame for the application
 */
public class UIFrame {
    Simulation simulation; // The simulation to be displayed

    JButton startPauseButton;   // Button to start/pause the simulation
    JButton clearButton;        // Button to clear the simulation
    JButton saveSimButton;      // Button to save the simulation
    JButton loadSimButton;      // Button to load the simulation
    JButton setShapeButton;     // Button to set the shape of the cells
    JButton populateButton;     // Button to populate the simulation

    JButton applyRulesButton;   // Button to apply the selected rules to the simulation
    JButton saveRulesButton;    // Button to save the rules
    JButton loadRulesButton;    // Button to load the rules

    JTextField presetNameField; // Field to enter the name of the ruleset
    JTextField simNameField;    // Field to enter the name of the simulation

    JComboBox<shapeList> shapeDropdown; // Dropdown menu for the cell shape
    FileSelector presetDropdown;        // "Smart" dropdown menu for the rulesets
    FileSelector simDropdown;           // "Smart" dropdown menu for the simulations
    
    JSpinner popDensitySpinner;         // Spinner for the population density
    JSpinner minLiveSpinner;            // Spinner for the minimum number of live neighbors
    JSpinner maxLiveSpinner;            // Spinner for the maximum number of live neighbors
    JSpinner spawnSpinner;              // Spinner for the number of neighbors required for spawn
    
    static final String SIM_SAVEPATH = "hf/resources/simulations/"; // Path to save simulations
    static final String RULE_SAVEPATH = "hf/resources/rules/";      // Path to save rulesets
    static final int CELL_SIZE = 10;                                // Size of the cells in pixels
    
    /**
     * Constructor
     * @param width the width of the frame
     * @param height the height of the frame
     * @param simulation the simulation to be displayed
     */
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

        // Start/Pause and Clear buttons
        startPauseButton = new JButton("Start");
        new StartPauseListener(simulation, startPauseButton);
        clearButton = new JButton("Clear");
        new ClearListener(simulation, clearButton);

        // Cell shape and population density controls
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

        // Ruleset controls
        minLiveSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 10, 1)); // min=0, max=10, step=1, initial=1
        maxLiveSpinner = new JSpinner(new SpinnerNumberModel(3, 0, 10, 1));
        spawnSpinner = new JSpinner(new SpinnerNumberModel(3, 0, 10, 1));
        applyRulesButton = new JButton("Apply");
        new SetRulesListener(simulation, minLiveSpinner, maxLiveSpinner, spawnSpinner, applyRulesButton);
        // Load ruleset controls
        presetDropdown = new FileSelector(RULE_SAVEPATH);
        loadRulesButton = new JButton("Load");
        new RuleLoadListener(simulation, presetDropdown, minLiveSpinner, maxLiveSpinner, spawnSpinner, loadRulesButton, RULE_SAVEPATH);
        
        //Adding previously created elements to UI
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

        // Save/load simulation and ruleset controls
        simNameField = new JTextField(10); // 10 columns for width
        saveSimButton = new JButton("Save");
        new SimSaveListener(simulation, simNameField, saveSimButton, SIM_SAVEPATH);

        simDropdown = new FileSelector(SIM_SAVEPATH);
        loadSimButton = new JButton("Load");
        new SimLoadListener(simulation, simDropdown, loadSimButton, SIM_SAVEPATH);

        presetNameField = new JTextField(10);
        saveRulesButton = new JButton("Save");
        new RuleSaveListener(simulation, presetNameField, saveRulesButton, RULE_SAVEPATH);

        // Adding previously created elements to UI
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
        JPanel gameBoard = new GameUI(simulation, CELL_SIZE);
        gameBoard.setBackground(Color.BLACK);
        gameBoard.setLayout(new BorderLayout());

        // Add the panels to the frame
        frame.add(controlPanel, BorderLayout.NORTH);
        frame.add(gameBoard, BorderLayout.CENTER);

        // Show the frame
        frame.setVisible(true);
    }

    //defaults to 1000x700 pixels
    /**
     * Constructor
     * @param simulation the simulation to be displayed
     * Default size is 1000x700 pixels
     */
    public UIFrame(Simulation simulation) {
        this(1000, 700, simulation);
    }
}

