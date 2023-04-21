import React from 'react';
import PropTypes from 'prop-types';

const SimulationComputeButton = ({
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
      return <button onClick={() => onStop({ simulationId })}>Stop</button>;
    case 'STOPPED':
      return <button onClick={() => onStart({ simulationId })}>Start</button>;
    default:
      return null;
  }
};

SimulationComputeButton.propTypes = {
  simulationId: PropTypes.string,
  status: PropTypes.string,
  onStart: PropTypes.func.isRequired,
  onStop: PropTypes.func.isRequired,
  hidden: PropTypes.bool,
};

export default SimulationComputeButton;
