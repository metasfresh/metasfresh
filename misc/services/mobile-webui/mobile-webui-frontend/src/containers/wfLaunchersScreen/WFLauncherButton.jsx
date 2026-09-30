import React from 'react';
import PropTypes from 'prop-types';
import { useDispatch } from 'react-redux';
import { toastError } from '../../utils/toast';
import { continueWorkflowRequest, startWorkflowRequest } from '../../api/launchers';
import { updateWFProcess } from '../../actions/WorkflowActions';
import { getWFProcessScreenLocation } from '../../routes/workflow_locations';

import ButtonWithIndicator from '../../components/buttons/ButtonWithIndicator';
import { useMobileNavigation } from '../../hooks/useMobileNavigation';
import { WorkflowLauncherIndicator } from '../../constants/WorkflowLauncherIndicator';

const TEST_PROPS = [
  'salesOrderId',
  'qtyToDeliver',
  'productId',
  'customerId',
  'customerLocationId',
  'bpartnerLocationId',
  'inventoryId',
];

const WFLauncherButton = ({
  applicationId,
  startedWFProcessId,
  wfParameters,
  caption,
  showWarningSign,
  indicator,
  testId,
  disabled,
}) => {
  const dispatch = useDispatch();
  const history = useMobileNavigation();
  const handleClick = () => {
    const wfProcessPromise = startedWFProcessId
      ? continueWorkflowRequest(startedWFProcessId)
      : startWorkflowRequest({ wfParameters });

    wfProcessPromise
      .then((wfProcess) => {
        // NOTE: the store update and the navigation below are NOT batched on React 17 /
        // connected-react-router 6.9 / react-redux 7.2, so ApplicationLayout can mount on the job
        // route one render pass before this dispatched process is observable in the store. The
        // redirect-home guard in ApplicationLayout tolerates that ordering (it defers goHome one
        // tick); if this navigation is ever reworked to a root-cause fix (guarantee the store update
        // is observable before navigating), that guard's deferral can be revisited.
        dispatch(updateWFProcess({ wfProcess, parent: null }));
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
      indicator1={indicator ?? '-'}
      indicator2={startedWFProcessId ? WorkflowLauncherIndicator.JOB_ALREADY_STARTED : '-'}
      disabled={disabled}
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
  indicator: PropTypes.string,
  testId: PropTypes.string,
  disabled: PropTypes.bool,
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
