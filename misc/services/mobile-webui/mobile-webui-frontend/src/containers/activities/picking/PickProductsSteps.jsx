import React, { Component } from 'react';
import PropTypes from 'prop-types';

import PickStep from './PickStep';

class PickProductsSteps extends Component {
  render() {
    const { steps, wfProcessId, activityId, lineId } = this.props;
    return (
      <div className="steps-container">
        {steps.length > 0 &&
          steps.map((stepItem) => {
            return (
              <PickStep
                key={stepItem.pickingStepId}
                {...stepItem}
                wfProcessId={wfProcessId}
                activityId={activityId}
                lineId={lineId}
                stepId={stepItem.pickingStepId}
              />
            );
          })}
      </div>
    );
  }
}

PickProductsSteps.propTypes = {
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  steps: PropTypes.array.isRequired,
};

export default PickProductsSteps;
