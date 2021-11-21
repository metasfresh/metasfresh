import React from 'react';
import PropTypes from 'prop-types';

import StepButton from './DistributionStepButton';

const DistributionLineScreen = (props) => {
  const { wfProcessId, activityId, lineId, steps } = props;

  return (
    <div className="pt-2 section lines-screen-container">
      <div className="steps-container">
        {steps.length > 0 &&
          steps.map((stepItem, idx) => {
            console.log('stepItem: ', stepItem);
            return (
              <StepButton
                key={idx}
                wfProcessId={wfProcessId}
                activityId={activityId}
                lineId={lineId}
                stepId={stepItem.id}
                productName={stepItem.productName}
                pickFromLocator={stepItem.pickFromLocator}
                pickFromHU={stepItem.pickFromHU}
                uom={stepItem.uom}
                qtyPicked={stepItem.qtyPicked}
                qtyToMove={stepItem.qtyToMove}
                completeStatus={stepItem.completeStatus}
              />
            );
          })}
      </div>
    </div>
  );
};

DistributionLineScreen.propTypes = {
  //
  // Props
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  steps: PropTypes.array.isRequired,
};

export default DistributionLineScreen;
