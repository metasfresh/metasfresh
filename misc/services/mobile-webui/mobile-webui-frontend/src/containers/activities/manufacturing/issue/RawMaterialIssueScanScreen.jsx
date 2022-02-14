import React from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';

import { trl } from '../../../../utils/translations';
import { toastError } from '../../../../utils/toast';
import { getActivityById, getQtyRejectedReasonsFromActivity, getStepById } from '../../../../reducers/wfProcesses';
import { updateManufacturingIssue, updateManufacturingIssueQty } from '../../../../actions/ManufacturingActions';

import ScanHUAndGetQtyComponent from '../../../../components/ScanHUAndGetQtyComponent';
import { toQRCodeString } from '../../../../utils/huQRCodes';

const RawMaterialIssueScanScreen = () => {
  const {
    params: { workflowId: wfProcessId, activityId, lineId, stepId },
  } = useRouteMatch();

  const { huQRCode, qtyToIssue, uom } = useSelector((state) =>
    getStepById(state, wfProcessId, activityId, lineId, stepId)
  );

  const qtyRejectedReasons = useSelector((state) => {
    const activity = getActivityById(state, wfProcessId, activityId);
    return getQtyRejectedReasonsFromActivity(activity);
  });

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
      // Callbacks:
      onResult={onResult}
    />
  );
};

export default RawMaterialIssueScanScreen;
