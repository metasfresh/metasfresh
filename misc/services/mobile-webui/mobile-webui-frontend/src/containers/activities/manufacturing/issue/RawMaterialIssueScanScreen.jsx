import React, { useEffect } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import counterpart from 'counterpart';

import { toastError } from '../../../../utils/toast';
import { manufacturingScanScreenLocation } from '../../../../routes/manufacturing_issue';
import { getStepById } from '../../../../reducers/wfProcesses_status';
import { pushHeaderEntry } from '../../../../actions/HeaderActions';
import { updateManufacturingIssue, updateManufacturingIssueQty } from '../../../../actions/ManufacturingActions';

import ScanHUAndGetQtyComponent from '../../ScanHUAndGetQtyComponent';

const RawMaterialIssueScanScreen = () => {
  const {
    params: { applicationId, workflowId: wfProcessId, activityId, lineId, stepId },
  } = useRouteMatch();

  console.log('RawMaterialIssueScanScreen', { wfProcessId, activityId, lineId, stepId });

  const { huBarcode, qtyToIssue, uom } = useSelector((state) =>
    getStepById(state, wfProcessId, activityId, lineId, stepId)
  );

  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(
      pushHeaderEntry({
        location: manufacturingScanScreenLocation({ applicationId, wfProcessId, activityId, lineId, stepId }),
        values: [
          {
            caption: counterpart.translate('general.Barcode'),
            value: huBarcode,
          },
          {
            caption: counterpart.translate('activities.mfg.issues.qtyToIssue'),
            value: qtyToIssue + ' ' + uom,
          },
        ],
      })
    );
  });

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
      qtyCaption={counterpart.translate('general.QtyToPick')}
      qtyTarget={qtyToIssue}
      qtyInitial={qtyToIssue}
      uom={uom}
      // Callbacks:
      onResult={onResult}
    />
  );
};

export default RawMaterialIssueScanScreen;
