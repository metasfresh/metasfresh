import React, { useEffect } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';

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

  const { locatorQRCode } = useSelector((state) =>
    getPropsFromState({ state, wfProcessId, activityId, lineId, stepId })
  );

  const history = useHistory();
  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(
      pushHeaderEntry({
        location: url,
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
      eligibleBarcode={locatorQRCode}
      invalidBarcodeMessageKey={'activities.distribution.invalidLocatorQRCode'}
      onResult={onResult}
    />
  );
};

const getPropsFromState = ({ state, wfProcessId, activityId, lineId, stepId }) => {
  const step = getStepById(state, wfProcessId, activityId, lineId, stepId);

  return {
    locatorQRCode: step.dropToLocator.qrCode,
  };
};

export default DistributionStepDropToScreen;
