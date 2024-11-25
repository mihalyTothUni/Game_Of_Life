package logic;


import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import logic.SessionRules.shapeList;

public class Simulation {
    GameField currentField;
    GameField nextField;
    SessionRules ruleset;
    volatile boolean running = false;
    Thread thread;
    @JsonIgnore
    private List<SimulationObserver> observers;

    //sets up the simulation based on the provided rules
    public Simulation(SessionRules rules){
        
        ruleset = rules;
        if(ruleset.popPrecent == 0){
            currentField = new GameField(ruleset.dimX, ruleset.dimY);
        }
        else{
            currentField = new GameField(ruleset.dimX, ruleset.dimY, ruleset.popPrecent);
        }
        nextField = new GameField(ruleset.dimX, ruleset.dimY);
        
        observers = new ArrayList<>();

        //thread for running the simulation, will be paused until start() is called
        thread = new Thread(() -> {
            while(true){
              synchronized (this) {
                while (!running) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } 
                }
                }
                tick();
                try {
                    Thread.sleep(100); // Adjust the sleep time as needed
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }  
            }
            

        });

        thread.start();
    }

    //calculate next state of entire field
    void nextState(){
        for(int x = 0; x < currentField.dimX; x++){
            for (int y = 0; y < currentField.dimY; y++){
                Coordinates currentCell = new Coordinates(x, y);
                boolean state = calculateAlive(currentCell);
                nextField.set(currentCell, state);
            }
        }
    }

    //switch current and active field
    void switchFields(){
        GameField tmp = currentField;
        currentField = nextField;
        nextField = tmp;
    }

    //advance simulation by one step and notify observers
    void tick(){
        System.out.println("tick "+ getShape());
        nextState();
        switchFields();
        notifyObservers();
    }


    // Ensure observers list is initialized
    private void ensureObserversInitialized() {
        if (observers == null) {
            observers = new ArrayList<>();
    }
}

    //add an observer to the simulation
    public void addObserver(SimulationObserver observer){
        ensureObserversInitialized();
        observers.add(observer);
        notifyObservers();
    }

    void notifyObservers(){
        ensureObserversInitialized();
        for(SimulationObserver observer : observers){
            observer.onSimulationTick();
        }
    }

    //start simulation thread
    public synchronized void  start(){
        running = true;
        notifyAll();        
    }

    //pause simulation (will halt at next tick)
    public synchronized void pause(){
        running = false;
        
    }

    //check if simulation is running
    public boolean isRunning(){
        return running;
    }


    //function for calculating a cells state in the next turn
    boolean calculateAlive(Coordinates coords){
        int livingNeighbors = 0;
        //count living neighbors of cell
        List<Coordinates> neighbors = findNeighbors(coords);
        for (Coordinates coordinates : neighbors) {
            if(currentField.get(coordinates)){
                livingNeighbors++;
            }
        }
        //if overpopulated, the cell dies
        if(livingNeighbors > ruleset.getRule("maxLive")){
            return false;
        }
        //if underpopulated, the cell dies
        if(livingNeighbors < ruleset.getRule("minLive")){
            return false;
        }
        //now cell is surely between minLive and maxLive
        //if it is alive, it will stay alive
        if(currentField.get(coords)){
            return true;
        }
        //if it wasnt alive, but meets spawn criteria, it becomes alive 
        if(livingNeighbors >= ruleset.getRule("spawn")){
            return true;
        }

        //if it wasnt alive and doesnt spawn, it stays dead
        return false;
    }    


    //puts all coordinates of neighbors in a list
    //even some invalid ones, but those will count as dead anyway
    List<Coordinates> findNeighbors(Coordinates coords){
        ArrayList<Coordinates> result = new ArrayList<>();

        switch (ruleset.getShape()) {
            case TRIANGLE:
                //only cells with adjacent edges count, not diagonal ones
                
                //left and right
                result.add(new Coordinates(coords.getX() - 1, coords.getY()));
                result.add(new Coordinates(coords.getX() + 1, coords.getY()));
                //odd rows are pointing up, even rows are pointing down
                if(coords.getY() % 2 == 0){
                    //below
                    result.add(new Coordinates(coords.getX(), coords.getY() + 1));

                }else{
                    //above
                    result.add(new Coordinates(coords.getX(), coords.getY() -1));

                }
                break;

            case HEXAGON:
                //odd rows are shifted half a hexagon inwards and then mapped to grid
                //only counting neighbors by edges and not vertices here, too
                
                //common neighbors
                result.add(new Coordinates(coords.getX(), coords.getY() - 1));
                result.add(new Coordinates(coords.getX(), coords.getY() + 1));
                result.add(new Coordinates(coords.getX() - 1, coords.getY()));
                result.add(new Coordinates(coords.getX() + 1, coords.getY()));
                //even-odd neighbors
                if(coords.getY() % 2 == 0){
                    result.add(new Coordinates(coords.getX() - 1, coords.getY() - 1));
                    result.add(new Coordinates(coords.getX() - 1, coords.getY() + 1));
                }else{
                    result.add(new Coordinates(coords.getX() + 1, coords.getY() + 1));
                    result.add(new Coordinates(coords.getX() + 1, coords.getY() + 1));
                }
                break;

            //defaults to SQUARE cells
            default:
                //all neighbors with adjacent vertices
                result.add(new Coordinates(coords.getX(), coords.getY() - 1));
                result.add(new Coordinates(coords.getX() + 1, coords.getY() - 1));
                result.add(new Coordinates(coords.getX() + 1, coords.getY()));
                result.add(new Coordinates(coords.getX() + 1, coords.getY() + 1));
                result.add(new Coordinates(coords.getX(), coords.getY() + 1));
                result.add(new Coordinates(coords.getX() - 1, coords.getY() + 1));
                result.add(new Coordinates(coords.getX() - 1, coords.getY()));
                result.add(new Coordinates(coords.getX() - 1, coords.getY() - 1));
                
                break;
        }

        return result;
    }

    // Default constructor for Jackson
    public Simulation() {
        observers = new ArrayList<>();
        thread = new Thread(() -> {
            while(true){
              synchronized (this) {
                while (!running) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } 
                }
                }
                tick();
                try {
                    Thread.sleep(100); // Adjust the sleep time as needed
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }  
            }
            

        });

        thread.start();
    }
    // Getters and Setters for Jackson
    public GameField getCurrentField() {
        return currentField;
    }
    //Random populater method uses this too
    public void setCurrentField(GameField currentField) {
        this.currentField = currentField;
        notifyObservers();
    }

    public GameField getNextField() {
        return nextField;
    }

    public void setNextField(GameField nextField) {
        this.nextField = nextField;
    }

    public SessionRules getRuleset() {
        return ruleset;
    }

    public void setRuleset(SessionRules ruleset) {
        this.ruleset = ruleset;
    }
    //clears both current and next field
    public void clear() {
        currentField.clear();
        nextField.clear();
        
        notifyObservers();
    }
    //set the cell shape
	public void setShape(shapeList selectedShape) {
        ruleset.cellShape = selectedShape;
        //notify observers of change
        notifyObservers();
    }
    //get the cell shape
    public shapeList getShape() {
        return ruleset.cellShape;
    }
    //get the current observers
    public List<SimulationObserver> getObservers() {
        return observers;
    }
    
}
