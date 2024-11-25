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
@JsonIgnoreType
public class GameUI extends JPanel implements SimulationObserver {
    Simulation simulation; // The simulation we are drawing
    int rows; // Number of rows
    int cols; // Number of columns
    int cellSize; // Size of each cell
    GameField currentField; // The field we want to draw

    HexUI hexManager; // Helper class because hexagons are hard
    SquareUI squareManager;  // Turns out squares aren't easy either
    TriUI triManager;   // Dont get me started on these things

    public GameUI(Simulation simulation, int cellSize) {
        this.simulation = simulation;
        this.cellSize = cellSize;
        this.currentField = simulation.getCurrentField();
        this.rows = currentField.getDimY();
        this.cols = currentField.getDimX();

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

        //Add itself to observers
        simulation.addObserver(this);
    }

    // Update simulation field (new sim has been loaded)
    public void changeSimulation(Simulation newSimulation){
        simulation = newSimulation;
        updateCellStates();
    }

    

    // Update cell states and repaint
    public void updateCellStates() {
        this.currentField = simulation.getCurrentField();
        repaint();
    }
    
    //When we observe a change, show it in the editor
    public void onSimulationTick() {
        updateCellStates();
    }

    // Handle cell clicks
    private void handleCellClick(Point point) {
        Coordinates selection = new Coordinates(-1, -1);

        // Calculate row and col based on cell shape
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);

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

    private void drawSquareCell(Graphics2D g2d, int row, int col) {
        int x = col * cellSize;
        int y = row * cellSize;
        g2d.fillRect(x, y, cellSize, cellSize);
    }

    private void drawTriangleCell(Graphics2D g2d, int row, int col) {
        int x = col * cellSize;
        int y = row * cellSize;
        int halfSize = cellSize / 2;

        Polygon triangle;
        if ((row + col) % 2 == 0) { // Alternate upward and downward triangles
            triangle = new Polygon(
                new int[]{x, x + cellSize, x + halfSize},
                new int[]{y + cellSize, y + cellSize, y},
                3
            );
        } else {
            triangle = new Polygon(
                new int[]{x, x + cellSize, x + halfSize},
                new int[]{y, y, y + cellSize},
                3
            );
        }
        g2d.fillPolygon(triangle);
    }
    //TODO still a bit scuffed
    /* 
    private void drawHexagonCell(Graphics2D g2d, int row, int col) {
        int hexaSize = cellSize * 3 / 2;
        int x = col * hexaSize; // Horizontal spacing
        int y = row * hexaSize; // Vertical spacing
        if (col % 2 == 1) y += hexaSize * Math.sqrt(3) / 4; // Offset for staggered rows

        Polygon hexagon = new Polygon();
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(60.0 * i + 30);
            int xOffset = (int) (x + hexaSize * Math.cos(angle));
            int yOffset = (int) (y + hexaSize * Math.sin(angle));
            hexagon.addPoint(xOffset, yOffset);
        }
        g2d.fillPolygon(hexagon);
    }*/

    
}

