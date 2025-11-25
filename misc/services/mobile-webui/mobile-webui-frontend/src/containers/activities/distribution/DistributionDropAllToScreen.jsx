import React from 'react';
import { useDispatch } from 'react-redux';
import { postDistributionDropTo } from '../../../api/distribution';

import ScanHUAndGetQtyComponent from '../../../components/ScanHUAndGetQtyComponent';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { distributionJobScreenLocation } from '../../../routes/distribution';

const DistributionDropAllToScreen = () => {
  const { history, wfProcessId, activityId } = useScreenDefinition({
    screenId: 'DistributionDropAllToScreen',
    back: distributionJobScreenLocation,
  });

  const dispatch = useDispatch();

  const onResult = ({ scannedBarcode }) => {
    return postDistributionDropTo({
      wfProcessId,
      activityId,
      dropToLocatorQRCode: scannedBarcode,
    }).then((wfProcess) => {
      dispatch(updateWFProcess({ wfProcess }));
      history.goBack();
    });
  };

  return (
    <ScanHUAndGetQtyComponent
      invalidBarcodeMessageKey={'activities.distribution.invalidLocatorQRCode'}
      onResult={onResult}
    />
  );
};

export default DistributionDropAllToScreen;
