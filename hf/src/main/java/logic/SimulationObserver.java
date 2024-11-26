package logic;

import com.fasterxml.jackson.annotation.JsonIgnoreType;

// Interface for classes that want to observe the simulation
/**
 * Interface for classes that want to observe the simulation
 * Used for the observer pattern, useful for updating the GUI
 */
@JsonIgnoreType
public interface SimulationObserver {
    /**
     * Called when the simulation ticks
     */
    public void onSimulationTick();
}
