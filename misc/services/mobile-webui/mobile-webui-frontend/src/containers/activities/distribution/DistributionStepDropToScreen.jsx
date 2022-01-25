import React, { useEffect } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';

import { trl } from '../../../utils/translations';
import { toastError } from '../../../utils/toast';
import { postDistributionDropTo } from '../../../api/distribution';
import { updateDistributionDropTo } from '../../../actions/DistributionActions';
import { getStepById } from '../../../reducers/wfProcesses';
import { pushHeaderEntry } from '../../../actions/HeaderActions';

import ScanHUAndGetQtyComponent from '../../../components/ScanHUAndGetQtyComponent';

const DistributionStepDropToScreen = () => {
  const {
    url,
    params: { workflowId: wfProcessId, activityId, lineId, stepId },
  } = useRouteMatch();

  const { caption, qtyToMove, locatorBarcode } = useSelector((state) =>
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
            caption: trl('general.DropToLocator'),
            value: `${caption}( ${locatorBarcode} )`,
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
    postDistributionDropTo({
      wfProcessId,
      activityId,
      stepId,
    })
      .then(() => {
        dispatch(
          updateDistributionDropTo({
            wfProcessId,
            activityId,
            lineId,
            stepId,
            qtyPicked: qty,
            qtyRejectedReasonCode: reason,
            droppedToLocator: true,
          })
        );

        history.go(-1);
      })
      .catch((axiosError) => toastError({ axiosError }));
  };

  return (
    <ScanHUAndGetQtyComponent
      eligibleBarcode={locatorBarcode}
      invalidBarcodeMessageKey={'activities.distribution.invalidLocatorBarcode'}
      onResult={onResult}
    />
  );
};

const getPropsFromState = ({ state, wfProcessId, activityId, lineId, stepId }) => {
  const step = getStepById(state, wfProcessId, activityId, lineId, stepId);

  return {
    caption: step.dropToLocator.caption,
    qtyToMove: step.qtyToMove,
    locatorBarcode: step.pickFromHU.barcode,
  };
};

export default DistributionStepDropToScreen;
