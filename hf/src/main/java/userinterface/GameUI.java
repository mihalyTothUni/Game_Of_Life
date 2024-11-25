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
        this.rows = currentField.getDimY();
        this.cols = currentField.getDimX();

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
            double hexHeight = cellSize * Math.sqrt(3) / 2;
            row = (int) (mouseY / hexHeight);
            col = (int) (mouseX / (cellSize * 0.75));
            double xOffset = mouseX - (col * cellSize * 0.75);
            double yOffset = mouseY - (row * hexHeight);
            if (col % 2 == 0) {
                if (yOffset < (-hexHeight / cellSize) * xOffset + hexHeight) {
                    row--;
                    col--;
                } else if (yOffset < (hexHeight / cellSize) * xOffset - hexHeight) {
                    row--;
                }
            } else {
                if (yOffset < (hexHeight / cellSize) * xOffset) {
                    row--;
                } else if (yOffset < (-hexHeight / cellSize) * xOffset + hexHeight) {
                    col--;
                }
            }
            break;
        }

        // Ensure the calculated row and col are within bounds
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            Coordinates coords = new Coordinates(col, row);
            currentField.set(coords, !currentField.get(coords)); // Toggle cell state
            System.out.println("click at " + coords.getX() + ";" + coords.getY());
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
                if (currentField.get(new Coordinates(col, row))) { // Draw only active cells
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
    }

    
}

