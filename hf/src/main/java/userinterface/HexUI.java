package userinterface;

import java.awt.*;
import javax.swing.*;

import logic.Coordinates;
import logic.GameField;

public class HexUI extends JPanel {
    int hexSize; // Size of hexagon (distance from center to a corner)
    int hexWidth;   // Width of hexagons (across flat sides)
    int hexHeight;  // Height of hexagon (across top and bottom point)
    int verticalSpacing;  // Needed for calculating overlap
    int cols;   // Number of columns
    int rows;   // Number of rows
    private Polygon[][] hexagons;  // Store the hexagons for easy access

    public HexUI(int rows, int cols, int cellSize) {
        this.rows = rows;
        this.cols = cols;
        
        hexSize = cellSize / 2;
        hexWidth = (int) (Math.sqrt(3) * hexSize);
        hexHeight = 2 * hexSize;
        verticalSpacing = (int) (hexHeight * 0.75);

        hexagons = new Polygon[cols][rows];
        createHexGrid();
    }

    // Create hexagons based on grid layout
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

    // Create a single hexagon at the specified position
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

    // Detect which hexagon was clicked
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

    // Draws the field onto this grid
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
        //not needed
    }

}
