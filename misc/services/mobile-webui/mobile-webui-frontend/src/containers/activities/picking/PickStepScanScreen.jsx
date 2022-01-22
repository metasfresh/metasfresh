import React from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { shallowEqual, useDispatch, useSelector } from 'react-redux';
import counterpart from 'counterpart';

import { getActivityById, getQtyRejectedReasonsFromActivity, getStepById } from '../../../reducers/wfProcesses_status';
import { toastError } from '../../../utils/toast';
import { getPickFrom, getQtyToPick } from '../../../utils/picking';
import { postStepPicked } from '../../../api/picking';
import { updatePickingStepQty } from '../../../actions/PickingActions';

import ScanHUAndGetQtyComponent from '../../../components/ScanHUAndGetQtyComponent';

const PickStepScanScreen = () => {
  const {
    params: { workflowId: wfProcessId, activityId, lineId, stepId, altStepId },
  } = useRouteMatch();

  const { eligibleBarcode, qtyToPick, uom, qtyRejectedReasons } = useSelector((state) => {
    const activity = getActivityById(state, wfProcessId, activityId);
    const qtyRejectedReasons = getQtyRejectedReasonsFromActivity(activity);

    const stepProps = getStepById(state, wfProcessId, activityId, lineId, stepId);
    const eligibleBarcode = getPickFrom({ stepProps, altStepId }).huBarcode;
    const qtyToPick = getQtyToPick({ stepProps, altStepId });

    return {
      eligibleBarcode,
      qtyToPick,
      uom: stepProps.uom,
      qtyRejectedReasons,
    };
  }, shallowEqual);

  const history = useHistory();
  const dispatch = useDispatch();
  const onResult = ({ qty = 0, reason = null, scannedBarcode = null }) => {
    const qtyRejected = qtyToPick - qty;

    postStepPicked({
      wfProcessId,
      activityId,
      stepId,
      huBarcode: scannedBarcode,
      qtyPicked: qty,
      qtyRejectedReasonCode: reason,
      qtyRejected,
    })
      .then(() => {
        dispatch(
          updatePickingStepQty({
            wfProcessId,
            activityId,
            lineId,
            stepId,
            altStepId,
            qtyPicked: qty,
            qtyRejected,
            qtyRejectedReasonCode: reason,
          })
        );
        history.go(-2); // go to picking line screen
      })
      .catch((axiosError) => toastError({ axiosError }));
  };

  return (
    <ScanHUAndGetQtyComponent
      eligibleBarcode={eligibleBarcode}
      qtyCaption={counterpart.translate('general.QtyToPick')}
      qtyTarget={qtyToPick}
      qtyInitial={qtyToPick}
      uom={uom}
      qtyRejectedReasons={qtyRejectedReasons}
      //
      onResult={onResult}
    />
  );
};

export default PickStepScanScreen;
