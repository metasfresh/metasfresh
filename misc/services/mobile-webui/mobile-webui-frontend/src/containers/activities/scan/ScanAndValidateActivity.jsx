import React from 'react';
import PropTypes from 'prop-types';
import { useHistory } from 'react-router-dom';
import { scanAndValidateBarcodeLocation } from '../../../routes/scan';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';

export const COMPONENTTYPE_ScanAndValidateBarcode = 'common/scanAndValidateBarcode';

const ScanAndValidateActivity = (props) => {
  const history = useHistory();
  const { applicationId, wfProcessId, activityState } = props;

  const isUserEditable = activityState.dataStored.isUserEditable;
  const activityCompleteStatus = activityState.dataStored.completeStatus;

  const handleClick = (validOptionIndex) => {
    const { activityId } = activityState;

    history.push(`${scanAndValidateBarcodeLocation({ applicationId, wfProcessId, activityId, validOptionIndex })}`);
  };

  if (!activityState.dataStored.validOptions || !activityState.dataStored.validOptions.length) {
    return null;
  }

  return (
    <>
      {activityState.dataStored.validOptions.map((option, index) => (
        <ButtonWithIndicator
          key={option.qrCode}
          caption={option.caption}
          completeStatus={activityCompleteStatus}
          disabled={!isUserEditable}
          onClick={() => handleClick(index)}
        />
      ))}
    </>
  );
};

ScanAndValidateActivity.propTypes = {
  applicationId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activityState: PropTypes.object.isRequired,
};

export default ScanAndValidateActivity;
