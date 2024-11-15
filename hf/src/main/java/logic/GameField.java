package logic;


public class GameField {
    Cell[][] map;
    int dimX;
    int dimY; 

    //initializes a dimX by dimY field with dead cells
    public GameField(int dimX, int dimY){
        this.dimX = dimX;
        this.dimY = dimY;
        map = new Cell[dimX][dimY];
    }

    //constructor with no parameters, sets default values
    public GameField(){
        this(50,100);
    }

    //initializes field with given population density
    public GameField(int dimX, int dimY, int percentage){
        this(dimX, dimY);
        for(int i = 0; i < dimX; i++){
            for (int j = 0; j < dimY; j++) {
                map[dimX][dimY] = new Cell(percentage);
            }
        }
    }

    //get the state of a cell at a given point
    //if it is out of bounds, defaults to dead
    public boolean get(Coordinates coords){
        if(coords.getX() < 0 || coords.getY() < 0 || coords.getX() >= dimX || coords.getY() >= dimY){
            return false;
        }
        return map[coords.getX()][coords.getY()].isAlive();
    }

    //set the state of a cell at a given point
    //if out of bounds, simply return
    public void set(Coordinates coords, boolean state){
        if(coords.getX() < 0 || coords.getY() < 0 || coords.getX() >= dimX || coords.getY() >= dimY){
            return;
        }
        map[coords.getX()][coords.getY()].setAlive(state);
    }
}
