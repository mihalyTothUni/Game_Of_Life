package userinterface;

import java.awt.*;
import javax.swing.*;

import logic.Coordinates;
import logic.GameField;

/**
 * Class for drawing a clickable square grid
 */
public class SquareUI extends JPanel {
    private int cellSize; // Size of square side
    private int cols;    // Number of columns
    private int rows;    // Number of rows
    private Rectangle[][] squares; // Store the squares for easy access

    /**
     * Constructor
     * @param rows  number of rows
     * @param cols  number of columns
     * @param cellSize size of each cell
     */
    public SquareUI(int rows, int cols, int cellSize) {
        this.cellSize = cellSize;
        this.rows = rows;
        this.cols = cols;
        squares = new Rectangle[cols][rows];
        createSquareGrid();
    }

    /**
     * Create squares based on grid layout
     */
    private void createSquareGrid() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = col * cellSize;
                int y = row * cellSize;
                squares[col][row] = new Rectangle(x, y, cellSize, cellSize);
            }
        }
    }

    /**
     * Draw the grid of cells based on the field
     * @param g   the graphics object to paint with
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
                g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
            }
        }
    }

    /**
     * Detect which square was clicked
     * @param point the point that was clicked
     * @return the coordinates of the square that was clicked
     */
    public Coordinates detectCellClick(Point point) {
        int col = point.x / cellSize;
        int row = point.y / cellSize;
        return new Coordinates(col, row);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Not used
    }

}
