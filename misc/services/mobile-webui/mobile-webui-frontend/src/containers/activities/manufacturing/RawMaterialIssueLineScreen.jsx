import React from 'react';
import PropTypes from 'prop-types';

import StepButton from './RawMaterialIssueStepButton';

const RawMaterialIssueLineScreen = (props) => {
  const { wfProcessId, activityId, lineId, steps } = props;

  return (
    <div className="pt-2 section lines-screen-container">
      <div className="steps-container">
        {steps.length > 0 &&
          steps.map((stepItem, idx) => {
            return (
              <StepButton
                key={idx}
                wfProcessId={wfProcessId}
                activityId={activityId}
                lineId={lineId}
                stepId={stepItem.id}
                locatorName={stepItem.productName}
                {...stepItem}
              />
            );
          })}
      </div>
    </div>
  );
};

RawMaterialIssueLineScreen.propTypes = {
  //
  // Props
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  steps: PropTypes.array.isRequired,
  lineId: PropTypes.string.isRequired,
};

export default RawMaterialIssueLineScreen;
