package main;

import java.io.IOException;


import io.*;
import logic.*;
import userinterface.*;


public class Main {
    public static void main(String[] args) throws IOException {
        Simulation sim1 = new Simulation(new SessionRules());

        


        UIFrame frame = new UIFrame(sim1);

        
        //TODO implement save AND ADD LOAD OPTION for sims


        //TODO implement preset saving and loading

        //TODO triangles seem funky, look into it

        //TODO hexagons are completely screwed
        //they aint even pointy up like they should be
        
        
            
            

    }
}
