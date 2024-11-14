package main.logic;

import java.util.Map;
//stores the rules for the current simulation
public class SessionRules {
    //live, die, spawn conditions
    Map<String, Integer> gameRules;
    //possible shapes
    enum cellShapes {SQUARE, TRIANGLE, HEXAGON}
    //population percentage
    int popPrecent;


}
