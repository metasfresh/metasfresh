import React from 'react';
import PropTypes from 'prop-types';
import { useHistory } from 'react-router-dom';
import { useDispatch } from 'react-redux';

import * as CompleteStatus from '../../constants/CompleteStatus';
import { toastError } from '../../utils/toast';
import { continueWorkflowRequest, startWorkflowRequest } from '../../api/launchers';
import { updateWFProcess } from '../../actions/WorkflowActions';
import { getWFProcessScreenLocation } from '../../routes/workflow_locations';

import ButtonWithIndicator from '../../components/buttons/ButtonWithIndicator';

const WFLauncherButton = ({ applicationId, startedWFProcessId, wfParameters, caption, showWarningSign }) => {
  const dispatch = useDispatch();
  const history = useHistory();
  const handleClick = () => {
    const wfProcessPromise = startedWFProcessId
      ? continueWorkflowRequest(startedWFProcessId)
      : startWorkflowRequest({ wfParameters });

    wfProcessPromise
      .then((wfProcess) => {
        dispatch(updateWFProcess({ wfProcess }));
        history.push(getWFProcessScreenLocation({ applicationId, wfProcessId: wfProcess.id }));
      })
      .catch((axiosError) => toastError({ axiosError }));
  };

  return (
    <ButtonWithIndicator
      caption={caption}
      showWarningSign={showWarningSign}
      completeStatus={startedWFProcessId ? CompleteStatus.IN_PROGRESS : CompleteStatus.NOT_STARTED}
      disabled={false}
      onClick={handleClick}
    />
  );
};

WFLauncherButton.propTypes = {
  applicationId: PropTypes.string.isRequired,
  startedWFProcessId: PropTypes.string,
  wfParameters: PropTypes.object,
  caption: PropTypes.string.isRequired,
  showWarningSign: PropTypes.bool,
};

export default WFLauncherButton;
