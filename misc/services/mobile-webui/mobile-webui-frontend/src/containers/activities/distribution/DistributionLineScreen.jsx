import React from 'react';
import { shallowEqual, useSelector } from 'react-redux';

import { trl } from '../../../utils/translations';
import { getLineById, getStepsArrayFromLine } from '../../../reducers/wfProcesses';

import DistributionStepButton from './DistributionStepButton';
import { formatQtyToHumanReadableStr } from '../../../utils/qtys';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import { distributionPickFromScreenLocation } from '../../../routes/distribution';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { getWFProcessScreenLocation } from '../../../routes/workflow_locations';
import { useMobileLocation } from '../../../hooks/useMobileLocation';
import { computeQtyToPickRemaining } from '../../../reducers/wfProcesses/distribution/computeQtyToPickRemaining';
import { useWFProcessHeaders } from '../../wfProcessScreen/WFProcessScreen';

const DistributionLineScreen = () => {
  const { history, applicationId, wfProcessId, activityId, lineId } = useDistributionLineScreenDefinition({
    screenId: 'DistributionLineScreen',
    back: getWFProcessScreenLocation,
  });

  const { steps, allowPickingAnyHU } = useDistributionLineProps({
    wfProcessId,
    activityId,
    lineId,
  });

  const onScanButtonClick = () => {
    history.push(distributionPickFromScreenLocation({ applicationId, wfProcessId, activityId, lineId }));
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

const useDistributionLineProps = ({ wfProcessId, activityId, lineId }) => {
  return useSelector((state) => {
    if (!lineId) return {};

    const line = getLineById(state, wfProcessId, activityId, lineId);
    const stepsArray = getStepsArrayFromLine(line);
    return {
      ...line,
      qtyToPickRemaining: computeQtyToPickRemaining({ line }),
      steps: stepsArray,
    };
  }, shallowEqual);
};

//
//
//
//
//

const useDistributionLineScreenDefinition = ({ screenId, captionKey, back } = {}) => {
  const { wfProcessId, activityId, lineId } = useMobileLocation();
  const headers = useDistributionLineHeaders({ wfProcessId, activityId, lineId });
  return useScreenDefinition({
    screenId,
    captionKey,
    back,
    values: headers,
  });
};

export const useDistributionLineHeaders = ({ wfProcessId, activityId, lineId }) => {
  const { productName, uom, qtyToMove, pickFromLocator } = useDistributionLineProps({
    wfProcessId,
    activityId,
    lineId,
  });
  const jobHeaders = useWFProcessHeaders({ wfProcessId });
  const headers = [...jobHeaders];
  if (productName) {
    headers.push({
      id: 'ProductValueAndName', // shall match the de.metas.distribution.mobileui.config.DistributionJobCaptionField#getCode
      caption: trl('general.Product'),
      value: productName,
      bold: true,
    });
  }
  if (qtyToMove != null) {
    headers.push({
      id: 'Qty', // shall match the de.metas.distribution.mobileui.config.DistributionJobCaptionField#getCode
      caption: trl('general.QtyToMove'),
      value: formatQtyToHumanReadableStr({ qty: qtyToMove, uom }),
      bold: true,
    });
  }
  if (pickFromLocator?.caption) {
    headers.push({
      id: 'LocatorFrom', // shall match the de.metas.distribution.mobileui.config.DistributionJobCaptionField#getCode
      caption: trl('general.LocatorFrom'),
      value: pickFromLocator?.caption,
      bold: true,
    });
  }

  return headers;
};

//
//
//
//
//

export default DistributionLineScreen;
