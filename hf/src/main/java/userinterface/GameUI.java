package userinterface;

import javax.swing.*;

import com.fasterxml.jackson.annotation.JsonIgnoreType;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import logic.Coordinates;
import logic.GameField;
import logic.SessionRules.shapeList;
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

    public GameUI(Simulation simulation, int cellSize) {
        this.simulation = simulation;
        this.cellSize = cellSize;
        this.currentField = simulation.getCurrentField();
        this.rows = currentField.getDimX();
        this.cols = currentField.getDimY();

        setPreferredSize(new Dimension(cols * cellSize, rows * cellSize));
        setBackground(Color.BLACK);

        // Add a mouse listener to handle clicks
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleCellClick(e.getX(), e.getY());
            }
        });

        //Add itself to observers
        simulation.addObserver(this);
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
    private void handleCellClick(int mouseX, int mouseY) {
        int row = 0;
        int col = 0;

        // Calculate row and col based on cell shape
        switch (simulation.getShape()) {
            case SQUARE:
                row = mouseY / cellSize;
                col = mouseX / cellSize;
                break;
            case TRIANGLE:
                row = mouseY / cellSize;
                col = mouseX / cellSize;
                // Adjust for triangle alternation
                if ((row + col) % 2 != 0) {
                    // Handle odd/even positioning if needed
                }
                break;
            case HEXAGON:
                double hexHeight = Math.sqrt(3) / 2 * cellSize;
                row = (int) (mouseY / hexHeight);
                col = (int) (mouseX / (cellSize * 0.75));
                if (col % 2 == 1 && mouseY % hexHeight < hexHeight / 2) {
                    row--;
                }
                break;
        }

        // Ensure the calculated row and col are within bounds
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            Coordinates coords = new Coordinates(row, col);
            currentField.set(coords, !currentField.get(coords)); // Toggle cell state
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (currentField.get(new Coordinates(row, col))) { // Draw only active cells
                    switch (simulation.getShape()) {
                        case SQUARE:
                            drawSquareCell(g2d, row, col);
                            break;
                        case TRIANGLE:
                            drawTriangleCell(g2d, row, col);
                            break;
                        case HEXAGON:
                            drawHexagonCell(g2d, row, col);
                            break;
                    }
                }
            }
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
    private void drawHexagonCell(Graphics2D g2d, int row, int col) {
        int x = col * (int) (cellSize * 0.75); // Horizontal spacing
        int y = row * (int) (cellSize * Math.sqrt(3) / 2); // Vertical spacing
        if (col % 2 == 1) y += cellSize * Math.sqrt(3) / 4; // Offset for staggered rows

        Polygon hexagon = new Polygon();
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(60.0 * i);
            int xOffset = (int) (x + cellSize * Math.cos(angle));
            int yOffset = (int) (y + cellSize * Math.sin(angle));
            hexagon.addPoint(xOffset, yOffset);
        }
        g2d.fillPolygon(hexagon);
    }

    
}

