import React from 'react';
import PropTypes from 'prop-types';
import { useHistory } from 'react-router-dom';

import ButtonWithIndicator from './ButtonWithIndicator';

const ScanActivity = (props) => {
  const history = useHistory();
  const { activityState } = props;
  console.log('ScanActivity.render: activityState=%o', activityState);

  const scannedBarcode = activityState.dataStored.scannedBarcode;
  const scannedBarcodeCaption = activityState.componentProps.barcodeCaption;

  const scanButtonStatus = activityState.dataStored.isComplete ? 'complete' : 'incomplete';
  const scanButtonCaption = scannedBarcodeCaption || scannedBarcode || activityState.caption || 'Scan';

  const handleClick = () => {
    const { wfProcessId } = props;
    const { activityId } = activityState;

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
  wfProcessId: PropTypes.string.isRequired,
  activityState: PropTypes.object.isRequired,
};

export default ScanActivity;
