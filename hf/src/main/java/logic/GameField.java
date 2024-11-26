package logic;


public class GameField {
    private Cell[][] map;
    int dimX;
    int dimY; 

    //initializes a dimX by dimY field with dead cells
    /**
     * Constructor for GameField class
     * @param dimX width of the field
     * @param dimY height of the field
     * Initializes a field with dead cells
     */
    public GameField(int dimX, int dimY){
        this.dimX = dimX;
        this.dimY = dimY;
        map = new Cell[dimX][dimY];
        for(int i = 0; i < dimX; i++){
            for (int j = 0; j < dimY; j++) {
                map[i][j] = new Cell();
            }
        }
    }

    /**
     * Default constructor for GameField class
     * Initializes a 100x50 field with dead cells
     */
    public GameField(){
        this(100,50);
    }

    /**
     * Constructor for GameField class
     * @param dimX width of the field
     * @param dimY height of the field
     * @param percentage percentage of cells that are alive
     */
    public GameField(int dimX, int dimY, int percentage){
        this(dimX, dimY);
        for(int i = 0; i < dimX; i++){
            for (int j = 0; j < dimY; j++) {
                map[i][j] = new Cell(percentage);
            }
        }
    }

    /**
     * Get the state of a cell at a given point
     * @param coords the coordinates of the cell
     * @return the state of the cell
     * If the coordinates are out of bounds, defaults to dead
     */
    public boolean get(Coordinates coords){
        if(coords.getX() < 0 || coords.getY() < 0 || coords.getX() >= dimX || coords.getY() >= dimY){
            return false;
        }
        return map[coords.getX()][coords.getY()].isAlive();
    }

    /**
     * Set the state of a cell at a given point
     * @param coords the coordinates of the cell
     * @param state the state to set the cell to
     * @throws IllegalArgumentException if the coordinates are out of bounds
     */
    public void set(Coordinates coords, boolean state) throws IllegalArgumentException{
        if(coords.getX() < 0 || coords.getY() < 0 || coords.getX() >= dimX || coords.getY() >= dimY){
            throw new IllegalArgumentException("Coordinates out of bounds");
        }
        map[coords.getX()][coords.getY()].setAlive(state);
    }
    
    /**
     * Clears the field, setting all cells to dead
     */
    public void clear() {
        for(int i = 0; i < dimX; i++){
            for (int j = 0; j < dimY; j++) {
                map[i][j].setAlive(false);
            }
        }
    }

    // Getters and Setters for Jackson from here on
    /**
     * Get the width of the field
     * @return the width of the field
     */
    public int getDimX() {
        return dimX;
    }
    /**
     * Set the width of the field
     * @param dimX the width of the field
     */
    /**
     * Set the width of the field
     * @param dimX the width of the field
     */
    public void setDimX(int dimX) {
        this.dimX = dimX;
    }
    /**
     * Get the height of the field
     * @return the height of the field
     */
    public int getDimY() {
        return dimY;
    }
    /**
     * Set the height of the field
     * @param dimY the height of the field
     */
    public void setDimY(int dimY) {
        this.dimY = dimY;
    }
    /**
     * Get the map of cells
     * @return the map
     */
    public Cell[][] getMap() {
        return map;
    }
    /**
     * Set the map of cells
     * @param field the map to set
     */
    public void setMap(Cell[][] field) {
        this.map = field;
    }
    
    
}
