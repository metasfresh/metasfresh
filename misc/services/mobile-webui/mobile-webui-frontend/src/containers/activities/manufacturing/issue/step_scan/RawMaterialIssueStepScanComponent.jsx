import React, { useCallback } from 'react';
import { useDispatch, useSelector } from 'react-redux';

import { toastError } from '../../../../../utils/toast';
import { updateManufacturingIssue } from '../../../../../actions/ManufacturingActions';

import ScanHUAndGetQtyComponent from '../../../../../components/ScanHUAndGetQtyComponent';
import { toQRCodeString } from '../../../../../utils/qrCode/hu';
import { computeStepScanPropsFromActivity } from './computeStepScanPropsFromActivity';
import { computeStepScanUserInfoQtys } from './computeStepScanUserInfoQtys';
import PropTypes from 'prop-types';
import {
  getActivityById,
  getStepByIdFromActivity,
  QTY_REJECTED_REASON_EMPTIED_KEY,
} from '../../../../../reducers/wfProcesses';
import { trl } from '../../../../../utils/translations';
import { formatQtyToHumanReadableStr } from '../../../../../utils/qtys';
import { useBooleanSetting } from '../../../../../reducers/settings';
import { useMobileNavigation } from '../../../../../hooks/useMobileNavigation';
import {
  getNonIssuedStepByHuIdFromActivity,
  getNonIssuedStepByQRCodeFromActivity,
} from '../../../../../reducers/wfProcesses/manufacturing';

const RawMaterialIssueStepScanComponent = ({ wfProcessId, activityId, lineId, stepId }) => {
  console.log('RawMaterialIssueStepScanComponent', { wfProcessId, activityId, lineId, stepId });

  // if qtyInput.ProcessedQtyIsStillOnScale === true, it means the already issued qty for the selected line is still on the scale,
  // the process will know to subtract it before issuing again.
  const isProcessedQtyStillOnScale = useBooleanSetting('qtyInput.ProcessedQtyIsStillOnScale');

  const activity = useSelector((state) => getActivityById(state, wfProcessId, activityId));
  const isConfirmEmptyingHU = activity?.dataStored?.isConfirmEmptyingHU;

  const eligibleBarcode =
    stepId != null ? toQRCodeString(getStepByIdFromActivity(activity, lineId, stepId).huQRCode) : null;

  const resolveScannedBarcode = (scannedBarcode, huId) => {
    let step;
    if (huId) {
      step = getNonIssuedStepByHuIdFromActivity({ activity, lineId, huId });
    } else {
      step = getNonIssuedStepByQRCodeFromActivity({ activity, lineId, qrCode: scannedBarcode });
    }

    if (!step) {
      throw trl('activities.picking.notEligibleHUBarcode');
    }

    const {
      uom,
      qtyToIssueTarget,
      qtyToIssueMax,
      lineQtyToIssue,
      lineQtyToIssueTolerance,
      lineQtyToIssueRemaining,
      lineQtyIssued,
      isWeightable,
      isIssueWholeHU,
      qtyRejectedReasons,
      scaleDevice,
      scaleTolerance,
      qtyHUCapacity,
      qtyAlreadyOnScale,
    } = computeStepScanPropsFromActivity({ activity, lineId, stepId: step.id, isProcessedQtyStillOnScale });

    return {
      //
      // Props needed for ScanHUAndGetQtyComponent:
      userInfo: computeStepScanUserInfoQtys({
        uom,
        lineQtyToIssue,
        lineQtyToIssueTolerance,
        lineQtyToIssueRemaining,
      }),
      qtyTarget: qtyToIssueTarget,
      qtyMax: qtyToIssueMax,
      uom,
      qtyRejectedReasons,
      scaleDevice,
      scaleTolerance,
      lineQtyToIssue,
      lineQtyIssued,
      qtyHUCapacity,
      qtyAlreadyOnScale,
      //
      // Props which are needed by `onResult` function (see below):
      stepId: step.id,
      isWeightable,
      isIssueWholeHU,
    };
  };

  // "Empty (auto. inventory)" write-off: on `isConfirmEmptyingHU`, confirm before booking, naming the
  // leftover quantity and its UOM. Declining leaves the operator on this dialog and posts nothing.
  const getEmptyingConfirmationPrompt = useCallback((qtyInput, { qtyRejected, rejectedReason, uom } = {}) => {
    if (rejectedReason !== QTY_REJECTED_REASON_EMPTIED_KEY || !(qtyRejected > 0)) {
      return undefined;
    }
    return trl('activities.manufacturing.confirmEmptyHUPrompt', {
      qty: formatQtyToHumanReadableStr({ qty: qtyRejected, uom }),
    });
  }, []);

  const dispatch = useDispatch();
  const history = useMobileNavigation();
  const onResult = ({ qty = 0, qtyRejected = 0, reason = null, resolvedBarcodeData }) => {
    console.log('onResult', { qty, qtyRejected, reason, resolvedBarcodeData });

    const stepId = resolvedBarcodeData.stepId;
    const isWeightable = !!resolvedBarcodeData.isWeightable;
    // The whole-HU decision must be the one made when the step was offered (target vs the HU's own
    // capacity — computeStepScanPropsFromActivity.js), not recomputed from the qty the operator
    // actually typed: typing less than capacity is exactly the "issue a partial amount with a
    // reason" case (not found / damaged / empty), and recomputing from the entered qty always
    // evaluates false there, silently dropping qtyRejected/qtyRejectedReasonCode before they reach
    // the backend.
    const isIssueWholeHU = !!resolvedBarcodeData.isIssueWholeHU;

    return dispatch(
      updateManufacturingIssue({
        wfProcessId,
        activityId,
        lineId,
        stepId,
        huWeightGrossBeforeIssue: isWeightable && isIssueWholeHU ? qty : null,
        qtyIssued: qty,
        qtyRejected: isIssueWholeHU ? qtyRejected : 0,
        qtyRejectedReasonCode: isIssueWholeHU ? reason : null,
      })
    )
      .then(() => history.goBack())
      .catch((axiosError) => toastError({ axiosError }));
  };

  return (
    <ScanHUAndGetQtyComponent
      eligibleBarcode={eligibleBarcode}
      resolveScannedBarcode={resolveScannedBarcode}
      useHUScanner={true}
      //
      // userInfo={userInfo}
      // qtyTarget={qtyToIssueTarget}
      // qtyMax={qtyToIssueMax}
      // uom={uom}
      // qtyRejectedReasons={qtyRejectedReasons}
      // scaleDevice={scaleDevice}
      //
      // Callbacks:
      getConfirmationPromptForQty={isConfirmEmptyingHU ? getEmptyingConfirmationPrompt : undefined}
      onResult={onResult}
    />
  );
};

RawMaterialIssueStepScanComponent.propTypes = {
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  stepId: PropTypes.string,
};

export default RawMaterialIssueStepScanComponent;
