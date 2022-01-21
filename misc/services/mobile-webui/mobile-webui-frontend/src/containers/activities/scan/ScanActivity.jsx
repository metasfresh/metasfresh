import React from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';
import { useHistory } from 'react-router-dom';

import ButtonWithIndicator from '../../../components/ButtonWithIndicator';
import { scanBarcodeLocation } from '../../../routes/scan';

const ScanActivity = (props) => {
  const history = useHistory();
  const { applicationId, wfProcessId, activityState } = props;

  const scanButtonCaption = computeButtonCaption(activityState);
  const isUserEditable = activityState.dataStored.isUserEditable;
  const activityCompleteStatus = activityState.dataStored.completeStatus;

  const handleClick = () => {
    const { activityId } = activityState;

    history.push(scanBarcodeLocation({ applicationId, wfProcessId, activityId }));
  };

  return (
    <ButtonWithIndicator
      caption={scanButtonCaption}
      completeStatus={activityCompleteStatus}
      disabled={!isUserEditable}
      onClick={handleClick}
    />
  );
};

const computeButtonCaption = (activityState) => {
  const scannedBarcode = activityState.dataStored.scannedBarcode;
  const scannedBarcodeCaption = activityState.componentProps.barcodeCaption;

  return (
    scannedBarcodeCaption ||
    scannedBarcode ||
    activityState.caption ||
    counterpart.translate('activities.scanBarcode.defaultCaption')
  );
};

ScanActivity.propTypes = {
  applicationId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activityState: PropTypes.object.isRequired,
};

export default ScanActivity;
