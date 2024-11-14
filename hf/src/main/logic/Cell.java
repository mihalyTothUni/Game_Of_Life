package main.logic;

//stores if it is alive or dead
public class Cell {
    boolean isAlive;

    //basic constructor where state can be defined
    public Cell(boolean alive){
        isAlive = alive;
    }

    //parameterless constructor, defaults to dead state
    public Cell(){
        this.Cell(false);
    }

    //constructor with randomized state
    //has percentage chance to be alive (0-100)
    public Cell(int percentage){
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("Percentage must be between 0 and 100");
        }
        isAlive = Math.random() * 100 < percentage;
    }
    
    //state getter
    public boolean isAlive(){
        return isAlive;
    }
}
