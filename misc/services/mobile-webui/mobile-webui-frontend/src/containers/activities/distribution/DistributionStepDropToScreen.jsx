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
import { toQRCodeDisplayable, toQRCodeString } from '../../../utils/huQRCodes';

const DistributionStepDropToScreen = () => {
  const {
    url,
    params: { workflowId: wfProcessId, activityId, lineId, stepId },
  } = useRouteMatch();

  const { caption, qtyToMove, locatorQRCode } = useSelector((state) =>
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
            value: `${caption} (${toQRCodeDisplayable(locatorQRCode)})`,
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
      eligibleBarcode={toQRCodeString(locatorQRCode)}
      invalidBarcodeMessageKey={'activities.distribution.invalidLocatorQRCode'}
      onResult={onResult}
    />
  );
};

const getPropsFromState = ({ state, wfProcessId, activityId, lineId, stepId }) => {
  const step = getStepById(state, wfProcessId, activityId, lineId, stepId);

  return {
    caption: step.dropToLocator.caption,
    qtyToMove: step.qtyToMove,
    locatorQRCode: step.pickFromHU.qrCode,
  };
};

export default DistributionStepDropToScreen;
