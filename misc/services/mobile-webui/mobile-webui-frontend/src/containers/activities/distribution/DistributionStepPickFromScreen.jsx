import React, { useEffect } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';

import { trl } from '../../../utils/translations';
import { toastError } from '../../../utils/toast';
import {
  getActivityById,
  getQtyRejectedReasonsFromActivity,
  getStepByIdFromActivity,
} from '../../../reducers/wfProcesses';
import { postDistributionPickFrom } from '../../../api/distribution';
import { updateDistributionPickFrom } from '../../../actions/DistributionActions';
import { pushHeaderEntry } from '../../../actions/HeaderActions';

import ScanHUAndGetQtyComponent from '../../../components/ScanHUAndGetQtyComponent';
import { toQRCodeDisplayable, toQRCodeString } from '../../../utils/huQRCodes';

const DistributionStepPickFromScreen = () => {
  const {
    url,
    params: { workflowId: wfProcessId, activityId, lineId, stepId },
  } = useRouteMatch();

  const { huQRCode, qtyToMove, uom, qtyRejectedReasons } = useSelector((state) =>
    getPropsFromState({ state, wfProcessId, activityId, lineId, stepId })
  );

  const history = useHistory();
  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(
      pushHeaderEntry({
        location: url,
        values: [
          {
            // eslint-disable-next-line no-undef
            caption: trl('activities.distribution.scanHU'),
            value: toQRCodeDisplayable(huQRCode),
          },
          {
            caption: trl('general.QtyToMove'),
            value: qtyToMove,
          },
        ],
      })
    );
  }, []);

  const onResult = ({ qty = 0, reason = null }) => {
    postDistributionPickFrom({
      wfProcessId,
      activityId,
      stepId,
      pickFrom: {
        qtyPicked: qty,
        qtyRejectedReasonCode: reason,
      },
    })
      .then(() => {
        dispatch(
          updateDistributionPickFrom({
            wfProcessId,
            activityId,
            lineId,
            stepId,
            qtyPicked: qty,
            qtyRejectedReasonCode: reason,
          })
        );

        history.go(-2);
      })
      .catch((axiosError) => toastError({ axiosError }));
  };

  return (
    <ScanHUAndGetQtyComponent
      eligibleBarcode={toQRCodeString(huQRCode)}
      qtyCaption={trl('general.QtyToMove')}
      qtyInitial={qtyToMove}
      qtyTarget={qtyToMove}
      uom={uom}
      qtyRejectedReasons={qtyRejectedReasons}
      invalidQtyMessageKey={'activities.distribution.invalidQtyToMove'}
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
