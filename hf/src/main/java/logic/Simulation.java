package logic;


import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import logic.SessionRules.shapeList;

/**
 * Simulation class, contains the game field and ruleset
 * Runs the simulation
 */
public class Simulation {
    static final int TICK_SPEED = 10;        // Ticks per second
    private GameField currentField;         // Current state of the field
    private GameField nextField;            // Next state of the field
    private SessionRules ruleset;           // Ruleset for the simulation
    private volatile boolean running = false;   // Is the simulation running
    private Thread thread;                  // Thread for running the simulation
    @JsonIgnore
    private List<SimulationObserver> observers; // List of observers watching the simulation

    /**
     * Constructor for Simulation
     * @param rules ruleset for the simulation
     */
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

        // Thread for running the simulation, will be paused until start() is called
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
                    Thread.sleep(1000 / TICK_SPEED);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }  
            }
            

        });

        thread.start();
    }

    /**
     * Calculate the next state of the field
     */
    private void nextState(){
        for(int x = 0; x < currentField.dimX; x++){
            for (int y = 0; y < currentField.dimY; y++){
                Coordinates currentCell = new Coordinates(x, y);
                boolean state = calculateAlive(currentCell);
                nextField.set(currentCell, state);
            }
        }
    }

    /**
     * Switch the current and next fields
     */
    private void switchFields(){
        GameField tmp = currentField;
        currentField = nextField;
        nextField = tmp;
    }

    /**
     * Advance the simulation by one tick
     */
    private void tick(){
        nextState();
        switchFields();
        notifyObservers();
    }

    /**
     * Ensure that the observers list is initialized
     * Avoids null pointer exceptions
     */
    private void ensureObserversInitialized() {
        if (observers == null) {
            observers = new ArrayList<>();
        }
    }

    /**
     * Add an observer to the simulation
     * @param observer observer to add
     */
    public void addObserver(SimulationObserver observer){
        ensureObserversInitialized();
        observers.add(observer);
        notifyObservers();
    }

    /**
     * Notify all observers of a simulation tick
     */
    private void notifyObservers(){
        ensureObserversInitialized();
        for(SimulationObserver observer : observers){
            observer.onSimulationTick();
        }
    }

    /**
     * Start the simulation
     */
    public synchronized void  start(){
        running = true;
        notifyAll();        
    }

    /**
     * Pause the simulation
     * Will halt at the next tick
     */
    public synchronized void pause(){
        running = false;
        
    }

    /**
     * Check if the simulation is running
     * @return true if the simulation is currently running
     */
    public boolean isRunning(){
        return running;
    }


    /**
     * Calculate if a cell will be alive in the next turn
     * @param coords coordinates of the cell
     * @return true if the cell will be alive
     */
    private boolean calculateAlive(Coordinates coords){
        int livingNeighbors = 0;
        // Count living neighbors of cell
        List<Coordinates> neighbors = findNeighbors(coords);
        for (Coordinates coordinates : neighbors) {
            if(currentField.get(coordinates)){
                livingNeighbors++;
            }
        }
        // If overpopulated, the cell dies
        if(livingNeighbors > ruleset.getRule("maxLive")){
            return false;
        }
        // If underpopulated, the cell dies
        if(livingNeighbors < ruleset.getRule("minLive")){
            return false;
        }
        
        /**
         * Now cell is surely between minLive and maxLive
         * If it is alive, it will stay alive
         */
        if(currentField.get(coords)){
            return true;
        }
        // If it wasnt alive, but meets spawn criteria, it spawns 
        if(livingNeighbors >= ruleset.getRule("spawn")){
            return true;
        }

        // If it wasnt alive and doesnt spawn, it stays dead
        return false;
    }    


    //puts all coordinates of neighbors in a list
    //even some invalid ones, but those will count as dead anyway
    /**
     * Find all neighbors of a cell by its coordinates
     * @param coords coordinates of the cell in question
     * @return list of coordinates of all neighbors
     */
    private List<Coordinates> findNeighbors(Coordinates coords){
        ArrayList<Coordinates> result = new ArrayList<>();

        switch (ruleset.getShape()) {
            case TRIANGLE:
                // Only cells with adjacent edges count as neighbors
                
                // Left and right
                result.add(new Coordinates(coords.getX() - 1, coords.getY()));
                result.add(new Coordinates(coords.getX() + 1, coords.getY()));

                // Triangles are alternating facing up and down
                boolean isUpward = (coords.getY() + coords.getX()) % 2 != 0;
                if(isUpward){
                    // Points up, adjacent to one below it
                    result.add(new Coordinates(coords.getX(), coords.getY() + 1));
                }else{
                    // Points down, adjacent to one above it
                    result.add(new Coordinates(coords.getX(), coords.getY() - 1));
                }
                break;

            case HEXAGON:
                // Odd rows are shifted half a hexagon to the right and then mapped to grid
                // Only counting neighbors by adjacent edges here, too
                
                // Common neighbors
                result.add(new Coordinates(coords.getX(), coords.getY() - 1));
                result.add(new Coordinates(coords.getX(), coords.getY() + 1));
                result.add(new Coordinates(coords.getX() - 1, coords.getY()));
                result.add(new Coordinates(coords.getX() + 1, coords.getY()));
                // Even-odd neighbors
                if(coords.getY() % 2 == 0){
                    result.add(new Coordinates(coords.getX() - 1, coords.getY() - 1));
                    result.add(new Coordinates(coords.getX() - 1, coords.getY() + 1));
                }else{
                    result.add(new Coordinates(coords.getX() + 1, coords.getY() + 1));
                    result.add(new Coordinates(coords.getX() + 1, coords.getY() - 1));
                }
                break;

            case SQUARE:
                // All neighbors with adjacent vertices (classic Conway style)
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

    /**
     * Clear the current and next fields
     */
    public void clear() {
        currentField.clear();
        nextField.clear();
        notifyObservers();
    }
    /**
     * Set the cell shape
     * @param selectedShape the shape to set
     */
    public void setShape(shapeList selectedShape) {
        ruleset.cellShape = selectedShape;
        // Notify observers of change
        notifyObservers();
    }
    /**
     * Get the current shape of the cells
     * @return the current shape
     */
    public shapeList getShape() {
        return ruleset.cellShape;
    }

    // Getters and Setters for Jackson framework from here on

    /**
     * Default constructor for Simulation
     * Used for Jackson deserialization
     */
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
                    Thread.sleep(1000 / TICK_SPEED);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }  
            }
            

        });

        thread.start();
    }


    /**
     * Get the current field
     * @return current field
     */
    public GameField getCurrentField() {
        return currentField;
    }
    /**
     * Set the current field
     * @param currentField the field to set as current
     */
    public void setCurrentField(GameField currentField) {
        this.currentField = currentField;
        notifyObservers();
    }
    /**
     * Get the next field
     * @return next field
     */
    public GameField getNextField() {
        return nextField;
    }
    /**
     * Set the next field
     * @param nextField the field to set as next
     */
    public void setNextField(GameField nextField) {
        this.nextField = nextField;
    }
    /**
     * Get the ruleset
     * @return ruleset
     */
    public SessionRules getRuleset() {
        return ruleset;
    }
    /**
     * Set the ruleset
     * @param ruleset the ruleset to set
     */
    public void setRuleset(SessionRules ruleset) {
        this.ruleset = ruleset;
    }
    /**
     * Get the current observers
     * @return list of observers
     */
    public List<SimulationObserver> getObservers() {
        return observers;
    }
    
}
