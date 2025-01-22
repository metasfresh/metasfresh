import React, { useEffect } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { postDistributionDropTo } from '../../../api/distribution';
import { getStepById } from '../../../reducers/wfProcesses';
import { pushHeaderEntry } from '../../../actions/HeaderActions';

import ScanHUAndGetQtyComponent from '../../../components/ScanHUAndGetQtyComponent';
import { updateWFProcess } from '../../../actions/WorkflowActions';

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

  const onResult = () => {
    return postDistributionDropTo({
      wfProcessId,
      activityId,
      stepId,
    }).then((wfProcess) => {
      dispatch(updateWFProcess({ wfProcess }));
      history.go(-1);
    });
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
