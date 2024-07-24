import React, { useState } from 'react';
import PropTypes from 'prop-types';
import { useHistory } from 'react-router-dom';

import { trl } from '../../../utils/translations';
import { scanBarcodeLocation } from '../../../routes/scan';

import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import YesNoDialog from '../../../components/dialogs/YesNoDialog';

export const COMPONENTTYPE_ScanBarcode = 'common/scanBarcode';

const ScanActivity = (props) => {
  const history = useHistory();
  const [showConfirmationDialog, setShowConfirmationDialog] = useState(false);
  const { applicationId, wfProcessId, activityState } = props;

  const scanButtonCaption = computeButtonCaption(activityState);
  const isUserEditable = activityState.dataStored.isUserEditable;
  const activityCompleteStatus = activityState.dataStored.completeStatus;
  const confirmationModalMsg = activityState.dataStored.confirmationModalMsg;
  const { activityId } = activityState;

  const handleClick = () => {
    const currentValue = activityState.dataStored.currentValue;
    if (confirmationModalMsg && currentValue) {
      setShowConfirmationDialog(true);
      return;
    }

    history.push(scanBarcodeLocation({ applicationId, wfProcessId, activityId }));
  };

  const handleModalConfirmation = (resend) => {
    if (resend) {
      history.push(`${scanBarcodeLocation({ applicationId, wfProcessId, activityId })}?resendQr=true`);
    } else {
      history.push(scanBarcodeLocation({ applicationId, wfProcessId, activityId }));
    }
    setShowConfirmationDialog(false);
  };

  return (
    <>
      {showConfirmationDialog && (
        <YesNoDialog
          promptQuestion={confirmationModalMsg}
          onYes={() => handleModalConfirmation(true)}
          onNo={() => handleModalConfirmation(false)}
        />
      )}
      <ButtonWithIndicator
        caption={scanButtonCaption}
        completeStatus={activityCompleteStatus}
        disabled={!isUserEditable}
        onClick={handleClick}
      />
    </>
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
