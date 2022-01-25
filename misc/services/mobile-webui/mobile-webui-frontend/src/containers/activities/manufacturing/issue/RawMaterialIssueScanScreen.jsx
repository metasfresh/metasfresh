import React, { useEffect } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';

import { trl } from '../../../../utils/translations';
import { toastError } from '../../../../utils/toast';
import { manufacturingScanScreenLocation } from '../../../../routes/manufacturing_issue';
import { getActivityById, getQtyRejectedReasonsFromActivity, getStepById } from '../../../../reducers/wfProcesses';
import { pushHeaderEntry } from '../../../../actions/HeaderActions';
import { updateManufacturingIssue, updateManufacturingIssueQty } from '../../../../actions/ManufacturingActions';

import ScanHUAndGetQtyComponent from '../../../../components/ScanHUAndGetQtyComponent';

const RawMaterialIssueScanScreen = () => {
  const {
    params: { applicationId, workflowId: wfProcessId, activityId, lineId, stepId },
  } = useRouteMatch();

  const { huBarcode, qtyToIssue, uom } = useSelector((state) =>
    getStepById(state, wfProcessId, activityId, lineId, stepId)
  );

  const qtyRejectedReasons = useSelector((state) => {
    const activity = getActivityById(state, wfProcessId, activityId);
    return getQtyRejectedReasonsFromActivity(activity);
  });

  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(
      pushHeaderEntry({
        location: manufacturingScanScreenLocation({ applicationId, wfProcessId, activityId, lineId, stepId }),
        values: [
          {
            caption: trl('general.Barcode'),
            value: huBarcode,
          },
          {
            caption: trl('activities.mfg.issues.qtyToIssue'),
            value: qtyToIssue + ' ' + uom,
          },
        ],
      })
    );
  }, []);

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
      eligibleBarcode={huBarcode}
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
