package io;


import com.fasterxml.jackson.databind.ObjectMapper;
import logic.Simulation;
import java.io.File;
import java.io.IOException;





/**
 * Class for serializing and deserializing simulations to and from JSON files
 * Uses Jackson for JSON handling
 */
public class SimulationJSON {
    private final ObjectMapper objectMapper; // Jackson object mapper

    /**
     * Constructor
     * Initializes the object mapper
     */
    public SimulationJSON() {
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Saves a Simulation object to a JSON file
     * @param simulation    The simulation to save
     * @param filePath      The path where to save the JSON file
     * @throws IOException  If there's an error writing the file
     */
    public void serialize(Simulation simulation, String filePath) throws IOException {
        objectMapper.writeValue(new File(filePath), simulation);
    }

    /**
     * Loads a Simulation object from a JSON file
     * @param filePath  The path of the JSON file to load
     * @return          The deserialized Simulation object
     * @throws IOException If there's an error reading the file
     */
    public Simulation deserialize(String filePath) throws IOException {
        return objectMapper.readValue(new File(filePath), Simulation.class);
    }
}