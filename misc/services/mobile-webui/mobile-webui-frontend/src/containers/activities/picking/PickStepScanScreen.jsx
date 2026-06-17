import React, { useCallback } from 'react';
import { shallowEqual, useDispatch, useSelector } from 'react-redux';

import { trl } from '../../../utils/translations';
import {
  getActivityById,
  getLineById,
  getQtyRejectedReasonsFromActivity,
  getStepById,
} from '../../../reducers/wfProcesses';
import { toastError } from '../../../utils/toast';
import { getPickFromForStep, getQtyToPickForStep } from '../../../utils/picking';

import ScanHUAndGetQtyComponent from '../../../components/ScanHUAndGetQtyComponent';
import Spinner from '../../../components/Spinner';
import { toQRCodeString } from '../../../utils/qrCode/hu';
import { toNumberOrZero } from '../../../utils/numbers';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { pickingLineScreenLocation, pickingStepScreenLocation } from '../../../routes/picking';
import { postStepPickedThunk } from '../../../apps/picking/redux/postStepPickedThunk';
import { useAvailablePickingTargets } from '../../../api/picking';
import { PickingTargetType } from '../../../constants/PickingTargetType';

const PickStepScanScreen = () => {
  const { history, wfProcessId, activityId, lineId, stepId, altStepId } = useScreenDefinition({
    captionKey: 'activities.picking.scanQRCode',
    back: pickingStepScreenLocation,
  });

  const { eligibleQRCode, qtyToPick, uom, qtyRejectedReasons, qtyRemainingToPick, isShowPromptWhenOverPicking } =
    useSelector(
      (state) => getPropsFromState({ state, wfProcessId, activityId, lineId, stepId, altStepId }),
      shallowEqual
    );

  // GRAI Flow-Through: when GRAI scanning is required for this job's customer, ScanHUAndGetQtyComponent
  // auto-invokes the inline GRAI capture after qty entry (non-skippable) and reports the captured codes
  // (setGrais/graiCodes) on the same onResult — so the whole pick goes out as ONE atomic event. When it
  // is not required the flow is unchanged.
  const { graiScanEnabled, isTargetsLoading } = useAvailablePickingTargets({
    wfProcessId,
    lineId,
    type: PickingTargetType.TU,
  });

  const getConfirmationPromptForQty = useCallback(
    (qtyInput) => {
      if (qtyRemainingToPick !== undefined && toNumberOrZero(qtyInput) > qtyRemainingToPick) {
        return trl('activities.picking.overPickConfirmationPrompt');
      }
      return undefined;
    },
    [qtyRemainingToPick]
  );

  const dispatch = useDispatch();

  const onResult = ({ qty = 0, reason = null, scannedBarcode = null, setGrais, graiCodes }) => {
    const qtyRejected = qtyToPick - qty;

    return dispatch(
      postStepPickedThunk({
        history,
        wfProcessId,
        activityId,
        lineId,
        stepId,
        huQRCode: scannedBarcode,
        qtyPicked: qty,
        qtyRejectedReasonCode: reason,
        qtyRejected,
        setGrais,
        graiCodes,
      })
    )
      .then(({ isPickingJobCompleted }) => isPickingJobCompleted && history.goTo(pickingLineScreenLocation)) // go to picking line screen
      .catch((axiosError) => toastError({ axiosError }));
  };

  // Block qty entry until we know whether GRAI capture is required: if the operator confirmed the qty
  // while graiScanEnabled was still defaulting to false (the targets GET in flight), a GRAI-required
  // pick would be sent without GRAIs.
  if (isTargetsLoading) {
    return <Spinner />;
  }

  return (
    <ScanHUAndGetQtyComponent
      eligibleBarcode={eligibleQRCode}
      qtyTargetCaption={trl('general.QtyToPick')}
      qtyMax={qtyToPick}
      qtyTarget={qtyToPick}
      uom={uom}
      qtyRejectedReasons={qtyRejectedReasons}
      graiScanEnabled={graiScanEnabled}
      //
      getConfirmationPromptForQty={isShowPromptWhenOverPicking ? getConfirmationPromptForQty : undefined}
      onResult={onResult}
    />
  );
};

const getPropsFromState = ({ state, wfProcessId, activityId, lineId, stepId, altStepId }) => {
  const activity = getActivityById(state, wfProcessId, activityId);
  const qtyRejectedReasons = getQtyRejectedReasonsFromActivity(activity);

  const lineProps = getLineById(state, wfProcessId, activityId, lineId);
  const stepProps = getStepById(state, wfProcessId, activityId, lineId, stepId);
  const eligibleQRCode = toQRCodeString(getPickFromForStep({ stepProps, altStepId }).huQRCode);
  const qtyToPick = getQtyToPickForStep({ stepProps, altStepId });

  return {
    eligibleQRCode,
    qtyToPick,
    uom: stepProps.uom,
    qtyRejectedReasons,
    qtyRemainingToPick: lineProps.qtyRemainingToPick,
    isShowPromptWhenOverPicking: activity?.dataStored?.isShowPromptWhenOverPicking,
  };
};

export default PickStepScanScreen;
