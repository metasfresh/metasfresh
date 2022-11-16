import React from 'react';
import PropTypes from 'prop-types';

import { pickingStepScreenLocation } from '../../../routes/picking';
import { computePickFromStatus } from '../../../reducers/wfProcesses/picking';
import PickAlternatives from './PickAlternatives';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import ButtonQuantityProp from '../../../components/buttons/ButtonQuantityProp';
import { useHistory } from 'react-router-dom';

const PickStepButton = ({
  applicationId,
  wfProcessId,
  activityId,
  lineId,
  stepId,
  altStepId,
  pickFromAlternatives,
  uom,
  qtyToPick,
  pickFrom,
}) => {
  const history = useHistory();
  const handleClick = () => {
    history.push(pickingStepScreenLocation({ applicationId, wfProcessId, activityId, lineId, stepId, altStepId }));
  };

  const isAlternative = !!altStepId;
  const completeStatus = computePickFromStatus(pickFrom);

  return (
    <>
      <ButtonWithIndicator
        caption={(isAlternative ? 'ALT:' : '') + pickFrom.locatorName}
        completeStatus={completeStatus}
        onClick={handleClick}
      >
        <ButtonQuantityProp
          qtyCurrent={pickFrom.qtyPicked}
          qtyTarget={qtyToPick}
          uom={uom}
          applicationId={applicationId}
        />
      </ButtonWithIndicator>
      {pickFromAlternatives && (
        <PickAlternatives
          applicationId={applicationId}
          wfProcessId={wfProcessId}
          activityId={activityId}
          lineId={lineId}
          stepId={stepId}
          pickFromAlternatives={pickFromAlternatives}
          uom={uom}
        />
      )}
    </>
  );
};

PickStepButton.propTypes = {
  applicationId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  stepId: PropTypes.string.isRequired,
  pickFrom: PropTypes.object.isRequired,
  qtyToPick: PropTypes.number.isRequired,
  altStepId: PropTypes.string,
  pickFromAlternatives: PropTypes.object,
  uom: PropTypes.string.isRequired,
};

export default PickStepButton;
