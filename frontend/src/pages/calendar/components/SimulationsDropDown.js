import React from 'react';
import PropTypes from 'prop-types';
import SimpleList from '../../../components/widget/List/SimpleList';

const OPTION_None = { key: 'NONE', caption: 'Actual data' }; // TODO trl
const OPTION_NEW = { key: 'NEW', caption: 'New simulation' }; // TODO trl

function computeSelectedSimulation(simulations, selectedSimulationId) {
  if (selectedSimulationId == null) {
    return null;
  }

  const selectedSimulation = simulations.find(
    (simulation) =>
      String(simulation.simulationId) === String(selectedSimulationId)
  );
  if (selectedSimulation != null) {
    return selectedSimulation;
  }

  return {
    simulationId: selectedSimulationId,
    name: `<${selectedSimulationId}>`,
  };
}

const toKeyCaptionOrNone = (simulation) => {
  return simulation
    ? {
        key: simulation.simulationId,
        caption: simulation.name,
        extendedProps: { simulation },
      }
    : OPTION_None;
};

const SimulationsDropDown = ({
  simulations,
  selectedSimulationId,
  onSelect,
  onNew,
  onOpenDropdown,
}) => {
  const selectedSimulation = computeSelectedSimulation(
    simulations,
    selectedSimulationId
  );

  const handleOnSelect = (keyCaptionEntry) => {
    if (!keyCaptionEntry || keyCaptionEntry.key === 'NONE') {
      onSelect(null);
    } else if (keyCaptionEntry.key === 'NEW') {
      onNew();
    } else {
      onSelect(keyCaptionEntry.extendedProps.simulation);
    }
  };

  return (
    <SimpleList
      className="calendar-simulations-dropdown"
      list={[OPTION_NEW, OPTION_None, ...simulations.map(toKeyCaptionOrNone)]}
      selected={toKeyCaptionOrNone(selectedSimulation)}
      onSelect={handleOnSelect}
      onOpenDropdown={onOpenDropdown}
    />
  );
};

SimulationsDropDown.propTypes = {
  simulations: PropTypes.array.isRequired,
  selectedSimulationId: PropTypes.oneOfType([
    PropTypes.string,
    PropTypes.number,
  ]),
  onNew: PropTypes.func.isRequired,
  onSelect: PropTypes.func.isRequired,
  onOpenDropdown: PropTypes.func,
};

export default SimulationsDropDown;
