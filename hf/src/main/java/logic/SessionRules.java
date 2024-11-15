package logic;

import java.util.HashMap;
import java.util.Map;
//stores the rules for the current simulation
public class SessionRules {
    //live, die, spawn conditions
    Map<String, Integer> gameRules;
    //possible shapes
    public enum shapeList {SQUARE, TRIANGLE, HEXAGON}

    shapeList cellShape;
    //population percentage
    int popPrecent;
    //Field dimensions
    int dimX;
    int dimY;

    //constructor with custom parameters
    public SessionRules(Map<String, Integer> rules, shapeList shape, int dimX, int dimY, int percent){
        gameRules = rules;
        cellShape = shape;
        popPrecent = percent;
        this.dimX = dimX;
        this.dimY = dimY;
    }

    //sets classic Conway rules and a blank 50x100 field
    public SessionRules(){
        Map<String, Integer> conway = new HashMap<>();
        conway.put("minLive", 2);
        conway.put("maxLive", 3);
        conway.put("spawn", 3);

        gameRules = conway;
        cellShape = shapeList.SQUARE;
        popPrecent = 0;
        dimX = 50;
        dimY = 100;
        
        
    }

    //gets the rules for the current simulation
    public Map<String, Integer> getRules(){
        return gameRules;
    }

    //get the value of a specific rule
    public int getRule(String rule){
        return gameRules.get(rule);
    }


    //gets the shape of the cells
    public shapeList getShape(){
        return cellShape;
    }

    



}
