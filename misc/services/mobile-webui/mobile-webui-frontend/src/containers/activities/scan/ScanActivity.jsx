import React from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';
import { useHistory } from 'react-router-dom';

import ButtonWithIndicator from '../../../components/ButtonWithIndicator';

const ScanActivity = (props) => {
  const history = useHistory();
  const { activityState } = props;

  const scannedBarcode = activityState.dataStored.scannedBarcode;
  const scannedBarcodeCaption = activityState.componentProps.barcodeCaption;

  const scanButtonStatus = activityState.dataStored.isComplete ? 'complete' : 'incomplete';
  const scanButtonCaption =
    scannedBarcodeCaption ||
    scannedBarcode ||
    activityState.caption ||
    counterpart.translate('activities.scanBarcode.defaultCaption');

  const handleClick = () => {
    const { wfProcessId } = props;
    const { activityId } = activityState;

    console.log('history:', history);
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
