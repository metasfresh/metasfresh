import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import counterpart from 'counterpart';

import { postDistributionDropTo } from '../../../api/distribution';
import { updateDistributionDropTo } from '../../../actions/DistributionActions';
import { toastError } from '../../../utils/toast';
import ScanHUAndGetQtyComponent from '../../../components/ScanHUAndGetQtyComponent';
import { getStepById } from '../../../reducers/wfProcesses_status';
import { pushHeaderEntry } from '../../../actions/HeaderActions';
import { useHistory, useRouteMatch } from 'react-router-dom';

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
            caption: counterpart.translate('general.DropToLocator'),
            value: `${caption}( ${locatorBarcode} )`,
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
