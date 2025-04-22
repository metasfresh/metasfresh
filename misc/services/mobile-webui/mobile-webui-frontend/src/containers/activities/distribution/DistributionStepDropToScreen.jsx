import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { postDistributionDropTo } from '../../../api/distribution';
import { getStepById } from '../../../reducers/wfProcesses';

import ScanHUAndGetQtyComponent from '../../../components/ScanHUAndGetQtyComponent';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { distributionStepScreenLocation } from '../../../routes/distribution';

const DistributionStepDropToScreen = () => {
  const { history, wfProcessId, activityId, lineId, stepId } = useScreenDefinition({
    screenId: 'DistributionStepDropToScreen',
    back: distributionStepScreenLocation,
  });

  const { locatorQRCode } = useSelector((state) =>
    getPropsFromState({ state, wfProcessId, activityId, lineId, stepId })
  );

  const dispatch = useDispatch();

  const onResult = () => {
    return postDistributionDropTo({
      wfProcessId,
      activityId,
      stepId,
    }).then((wfProcess) => {
      dispatch(updateWFProcess({ wfProcess }));
      history.goBack();
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
