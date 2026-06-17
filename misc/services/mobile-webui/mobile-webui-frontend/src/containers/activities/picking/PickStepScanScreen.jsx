import React, { useCallback, useState } from 'react';
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
import { getAssignedGrais, getExtraGrais, mergeGraiArrays } from '../../../utils/grai';

import ScanHUAndGetQtyComponent from '../../../components/ScanHUAndGetQtyComponent';
import GraiCapturePanel from '../../../components/GraiCapturePanel';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
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

  // GRAI Flow-Through: when GRAI scanning is required for this job's customer, the pick is NOT sent
  // when the qty is confirmed; instead an inline, non-skippable GRAI capture is auto-invoked and the
  // whole pick (qty + the captured GRAIs) is sent in ONE atomic event. When it is not required the
  // flow is unchanged — qty confirm sends the pick directly.
  const { graiScanEnabled } = useAvailablePickingTargets({ wfProcessId, lineId, type: PickingTargetType.TU });

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

  // null while entering the qty; once qty is confirmed and GRAI capture is required, holds the
  // confirmed pick params awaiting their GRAIs.
  const [pendingPick, setPendingPick] = useState(null);
  const [graiCodes, setGraiCodes] = useState([]);

  const dispatchPick = useCallback(
    (pick, capturedGrais) => {
      const graiFields = capturedGrais != null ? { setGrais: true, graiCodes: capturedGrais } : {};
      return dispatch(
        postStepPickedThunk({
          history,
          wfProcessId,
          activityId,
          lineId,
          stepId,
          huQRCode: pick.scannedBarcode,
          qtyPicked: pick.qty,
          qtyRejectedReasonCode: pick.reason,
          qtyRejected: pick.qtyRejected,
          ...graiFields,
        })
      )
        .then(({ isPickingJobCompleted }) => {
          setPendingPick(null);
          setGraiCodes([]);
          if (isPickingJobCompleted) history.goTo(pickingLineScreenLocation); // go to picking line screen
        })
        .catch((axiosError) => toastError({ axiosError }));
    },
    [dispatch, history, wfProcessId, activityId, lineId, stepId]
  );

  const onResult = ({ qty = 0, reason = null, scannedBarcode = null }) => {
    const pick = { qty, reason, scannedBarcode, qtyRejected: qtyToPick - qty };
    if (graiScanEnabled && qty > 0) {
      setGraiCodes([]);
      setPendingPick(pick); // auto-invoke inline GRAI capture before sending the pick
      return;
    }
    dispatchPick(pick, null);
  };

  if (pendingPick) {
    const expectedCount = pendingPick.qty;
    const assignedGrais = getAssignedGrais(graiCodes, expectedCount);
    const extraGrais = getExtraGrais(graiCodes, expectedCount);
    // Save is enabled only when exactly N (= picked TUs) GRAIs are captured, with no extras.
    const canSave = graiCodes.length === expectedCount;

    return (
      <GraiCapturePanel
        graiCodes={graiCodes}
        assignedGrais={assignedGrais}
        extraGrais={extraGrais}
        expectedCount={expectedCount}
        countKey="activities.picking.graiScan.count"
        countExtraKey="activities.picking.graiScan.countExtra"
        clearAllButtonKey="activities.picking.graiScan.clearAll.buttonCaption"
        clearAllConfirmKey="activities.picking.graiScan.clearAll.confirmQuestion"
        onAddGrais={(newGrais) => setGraiCodes((prev) => mergeGraiArrays(prev, newGrais))}
        onRemoveGrai={(grai) => setGraiCodes((prev) => prev.filter((g) => g !== grai))}
        onClearAll={() => setGraiCodes([])}
      >
        <ButtonWithIndicator
          captionKey="activities.picking.graiScan.save.buttonCaption"
          testId="grai-save-button"
          disabled={!canSave}
          onClick={() => dispatchPick(pendingPick, assignedGrais)}
          additionalCssClass="action-button"
        />
      </GraiCapturePanel>
    );
  }

  return (
    <ScanHUAndGetQtyComponent
      eligibleBarcode={eligibleQRCode}
      qtyTargetCaption={trl('general.QtyToPick')}
      qtyMax={qtyToPick}
      qtyTarget={qtyToPick}
      uom={uom}
      qtyRejectedReasons={qtyRejectedReasons}
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
