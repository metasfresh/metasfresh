import React from 'react';
import { useDispatch, useSelector } from 'react-redux';

import { trl } from '../../../utils/translations';
import { toastError } from '../../../utils/toast';
import {
  getActivityById,
  getQtyRejectedReasonsFromActivity,
  getStepByIdFromActivity,
} from '../../../reducers/wfProcesses';
import { postDistributionPickFrom } from '../../../api/distribution';

import ScanHUAndGetQtyComponent from '../../../components/ScanHUAndGetQtyComponent';
import { toQRCodeString } from '../../../utils/qrCode/hu';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { distributionLineScreenLocation, distributionStepScreenLocation } from '../../../routes/distribution';

const DistributionStepPickFromScreen = () => {
  const { history, wfProcessId, activityId, lineId, stepId } = useScreenDefinition({
    screenId: 'DistributionStepPickFromScreen',
    captionKey: 'activities.distribution.scanHU',
    back: distributionStepScreenLocation,
  });

  const { huQRCode } = useSelector((state) => getPropsFromState({ state, wfProcessId, activityId, lineId, stepId }));

  const dispatch = useDispatch();

  const onResult = ({ scannedBarcode }) => {
    postDistributionPickFrom({
      wfProcessId,
      activityId,
      stepId,
      pickFrom: {
        qrCode: toQRCodeString(scannedBarcode),
      },
    })
      .then((wfProcess) => {
        dispatch(updateWFProcess({ wfProcess }));
        history.goTo(distributionLineScreenLocation);
      })
      .catch((axiosError) => toastError({ axiosError }));
  };

  return (
    <ScanHUAndGetQtyComponent
      eligibleBarcode={toQRCodeString(huQRCode)}
      qtyTargetCaption={trl('general.QtyToMove')}
      onResult={onResult}
    />
  );
};

const getPropsFromState = ({ state, wfProcessId, activityId, lineId, stepId }) => {
  const activity = getActivityById(state, wfProcessId, activityId);
  const step = getStepByIdFromActivity(activity, lineId, stepId);

  return {
    huQRCode: step.pickFromHU.qrCode,
    qtyToMove: step.qtyToMove,
    uom: step.uom,
    qtyRejectedReasons: getQtyRejectedReasonsFromActivity(activity),
  };
};

export default DistributionStepPickFromScreen;
