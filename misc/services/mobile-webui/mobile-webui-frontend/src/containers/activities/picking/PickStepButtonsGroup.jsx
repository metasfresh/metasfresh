import React, { Component } from 'react';
import PropTypes from 'prop-types';

import PickStepButton from './PickStepButton';

class PickStepButtonsGroup extends Component {
  render() {
    const { wfProcessId, activityId, lineId, steps } = this.props;
    return (
      <div className="steps-container">
        {steps.length > 0 &&
          steps.map((stepItem) => {
            return (
              <PickStepButton
                key={stepItem.pickingStepId}
                wfProcessId={wfProcessId}
                activityId={activityId}
                lineId={lineId}
                stepId={stepItem.pickingStepId}
                {...stepItem}
              />
            );
          })}
      </div>
    );
  }
}

PickStepButtonsGroup.propTypes = {
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  steps: PropTypes.array.isRequired,
};

export default PickStepButtonsGroup;
