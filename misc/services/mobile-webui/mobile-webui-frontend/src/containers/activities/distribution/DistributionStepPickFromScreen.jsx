import React, { useEffect } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';

import { trl } from '../../../utils/translations';
import { toastError } from '../../../utils/toast';
import { getActivityById, getQtyRejectedReasonsFromActivity, getStepByIdFromActivity, } from '../../../reducers/wfProcesses';
import { postDistributionPickFrom } from '../../../api/distribution';
import { updateDistributionPickFrom } from '../../../actions/DistributionActions';
import { pushHeaderEntry } from '../../../actions/HeaderActions';

import ScanHUAndGetQtyComponent from '../../../components/ScanHUAndGetQtyComponent';
import { toQRCodeString } from '../../../utils/qrCode/hu';

const DistributionStepPickFromScreen = () => {
  const {
    url,
    params: { workflowId: wfProcessId, activityId, lineId, stepId },
  } = useRouteMatch();

  const { huQRCode, qtyToMove } = useSelector((state) =>
    getPropsFromState({ state, wfProcessId, activityId, lineId, stepId })
  );

  const history = useHistory();
  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(
      pushHeaderEntry({
        location: url,
        caption: trl('activities.distribution.scanHU'),
      })
    );
  }, []);

  const onResult = ({ scannedBarcode }) => {
    postDistributionPickFrom({
      wfProcessId,
      activityId,
      stepId,
      pickFrom: {
        qrCode: toQRCodeString(scannedBarcode),
      },
    })
      .then(() => {
        dispatch(
          updateDistributionPickFrom({
            wfProcessId,
            activityId,
            lineId,
            stepId,
            qtyPicked: qtyToMove,
            qtyRejectedReasonCode: null,
          })
        );

        history.go(-2);
      })
      .catch((axiosError) => toastError({ axiosError }));
  };

  return (
    <ScanHUAndGetQtyComponent
      eligibleBarcode={toQRCodeString(huQRCode)}
      qtyTargetCaption={trl('general.QtyToMove')}
      onResult={onResult}
    />
  );
};

const getPropsFromState = ({ state, wfProcessId, activityId, lineId, stepId }) => {
  const activity = getActivityById(state, wfProcessId, activityId);
  const step = getStepByIdFromActivity(activity, lineId, stepId);

  return {
    huQRCode: step.pickFromHU.qrCode,
    qtyToMove: step.qtyToMove,
    uom: step.uom,
    qtyRejectedReasons: getQtyRejectedReasonsFromActivity(activity),
  };
};

export default DistributionStepPickFromScreen;
