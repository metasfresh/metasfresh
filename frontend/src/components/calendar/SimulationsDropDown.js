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
  selectedSimulation,
  onSelect,
  onNew,
}) => {
  const simulationsById = simulations.reduce((accum, simulation) => {
    accum[simulation.simulationId] = simulation;
    return accum;
  }, {});

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

  const OPTION_None = { key: 'NONE', caption: 'None' }; // TODO trl
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
  selectedSimulation: PropTypes.object,
  onNew: PropTypes.func.isRequired,
  onSelect: PropTypes.func.isRequired,
};

export default SimulationsDropDown;
