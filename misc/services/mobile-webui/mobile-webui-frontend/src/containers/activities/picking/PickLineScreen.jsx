import React from 'react';
import PropTypes from 'prop-types';
import PickStepButton from './PickStepButton';

const normalizePickFromAlternatives = (pickFromAlternatives) => {
  let isArrayPFA = Array.isArray(pickFromAlternatives);
  let normalizedAlts = {};
  if (isArrayPFA) {
    for (let item of pickFromAlternatives) {
      normalizedAlts[item.id] = item;
    }
  }
  return isArrayPFA ? normalizedAlts : pickFromAlternatives;
};

const PickLineScreen = (props) => {
  const { wfProcessId, activityId, lineId, steps } = props;

  return (
    <div className="pt-2 section lines-screen-container">
      <div className="steps-container">
        {steps.length > 0 &&
          steps.map((stepItem, idx) => {
            let pickFromAlternatives = normalizePickFromAlternatives(stepItem.pickFromAlternatives);
            return (
              <PickStepButton
                key={idx}
                wfProcessId={wfProcessId}
                activityId={activityId}
                lineId={lineId}
                stepId={stepItem.pickingStepId}
                pickFromAlternatives={pickFromAlternatives}
                {...stepItem}
              />
            );
          })}
      </div>
    </div>
  );
};

PickLineScreen.propTypes = {
  //
  // Props
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  steps: PropTypes.array.isRequired,
};

export default PickLineScreen;
