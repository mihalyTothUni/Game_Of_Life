package userinterface;

import java.awt.*;
import javax.swing.*;

import logic.Coordinates;
import logic.GameField;

/**
 * Class for drawing a clickable triangular grid
 */
public class TriUI extends JPanel {
    private int triangleSize; // Length of triangle side
    private int height;       // Height of triangles
    private int cols;        // Number of columns
    private int rows;        // Number of rows
    private Polygon[][] triangles;     // Store triangles for easy access

    /**
     * Constructor
     * @param rows  number of rows
     * @param cols  number of columns
     * @param cellSize size of each cell
     */
    public TriUI(int rows, int cols, int cellSize) {
        this.triangleSize = (int) ((2 * cellSize) / Math.sqrt(3));
        height = (int) (Math.sqrt(3) / 2 * triangleSize);
        this.rows = rows;
        this.cols = cols;
        triangles = new Polygon[cols][rows];
        createTriangleGrid();
    }

    /**
     * Create triangles based on grid layout
     */
    private void createTriangleGrid() {

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int xOffset = col * triangleSize;
                int yOffset = row * height;

                // Alternate between upward and downward pointing triangles
                boolean isUpward = (row + col) % 2 != 0;
                triangles[col][row] = createTriangle(xOffset, yOffset, isUpward);
            }
        }
    }

    /**
     * Create a single triangle (upward or downward) at the specified position
     * @param xOffset first point x coordinate
     * @param yOffset first point y coordinate
     * @param isUpward true if the triangle should point upwards, false if it should point downwards
     * @return the created triangle polygon
     */
    private Polygon createTriangle(int xOffset, int yOffset, boolean isUpward) {
        Polygon triangle = new Polygon();
        if (isUpward) {
            yOffset += height;
            triangle.addPoint(xOffset, yOffset);
            triangle.addPoint(xOffset + triangleSize / 2, yOffset - height);
            triangle.addPoint(xOffset + triangleSize, yOffset);
        } else {
            triangle.addPoint(xOffset, yOffset);
            triangle.addPoint(xOffset + triangleSize / 2, yOffset + height);
            triangle.addPoint(xOffset + triangleSize, yOffset);
        }
        return triangle;
    }

    /**
     * Detect which triangle was clicked
     * @param point the point where the click occurred
     * @return  the coordinates of the clicked triangle
     */
    public Coordinates detectCellClick(Point point) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (triangles[col][row].contains(point)) {
                    return new Coordinates(col, row);
                }
            }
        }
        // If we didnt find it, return invalid
        return new Coordinates(-1,-1);
    }


    /**
     * Draw the grid of cells using this triangular grid
     * @param g  the graphics object to paint with
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
                g.fillPolygon(triangles[x][y]);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Not used
    }
}
