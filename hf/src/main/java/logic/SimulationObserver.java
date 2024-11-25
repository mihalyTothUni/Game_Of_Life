package logic;

import com.fasterxml.jackson.annotation.JsonIgnoreType;

// Interface for classes that want to observe the simulation
@JsonIgnoreType
public interface SimulationObserver {
    public void onSimulationTick();
}
