package io;


import com.fasterxml.jackson.databind.ObjectMapper;
import logic.Simulation;
import java.io.File;
import java.io.IOException;




//used for serializing to barely readable JSON files
public class SimulationJSON {
    private final ObjectMapper objectMapper;

    public SimulationJSON() {
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Saves a Simulation object to a JSON file
     * @param simulation The simulation to save
     * @param filePath The path where to save the JSON file
     * @throws IOException If there's an error writing the file
     */
    public void saveToJSON(Simulation simulation, String filePath) throws IOException {
        objectMapper.writeValue(new File(filePath), simulation);
    }

    /**
     * Loads a Simulation object from a JSON file
     * @param filePath The path of the JSON file to load
     * @return The deserialized Simulation object
     * @throws IOException If there's an error reading the file
     */
    public Simulation loadFromJSON(String filePath) throws IOException {
        return objectMapper.readValue(new File(filePath), Simulation.class);
    }
}