import React from 'react';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { distributionJobsListScreenLocation } from '../../../routes/distribution';
import { postDropAll } from '../../../api/distribution';
import { toastErrorFromObj } from '../../../utils/toast';
import { trl } from '../../../utils/translations';

const DistributionJobsDropAllScreen = () => {
  const { history } = useScreenDefinition({
    screenId: 'DistributionJobsDropAllScreen',
    captionKey: 'activities.distribution.scanDropToLocator',
    back: distributionJobsListScreenLocation(),
  });

  const onBarcodeScanned = ({ scannedBarcode }) => {
    return postDropAll({ dropToQRCode: scannedBarcode })
      .then(() => history.goBack())
      .catch(toastErrorFromObj);
  };

  return (
    <BarcodeScannerComponent
      onResolvedResult={onBarcodeScanned}
      inputPlaceholderText={trl('activities.distribution.scanDropToLocator')}
    />
  );
};

export default DistributionJobsDropAllScreen;
