package logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Mainly just sanity checks
public class SimulationTest {
    private Simulation simulation;
    private SessionRules rules;

    @BeforeEach
    public void setUp() {
        rules = new SessionRules();
        simulation = new Simulation(rules);
    }

    @Test
    public void testSimulationInitialization() {
        assertNotNull(simulation.getCurrentField());
        assertNotNull(simulation.getNextField());
    }

    @Test
    public void testAddObserver() {
        SimulationObserver observer = new SimulationObserver() {
            @Override
            public void onSimulationTick() {
                // Do nothing
            }
        };
        simulation.addObserver(observer);
        assertTrue(simulation.getObservers().contains(observer));
    }

}