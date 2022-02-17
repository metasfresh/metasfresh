import React from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';

import { trl } from '../../../../utils/translations';
import { toastError } from '../../../../utils/toast';
import {
  getActivityById,
  getQtyRejectedReasonsFromActivity,
  getScaleDeviceFromActivity,
  getStepByIdFromActivity,
} from '../../../../reducers/wfProcesses';
import { updateManufacturingIssue, updateManufacturingIssueQty } from '../../../../actions/ManufacturingActions';

import ScanHUAndGetQtyComponent from '../../../../components/ScanHUAndGetQtyComponent';
import { toQRCodeString } from '../../../../utils/huQRCodes';

const RawMaterialIssueScanScreen = () => {
  const {
    params: { workflowId: wfProcessId, activityId, lineId, stepId },
  } = useRouteMatch();

  const { huQRCode, qtyToIssue, uom, qtyRejectedReasons, scaleDevice } = useSelector((state) =>
    getPropsFromState({ state, wfProcessId, activityId, lineId, stepId })
  );

  const dispatch = useDispatch();
  const history = useHistory();
  const onResult = ({ qty = 0, reason = null }) => {
    dispatch(
      updateManufacturingIssueQty({
        wfProcessId,
        activityId,
        lineId,
        stepId,
        qtyPicked: qty,
        qtyRejectedReasonCode: reason,
      })
    );
    dispatch(updateManufacturingIssue({ wfProcessId, activityId, lineId, stepId }))
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
      scaleDevice={scaleDevice}
      // Callbacks:
      onResult={onResult}
    />
  );
};

const getPropsFromState = ({ state, wfProcessId, activityId, lineId, stepId }) => {
  const activity = getActivityById(state, wfProcessId, activityId);
  const step = getStepByIdFromActivity(activity, lineId, stepId);

  return {
    huQRCode: step.huQRCode,
    qtyToIssue: step.qtyToIssue,
    uom: step.uom,
    qtyRejectedReasons: getQtyRejectedReasonsFromActivity(activity),
    scaleDevice: getScaleDeviceFromActivity(activity),
  };
};

export default RawMaterialIssueScanScreen;
