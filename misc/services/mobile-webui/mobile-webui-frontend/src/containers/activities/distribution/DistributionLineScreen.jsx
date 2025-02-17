import React from 'react';
import { shallowEqual, useSelector } from 'react-redux';
import { useRouteMatch } from 'react-router-dom';

import { trl } from '../../../utils/translations';
import { getLineById, getStepsArrayFromLine } from '../../../reducers/wfProcesses';

import DistributionStepButton from './DistributionStepButton';
import { formatQtyToHumanReadableStr } from '../../../utils/qtys';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import { distributionLinePickFromScreenLocation } from '../../../routes/distribution';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { getWFProcessScreenLocation } from '../../../routes/workflow_locations';

const DistributionLineScreen = () => {
  const { history, applicationId, wfProcessId, activityId, lineId } = useDistributionScreenDefinition({
    screenId: 'DistributionLineScreen',
    back: getWFProcessScreenLocation,
  });

  const { steps, allowPickingAnyHU } = useDistributionLineProps({
    wfProcessId,
    activityId,
    lineId,
  });

  const onScanButtonClick = () => {
    history.push(distributionLinePickFromScreenLocation({ applicationId, wfProcessId, activityId, lineId }));
  };

  return (
    <div className="section pt-2">
      <div className="buttons">
        {allowPickingAnyHU && <ButtonWithIndicator captionKey="general.scanQRCode" onClick={onScanButtonClick} />}
        {steps.length > 0 &&
          steps.map((stepItem, idx) => {
            return (
              <DistributionStepButton
                key={idx}
                testId={`step-${idx + 1}-button`}
                applicationId={applicationId}
                wfProcessId={wfProcessId}
                activityId={activityId}
                lineId={lineId}
                stepId={stepItem.id}
                productName={stepItem.productName}
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

//
//
//
//
//

export const useDistributionLineProps = ({ wfProcessId, activityId, lineId }) => {
  return useSelector((state) => {
    const line = getLineById(state, wfProcessId, activityId, lineId);
    return {
      ...line,
      steps: getStepsArrayFromLine(line),
    };
  }, shallowEqual);
};

//
//
//
//
//

export const useDistributionScreenDefinition = ({ screenId, captionKey, back } = {}) => {
  const {
    params: { workflowId: wfProcessId, activityId, lineId },
  } = useRouteMatch();

  const { productName, uom, qtyToMove } = useDistributionLineProps({ wfProcessId, activityId, lineId });

  return useScreenDefinition({
    screenId,
    captionKey,
    back,
    values: [
      {
        caption: trl('general.Product'),
        value: productName,
        bold: true,
      },
      {
        caption: trl('general.QtyToMove'),
        value: formatQtyToHumanReadableStr({ qty: qtyToMove, uom }),
        bold: true,
      },
    ],
  });
};

//
//
//
//
//

export default DistributionLineScreen;
