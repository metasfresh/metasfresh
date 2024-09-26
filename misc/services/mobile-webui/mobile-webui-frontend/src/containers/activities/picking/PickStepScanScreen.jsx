import React, { useCallback, useEffect } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { shallowEqual, useDispatch, useSelector } from 'react-redux';

import { trl } from '../../../utils/translations';
import { pushHeaderEntry } from '../../../actions/HeaderActions';
import {
  getActivityById,
  getLineById,
  getQtyRejectedReasonsFromActivity,
  getStepById,
} from '../../../reducers/wfProcesses';
import { toastError } from '../../../utils/toast';
import { getPickFromForStep, getQtyToPickForStep } from '../../../utils/picking';
import { postStepPicked } from '../../../api/picking';

import ScanHUAndGetQtyComponent from '../../../components/ScanHUAndGetQtyComponent';
import { toQRCodeString } from '../../../utils/qrCode/hu';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { toNumberOrZero } from '../../../utils/numberUtils';

const PickStepScanScreen = () => {
  const {
    url,
    params: { workflowId: wfProcessId, activityId, lineId, stepId, altStepId },
  } = useRouteMatch();

  const { eligibleQRCode, qtyToPick, uom, qtyRejectedReasons, qtyRemainingToPick, isShowPromptWhenOverPicking } =
    useSelector(
      (state) => getPropsFromState({ state, wfProcessId, activityId, lineId, stepId, altStepId }),
      shallowEqual
    );

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
  useEffect(() => {
    dispatch(
      pushHeaderEntry({
        location: url,
        caption: trl('activities.picking.scanQRCode'),
        values: [],
      })
    );
  }, []);

  const history = useHistory();
  const onResult = ({ qty = 0, reason = null, scannedBarcode = null }) => {
    const qtyRejected = qtyToPick - qty;

    postStepPicked({
      wfProcessId,
      activityId,
      lineId,
      stepId,
      huQRCode: scannedBarcode,
      qtyPicked: qty,
      qtyRejectedReasonCode: reason,
      qtyRejected,
    })
      .then((wfProcess) => dispatch(updateWFProcess({ wfProcess })))
      .then(() => {
        history.go(-2); // go to picking line screen
      })
      .catch((axiosError) => toastError({ axiosError }));
  };

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
