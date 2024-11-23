package io;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import logic.SessionRules;


//used for serializing rules to JSON files
public class RulesJSON {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static String serialize(SessionRules sessionRules) throws JsonProcessingException {
        return objectMapper.writeValueAsString(sessionRules);
    }
    public static SessionRules deserialize(String filePath) throws IOException {
        return objectMapper.readValue(new File(filePath), SessionRules.class);
    }
}
