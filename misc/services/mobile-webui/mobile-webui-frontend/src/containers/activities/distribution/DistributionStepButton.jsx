import React from 'react';
import PropTypes from 'prop-types';

import { distributionStepScreenLocation } from '../../../routes/distribution';

import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import ButtonQuantityProp from '../../../components/buttons/ButtonQuantityProp';
import { toQRCodeDisplayable } from '../../../utils/qrCode/hu';
import { useMobileNavigation } from '../../../hooks/useMobileNavigation';

const DistributionStepButton = ({
  testId,
  applicationId,
  wfProcessId,
  activityId,
  lineId,
  stepId,
  pickFromHU,
  uom,
  qtyPicked,
  completeStatus,
  qtyToMove,
}) => {
  const history = useMobileNavigation();
  const handleClick = () => {
    history.push(distributionStepScreenLocation({ applicationId, wfProcessId, activityId, lineId, stepId }));
  };

  return (
    <ButtonWithIndicator
      testId={testId}
      caption={toQRCodeDisplayable(pickFromHU.qrCode)}
      completeStatus={completeStatus}
      onClick={handleClick}
    >
      <ButtonQuantityProp qtyCurrent={qtyPicked} qtyTarget={qtyToMove} uom={uom} applicationId={applicationId} />
    </ButtonWithIndicator>
  );
};

DistributionStepButton.propTypes = {
  testId: PropTypes.string,
  applicationId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  stepId: PropTypes.string.isRequired,
  productName: PropTypes.string.isRequired,
  pickFromHU: PropTypes.object,
  uom: PropTypes.string,
  qtyPicked: PropTypes.number,
  qtyToMove: PropTypes.number.isRequired,
  stepState: PropTypes.object,
  completeStatus: PropTypes.string.isRequired,
};

export default DistributionStepButton;
