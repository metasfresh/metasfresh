import React from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';
import { useHistory } from 'react-router-dom';

import ButtonWithIndicator from '../../../components/ButtonWithIndicator';

const ScanActivity = (props) => {
  const history = useHistory();
  const { activityState } = props;

  const isUserEditable = activityState.dataStored.isUserEditable;

  const scannedBarcode = activityState.dataStored.scannedBarcode;
  const scannedBarcodeCaption = activityState.componentProps.barcodeCaption;

  const activityCompleteStatus = activityState.dataStored.completeStatus;
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
      <button className="button is-outlined complete-btn" disabled={!isUserEditable} onClick={handleClick}>
        <ButtonWithIndicator caption={scanButtonCaption} completeStatus={activityCompleteStatus} />
      </button>
    </div>
  );
};

ScanActivity.propTypes = {
  wfProcessId: PropTypes.string.isRequired,
  activityState: PropTypes.object.isRequired,
};

export default ScanActivity;
