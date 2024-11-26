package io;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import logic.SessionRules;



/**
 * Class for serializing and deserializing rules to and from JSON files
 * Uses Jackson for JSON handling
 */
public class RulesJSON {
    private final ObjectMapper objectMapper; // Jackson object mapper
    
    /**
     * Constructor
     * Initializes the object mapper
     */
    public RulesJSON() {
        objectMapper = new ObjectMapper();
    }
    /**
     * Serializes the session rules to a JSON file
     * @param sessionRules the rules to serialize
     * @param filePath     the path to the file to save the rules to
     * @throws IOException if there's an error writing the file
     */
    public void serialize(SessionRules sessionRules, String filePath) throws IOException {
        objectMapper.writeValue(new File(filePath), sessionRules);
    }
    /**
     * Deserializes the session rules from a JSON file
     * @param filePath  the path to the file to read the rules from
     * @return          the deserialized rules
     * @throws IOException if there's an error reading the file
     */
    public SessionRules deserialize(String filePath) throws IOException {
        return objectMapper.readValue(new File(filePath), SessionRules.class);
    }
}
