import React from 'react';

export const useSimulations = (initialSelectedSimulationId) => {
  const [availableSimulationsState, setAvailableSimulationsState] =
    React.useState([]);
  const [selectedSimulationIdState, setSelectedSimulationIdState] =
    React.useState(initialSelectedSimulationId);

  return {
    setFromArray: (newAvailableSimulations) => {
      setAvailableSimulationsState(newAvailableSimulations || []);
    },

    toArray: () => {
      // IMPORTANT: don't copy it because we don't want to trigger a "react change"
      return availableSimulationsState;
    },

    getSelectedSimulationId: () => {
      return selectedSimulationIdState;
    },

    setSelectedSimulationId: (newSelectedSimulationId) => {
      setSelectedSimulationIdState(newSelectedSimulationId);
    },

    addSimulationAndSelect: (newSimulation) => {
      setAvailableSimulationsState((prevAvailableSimulations) => {
        [...prevAvailableSimulations, newSimulation];
      });

      setSelectedSimulationIdState(newSimulation.simulationId);
    },
  };
};
