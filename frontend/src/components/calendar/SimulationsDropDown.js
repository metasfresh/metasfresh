import React from 'react';
import PropTypes from 'prop-types';
import SimpleList from '../widget/List/SimpleList';

const toKeyCaption = (simulation) => {
  return simulation
    ? { key: simulation.simulationId, caption: simulation.name }
    : null;
};

const SimulationsDropDown = ({
  simulations,
  selectedSimulationId,
  onSelect,
  onNew,
}) => {
  const simulationsById = simulations.reduce((accum, simulation) => {
    accum[simulation.simulationId] = simulation;
    return accum;
  }, {});

  const selectedSimulation =
    selectedSimulationId != null ? simulationsById[selectedSimulationId] : null;

  const handleOnSelect = (keyCaptionEntry) => {
    if (!keyCaptionEntry || keyCaptionEntry.key === 'NONE') {
      onSelect(null);
    } else if (keyCaptionEntry.key === 'NEW') {
      onNew();
    } else {
      const simulation = simulationsById[keyCaptionEntry.key];
      onSelect(simulation);
    }
  };

  const OPTION_None = { key: 'NONE', caption: 'Actual data' }; // TODO trl
  const OPTION_NEW = { key: 'NEW', caption: 'New simulation' }; // TODO trl
  return (
    <SimpleList
      list={[OPTION_NEW, OPTION_None, ...simulations.map(toKeyCaption)]}
      selected={
        selectedSimulation != null
          ? toKeyCaption(selectedSimulation)
          : OPTION_None
      }
      onSelect={handleOnSelect}
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
};

export default SimulationsDropDown;
