package main;

import java.io.IOException;

//import interface.*;
import io.*;
import logic.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Simulation sim1 = new Simulation(new SessionRules());

        SimulationJSON ser1 = new SimulationJSON();

        ser1.saveToJSON(sim1, "sim1.json");
        
        Simulation simRE = ser1.loadFromJSON("sim1.json");

        ser1.saveToJSON(simRE, "sim2.json");

        System.out.println("Success");
    }
}
