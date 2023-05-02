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

const RawMaterialIssueScanScreen = () => {
  const {
    params: { workflowId: wfProcessId, activityId, lineId, stepId },
  } = useRouteMatch();

  const { huQRCode, qtyToIssue, uom, qtyRejectedReasons, weightable, scaleDevice } = useSelector((state) =>
    getPropsFromState({ state, wfProcessId, activityId, lineId, stepId })
  );

  const dispatch = useDispatch();
  const history = useHistory();
  const onResult = ({ qty = 0, reason = null }) => {
    dispatch(
      updateManufacturingIssue({
        wfProcessId,
        activityId,
        lineId,
        stepId,
        qtyIssued: qty,
        qtyRejected: qtyToIssue - qty,
        qtyRejectedReasonCode: reason,
      })
    )
      .catch((axiosError) => toastError({ axiosError }))
      .finally(() => history.go(-1));
  };

  return (
    <ScanHUAndGetQtyComponent
      eligibleBarcode={toQRCodeString(huQRCode)}
      qtyCaption={trl('general.QtyToPick')}
      qtyTarget={qtyToIssue}
      qtyInitial={qtyToIssue}
      uom={uom}
      qtyRejectedReasons={qtyRejectedReasons}
      scaleDevice={weightable ? scaleDevice : null}
      // Callbacks:
      onResult={onResult}
    />
  );
};

const getPropsFromState = ({ state, wfProcessId, activityId, lineId, stepId }) => {
  const activity = getActivityById(state, wfProcessId, activityId);
  const line = getLineByIdFromActivity(activity, lineId);
  const step = getStepByIdFromActivity(activity, lineId, stepId);

  console.log('RawMaterialIssueScanScreen.getPropsFromState', { line, step });

  return {
    huQRCode: step.huQRCode,
    qtyToIssue: step.qtyToIssue,
    uom: step.uom,
    weightable: !!line.weightable,
    qtyRejectedReasons: getQtyRejectedReasonsFromActivity(activity),
    scaleDevice: getScaleDeviceFromActivity(activity),
  };
};

export default RawMaterialIssueScanScreen;
