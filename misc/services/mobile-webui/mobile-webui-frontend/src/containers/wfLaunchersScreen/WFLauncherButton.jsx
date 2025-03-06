import React from 'react';
import PropTypes from 'prop-types';
import { useDispatch } from 'react-redux';

import * as CompleteStatus from '../../constants/CompleteStatus';
import { toastError } from '../../utils/toast';
import { continueWorkflowRequest, startWorkflowRequest } from '../../api/launchers';
import { updateWFProcess } from '../../actions/WorkflowActions';
import { getWFProcessScreenLocation } from '../../routes/workflow_locations';

import ButtonWithIndicator from '../../components/buttons/ButtonWithIndicator';
import { useMobileNavigation } from '../../hooks/useMobileNavigation';

const TEST_PROPS = ['qtyToDeliver'];

const WFLauncherButton = ({ applicationId, startedWFProcessId, wfParameters, caption, showWarningSign, testId }) => {
  const dispatch = useDispatch();
  const history = useMobileNavigation();
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
      testId={testId}
      {...toTestProps(wfParameters)}
      additionalCssClass="wflauncher-button"
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
  testId: PropTypes.string,
};

export default WFLauncherButton;

//
//
// ------------
//
//
//

const toTestProps = (wfParameters) => {
  if (!wfParameters) {
    return {};
  }

  return Object.keys(wfParameters)
    .filter((key) => TEST_PROPS.includes(key))
    .reduce((acc, key) => {
      const value = wfParameters[key];
      if (value != null) {
        acc['data-' + key.toLowerCase()] = value;
      }
      return acc;
    }, {});
};
