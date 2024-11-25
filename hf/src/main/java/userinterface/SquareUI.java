package userinterface;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

import logic.Coordinates;
import logic.GameField;

public class SquareUI extends JPanel {
    int cellSize; // Size of square side
    int cols;    // Number of columns
    int rows;    // Number of rows
    private Rectangle[][] squares; // Store the squares for easy access

    public SquareUI(int rows, int cols, int cellSize) {
        this.cellSize = cellSize;
        this.rows = rows;
        this.cols = cols;
        squares = new Rectangle[cols][rows];
        createSquareGrid();
    }

    // Create squares based on grid layout
    private void createSquareGrid() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = col * cellSize;
                int y = row * cellSize;
                squares[col][row] = new Rectangle(x, y, cellSize, cellSize);
            }
        }
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
                g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
            }
        }
    }

    // Click detection to pass back to GameUI
    public Coordinates detectCellClick(Point point) {
        int col = point.x / cellSize;
        int row = point.y / cellSize;
        return new Coordinates(col, row);
    }


    @Override
    protected void paintComponent(Graphics g) {
        //not needed
    }

}
