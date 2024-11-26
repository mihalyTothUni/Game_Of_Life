package logic;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that holds the rules for the current simulation
 */
public class SessionRules {
    
    private Map<String, Integer> gameRules;     // Live, die, spawn conditions
    
    public enum shapeList {SQUARE, TRIANGLE, HEXAGON} // Possible shapes

    static final int DEFAULT_WIDTH = 100;   // Default values for the field
    static final int DEFAULT_HEIGHT = 50;

    shapeList cellShape;    // Shape of the cells
    int popPrecent;         // Percentage of cells that should be alive
    int dimX;               //Field dimensions
    int dimY;

    /**
     * Constructor for SessionRules class
     * @param rules Map of rules for the simulation
     * @param shape shapeList value for the shape of the cells
     * @param dimX width of the field
     * @param dimY height of the field
     * @param percent percentage of cells that are alive
     */
    public SessionRules(Map<String, Integer> rules, shapeList shape, int dimX, int dimY, int percent){
        gameRules = rules;
        cellShape = shape;
        popPrecent = percent;
        this.dimX = dimX;
        this.dimY = dimY;
    }

    /**
     * Default constructor for SessionRules class
     * Sets the rules to Conway rules and a 50x100 field
     */
    public SessionRules(){
        Map<String, Integer> conway = new HashMap<>();
        conway.put("minLive", 2);
        conway.put("maxLive", 3);
        conway.put("spawn", 3);

        gameRules = conway;
        cellShape = shapeList.SQUARE;
        popPrecent = 0;
        dimX = DEFAULT_WIDTH;
        dimY = DEFAULT_HEIGHT;
    }

    /**
     * Ruleset getter
     * @return Map of rules for the simulation
     */
    public Map<String, Integer> getRules(){
        return gameRules;
    }

    //get the value of a specific rule
    /**
     * Get the value of a specific rule
     * @param rule String name of the rule
     * @return int value of the rule
     */
    public int getRule(String rule){
        return gameRules.get(rule);
    }

    /**
     * Get the shape of the cells
     * @return shapeList value of the shape
     */
    public shapeList getShape(){
        return cellShape;
    }

    // Getters and Setters for Jackson from here on
    /**
     * Get the width of the field
     * @return value of the width
     */
    public int getDimX() {
        return dimX;
    }
    /**
     * Set the width of the field
     * @param dimX value of the width
     */
    public void setDimX(int dimX) {
        this.dimX = dimX;
    }
    /**
     * Get the height of the field
     * @return value of the height
     */
    public int getDimY() {
        return dimY;
    }
    /**
     * Set the height of the field
     * @param dimY value of the height
     */
    public void setDimY(int dimY) {
        this.dimY = dimY;
    }
    /**
     * Get the percentage of cells that should be alive
     * @return value of the percentage
     */
    public int getPopPrecent() {
        return popPrecent;
    }
    /**
     * Set the percentage of cells that should be alive
     * @param popPrecent value of the percentage
     */
    public void setPopPrecent(int popPrecent) {
        this.popPrecent = popPrecent;
    }

    /**
     * Set the shape of the cells
     * @param shape shapeList value of the shape
     */
    public void setShape(shapeList shape){
        cellShape = shape;
    }
    /**
     * Set the rules for the simulation
     * @param gameRules Map of rules for the simulation
     */
    public void setRules(Map<String, Integer> gameRules) {
        this.gameRules = gameRules;
    }

    



}
