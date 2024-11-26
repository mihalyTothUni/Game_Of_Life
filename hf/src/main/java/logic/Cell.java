package logic;

/**
 * Cell class for the Game of Life simulation
 * Stores the state of a cell (alive or dead)
 */
public class Cell {
    private boolean isAlive;

    /**
     * Constructor for Cell class
     * @param alive boolean value for the state of the cell
     */
    public Cell(boolean alive){
        isAlive = alive;
    }

    /**
     * Default constructor for Cell class
     * Initializes cell as dead
     */
    public Cell(){
        this(false);
    }

    /**
     * Constructor for Cell class with randomized state
     * @param percentage int value for the chance of the cell being alive (0-100)
     */
    public Cell(int percentage){
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("Percentage must be between 0 and 100");
        }
        isAlive = Math.random() * 100 < percentage;
    }
    
    /**
     * Returns the state of the cell
     * @return boolean value for the state of the cell
     */
    public boolean isAlive(){
        return isAlive;
    }

    /**
     * Sets the state of the cell
     * @param state boolean value to set
     */
    public void setAlive(boolean state){
        isAlive = state;
    }
}
