import React from 'react';
import { useDispatch, useSelector, useStore } from 'react-redux';

import { toastError } from '../../../../../utils/toast';
import { postManufacturingIssueEventThunk } from '../../../../../actions/ManufacturingActions';
import { updateWFProcess } from '../../../../../actions/WorkflowActions';
import { createIssueScheduleOnTheFly } from '../../../../../api/manufacturing';

import ScanHUAndGetQtyComponent from '../../../../../components/ScanHUAndGetQtyComponent';
import { toQRCodeString } from '../../../../../utils/qrCode/hu';
import { computeStepScanPropsFromActivity } from './computeStepScanPropsFromActivity';
import { computeStepScanUserInfoQtys } from './computeStepScanUserInfoQtys';
import PropTypes from 'prop-types';
import { getActivityById, getStepByIdFromActivity } from '../../../../../reducers/wfProcesses';
import { trl } from '../../../../../utils/translations';
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

  const store = useStore();
  const dispatch = useDispatch();
  const history = useMobileNavigation();

  const eligibleBarcode =
    stepId != null ? toQRCodeString(getStepByIdFromActivity(activity, lineId, stepId).huQRCode) : null;

  const resolveScannedBarcode = async (scannedBarcode, huId) => {
    let step;
    if (huId) {
      step = getNonIssuedStepByHuIdFromActivity({ activity, lineId, huId });
    } else {
      step = getNonIssuedStepByQRCodeFromActivity({ activity, lineId, qrCode: scannedBarcode });
    }

    // If no local step found, try on-the-fly schedule creation via backend
    let freshActivity = activity;
    if (!step) {
      try {
        const wfProcess = await createIssueScheduleOnTheFly({
          wfProcessId,
          huQRCode: scannedBarcode,
        });

        // Update Redux state with the returned WFProcess
        dispatch(updateWFProcess({ wfProcess }));

        // Read fresh activity from the updated Redux store
        freshActivity = getActivityById(store.getState(), wfProcessId, activityId);

        // Re-lookup the step — first try current line, then all lines
        if (huId) {
          step = getNonIssuedStepByHuIdFromActivity({ activity: freshActivity, lineId, huId });
        } else {
          step = getNonIssuedStepByQRCodeFromActivity({ activity: freshActivity, lineId, qrCode: scannedBarcode });
        }

        // If still not found in current line, search all lines
        if (!step) {
          const allLineIds = Object.keys(freshActivity?.dataStored?.lines ?? {});
          for (const lid of allLineIds) {
            const found = huId
              ? getNonIssuedStepByHuIdFromActivity({ activity: freshActivity, lineId: lid, huId })
              : getNonIssuedStepByQRCodeFromActivity({ activity: freshActivity, lineId: lid, qrCode: scannedBarcode });
            if (found) {
              step = found;
              break;
            }
          }
        }
      } catch (e) {
        console.warn('On-the-fly issue schedule creation failed:', e);
        // fall through to throw original error
      }
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
      qtyRejectedReasons,
      scaleDevice,
      scaleTolerance,
      qtyHUCapacity,
      qtyAlreadyOnScale,
    } = computeStepScanPropsFromActivity({
      activity: freshActivity,
      lineId,
      stepId: step.id,
      isProcessedQtyStillOnScale,
    });

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
    };
  };

  const onResult = ({ qty = 0, qtyRejected = 0, reason = null, resolvedBarcodeData }) => {
    console.log('onResult', { qty, qtyRejected, reason, resolvedBarcodeData });

    const stepId = resolvedBarcodeData.stepId;
    const isWeightable = !!resolvedBarcodeData.isWeightable;
    const isIssueWholeHU = qty >= resolvedBarcodeData.qtyHUCapacity;

    return dispatch(
      postManufacturingIssueEventThunk({
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
