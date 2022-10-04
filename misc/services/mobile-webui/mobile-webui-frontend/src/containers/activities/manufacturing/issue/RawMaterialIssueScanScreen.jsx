import React from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';

import { trl } from '../../../../utils/translations';
import { toastError } from '../../../../utils/toast';
import {
  getActivityById,
  getLineByIdFromActivity,
  getQtyRejectedReasonsFromActivity,
  getScaleDeviceFromActivity,
  getStepByIdFromActivity,
} from '../../../../reducers/wfProcesses';
import { updateManufacturingIssue } from '../../../../actions/ManufacturingActions';

import ScanHUAndGetQtyComponent from '../../../../components/ScanHUAndGetQtyComponent';
import { toQRCodeString } from '../../../../utils/huQRCodes';
import { formatQtyToHumanReadable } from '../../../../utils/qtys';

const RawMaterialIssueScanScreen = () => {
  const {
    params: { workflowId: wfProcessId, activityId, lineId, stepId },
  } = useRouteMatch();

  const { huQRCode, uom, userInfo, qtyToIssue, qtyToIssueMax, isIssueWholeStep, qtyRejectedReasons, scaleDevice } =
    useSelector((state) => getPropsFromState({ state, wfProcessId, activityId, lineId, stepId }));

  const dispatch = useDispatch();
  const history = useHistory();
  const onResult = ({ qty = 0, qtyRejected = 0, reason = null }) => {
    dispatch(
      updateManufacturingIssue({
        wfProcessId,
        activityId,
        lineId,
        stepId,
        qtyIssued: qty,
        qtyRejected: isIssueWholeStep ? qtyRejected : 0,
        qtyRejectedReasonCode: isIssueWholeStep ? reason : null,
      })
    )
      .catch((axiosError) => toastError({ axiosError }))
      .finally(() => history.go(-1));
  };

  return (
    <ScanHUAndGetQtyComponent
      eligibleBarcode={toQRCodeString(huQRCode)}
      userInfo={userInfo}
      qtyInitial={qtyToIssue}
      qtyMax={qtyToIssueMax}
      uom={uom}
      qtyRejectedReasons={qtyRejectedReasons}
      scaleDevice={scaleDevice}
      // Callbacks:
      onResult={onResult}
    />
  );
};

const getPropsFromState = ({ state, wfProcessId, activityId, lineId, stepId }) => {
  const activity = getActivityById(state, wfProcessId, activityId);
  const line = getLineByIdFromActivity(activity, lineId);
  const step = getStepByIdFromActivity(activity, lineId, stepId);

  const uom = step.uom;
  const lineQtyToIssue = line.qtyToIssue;
  const lineQtyIssued = line.qtyIssued;
  const lineQtyToIssueTolerancePerc = line.qtyToIssueTolerancePerc;
  const stepQtyToIssue = step.qtyToIssue;
  const isWeightable = !!line.weightable;

  const lineQtyToIssueMax = Math.max(line.qtyToIssueMax, lineQtyToIssue);
  let qtyToIssueMax = Math.max(lineQtyToIssueMax - lineQtyIssued, 0);
  qtyToIssueMax = Math.min(qtyToIssueMax, step.qtyHUCapacity);

  const lineQtyToIssueRemaining = Math.max(lineQtyToIssue - lineQtyIssued, 0);
  const qtyToIssue = Math.min(stepQtyToIssue, lineQtyToIssueRemaining, qtyToIssueMax);

  const isIssueWholeStep = qtyToIssue >= step.qtyHUCapacity;

  const userInfo = [
    {
      caption: trl('general.QtyToPick') + ' (total)', // TODO trl
      value: formatQtyToHumanReadable({ qty: lineQtyToIssue, uom, tolerancePercent: lineQtyToIssueTolerancePerc }),
    },
    {
      caption: trl('general.QtyToPick'),
      value: formatQtyToHumanReadable({ qty: lineQtyToIssueRemaining, uom }),
    },
  ];

  console.log('RawMaterialIssueScanScreen.getPropsFromState', {
    qtyToIssue,
    qtyToIssueMax,
    isIssueWholeStep,
    //
    line,
    step,
    //
    lineQtyToIssueMax,
    lineQtyToIssueRemaining,
    lineQtyToIssue,
    stepQtyToIssue,
  });

  return {
    huQRCode: step.huQRCode,
    uom: step.uom,
    userInfo,
    qtyToIssue: qtyToIssue,
    qtyToIssueMax: qtyToIssueMax,
    isIssueWholeStep,
    qtyRejectedReasons: isIssueWholeStep ? getQtyRejectedReasonsFromActivity(activity) : null,
    scaleDevice: isWeightable ? getScaleDeviceFromActivity(activity) : null,
  };
};

export default RawMaterialIssueScanScreen;
