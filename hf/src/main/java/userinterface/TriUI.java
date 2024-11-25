package userinterface;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

import logic.Coordinates;
import logic.GameField;

public class TriUI extends JPanel {
    int triangleSize; // Length of triangle side
    int height;       // Height of triangles
    int cols;        // Number of columns
    int rows;        // Number of rows
    private Polygon[][] triangles;     // Store triangles for easy access

    public TriUI(int rows, int cols, int cellSize) {
        this.triangleSize = (int) ((2 * cellSize) / Math.sqrt(3));
        height = (int) (Math.sqrt(3) / 2 * triangleSize);
        this.rows = rows;
        this.cols = cols;
        triangles = new Polygon[cols][rows];
        createTriangleGrid();

    }

    // Create triangles based on grid layout
    private void createTriangleGrid() {

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int xOffset = col * triangleSize;
                int yOffset = row * height;

                // Alternate between upward and downward triangles
                boolean isUpward = (row + col) % 2 != 0;

                triangles[col][row] = createTriangle(xOffset, yOffset, isUpward);
            }
        }
    }

    // Create a single triangle (upward or downward) at the specified position
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

    // Detect which triangle was clicked
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

    // Draws the field onto this grid
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
        //dont need this
    }
}
