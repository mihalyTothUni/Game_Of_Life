package main.logic;

import java.util.ArrayList;
import java.util.List;

public class Field {
    Cell[][] map;
    int dimX;
    int dimY; 

    //initializes a dimX by dimY field with dead cells
    public Field(int dimX, int dimY){
        this.dimX = dimX;
        this.dimY = dimY;
        map = new Cell[dimX][dimY];
    }

    //constructor with no parameters, sets default values
    public Field(){
        this.Field(50,100);
    }

    //initializes field with given population density
    public Field(int dimX, int dimY, int percentage){
        this.Field(dimX, dimY);
        for(int i = 0; i < dimX; i++){
            for (int j = 0; j < dimY; j++) {
                map[dimX][dimY] = new Cell(percentage);
            }
        }
    }
}
