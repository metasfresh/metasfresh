import React, { useEffect } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import counterpart from 'counterpart';

import { toastError } from '../../../utils/toast';
import {
  getActivityById,
  getQtyRejectedReasonsFromActivity,
  getStepByIdFromActivity,
} from '../../../reducers/wfProcesses_status';
import { postDistributionPickFrom } from '../../../api/distribution';
import { updateDistributionPickFrom } from '../../../actions/DistributionActions';
import { pushHeaderEntry } from '../../../actions/HeaderActions';

import ScanHUAndGetQtyComponent from '../../../components/ScanHUAndGetQtyComponent';

const DistributionStepPickFromScreen = () => {
  const {
    url,
    params: { workflowId: wfProcessId, activityId, lineId, stepId },
  } = useRouteMatch();

  const { huBarcode, qtyToMove, uom, qtyRejectedReasons } = useSelector((state) =>
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
            caption: counterpart.translate('activities.distribution.scanHU'),
            value: huBarcode,
          },
          {
            caption: counterpart.translate('general.QtyToMove'),
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
      eligibleBarcode={huBarcode}
      qtyCaption={counterpart.translate('general.QtyToMove')}
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
    huBarcode: step.pickFromHU.barcode,
    qtyToMove: step.qtyToMove,
    uom: step.uom,
    qtyRejectedReasons: getQtyRejectedReasonsFromActivity(activity),
  };
};

export default DistributionStepPickFromScreen;
