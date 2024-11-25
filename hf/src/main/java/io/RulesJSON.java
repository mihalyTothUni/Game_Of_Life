package io;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import logic.SessionRules;


//used for serializing rules to JSON files
public class RulesJSON {

    
    public RulesJSON() {
        objectMapper = new ObjectMapper();
    }

    private final ObjectMapper objectMapper;


    public void serialize(SessionRules sessionRules, String filePath) throws IOException {
        objectMapper.writeValue(new File(filePath), sessionRules);
    }
    
    public SessionRules deserialize(String filePath) throws IOException {
        return objectMapper.readValue(new File(filePath), SessionRules.class);
    }
}
