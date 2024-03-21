import React from 'react';
import PropTypes from 'prop-types';
import { useHistory } from 'react-router-dom';

import { trl } from '../../../utils/translations';
import { scanBarcodeLocation } from '../../../routes/scan';

import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';

export const COMPONENTTYPE_ScanBarcode = 'common/scanBarcode';

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
  const currentValue = activityState.dataStored.currentValue;
  if (currentValue && currentValue.caption) {
    return currentValue.caption;
  }

  return activityState.caption || trl('activities.scanBarcode.defaultCaption');
};

ScanActivity.propTypes = {
  applicationId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activityState: PropTypes.object.isRequired,
};

export default ScanActivity;
