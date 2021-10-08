import React from 'react';
import PropTypes from 'prop-types';
import { useHistory } from 'react-router-dom';

import ButtonWithIndicator from './ButtonWithIndicator';

const ScanActivity = (props) => {
  const history = useHistory();
  const { activityItem, activityState } = props;

  const scannedBarcode = activityState && activityState.dataStored ? activityState.dataStored.scannedBarcode : null;
  const scannedBarcodeCaption = activityItem.componentProps.barcodeCaption;

  const scanButtonStatus = scannedBarcode || scannedBarcodeCaption ? 'complete' : 'incomplete';
  const scanButtonCaption = scannedBarcodeCaption || scannedBarcode || activityItem.caption || 'Scan';

  const handleClick = () => {
    const { wfProcessId } = props;
    const { activityId } = activityItem;

    history.push(`/workflow/${wfProcessId}/activityId/${activityId}/scanner`);
  };

  return (
    <div className="mt-0">
      <button className="button is-outlined complete-btn" onClick={handleClick}>
        <ButtonWithIndicator caption={scanButtonCaption} indicatorType={scanButtonStatus} />
      </button>
    </div>
  );
};

ScanActivity.propTypes = {
  caption: PropTypes.string,
  scanButtonStatus: PropTypes.string,
  activityItem: PropTypes.object,
  activityState: PropTypes.object,
  wfProcessId: PropTypes.string.isRequired,
};

export default ScanActivity;
