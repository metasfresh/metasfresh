import React from 'react';
import PropTypes from 'prop-types';
import { useHistory } from 'react-router-dom';

import { trl } from '../../utils/translations';
import { abortWorkflowRequest } from '../../api/launchers';
import { appLaunchersLocation } from '../../routes/launchers';

import ConfirmButton from '../../components/buttons/ConfirmButton';

const AbortButton = ({ applicationId, wfProcessId }) => {
  const history = useHistory();
  const onUserConfirmed = () => {
    abortWorkflowRequest(wfProcessId).then(() => history.push(appLaunchersLocation({ applicationId })));
  };

  return (
    <div className="mt-5">
      <ConfirmButton
        caption={trl('activities.confirmButton.abort')}
        isDangerousAction={true}
        isUserEditable={true}
        onUserConfirmed={onUserConfirmed}
      />
    </div>
  );
};

AbortButton.propTypes = {
  applicationId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
};

export default AbortButton;
