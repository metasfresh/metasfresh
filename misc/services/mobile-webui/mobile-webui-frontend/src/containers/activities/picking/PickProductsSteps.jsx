import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { v4 as uuidv4 } from 'uuid';

import PickStep from './PickStep';

class PickProductsSteps extends Component {
  render() {
    const { steps, wfProcessId, activityId, lineIndex } = this.props;
    return (
      <div className="steps-container">
        {steps.length > 0 &&
          steps.map((stepItem, index) => {
            let uniqueId = uuidv4();
            return (
              <PickStep
                key={uniqueId}
                id={uniqueId}
                stepId={index}
                wfProcessId={wfProcessId}
                activityId={activityId}
                lineIndex={lineIndex}
                {...stepItem}
              />
            );
          })}
      </div>
    );
  }
}

PickProductsSteps.propTypes = {
  steps: PropTypes.array.isRequired,
  activityId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  lineIndex: PropTypes.number.isRequired,
};

export default PickProductsSteps;
