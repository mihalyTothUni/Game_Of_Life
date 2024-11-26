package io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logic.SessionRules;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;


public class TestRulesJSONTest {
    private RulesJSON testRulesJSON;

    @BeforeEach
    public void setUp() {
        testRulesJSON = new RulesJSON();
    }

    @Test
    public void testReadRulesFromFile() throws IOException {
        String filePath = "resources/rules/conway.json";
        SessionRules rules = testRulesJSON.deserialize(filePath);
        assertNotNull(rules);
        SessionRules expectedRules = new SessionRules();
        assertEquals(rules.getRules(), expectedRules.getRules());
        
    }

    @Test
    public void testWriteRulesToFile() throws IOException {
        String filePath = "resources/rules/testing.json";
        SessionRules rules = new SessionRules();
        // Initialize rules with some test data
        testRulesJSON.serialize(rules, filePath);
        // Read the file back and verify its content
        SessionRules readRules = testRulesJSON.deserialize(filePath);
        assertEquals(rules.getRules(), readRules.getRules());
    }

    @Test
    public void testInvalidFilePath() {
        String invalidFilePath = "invalid/path/to/rules.json";
        assertThrows(IOException.class, () -> {
            testRulesJSON.deserialize(invalidFilePath);
        });
    }
}
