import React from 'react';
import PropTypes from 'prop-types';

import PickStepButton from './PickStepButton';

const PickAlternatives = ({
  applicationId,
  wfProcessId,
  activityId,
  lineId,
  stepId,
  pickFromAlternatives,
  uom,
  disabled,
}) => {
  return (
    <>
      {pickFromAlternatives &&
        Object.values(pickFromAlternatives)
          .filter((pickFromAlternative) => pickFromAlternative.isDisplayed)
          .map((pickFromAlternative) => {
            return (
              <PickStepButton
                key={pickFromAlternative.alternativeId}
                disabled={disabled}
                applicationId={applicationId}
                wfProcessId={wfProcessId}
                activityId={activityId}
                lineId={lineId}
                stepId={stepId}
                altStepId={pickFromAlternative.alternativeId}
                //
                uom={uom}
                qtyToPick={pickFromAlternative.qtyToPick}
                pickFrom={pickFromAlternative}
              />
            );
          })}
    </>
  );
};

PickAlternatives.propTypes = {
  applicationId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  stepId: PropTypes.string.isRequired,
  pickFromAlternatives: PropTypes.object.isRequired,
  uom: PropTypes.string.isRequired,
  disabled: PropTypes.bool,
};

export default PickAlternatives;
