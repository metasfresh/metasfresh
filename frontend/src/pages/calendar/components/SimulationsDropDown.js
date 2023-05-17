import React from 'react';
import PropTypes from 'prop-types';
import SimpleList from '../../../components/widget/List/SimpleList';

import './SimulationsDropDown.scss';
import counterpart from 'counterpart';

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

const toKeyCaption = (simulation) => {
  if (simulation) {
    let caption = simulation.name;
    if (simulation.processed) {
      caption = counterpart.translate('simulation.processed') + caption;
    }

    return {
      key: simulation.simulationId,
      caption: caption,
      extendedProps: { simulation },
    };
  } else {
    return null;
  }
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
    if (!keyCaptionEntry || keyCaptionEntry.key === KEY_ACTUAL_DATA) {
      onSelect(null);
    } else if (keyCaptionEntry.key === KEY_NEW_SIMULATION) {
      onNew();
    } else {
      onSelect(keyCaptionEntry.extendedProps.simulation);
    }
  };

  const KEY_ACTUAL_DATA = 'ACTUAL';
  const OPTION_ACTUAL_DATA = {
    key: KEY_ACTUAL_DATA,
    caption: counterpart.translate('option.actual.data'),
  };

  const KEY_NEW_SIMULATION = 'NEW';
  const OPTION_NEW_SIMULATION = {
    key: KEY_NEW_SIMULATION,
    caption: counterpart.translate('option.new.simulation'),
  };

  return (
    <SimpleList
      className="calendar-simulations-dropdown"
      list={[
        OPTION_NEW_SIMULATION,
        OPTION_ACTUAL_DATA,
        ...simulations.map(toKeyCaption),
      ]}
      selected={toKeyCaption(selectedSimulation) || OPTION_ACTUAL_DATA}
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
