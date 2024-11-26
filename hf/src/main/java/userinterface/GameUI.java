package userinterface;

import javax.swing.*;

import com.fasterxml.jackson.annotation.JsonIgnoreType;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import logic.Coordinates;
import logic.GameField;
import logic.Simulation;
import logic.SimulationObserver;

//GameUI class for the user interface of the actual cell grid
/**
 * Class for the user interface of the actual cell grid
 * This class is responsible for drawing the grid and handling clicks
 * It also observes the simulation to update the grid when the simulation ticks
 * It is a JPanel, so it can be added to a JFrame
 * It is also a SimulationObserver, so it can be notified when the simulation ticks
 */
@JsonIgnoreType
public class GameUI extends JPanel implements SimulationObserver {
    private Simulation simulation;  // The simulation we are drawing
    private int rows;               // Number of rows
    private int cols;               // Number of columns
    private int cellSize;           // Size of each cell
    private GameField currentField; // The field we want to draw

    private HexUI hexManager;       // Helper class because hexagons are hard
    private SquareUI squareManager; // Turns out squares aren't easy either
    private TriUI triManager;       // Dont get me started on these things

    /**
     * Constructor
     * @param simulation the simulation to draw
     * @param  cellSize the size of each cell
     */
    public GameUI(Simulation simulation, int cellSize) {
        this.simulation = simulation;
        this.cellSize = cellSize;
        this.currentField = simulation.getCurrentField();
        this.rows = currentField.getDimY();
        this.cols = currentField.getDimX();

        // Initialize the grid managers
        squareManager = new SquareUI(rows, cols, cellSize);
        triManager = new TriUI(rows, cols, cellSize);
        hexManager = new HexUI(rows, cols, cellSize);

        setBackground(Color.BLACK);

        // Add a mouse listener to handle clicks
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleCellClick(e.getPoint());
            }
        });

        // Add itself to observer list
        simulation.addObserver(this);
    }

    
    /**
     * Update cell states and repaint
     */
    public void updateCellStates() {
        this.currentField = simulation.getCurrentField();
        repaint();
    }
    
    /**
     * Called when the simulation ticks
     * Updates the cell states and repaints the grid
     */
    public void onSimulationTick() {
        updateCellStates();
    }

    // Handle cell clicks
    /**
     * Handle cell clicks
     * @param point the point where the click occurred
     * Calculates the row and column of the cell that was clicked,
     * Toggles the state of the cell,
     * Repaints the grid
     */
    private void handleCellClick(Point point) {
        Coordinates selection = new Coordinates(-1, -1);

        // Calculate selected coordinates based on cell shape with helper classes
        switch (simulation.getShape()) {
            case SQUARE:
                selection = squareManager.detectCellClick(point);
                break;
            case TRIANGLE:
                selection = triManager.detectCellClick(point);
                break;
            case HEXAGON:
                selection = hexManager.detectCellClick(point);
            break;
        }

        // Ensure the calculated row and col are within bounds
        int row = selection.getY();
        int col = selection.getX();
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            currentField.set(selection, !currentField.get(selection)); // Toggle cell state
            repaint();
        }
    }

    /**
     * Paint the grid
     * @param g the graphics object to paint with
     * Delegates the drawing to the appropriate grid manager
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        switch (simulation.getShape()) {
            case SQUARE:
                squareManager.drawGrid(g2d, currentField);
                break;
            case TRIANGLE:
                triManager.drawGrid(g2d, currentField);
                break;
            case HEXAGON:
                hexManager.drawGrid(g2d, currentField);
                break;
        }
        
    }
}