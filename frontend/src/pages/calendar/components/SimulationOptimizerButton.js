import React from 'react';
import PropTypes from 'prop-types';

const SimulationOptimizerButton = ({
  simulationId,
  status,
  onStart,
  onStop,
  hidden,
}) => {
  if (hidden) {
    return null;
  }
  if (!simulationId) {
    return null;
  }

  switch (status) {
    case 'STARTED':
      return (
        <button onClick={() => onStop({ simulationId })}>
          Stop optimization
        </button>
      );
    case 'STOPPED':
      return (
        <button onClick={() => onStart({ simulationId })}>
          Start optimization
        </button>
      );
    default:
      return null;
  }
};

SimulationOptimizerButton.propTypes = {
  simulationId: PropTypes.string,
  status: PropTypes.string,
  onStart: PropTypes.func.isRequired,
  onStop: PropTypes.func.isRequired,
  hidden: PropTypes.bool,
};

export default SimulationOptimizerButton;
