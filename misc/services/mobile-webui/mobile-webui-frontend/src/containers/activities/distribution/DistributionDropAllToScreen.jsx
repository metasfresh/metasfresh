import React from 'react';
import { useDispatch } from 'react-redux';

import ScanHUAndGetQtyComponent from '../../../components/ScanHUAndGetQtyComponent';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { distributionJobScreenLocation } from '../../../routes/distribution';
import { postDistributionDropToThunk } from '../../../apps/distribution/redux/postDistributionDropToThunk';

const DistributionDropAllToScreen = () => {
  const { history, wfProcessId, activityId } = useScreenDefinition({
    screenId: 'DistributionDropAllToScreen',
    back: distributionJobScreenLocation,
  });

  const dispatch = useDispatch();

  const onResult = ({ scannedBarcode }) => {
    return dispatch(
      postDistributionDropToThunk({
        history,
        wfProcessId,
        activityId,
        dropToLocatorQRCode: scannedBarcode,
      })
    ).then(({ isDistributionJobCompleted }) => !isDistributionJobCompleted && history.goBack());
  };

  return (
    <ScanHUAndGetQtyComponent
      invalidBarcodeMessageKey={'activities.distribution.invalidLocatorQRCode'}
      onResult={onResult}
    />
  );
};

export default DistributionDropAllToScreen;
