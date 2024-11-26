package userinterface;

import java.awt.*;
import javax.swing.*;

import logic.Coordinates;
import logic.GameField;

/**
 * Class for drawing a clickable hexagonal grid
 */
public class HexUI extends JPanel {
    private int hexSize;    // Size of hexagon (distance from center to a corner)
    private int hexWidth;   // Width of hexagons (across flat sides)
    private int hexHeight;  // Height of hexagon (across top and bottom point)
    private int verticalSpacing;  // Needed for calculating overlap
    private int cols;       // Number of columns
    private int rows;       // Number of rows
    private Polygon[][] hexagons;  // Store the hexagons for easy access

    /**
     * Constructor
     * @param rows  number of rows
     * @param cols  number of columns
     * @param cellSize size of each cell
     */
    public HexUI(int rows, int cols, int cellSize) {
        this.rows = rows;
        this.cols = cols;
        
        // Calculate hexagon size and offset based on cell size
        hexSize = cellSize * 2 / 3;
        hexWidth = (int) (Math.sqrt(3) * hexSize);
        hexHeight = 2 * hexSize;
        verticalSpacing = (int) (hexHeight * 0.75);

        hexagons = new Polygon[cols][rows];
        createHexGrid();
    }

    /**
     * Create hexagons based on grid layout
     */
    private void createHexGrid() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int xOffset = col * hexWidth;
                int yOffset = row * verticalSpacing;

                // Odd rows are staggered inward
                if (row % 2 != 0) {
                    xOffset += hexWidth / 2;
                }
                hexagons[col][row] = createHexagon(xOffset, yOffset);
            }
        }
    }

    /**
     * Create a single hexagon at the specified position
     * @param xOffset center x coordinate
     * @param yOffset center y coordinate
     * @return the created hexagon
     */
    private Polygon createHexagon(int xOffset, int yOffset) {
        Polygon hex = new Polygon();
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(60.0 * i + 30);
            int x = (int) (xOffset + hexSize * Math.cos(angle));
            int y = (int) (yOffset + hexSize * Math.sin(angle));
            hex.addPoint(x, y);
        }
        return hex;
    }

    /**
     * Detect which hexagon was clicked
     * @param point the point where the click occurred
     * @return  the coordinates of the clicked hexagon
     */
    public Coordinates detectCellClick(Point point) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (hexagons[col][row].contains(point)) {
                    return new Coordinates(col, row);
                }
            }
        }
        // If we didnt find it, return invalid
        return new Coordinates(-1,-1);
    }

    /**
     * Draw the field of living and dead cells using this hexagonal grid
     * @param g the graphics object to paint with
     * @param field the field to draw
     */
    public void drawGrid(Graphics g, GameField field) {
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                if (field.get(new Coordinates(x, y))) {
                    g.setColor(Color.WHITE);
                } else {
                    g.setColor(Color.BLACK);
                }
                g.fillPolygon(hexagons[x][y]);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Not needed
    }

}
